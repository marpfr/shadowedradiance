package marpfr.shadowedradiance.common.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import marpfr.shadowedradiance.common.block.entity.LuxRelayBlockEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class LuxRelayBlockEntityRenderer implements BlockEntityRenderer<LuxRelayBlockEntity, LuxRelayBlockEntityRenderState> {

    @Override
    public LuxRelayBlockEntityRenderState createRenderState() {
        return new LuxRelayBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(LuxRelayBlockEntity blockEntity, LuxRelayBlockEntityRenderState renderState, float partialTick,
                                   @NonNull Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPosition, breakProgress);
    }

    @Override
    public void submit(LuxRelayBlockEntityRenderState renderState, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector nodeCollector,
                       @NonNull CameraRenderState cameraRenderState) {
    }
}
