package net.pandarix.core.init;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.pandarix.BeautifyCommon;

public class SoundInit
{
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister
            .create(BeautifyCommon.MOD_ID, Registries.SOUND_EVENT);

    //BOOKSTACK
    public static final RegistrySupplier<SoundEvent> BOOKSTACK_NEXT = registerSoundEvent("block.bookstack_next");
    public static final RegistrySupplier<SoundEvent> BOOKSTACK_BREAK = registerSoundEvent("block.bookstack_break");
    public static final RegistrySupplier<SoundEvent> BOOKSTACK_STEP = registerSoundEvent("block.bookstack_step");
    public static final RegistrySupplier<SoundEvent> BOOKSTACK_PLACE = registerSoundEvent("block.bookstack_place");
    public static final RegistrySupplier<SoundEvent> BOOKSTACK_HIT = registerSoundEvent("block.bookstack_hit");
    public static final RegistrySupplier<SoundEvent> BOOKSTACK_FALL = registerSoundEvent("block.bookstack_fall");

    //BLINDS
    public static final RegistrySupplier<SoundEvent> BLINDS_OPEN = registerSoundEvent("block.blinds_open");
    public static final RegistrySupplier<SoundEvent> BLINDS_CLOSE = registerSoundEvent("block.blinds_close");

    private static RegistrySupplier<SoundEvent> registerSoundEvent(String name)
    {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(BeautifyCommon.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }
}