package net.pandarix.neoforge.event;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.pandarix.BeautifyCommon;
import net.pandarix.particle.ParticleInit;
import net.pandarix.particle.custom.GlowEssenceParticles;

@EventBusSubscriber(modid = BeautifyCommon.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents
{
    // minecraft is autoclosing
    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event)
    {
        event.registerSpriteSet(ParticleInit.GLOWESSENCE_PARTICLES.get(),
                GlowEssenceParticles.Provider::new);
    }
}
