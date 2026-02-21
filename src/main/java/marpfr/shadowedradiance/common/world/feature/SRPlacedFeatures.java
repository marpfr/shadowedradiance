package marpfr.shadowedradiance.common.world.feature;

import marpfr.shadowedradiance.ShadowedRadiance;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class SRPlacedFeatures {

    public static final ResourceKey<PlacedFeature> LUXWOOD_PLACED = createKey("luxwood_placed");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(
                context,
                LUXWOOD_PLACED,
                configuredFeatures.getOrThrow(SRConfiguredFeatures.LUXWOOD_KEY),
                VegetationPlacements.treePlacement(
                        PlacementUtils.countExtra(12, 0.1f, 1),
                        Blocks.ACACIA_SAPLING
                ));
    }

    public static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(ShadowedRadiance.MODID, name));
    }

    public static void register(
            BootstrapContext<PlacedFeature> context,
            ResourceKey<PlacedFeature> key,
            Holder<ConfiguredFeature<?, ?>> configuredFeature,
            List<PlacementModifier> placements
    ) {
        context.register(key, new PlacedFeature(configuredFeature, List.copyOf(placements)));
    }
}
