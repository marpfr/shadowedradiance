package marpfr.shadowedradiance.datagen;

import marpfr.shadowedradiance.ShadowedRadiance;
import marpfr.shadowedradiance.common.particle.SRParticles;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.data.ParticleDescriptionProvider;

public class SRParticleDescriptionProvider extends ParticleDescriptionProvider {

    protected SRParticleDescriptionProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void addDescriptions() {
        this.spriteSet(SRParticles.LUX_TRANSFER_PARTICLE.get(),
                Identifier.fromNamespaceAndPath(ShadowedRadiance.MODID, "lux_transfer_particle_0"),
                Identifier.fromNamespaceAndPath(ShadowedRadiance.MODID, "lux_transfer_particle_1"),
                Identifier.fromNamespaceAndPath(ShadowedRadiance.MODID, "lux_transfer_particle_2"),
                Identifier.fromNamespaceAndPath(ShadowedRadiance.MODID, "lux_transfer_particle_3"),
                Identifier.fromNamespaceAndPath(ShadowedRadiance.MODID, "lux_transfer_particle_4"),
                Identifier.fromNamespaceAndPath(ShadowedRadiance.MODID, "lux_transfer_particle_5"),
                Identifier.fromNamespaceAndPath(ShadowedRadiance.MODID, "lux_transfer_particle_6"),
                Identifier.fromNamespaceAndPath(ShadowedRadiance.MODID, "lux_transfer_particle_7")
        );
    }
}
