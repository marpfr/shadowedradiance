package marpfr.shadowedradiance.common.world.feature;


import com.mojang.serialization.Codec;
import marpfr.shadowedradiance.common.block.SRBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;

public class LuxCrystalClusterFeature extends Feature<BlockStateConfiguration> {

    public LuxCrystalClusterFeature(Codec<BlockStateConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<BlockStateConfiguration> context) {
        BlockPos blockpos = context.origin();
        WorldGenLevel worldgenlevel = context.level();
        RandomSource randomsource = context.random();

        BlockStateConfiguration blockstateconfiguration;
        for (blockstateconfiguration = context.config(); blockpos.getY() > worldgenlevel.getMinY() + 3; blockpos = blockpos.below()) {
            if (!worldgenlevel.isEmptyBlock(blockpos.below())) {
                BlockState blockstate = worldgenlevel.getBlockState(blockpos.below());
                if (isStone(blockstate)) {
                    break;
                }
            }
        }

        if (blockpos.getY() <= worldgenlevel.getMinY() + 3) {
            return false;
        } else {
            placeCrystal(blockpos, worldgenlevel, randomsource);
            for (BlockPos bp1 : BlockPos.betweenClosed(blockpos.offset(-1, -1, -1), blockpos.offset(1, 1, 1))) {
                if (randomsource.nextInt(10) <= 2 && worldgenlevel.isEmptyBlock(bp1)) {
                    BlockState scndB = worldgenlevel.getBlockState(bp1.below());
                    if (isStone(scndB) || scndB.is(SRBlocks.LUX_IMBUED_STONE_BLOCK)) {
                        placeCrystal(bp1, worldgenlevel, randomsource);
                    }
                }
            }
        }
        return true;
    }

    private void placeCrystal(BlockPos blockpos, WorldGenLevel worldgenlevel, RandomSource randomsource) {
        worldgenlevel.setBlock(blockpos, SRBlocks.LUX_CRYSTAL_CLUSTER.get().defaultBlockState(), 3);
        worldgenlevel.setBlock(blockpos.below(), SRBlocks.LUX_IMBUED_STONE_BLOCK.get().defaultBlockState(), 3);
        for (BlockPos bp1 : BlockPos.betweenClosed(blockpos.offset(-1, -2, -1), blockpos.offset(1, 1, 1))) {
            if (randomsource.nextInt(10) <= 3 && isStone(worldgenlevel.getBlockState(bp1))) {
                worldgenlevel.setBlock(bp1, SRBlocks.LUX_IMBUED_STONE_BLOCK.get().defaultBlockState(), 3);
            }
        }
    }
}
