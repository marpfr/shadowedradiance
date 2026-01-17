package marpfr.shadowedradiance.common.block.entity;

import marpfr.shadowedradiance.common.item.SRItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class LuxAccumulatorBlockEntity extends BlockEntity {

    private boolean hasLens;

    public LuxAccumulatorBlockEntity(BlockPos pos, BlockState blockState) {
        super(SRBlockEntities.LUX_ACCUMULATOR_BLOCK_ENTITY.get(), pos, blockState);

        this.hasLens = false;
    }

    public void setHasLens(boolean value) {
        this.hasLens = value;
        this.setChanged();
        if (this.level != null && !this.level.isClientSide()) {
            level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_NEIGHBORS | Block.UPDATE_CLIENTS);
        }
    }

    public boolean getHasLens() {
        return this.hasLens;
    }

    @Override
    public void preRemoveSideEffects(@NonNull BlockPos pos, @NonNull BlockState state) {
        System.out.println(this.hasLens);
        System.out.println(this.level == null);
        if (this.hasLens && this.level != null) {
            Containers.dropContents(this.level, this.worldPosition, NonNullList.of(new ItemStack(SRItems.LUX_CRYSTAL_LENS_ITEM.get(), 1)));
        }
        super.preRemoveSideEffects(pos, state);
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {

    }

    @Override
    protected void saveAdditional(@NonNull ValueOutput output) {
        super.saveAdditional(output);
        output.putBoolean("hasLens", this.hasLens);
    }

    @Override
    protected void loadAdditional(@NonNull ValueInput input) {
        super.loadAdditional(input);
        this.hasLens = input.getBooleanOr("hasLens", false);
    }

    @Override
    public @NonNull CompoundTag getUpdateTag(HolderLookup.@NonNull Provider registries) {
        return this.saveWithoutMetadata(registries);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
