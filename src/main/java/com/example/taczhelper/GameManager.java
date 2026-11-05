package com.example.taczhelper;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = TaczHelper.MODID)
public final class GameManager {
    private static int countdownTicks = -1;
    private static BlockPos center = BlockPos.ZERO;
    private static final List<BlockPos> placedChests = new ArrayList<>();

    // バス降下: 1=バスに乗っている 2=降下中
    private static final Map<UUID, Integer> PHASE = new HashMap<>();
    private static BattleBusEntity bus = null;
    private static double busVx = 0, busVz = 0;
    private static int autoGlide = 70;

    /** origin = 試合の中心（開始ブロックの位置 or コマンド実行者の位置） */
    public static void start(MinecraftServer server, BlockPos origin) {
        center = origin;
        int cd = GameData.get(server).value(Setting.COUNTDOWN);
        if (cd <= 0) {
            begin(server);
        } else {
            countdownTicks = cd * 20;
            broadcast(server, "§e" + cd + "秒後にゲーム開始！");
        }
    }

    public static void stop(MinecraftServer server) {
        countdownTicks = -1;
        if (bus != null && bus.isAlive()) bus.discard();
        bus = null;
        for (UUID id : new ArrayList<>(PHASE.keySet())) {
            ServerPlayer p = server.getPlayerList().getPlayer(id);
            if (p != null) removeGlider(p);
        }
        PHASE.clear();
        clearChests(server.overworld());
        broadcast(server, "§cゲームを終了しました");
    }

    @SubscribeEvent
    public static void onTick(TickEvent.ServerTickEvent e) {
        if (e.phase != TickEvent.Phase.END) return;
        MinecraftServer server = e.getServer();
        if (!PHASE.isEmpty()) tickDrop(server);
        if (countdownTicks < 0) return;
        if (countdownTicks == 0) {
            countdownTicks = -1;
            begin(server);
            return;
        }
        if (countdownTicks % 20 == 0 && countdownTicks / 20 <= 5) {
            broadcast(server, "§e" + (countdownTicks / 20));
        }
        countdownTicks--;
    }

    private static void begin(MinecraftServer server) {
        GameData d = GameData.get(server);
        ServerLevel level = server.overworld();
        RandomSource rnd = level.getRandom();
        clearChests(level);
        if (bus != null && bus.isAlive()) bus.discard();
        bus = null;
        PHASE.clear();

        String kit = Kits.NAMES.get(d.value(Setting.KIT));
        boolean useBus = d.value(Setting.BUS) == 1;
        int height = d.value(Setting.SPAWN_HEIGHT);
        int spread = d.value(Setting.SPREAD);
        int mapR = d.value(Setting.MAP_RADIUS);
        int slow = d.value(Setting.SLOW_FALL);

        List<BlockPos> spawns = new ArrayList<>(d.spawnPoints);
        Collections.shuffle(spawns);
        int idx = 0;
        List<ServerPlayer> riders = new ArrayList<>();

        for (ServerPlayer p : server.getPlayerList().getPlayers()) {
            if (p.isSpectator()) continue;

            p.setGameMode(GameType.SURVIVAL);
            p.getInventory().clearContent();
            p.removeAllEffects();
            p.setHealth(p.getMaxHealth());
            p.getFoodData().setFoodLevel(20);
            p.getFoodData().setSaturation(20.0F);
            Kits.give(p, kit);

            if (useBus) {
                riders.add(p);
                continue;
            }

            BlockPos base;
            int radius;
            if (spawns.isEmpty()) {
                base = center;
                radius = mapR;
            } else {
                base = spawns.get(idx++ % spawns.size());
                radius = spread;
            }
            double ang = rnd.nextDouble() * Math.PI * 2;
            double dist = Math.sqrt(rnd.nextDouble()) * radius;
            double x = base.getX() + 0.5 + Math.cos(ang) * dist;
            double z = base.getZ() + 0.5 + Math.sin(ang) * dist;
            double y;
            if (height > 0) y = height;
            else if (!spawns.isEmpty()) y = base.getY() + 1;
            else y = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, BlockPos.containing(x, 0, z)).getY();

            p.teleportTo(level, x, y, z, p.getYRot(), 0.0F);
            p.fallDistance = 0;
            if (slow > 0) {
                p.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, slow * 20, 0, false, true));
            }
        }

        if (useBus) launchBus(server, level, d, riders);

        // ルートブロックの真上にチェストを出す
        int chance = d.value(Setting.LOOT_CHANCE);
        for (BlockPos m : new ArrayList<>(d.lootPoints)) {
            if (rnd.nextInt(100) < chance) placeLootChest(level, m.above(), rnd);
        }

        // マップ内にランダム散布
        int n = d.value(Setting.SCATTER_COUNT);
        for (int i = 0; i < n; i++) {
            double ang = rnd.nextDouble() * Math.PI * 2;
            double dist = Math.sqrt(rnd.nextDouble()) * mapR;
            BlockPos xz = BlockPos.containing(center.getX() + Math.cos(ang) * dist, 0, center.getZ() + Math.sin(ang) * dist);
            BlockPos surface = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, xz);
            placeLootChest(level, surface, rnd);
        }

        broadcast(server, "§aゲーム開始！");
    }

    // ================= バスで降下 =================

    private static void launchBus(MinecraftServer server, ServerLevel level, GameData d, List<ServerPlayer> riders) {
        int mapR = d.value(Setting.MAP_RADIUS);
        int h = d.value(Setting.BUS_HEIGHT);
        double speed = d.value(Setting.BUS_SPEED) / 10.0;
        autoGlide = d.value(Setting.AUTO_GLIDE);

        double ang = level.getRandom().nextDouble() * Math.PI * 2;
        double dx = Math.cos(ang), dz = Math.sin(ang);
        double half = mapR * 1.3;
        double sx = center.getX() + 0.5 - dx * half;
        double sz = center.getZ() + 0.5 - dz * half;
        busVx = dx * speed;
        busVz = dz * speed;
        int ticks = (int) Math.ceil(2 * half / speed);

        bus = new BattleBusEntity(ModEntities.BATTLE_BUS.get(), level);
        bus.setPos(sx, h, sz);
        bus.launch(busVx, busVz, ticks);
        level.addFreshEntity(bus);

        for (ServerPlayer p : riders) {
            p.teleportTo(level, sx, h, sz, bus.getYRot(), 0.0F);
            p.fallDistance = 0;
            p.startRiding(bus, true);
            PHASE.put(p.getUUID(), 1);
            p.displayClientMessage(Component.literal("§bShiftキーでバスから飛び降りよう！"), true);
        }
        broadcast(server, "§bバトルバスが出発！ 好きな場所でShiftキーを押して降りよう。落ちながらジャンプでグライダー展開！");
    }

    private static void tickDrop(MinecraftServer server) {
        Iterator<Map.Entry<UUID, Integer>> it = PHASE.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<UUID, Integer> en = it.next();
            ServerPlayer p = server.getPlayerList().getPlayer(en.getKey());
            if (p == null || !p.isAlive()) {
                it.remove();
                continue;
            }
            if (en.getValue() == 1) {
                if (!p.isPassenger()) {
                    // 飛び降りた！ バスの勢いを少し引き継ぐ
                    p.setDeltaMovement(busVx * 0.8, 0.1, busVz * 0.8);
                    p.hurtMarked = true;
                    p.fallDistance = 0;
                    equipGlider(p);
                    p.displayClientMessage(Component.literal("§bジャンプでグライダー展開！"), true);
                    en.setValue(2);
                }
            } else {
                if (p.onGround() || p.isInWater()) {
                    removeGlider(p);
                    p.displayClientMessage(Component.literal("§a着地！ グッドラック！"), true);
                    it.remove();
                    continue;
                }
                if (autoGlide > 0 && !p.isFallFlying() && p.getDeltaMovement().y < -0.5) {
                    int ground = server.overworld().getHeight(Heightmap.Types.MOTION_BLOCKING, p.getBlockX(), p.getBlockZ());
                    if (p.getY() - ground <= autoGlide) {
                        p.startFallFlying();
                        p.displayClientMessage(Component.literal("§bグライダー展開！"), true);
                    }
                }
            }
        }
    }

    private static void equipGlider(ServerPlayer p) {
        ItemStack g = new ItemStack(Items.ELYTRA);
        CompoundTag t = g.getOrCreateTag();
        t.putBoolean("Unbreakable", true);
        t.putBoolean("TaczGlider", true);
        g.setHoverName(Component.literal("§bグライダー"));
        p.setItemSlot(EquipmentSlot.CHEST, g);
    }

    private static void removeGlider(ServerPlayer p) {
        ItemStack chest = p.getItemBySlot(EquipmentSlot.CHEST);
        if (chest.hasTag() && chest.getTag().getBoolean("TaczGlider")) {
            p.setItemSlot(EquipmentSlot.CHEST, ItemStack.EMPTY);
            if (p.isFallFlying()) p.stopFallFlying();
        }
    }

    // ================= チェスト =================

    private static void placeLootChest(ServerLevel level, BlockPos pos, RandomSource rnd) {
        if (!level.getBlockState(pos).isAir()) return;
        level.setBlock(pos, Blocks.CHEST.defaultBlockState(), 3);
        if (level.getBlockEntity(pos) instanceof ChestBlockEntity chest) {
            Kits.fillChest(chest, rnd);
        }
        placedChests.add(pos);
    }

    private static void clearChests(ServerLevel level) {
        for (BlockPos pos : placedChests) {
            if (level.getBlockState(pos).is(Blocks.CHEST)) {
                if (level.getBlockEntity(pos) instanceof ChestBlockEntity c) c.clearContent();
                level.removeBlock(pos, false);
            }
        }
        placedChests.clear();
    }

    private static void broadcast(MinecraftServer server, String msg) {
        server.getPlayerList().broadcastSystemMessage(Component.literal(msg), false);
    }

    private GameManager() {}
}
