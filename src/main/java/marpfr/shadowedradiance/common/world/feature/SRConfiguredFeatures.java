package marpfr.shadowedradiance.common.world.feature;

import com.google.common.collect.ImmutableList;
import marpfr.shadowedradiance.ShadowedRadiance;
import marpfr.shadowedradiance.common.block.SRBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.CreakingHeartDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.PaleMossDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.DarkOakTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;

import java.util.Optional;
import java.util.OptionalInt;

public class SRConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> LUXWOOD_KEY = createKey("luxwood");

    public static final TreeGrower LUXWOOD_GROWER = new TreeGrower(
            Identifier.fromNamespaceAndPath(ShadowedRadiance.MODID, "luxwood").toShortString(),
            Optional.empty(),
            Optional.of(LUXWOOD_KEY),
            Optional.empty()
    );

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        register(
                context,
                LUXWOOD_KEY,
                Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(SRBlocks.LUXWOOD_LOG.get()),
                        new ForkingTrunkPlacer(4, 2, 2),
                        BlockStateProvider.simple(SRBlocks.LUXWOOD_LEAVES.get()),
                        new DarkOakFoliagePlacer(ConstantInt.of(1), ConstantInt.of(0)),
                        new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty())
                )
                        .ignoreVines()
                        .build()
        );
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, Identifier.fromNamespaceAndPath(ShadowedRadiance.MODID, name));
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(
            BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC config
    ) {
        context.register(key, new ConfiguredFeature<>(feature, config));
    }
}
