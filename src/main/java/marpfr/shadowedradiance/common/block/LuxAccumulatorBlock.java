package marpfr.shadowedradiance.common.block;

import com.mojang.serialization.MapCodec;
import marpfr.shadowedradiance.common.block.entity.LuxAccumulatorBlockEntity;
import marpfr.shadowedradiance.common.block.entity.SRBlockEntities;
import marpfr.shadowedradiance.common.item.SRItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
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

        if (player.isCrouching()) {
            if (player instanceof ServerPlayer serverPlayer) {
                serverPlayer.displayClientMessage(Component.literal("canSeeSky: " + level.canSeeSky(pos)), true);
            }

            return InteractionResult.SUCCESS;
        }

        if (level.getBlockEntity(pos) instanceof LuxAccumulatorBlockEntity blockEntity) {
            if (!blockEntity.getHasLens() && !stack.isEmpty() && stack.is(SRItems.LUX_CRYSTAL_LENS_ITEM)) {
                blockEntity.setHasLens(true);
                stack.shrink(1);
                level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 2f);
                return  InteractionResult.SUCCESS;
            } else if (blockEntity.getHasLens() && stack.isEmpty()) {
                blockEntity.setHasLens(false);
                player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(SRItems.LUX_CRYSTAL_LENS_ITEM.get(), 1));
                level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 2f);
                return  InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public void animateTick(@NonNull BlockState blockState, @NonNull Level level, @NonNull BlockPos blockPos, @NonNull RandomSource randomSource) {
        if (level.getBlockEntity(blockPos) instanceof LuxAccumulatorBlockEntity blockEntity) {
            if (!blockEntity.getHasLens()) {
                return;
            }

            float currentPot = LuxAccumulatorBlock.determineCurrentPotential(level, blockPos);
            if (currentPot == 0) {
                return;
            }
            for (int i = 0; i < Math.ceil(currentPot * 3.0f); i++) {
                if (currentPot > randomSource.nextFloat()) {
                    Vec3 pos = new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                    Vec3 pEndRel = new Vec3(0.5f, 0.9f + (0.2f * currentPot), 0.5f);
                    Vec3 pEnd = pos.add(pEndRel);

                    Vec3 pSrcDispl = new Vec3(
                            randomSource.nextInt(-1, 2) + randomSource.nextFloat() - 0.5d,
                            1.0d,
                            randomSource.nextInt(-1, 2) + randomSource.nextFloat() - 0.5d);

                    Vec3 pSrc = pEnd.add(pSrcDispl);

                    Vec3 speed = pEnd.subtract(pSrc);
                    speed = speed.scale(1.0d / speed.length()).scale(0.1d);

                    level.addParticle(ParticleTypes.END_ROD, pSrc.x, pSrc.y, pSrc.z, speed.x, speed.y, speed.z);
                }
            }
        }
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NonNull Level level, @NonNull BlockState state, @NonNull BlockEntityType<T> blockEntityType) {
        return level instanceof ServerLevel ?
                createTickerHelper(blockEntityType, SRBlockEntities.LUX_ACCUMULATOR_BLOCK_ENTITY.get(),
                        (l, b, s, e) -> e.tick(l, b, s)
                ) : null;
    }

    public static float determineCurrentPotential(Level level, BlockPos pos) {
        int skyLight = level.getBrightness(LightLayer.SKY, pos);

        if (skyLight < 14) {
            return 0.0F;
        }

        float i = (skyLight - level.getSkyDarken()) / 15.0F;
        float a = (Mth.sin(level.environmentAttributes().getValue(EnvironmentAttributes.SUN_ANGLE, pos) * (Math.PI / 180.0F) - 3.0F * Math.PI * 0.5F) + 0.2F) / 1.2F;
        if (a <= 0.0F) {
            return 0.0F;
        }

        return i * a;
    }
}
