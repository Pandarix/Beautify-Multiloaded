package net.pandarix.particle;

import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.pandarix.BeautifyCommon;

public class ParticleInit
{
    public static final Registrar<ParticleType<?>> PARTICLE_TYPES = BeautifyCommon.REGISTRIES.get().get(Registries.PARTICLE_TYPE);

    public static final RegistrySupplier<SimpleParticleType> GLOWESSENCE_PARTICLES = PARTICLE_TYPES
            .register(BeautifyCommon.createResource("glowessence_particles"), () -> new SimpleParticleType(true));

    public static void register()
    {
        BeautifyCommon.logRegistryEvent(PARTICLE_TYPES);
    }
}