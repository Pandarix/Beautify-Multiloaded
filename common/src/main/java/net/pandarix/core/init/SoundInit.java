package net.pandarix.core.init;

import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.pandarix.BeautifyCommon;

public class SoundInit
{

    public static final Registrar<SoundEvent> SOUND_EVENTS = BeautifyCommon.REGISTRIES.get().get(Registries.SOUND_EVENT);

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
        return SOUND_EVENTS.register(BeautifyCommon.createResource(name), () -> SoundEvent.createVariableRangeEvent(BeautifyCommon.createResource(name)));
    }

    public static void register()
    {
        BeautifyCommon.logRegistryEvent(SOUND_EVENTS);
    }
}