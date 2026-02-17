package marpfr.shadowedradiance.common.world.biome;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class SRSurfaceRules {

    public static SurfaceRules.RuleSource makeRules() {
        return SurfaceRules.sequence(
                LUMINOUS_GROVES
        );
    }

    private static final SurfaceRules.RuleSource LUMINOUS_GROVES = SurfaceRules.ifTrue(
        SurfaceRules.isBiome(SRBiomes.LUMINOUS_GROVES),
            SurfaceRules.sequence(
                    SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.state(Blocks.RED_SAND.defaultBlockState())),
                    SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, SurfaceRules.state(Blocks.RED_SANDSTONE.defaultBlockState()))
            )
//            SurfaceRules.sequence(
//                SurfaceRules.ifTrue(
//                        SurfaceRules.noiseCondition(Noises.SURFACE, 1.75D / 8.25, Double.MAX_VALUE),
//                        SurfaceRules.sequence(
//                                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.state(Blocks.RED_SAND.defaultBlockState())),
//                                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, SurfaceRules.state(Blocks.RED_SANDSTONE.defaultBlockState()))
//                        )
//                )
//            )
    );
}
