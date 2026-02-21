package marpfr.shadowedradiance.common.world.feature;

import marpfr.shadowedradiance.ShadowedRadiance;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SRFeatures {

    private static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, ShadowedRadiance.MODID);

    // public static DeferredHolder<Feature<?>, LuxCrystalClusterFeature> LUX_CRYSTAL_CLUSTER_FEATURE = FEATURES.register("lux_crystal_cluster", ()-> new LuxCrystalClusterFeature(BlockStateConfiguration.CODEC));

    public static void register(IEventBus modEventBus) {

        FEATURES.register(modEventBus);
    }

}
