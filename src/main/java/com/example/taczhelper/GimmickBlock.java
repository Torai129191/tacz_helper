package com.example.taczhelper;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/** アスレや建物に使う、踏むと何かが起きるオリジナルブロック */
public class GimmickBlock extends Block {
    public enum Kind { JUMP, SPEED, CHECKPOINT, GOAL, CRUMBLE, HEAL }

    private static final Map<UUID, Long> GOAL_COOLDOWN = new HashMap<>();
    private final Kind kind;

    public GimmickBlock(Properties props, Kind kind) {
        super(props);
        this.kind = kind;
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float dist) {
        if (kind == Kind.JUMP || kind == Kind.SPEED || kind == Kind.HEAL) {
            entity.causeFallDamage(dist, 0.0F, level.damageSources().fall());
        } else {
            super.fallOn(level, state, pos, entity, dist);
        }
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (level.isClientSide || !(level instanceof ServerLevel sl)) return;
        double cx = pos.getX() + 0.5, cy = pos.getY() + 1.0, cz = pos.getZ() + 0.5;

        switch (kind) {
            case JUMP -> {
                Vec3 v = entity.getDeltaMovement();
                entity.setDeltaMovement(v.x, 1.25, v.z);
                entity.hurtMarked = true;
                entity.fallDistance = 0;
                sl.playSound(null, pos, SoundEvents.SLIME_BLOCK_PLACE, SoundSource.BLOCKS, 1.0F, 1.2F);
                sl.sendParticles(ParticleTypes.CLOUD, cx, cy, cz, 6, 0.3, 0.1, 0.3, 0.02);
            }
            case SPEED -> {
                Vec3 look = entity.getLookAngle();
                Vec3 h = new Vec3(look.x, 0, look.z);
                if (h.lengthSqr() < 1.0E-4) h = entity.getDeltaMovement().multiply(1, 0, 1);
                h = h.normalize().scale(1.2);
                entity.setDeltaMovement(h.x, 0.35, h.z);
                entity.hurtMarked = true;
                entity.fallDistance = 0;
                sl.playSound(null, pos, SoundEvents.FIREWORK_ROCKET_LAUNCH, SoundSource.BLOCKS, 0.7F, 1.4F);
                sl.sendParticles(ParticleTypes.FLAME, cx, cy, cz, 6, 0.3, 0.1, 0.3, 0.02);
            }
            case CHECKPOINT -> {
                if (entity instanceof ServerPlayer sp) {
                    BlockPos target = pos.above();
                    if (!target.equals(sp.getRespawnPosition())) {
                        sp.setRespawnPosition(sl.dimension(), target, sp.getYRot(), true, false);
                        sp.displayClientMessage(Component.literal("§a★ チェックポイント！"), true);
                        sl.playSound(null, pos, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS, 1.0F, 1.2F);
                        sl.sendParticles(ParticleTypes.HAPPY_VILLAGER, cx, cy + 0.3, cz, 8, 0.3, 0.3, 0.3, 0.0);
                    }
                }
            }
            case GOAL -> {
                if (entity instanceof ServerPlayer sp) {
                    long now = sl.getGameTime();
                    Long last = GOAL_COOLDOWN.get(sp.getUUID());
                    if (last == null || now - last > 200) {
                        GOAL_COOLDOWN.put(sp.getUUID(), now);
                        sl.getServer().getPlayerList().broadcastSystemMessage(
                                Component.literal("§6★ " + sp.getName().getString() + " がゴール！ ★"), false);
                        sl.sendParticles(ParticleTypes.FIREWORK, cx, cy + 0.5, cz, 40, 0.6, 0.8, 0.6, 0.1);
                        sl.playSound(null, pos, SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundSource.PLAYERS, 1.0F, 1.0F);
                    }
                }
            }
            case CRUMBLE -> {
                if (!sl.getBlockTicks().hasScheduledTick(pos, this)) {
                    sl.scheduleTick(pos, this, 15);
                }
            }
            case HEAL -> {
                if (entity instanceof LivingEntity le && sl.getGameTime() % 20 == 0) {
                    le.heal(2.0F);
                    sl.sendParticles(ParticleTypes.HEART, cx, cy + 0.5, cz, 3, 0.3, 0.3, 0.3, 0.0);
                }
            }
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (kind == Kind.CRUMBLE) {
            level.sendParticles(ParticleTypes.CLOUD, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 6, 0.3, 0.3, 0.3, 0.02);
            level.destroyBlock(pos, false);
        }
    }
}
