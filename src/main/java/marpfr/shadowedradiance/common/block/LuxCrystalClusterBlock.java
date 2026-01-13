package marpfr.shadowedradiance.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Map;

public class LuxCrystalClusterBlock extends Block implements SimpleWaterloggedBlock {

    public static final MapCodec<LuxCrystalClusterBlock> CODEC = simpleCodec(LuxCrystalClusterBlock::new);

    @Override
    public @NonNull MapCodec<? extends LuxCrystalClusterBlock> codec() {
        return CODEC;
    }

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;

    private final Map<Direction, VoxelShape> shapes;

    public LuxCrystalClusterBlock(Properties properties) {
        super(properties.mapColor(MapColor.COLOR_YELLOW)
                        .forceSolidOn()
                        .noOcclusion()
                        .sound(SoundType.AMETHYST_CLUSTER)
                        .strength(1.5F)
                        .lightLevel(state -> 5)
                        .pushReaction(PushReaction.DESTROY)
                        .isRedstoneConductor((bs, br, bp) -> false));
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false).setValue(FACING, Direction.UP));
        this.shapes = Shapes.rotateAll(Block.boxZ(10.0F, 9.0F, 16.0));
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state) {
        return state.getFluidState().isEmpty();
    }

    @Override
    public int getLightBlock(@NonNull BlockState state) {
        return 0;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return this.shapes.get(state.getValue(FACING));
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);

        int i = level.getBrightness(LightLayer.SKY, pos) - level.getSkyDarken();
        float a = level.environmentAttributes().getValue(EnvironmentAttributes.SUN_ANGLE, pos);
        float f = a * (float) (Math.PI / 180.0);
        int r = 0;

        if (i > 0) {
            float f1 = f < (float) Math.PI ? 0.0F : (float) (Math.PI * 2);
            f += (f1 - f) * 0.2F;
            r = Math.round(i * Mth.cos(f));
        }

        r = Mth.clamp(r, 0, 15);

        if (r > 0 && random.nextInt(17 - r) == 0) {
            level.addParticle(
                    ParticleTypes.END_ROD,
                    pos.getX() + 0.1F + random.nextFloat() * 0.8F,
                    pos.getY() + random.nextFloat() * 0.8F,
                    pos.getZ() + 0.1F + random.nextFloat() * 0.8F,
                    0.0F,
                    0.05F,
                    0.0F
            );
        }
    }

    @Override
    protected boolean canSurvive(BlockState blockState, LevelReader level, BlockPos pos) {
        Direction direction = blockState.getValue(FACING);
        BlockPos blockpos = pos.relative(direction.getOpposite());
        return level.getBlockState(blockpos).isFaceSturdy(level, blockpos, direction);
    }

    @Override
    protected @NonNull BlockState updateShape(
            BlockState blockState,
            @NonNull LevelReader level,
            @NonNull ScheduledTickAccess scheduledTickAccess,
            @NonNull BlockPos blockPos,
            @NonNull Direction direction,
            @NonNull BlockPos neighborPos,
            @NonNull BlockState neighborState,
            @NonNull RandomSource randomSource
    ) {
        if (blockState.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return direction == blockState.getValue(FACING).getOpposite() && !blockState.canSurvive(level, blockPos)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(blockState, level, scheduledTickAccess, blockPos, direction, neighborPos, neighborState, randomSource);
    }


    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        LevelAccessor levelaccessor = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        return this.defaultBlockState()
                .setValue(WATERLOGGED, levelaccessor.getFluidState(blockpos).getType() == Fluids.WATER)
                .setValue(FACING, context.getClickedFace());
    }

    @Override
    protected @NonNull BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
    }

    @Override
    protected @NonNull BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.rotate(mirror.getRotation(blockState.getValue(FACING)));
    }

    @Override
    protected @NonNull FluidState getFluidState(BlockState blockState) {
        return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockStateBuilder) {
        blockStateBuilder.add(WATERLOGGED, FACING);
    }
}
