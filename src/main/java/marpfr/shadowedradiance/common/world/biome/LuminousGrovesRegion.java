package marpfr.shadowedradiance.common.world.biome;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.ParameterUtils;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

import static terrablender.api.ParameterUtils.*;

public class LuminousGrovesRegion extends Region {

    public LuminousGrovesRegion(Identifier name, int weight) {
        super(name, RegionType.OVERWORLD, weight);
    }


    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        this.addModifiedVanillaOverworldBiomes(mapper, (builder -> {

            builder.replaceBiome(Biomes.SWAMP, SRBiomes.LUMINOUS_GROVES);

//            var points = new ParameterUtils.ParameterPointListBuilder()
//                    .temperature(Temperature.COOL, Temperature.NEUTRAL)
//                    .humidity(Humidity.DRY, Humidity.NEUTRAL, Humidity.WET)
//                    .continentalness(Continentalness.span(Continentalness.NEAR_INLAND, Continentalness.FAR_INLAND), Continentalness.span(Continentalness.MID_INLAND, Continentalness.FAR_INLAND))
//                    .erosion(Erosion.EROSION_2, Erosion.EROSION_3, Erosion.EROSION_4)
//                    .depth(Depth.SURFACE, Depth.FLOOR)
//                    .weirdness(Weirdness.MID_SLICE_NORMAL_ASCENDING, Weirdness.HIGH_SLICE_NORMAL_ASCENDING, Weirdness.PEAK_NORMAL, Weirdness.HIGH_SLICE_NORMAL_DESCENDING, Weirdness.MID_SLICE_NORMAL_DESCENDING)
//                    .build();
//
//            points.forEach(p -> builder.replaceBiome(p, SRBiomes.LUMINOUS_GROVES));
        }));
    }
}
