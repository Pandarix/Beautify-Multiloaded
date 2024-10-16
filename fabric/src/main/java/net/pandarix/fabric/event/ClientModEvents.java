package net.pandarix.fabric.event;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.pandarix.particle.ParticleInit;
import net.pandarix.particle.custom.GlowEssenceParticles;

@Environment(EnvType.CLIENT)
public class ClientModEvents implements ClientModInitializer
{

    @Override
    public void onInitializeClient()
    {
        ParticleInit.PARTICLE_TYPES.register();
        ParticleFactoryRegistry.getInstance().register(ParticleInit.GLOWESSENCE_PARTICLES.get(), GlowEssenceParticles.Provider::new);
    }
}
