package com.example.taczhelper;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class BuildBottleItem extends Item {
    private final Blueprints.Shape shape;
    private final Blueprints.Palette palette;

    public BuildBottleItem(Properties props, Blueprints.Shape shape, Blueprints.Palette palette) {
        super(props);
        this.shape = shape;
        this.palette = palette;
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        Level level = ctx.getLevel();
        Player player = ctx.getPlayer();

        if (ctx.getClickedFace() != Direction.UP) {
            if (player != null && !level.isClientSide) {
                player.displayClientMessage(Component.literal("§c地面の上面をクリックしてね"), true);
            }
            return InteractionResult.FAIL;
        }
        if (level.isClientSide) return InteractionResult.SUCCESS;

        final BlockPos origin = level.getBlockState(ctx.getClickedPos()).canBeReplaced()
                ? ctx.getClickedPos()
                : ctx.getClickedPos().above();

        Direction f = ctx.getHorizontalDirection();
        final int fx = f.getStepX();
        final int fz = f.getStepZ();
        final int rx = -fz;
        final int rz = fx;

        Blueprints.build(shape, palette, (x, y, z, b) -> {
            BlockPos t = origin.offset(rx * x + fx * z, y, rz * x + fz * z);
            if (!level.isInWorldBounds(t)) return;
            BlockState cur = level.getBlockState(t);
            if (cur.getDestroySpeed(level, t) < 0) return; // 岩盤などは壊さない
            level.setBlock(t, b.defaultBlockState(), 2);
        });

        level.playSound(null, origin, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
        if (player == null || !player.getAbilities().instabuild) {
            ctx.getItemInHand().shrink(1);
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tips, TooltipFlag flag) {
        tips.add(Component.literal("§7地面の上面を右クリックで建築"));
        tips.add(Component.literal("§7向いている方向に伸びていくよ"));
    }
}
