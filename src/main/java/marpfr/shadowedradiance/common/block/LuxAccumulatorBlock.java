package marpfr.shadowedradiance.common.block;

import com.mojang.serialization.MapCodec;
import marpfr.shadowedradiance.common.block.entity.LuxAccumulatorBlockEntity;
import marpfr.shadowedradiance.common.block.entity.SRBlockEntities;
import marpfr.shadowedradiance.common.item.SRItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class LuxAccumulatorBlock extends BaseEntityBlock {

    public static final MapCodec<LuxAccumulatorBlock> CODEC = simpleCodec(LuxAccumulatorBlock::new);

    @Override
    protected @NonNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    public LuxAccumulatorBlock(Properties properties) {
        super(properties
                .noOcclusion()
                .strength(3.0F, 6.0F)
                .sound(SoundType.STONE));
    }

    @Override
    protected @NonNull RenderShape getRenderShape(@NonNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NonNull BlockPos pos, @NonNull BlockState state) {
        return new LuxAccumulatorBlockEntity(pos, state);
    }

    @Override
    protected @NonNull InteractionResult useItemOn(
            @NonNull ItemStack stack, @NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos,
            @NonNull Player player, @NonNull InteractionHand hand, @NonNull BlockHitResult hitResult) {

        if (level.getBlockEntity(pos) instanceof LuxAccumulatorBlockEntity blockEntity) {
            if (!blockEntity.getHasLens() && !stack.isEmpty() && stack.is(SRItems.LUX_CRYSTAL_LENS_ITEM)) {
                blockEntity.setHasLens(true);
                stack.shrink(1);
                level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 2f);
            } else if (blockEntity.getHasLens() && stack.isEmpty()) {
                blockEntity.setHasLens(false);
                player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(SRItems.LUX_CRYSTAL_LENS_ITEM.get(), 1));
                level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 2f);
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NonNull Level level, @NonNull BlockState state, @NonNull BlockEntityType<T> blockEntityType) {
        return level instanceof ServerLevel ?
                createTickerHelper(blockEntityType, SRBlockEntities.LUX_ACCUMULATOR_BLOCK_ENTITY.get(),
                        (l, b, s, e) -> e.tick(l, b, s)
                ) : null;
    }
}
