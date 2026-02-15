package marpfr.shadowedradiance.common.particle;

import marpfr.shadowedradiance.ShadowedRadiance;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class SRParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, ShadowedRadiance.MODID);

    public static final Supplier<SimpleParticleType> LUX_TRANSFER_PARTICLE =
            PARTICLE_TYPES.register("lux_transfer_particle", () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
