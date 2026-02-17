package marpfr.shadowedradiance.common.world.biome;

import marpfr.shadowedradiance.ShadowedRadiance;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.attribute.BackgroundMusic;
import net.minecraft.world.attribute.EnvironmentAttributeMap;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class SRBiomes {

    public static final ResourceKey<Biome> LUMINOUS_GROVES = register("luminous_groves");

    public static ResourceKey<Biome> register(String name) {
        return ResourceKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(ShadowedRadiance.MODID, name));
    }

    public static void bootstrap(BootstrapContext<Biome> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> configuredCarvers = context.lookup(Registries.CONFIGURED_CARVER);

        context.register(LUMINOUS_GROVES, luminousGroves(context, placedFeatures, configuredCarvers));
    }

    private static Biome luminousGroves(BootstrapContext<Biome> context, HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> configuredCarvers) {
        MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();

        BiomeGenerationSettings.Builder biomegenerationsettings$builder = new BiomeGenerationSettings.Builder(placedFeatures, configuredCarvers);

        EnvironmentAttributeMap environmentattributemap = EnvironmentAttributeMap.builder()
                .set(EnvironmentAttributes.SKY_COLOR, -4605511)
                .set(EnvironmentAttributes.FOG_COLOR, -8292496)
                .set(EnvironmentAttributes.WATER_FOG_COLOR, -11179648)
                .set(EnvironmentAttributes.BACKGROUND_MUSIC, BackgroundMusic.EMPTY)
                .set(EnvironmentAttributes.MUSIC_VOLUME, 0.0F)
                .build();

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .downfall(0.8f)
                .temperature(0.7f)
                .putAttributes(environmentattributemap)
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(7978751)
                        .grassColorOverride(7832178)
                        .foliageColorOverride(8883574)
                        .dryFoliageColorOverride(10528412)
                        .build())
                .mobSpawnSettings(mobspawnsettings$builder.build())
                .generationSettings(biomegenerationsettings$builder.build())
                .build();
    }
}
