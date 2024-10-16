package net.pandarix.particle;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.pandarix.BeautifyCommon;

public class ParticleInit
{
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister
            .create(BeautifyCommon.MOD_ID, Registries.PARTICLE_TYPE);

    public static final RegistrySupplier<SimpleParticleType> GLOWESSENCE_PARTICLES = PARTICLE_TYPES
            .register("glowessence_particles", () -> new SimpleParticleType(true));
}