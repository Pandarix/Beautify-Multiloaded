package net.pandarix.core.init;

import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.pandarix.BeautifyCommon;

public final class ItemGroupInit
{

    public static final Registrar<CreativeModeTab> CREATIVE_MODE_TABS = BeautifyCommon.REGISTRIES.get().get(Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> BEAUTIFY_TAB = CREATIVE_MODE_TABS.register(
            BeautifyCommon.createResource("beautify"),
            () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 1).icon(() -> new ItemStack(Items.ROSE_BUSH))
                    .title(Component.translatable("itemGroup.beautify"))
                    .build());

    public static void register()
    {
        BeautifyCommon.logRegistryEvent(CREATIVE_MODE_TABS);
    }
}
