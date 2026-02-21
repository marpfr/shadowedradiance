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
    private static final SurfaceRules.ConditionSource WATER_CHECK = SurfaceRules.waterBlockCheck(-1, 0);

    private static final SurfaceRules.RuleSource QUICKSAND_SURFACE
            = SurfaceRules.ifTrue(
                    SurfaceRules.noiseCondition(Noises.POWDER_SNOW, 0.45, 0.5),
                    SurfaceRules.ifTrue(
                            WATER_CHECK,
                            SurfaceRules.sequence(
                                    SurfaceRules.ifTrue(
                                            SurfaceRules.ON_FLOOR,
                                            SurfaceRules.state(Blocks.BLUE_WOOL.defaultBlockState())),
                                    SurfaceRules.ifTrue(
                                            SurfaceRules.UNDER_FLOOR,
                                            SurfaceRules.state(Blocks.GREEN_WOOL.defaultBlockState()))
                            )
                    ));


    private static final SurfaceRules.RuleSource LUMINOUS_GROVES = SurfaceRules.ifTrue(
        SurfaceRules.isBiome(SRBiomes.LUMINOUS_GROVES),
        SurfaceRules.ifTrue(
                SurfaceRules.abovePreliminarySurface(),
                SurfaceRules.sequence(
                        SurfaceRules.ifTrue(
                                SurfaceRules.noiseCondition(Noises.SURFACE, 1.75D / 8.25, Double.MAX_VALUE),
                                SurfaceRules.sequence(
                                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.state(Blocks.COARSE_DIRT.defaultBlockState())),
                                        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, SurfaceRules.state(Blocks.DIRT.defaultBlockState()))
                                )
                        ),
                    QUICKSAND_SURFACE,
                    SurfaceRules.sequence(
                            SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.state(Blocks.GRASS_BLOCK.defaultBlockState())),
                            SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, SurfaceRules.state(Blocks.DIRT.defaultBlockState()))
                    )
                )
        )
    );
}
