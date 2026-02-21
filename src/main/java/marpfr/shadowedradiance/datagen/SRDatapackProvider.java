package marpfr.shadowedradiance.datagen;

import marpfr.shadowedradiance.ShadowedRadiance;
import marpfr.shadowedradiance.common.world.biome.SRBiomes;
import marpfr.shadowedradiance.common.world.feature.SRConfiguredFeatures;
import marpfr.shadowedradiance.common.world.feature.SRPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class SRDatapackProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.BIOME, SRBiomes::bootstrap)
            .add(Registries.CONFIGURED_FEATURE, SRConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, SRPlacedFeatures::bootstrap);

    public SRDatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(ShadowedRadiance.MODID));
    }
}
