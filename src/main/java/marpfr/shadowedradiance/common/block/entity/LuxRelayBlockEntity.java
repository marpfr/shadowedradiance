package marpfr.shadowedradiance.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class LuxRelayBlockEntity extends BlockEntity {

    public LuxRelayBlockEntity(BlockPos pos, BlockState blockState) {
        super(SRBlockEntities.LUX_RELAY_BLOCK_ENTITY.get(), pos, blockState);
    }
}
