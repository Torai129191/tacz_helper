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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.SlabType;

import java.util.List;

public class BuildBottleItem extends Item {
    private final Blueprints.Shape shape;
    private final Blueprints.Palette palette;

    public BuildBottleItem(Properties props, Blueprints.Shape shape, Blueprints.Palette palette) {
        super(props);
        this.shape = shape;
        this.palette = palette;
    }

    /** アイコンの色 */
    public int color() {
        return palette.color();
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.literal("建築ボトル：" + shape.label + "（" + palette.label + "）");
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

        Blueprints.build(shape, palette, new WorldPlacer(level, origin, ctx.getHorizontalDirection()));

        level.playSound(null, origin, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
        if (player == null || !player.getAbilities().instabuild) {
            ctx.getItemInHand().shrink(1);
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tips, TooltipFlag flag) {
        tips.add(Component.literal("§e" + shape.desc));
        tips.add(Component.literal("§7地面の上面を右クリックで建築"));
        tips.add(Component.literal("§7向いている方向に伸びていくよ"));
    }

    /** 設計図の座標(x右, y上, z前)を、プレイヤーの向きに合わせて世界に置く */
    private static final class WorldPlacer implements Blueprints.Placer {
        private final Level level;
        private final BlockPos origin;
        private final int fx, fz, rx, rz;

        WorldPlacer(Level level, BlockPos origin, Direction facing) {
            this.level = level;
            this.origin = origin;
            this.fx = facing.getStepX();
            this.fz = facing.getStepZ();
            this.rx = -fz;
            this.rz = fx;
        }

        private BlockPos pos(int x, int y, int z) {
            return origin.offset(rx * x + fx * z, y, rz * x + fz * z);
        }

        private Direction dir(int d) {
            int[][] v = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
            int lx = v[d & 3][0], lz = v[d & 3][1];
            int wx = rx * lx + fx * lz;
            int wz = rz * lx + fz * lz;
            for (Direction dd : Direction.Plane.HORIZONTAL) {
                if (dd.getStepX() == wx && dd.getStepZ() == wz) return dd;
            }
            return Direction.NORTH;
        }

        private BlockPos put(int x, int y, int z, BlockState state) {
            BlockPos t = pos(x, y, z);
            if (!level.isInWorldBounds(t)) return null;
            if (level.getBlockState(t).getDestroySpeed(level, t) < 0) return null; // 岩盤などは壊さない
            level.setBlock(t, state, 2);
            return t;
        }

        @Override
        public void set(int x, int y, int z, Block b) {
            BlockState s = b.defaultBlockState();
            if (b instanceof LeavesBlock) s = s.setValue(LeavesBlock.PERSISTENT, true);
            put(x, y, z, s);
        }

        @Override
        public void stair(int x, int y, int z, Block b, int dir, boolean flip) {
            put(x, y, z, b.defaultBlockState()
                    .setValue(StairBlock.FACING, dir(dir))
                    .setValue(StairBlock.HALF, flip ? Half.TOP : Half.BOTTOM));
        }

        @Override
        public void slab(int x, int y, int z, Block b, boolean top) {
            put(x, y, z, b.defaultBlockState().setValue(SlabBlock.TYPE, top ? SlabType.TOP : SlabType.BOTTOM));
        }

        @Override
        public void ladder(int x, int y, int z, int dir) {
            put(x, y, z, Blocks.LADDER.defaultBlockState().setValue(LadderBlock.FACING, dir(dir)));
        }

        @Override
        public void chest(int x, int y, int z, int dir) {
            BlockPos t = put(x, y, z, Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, dir(dir)));
            if (t != null && level.getBlockEntity(t) instanceof ChestBlockEntity chest) {
                Kits.fillChest(chest, level.getRandom());
            }
        }
    }
}
