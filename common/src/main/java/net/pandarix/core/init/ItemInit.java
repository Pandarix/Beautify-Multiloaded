package net.pandarix.core.init;

import dev.architectury.registry.registries.Registrar;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.pandarix.BeautifyCommon;

public final class ItemInit
{

    public static final Registrar<Item> ITEMS = BeautifyCommon.REGISTRIES.get().get(Registries.ITEM);

    public static void register()
    {
        BeautifyCommon.logRegistryEvent(ITEMS);
    }
}
