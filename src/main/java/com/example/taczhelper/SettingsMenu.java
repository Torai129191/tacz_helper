package com.example.taczhelper;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

/** チェストの画面を使った、ゲーム設定のGUI。アイコンをクリックして値を切り替える */
public class SettingsMenu extends ChestMenu {
    // 設定アイコンを置く場所（4段のチェスト画面）
    private static final int[] SLOTS = {10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24};
    private static final int START_SLOT = 29;
    private static final int STOP_SLOT = 31;
    private static final int CLOSE_SLOT = 33;

    private final Container box;
    private final GameData data;
    private final MinecraftServer server;
    private final BlockPos origin;

    private SettingsMenu(int id, Inventory inv, Container box, GameData data, MinecraftServer server, BlockPos origin) {
        super(MenuType.GENERIC_9x4, id, inv, box, 4);
        this.box = box;
        this.data = data;
        this.server = server;
        this.origin = origin;
        refresh();
    }

    public static void open(ServerPlayer player, BlockPos origin) {
        MinecraftServer server = player.getServer();
        if (server == null) return;
        player.openMenu(new SimpleMenuProvider(
                (id, inv, p) -> new SettingsMenu(id, inv, new SimpleContainer(36), GameData.get(server), server, origin),
                Component.literal("TaC2Z ゲーム設定")));
    }

    private static Component plain(String s) {
        return Component.literal(s).withStyle(Style.EMPTY.withItalic(false));
    }

    private static ItemStack icon(Item item, String name, String... lore) {
        ItemStack st = new ItemStack(item);
        st.setHoverName(plain(name));
        if (lore.length > 0) {
            CompoundTag display = st.getOrCreateTagElement("display");
            ListTag list = new ListTag();
            for (String l : lore) list.add(StringTag.valueOf(Component.Serializer.toJson(plain(l))));
            display.put("Lore", list);
        }
        return st;
    }

    private static Item iconFor(Setting s) {
        return switch (s) {
            case KIT -> Items.CROSSBOW;
            case SPAWN_HEIGHT -> Items.FEATHER;
            case SPREAD -> Items.COMPASS;
            case COUNTDOWN -> Items.CLOCK;
            case SLOW_FALL -> Items.PHANTOM_MEMBRANE;
            case LOOT_CHANCE -> Items.CHEST;
            case SCATTER_COUNT -> Items.ENDER_CHEST;
            case MAP_RADIUS -> Items.FILLED_MAP;
            case PROTECT -> Items.SHIELD;
            case BUS -> Items.MINECART;
            case BUS_HEIGHT -> Items.FIREWORK_ROCKET;
            case BUS_SPEED -> Items.SUGAR;
            case AUTO_GLIDE -> Items.ELYTRA;
        };
    }

    private void refresh() {
        ItemStack pane = icon(Items.GRAY_STAINED_GLASS_PANE, " ");
        for (int i = 0; i < 36; i++) box.setItem(i, pane.copy());

        Setting[] all = Setting.values();
        for (int i = 0; i < all.length && i < SLOTS.length; i++) {
            Setting s = all[i];
            int now = data.value(s);
            String[] lore = new String[s.values.length + 2];
            lore[0] = "§8左クリック:次へ  右クリック:前へ";
            for (int k = 0; k < s.values.length; k++) {
                int v = s.values[k];
                lore[k + 1] = (v == now ? "§a▶ " : "§7   ") + s.display(v);
            }
            lore[s.values.length + 1] = " ";
            box.setItem(SLOTS[i], icon(iconFor(s), "§e" + s.label + ": §f" + s.display(now), lore));
        }
        box.setItem(START_SLOT, icon(Items.EMERALD_BLOCK, "§a▶ ゲーム開始", "§7この設定で試合を始めます", "§7(いまの位置が試合の中心)"));
        box.setItem(STOP_SLOT, icon(Items.REDSTONE_BLOCK, "§c■ ゲーム終了", "§7試合を止めて、チェストを片付けます"));
        box.setItem(CLOSE_SLOT, icon(Items.BARRIER, "§f閉じる"));
    }

    @Override
    public void clicked(int slotId, int button, ClickType clickType, Player player) {
        // アイテムは絶対に動かさない。チェスト側のスロットだけ反応する
        if (slotId < 0 || slotId >= 36) return;
        if (clickType != ClickType.PICKUP && clickType != ClickType.QUICK_MOVE && clickType != ClickType.SWAP) return;
        if (!(player instanceof ServerPlayer sp) || !sp.hasPermissions(2)) return;

        Setting[] all = Setting.values();
        for (int i = 0; i < all.length && i < SLOTS.length; i++) {
            if (SLOTS[i] == slotId) {
                data.shift(all[i], button == 1 ? -1 : 1);
                refresh();
                click(player);
                return;
            }
        }
        if (slotId == START_SLOT) {
            click(player);
            player.closeContainer();
            GameManager.start(server, origin);
        } else if (slotId == STOP_SLOT) {
            click(player);
            GameManager.stop(server);
        } else if (slotId == CLOSE_SLOT) {
            player.closeContainer();
        }
    }

    private static void click(Player player) {
        player.level().playSound(null, player.blockPosition(), SoundEvents.UI_LOOM_SELECT_PATTERN,
                SoundSource.PLAYERS, 0.6F, 1.3F);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }
}
