package com.example.taczhelper;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/** ブロックを組み合わせて、黄色いバスと気球を描く（専用の画像は不要） */
public class BattleBusRenderer extends EntityRenderer<BattleBusEntity> {

    public BattleBusRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
        this.shadowRadius = 0.0F;
    }

    @Override
    public ResourceLocation getTextureLocation(BattleBusEntity entity) {
        return new ResourceLocation("minecraft", "textures/block/yellow_concrete.png");
    }

    @Override
    public void render(BattleBusEntity bus, float yaw, float partialTick, PoseStack ps,
                       MultiBufferSource buf, int light) {
        ps.pushPose();
        ps.mulPose(Axis.YP.rotationDegrees(-yaw));

        // 車体
        cube(ps, buf, light, Blocks.YELLOW_CONCRETE.defaultBlockState(), -1.5F, 0.4F, -3.0F, 3.0F, 1.1F, 6.0F);
        cube(ps, buf, light, Blocks.LIGHT_BLUE_STAINED_GLASS.defaultBlockState(), -1.5F, 1.5F, -3.0F, 3.0F, 0.9F, 6.0F);
        cube(ps, buf, light, Blocks.WHITE_CONCRETE.defaultBlockState(), -1.6F, 2.4F, -3.1F, 3.2F, 0.2F, 6.2F);
        // 前のライト
        cube(ps, buf, light, Blocks.GLOWSTONE.defaultBlockState(), -1.2F, 0.7F, 3.0F, 0.5F, 0.5F, 0.15F);
        cube(ps, buf, light, Blocks.GLOWSTONE.defaultBlockState(), 0.7F, 0.7F, 3.0F, 0.5F, 0.5F, 0.15F);
        // タイヤ
        for (float sx : new float[]{-1.8F, 1.4F})
            for (float sz : new float[]{-2.4F, 1.6F})
                cube(ps, buf, light, Blocks.BLACK_CONCRETE.defaultBlockState(), sx, 0.0F, sz, 0.4F, 0.8F, 0.8F);
        // 気球
        BlockState chain = Blocks.CHAIN.defaultBlockState();
        for (float cx : new float[]{-1.0F, 0.0F})
            for (float cz : new float[]{-1.0F, 0.0F})
                cube(ps, buf, light, chain, cx, 2.6F, cz, 1.0F, 1.6F, 1.0F);
        cube(ps, buf, light, Blocks.RED_WOOL.defaultBlockState(), -2.0F, 4.2F, -2.0F, 4.0F, 3.0F, 4.0F);
        cube(ps, buf, light, Blocks.WHITE_WOOL.defaultBlockState(), -1.5F, 7.2F, -1.5F, 3.0F, 0.6F, 3.0F);
        cube(ps, buf, light, Blocks.WHITE_WOOL.defaultBlockState(), -2.1F, 5.2F, -2.1F, 4.2F, 0.6F, 4.2F);

        ps.popPose();
        super.render(bus, yaw, partialTick, ps, buf, light);
    }

    private static void cube(PoseStack ps, MultiBufferSource buf, int light, BlockState state,
                             float x, float y, float z, float sx, float sy, float sz) {
        ps.pushPose();
        ps.translate(x, y, z);
        ps.scale(sx, sy, sz);
        Minecraft.getInstance().getBlockRenderer()
                .renderSingleBlock(state, ps, buf, light, OverlayTexture.NO_OVERLAY);
        ps.popPose();
    }
}
