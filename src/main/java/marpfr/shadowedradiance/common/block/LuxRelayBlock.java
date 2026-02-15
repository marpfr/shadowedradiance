package marpfr.shadowedradiance.common.block;

import com.mojang.serialization.MapCodec;
import marpfr.shadowedradiance.common.block.entity.LuxRelayBlockEntity;
import marpfr.shadowedradiance.common.block.entity.SRBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Map;

public class LuxRelayBlock extends BaseEntityBlock {

    public static final MapCodec<LuxRelayBlock> CODEC = simpleCodec(LuxRelayBlock::new);

    public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;
    private final Map<Direction, VoxelShape> shapes;

    @Override
    protected @NonNull MapCodec<? extends BaseEntityBlock> codec() { return CODEC; }

    public LuxRelayBlock(Properties properties) {
        super(properties
                .noOcclusion()
                .strength(3.0F, 6.0F)
                .sound(SoundType.STONE));

        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.UP));
        this.shapes = Shapes.rotateAll(Block.boxZ(8.0, 2.0, 16.0));
    }

    @Override
    protected @NonNull VoxelShape getShape(@NonNull BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull CollisionContext context) {
        return this.shapes.get(state.getValue(FACING));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NonNull BlockPos pos, @NonNull BlockState state) {
        return new LuxRelayBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NonNull Level level, @NonNull BlockState state, @NonNull BlockEntityType<T> blockEntityType) {
        return level instanceof ServerLevel ?
                createTickerHelper(blockEntityType, SRBlockEntities.LUX_RELAY_BLOCK_ENTITY.get(),
                        (l, b, s, e) -> e.tick(l, b, s)
                ) : null;
    }

    @Override
    public void animateTick(@NonNull BlockState blockState, @NonNull Level level, @NonNull BlockPos blockPos, @NonNull RandomSource randomSource) {
        Vec3 blockCenterPoint = getCrystalOffset(blockPos, blockState);

        for (int i = 0; i < 5; i++) {
            Vec3 centerPoint = blockCenterPoint.add(
                    (randomSource.nextDouble() - 0.5) * 0.2,
                    (randomSource.nextDouble() - 0.5) * 0.2,
                    (randomSource.nextDouble() - 0.5) * 0.2
            );

            level.addParticle(ParticleTypes.ELECTRIC_SPARK, centerPoint.x, centerPoint.y, centerPoint.z, 0.0d, 0.0d, 0.0d);
        }
    }

    public static @NonNull Vec3 getCrystalOffset(@NonNull BlockPos blockPos, @NonNull BlockState blockState) {
        Vec3 rel = switch (blockState.getValue(FACING)) {
            case UP -> new Vec3(0.5d, 0.8d, 0.5d);
            case DOWN -> new Vec3(0.5d, 0.2d, 0.5d);
            case NORTH -> new Vec3(0.5d, 0.5d, 0.2d);
            case EAST -> new Vec3(0.8d, 0.5d, 0.5d);
            case SOUTH -> new Vec3(0.5d, 0.5d, 0.8d);
            case WEST -> new Vec3(0.2d, 0.5d, 0.5d);
        };

        return Vec3.atLowerCornerOf(blockPos).add(rel);
    }

    @Override
    public void destroy(LevelAccessor level, BlockPos pos, BlockState state) {
        if (level.getBlockEntity(pos) instanceof LuxRelayBlockEntity relay) {
            relay.remove();
        }

        super.destroy(level, pos, state);
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
        return direction == blockState.getValue(FACING).getOpposite() && !blockState.canSurvive(level, blockPos)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(blockState, level, scheduledTickAccess, blockPos, direction, neighborPos, neighborState, randomSource);
    }


    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        LevelAccessor levelaccessor = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        return this.defaultBlockState()
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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockStateBuilder) {
        blockStateBuilder.add(FACING);
    }
}
