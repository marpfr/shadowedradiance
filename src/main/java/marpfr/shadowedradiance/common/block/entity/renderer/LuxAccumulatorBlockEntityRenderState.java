package marpfr.shadowedradiance.common.block.entity.renderer;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

public class LuxAccumulatorBlockEntityRenderState extends BlockEntityRenderState {
    public float animationTime;
    public final ItemStackRenderState itemRenderState = new ItemStackRenderState();

    public boolean hasLens;
    public float currentPotential;
}
