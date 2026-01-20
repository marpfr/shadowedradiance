package marpfr.shadowedradiance.common.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import marpfr.shadowedradiance.common.block.entity.LuxAccumulatorBlockEntity;
import marpfr.shadowedradiance.common.item.SRItems;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class LuxAccumulatorBlockEntityRenderer implements BlockEntityRenderer<LuxAccumulatorBlockEntity, LuxAccumulatorBlockEntityRenderState> {

    private final ItemModelResolver itemModelResolver;

    public LuxAccumulatorBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public LuxAccumulatorBlockEntityRenderState createRenderState() {
        return new LuxAccumulatorBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(
            LuxAccumulatorBlockEntity blockEntity,
            LuxAccumulatorBlockEntityRenderState renderState,
            float partialTick,
            @NonNull Vec3 cameraPosition,
            ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPosition, breakProgress);

        renderState.animationTime = blockEntity.getLevel() != null ? (float)Math.floorMod(blockEntity.getLevel().getGameTime(), 40) + partialTick : 0.0F;

        renderState.hasLens = blockEntity.getHasLens();
        renderState.currentPotential = blockEntity.getCurrentPotential();

        itemModelResolver.updateForTopItem(
                renderState.itemRenderState,
                new ItemStack(SRItems.LUX_CRYSTAL_LENS_ITEM.get()),
                ItemDisplayContext.FIXED,
                blockEntity.getLevel(),
                null,
                (int) blockEntity.getBlockPos().asLong());
    }

    @Override
    public void submit(
            LuxAccumulatorBlockEntityRenderState renderState,
            @NonNull PoseStack poseStack,
            @NonNull SubmitNodeCollector submitNodeCollector,
            @NonNull CameraRenderState cameraRenderState) {
        float animationTime = renderState.animationTime;

        if (renderState.hasLens) {

            ItemStackRenderState itemStackRenderState = renderState.itemRenderState;
            if (!itemStackRenderState.isEmpty()) {

                poseStack.pushPose();

                poseStack.translate(0.5f, 0.9f + (0.2f * renderState.currentPotential), 0.5f);
                poseStack.scale(0.75f, 0.75f, 0.75f);
                poseStack.mulPose(Axis.XP.rotation(Mth.PI / 2f));

                renderState.itemRenderState.submit(
                        poseStack,
                        submitNodeCollector,
                        renderState.lightCoords,
                        OverlayTexture.NO_OVERLAY,
                        0);

                poseStack.popPose();
            }
        }
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}
