package net.pandarix;

import com.mojang.logging.LogUtils;
import com.teamresourceful.resourcefulconfig.api.loader.Configurator;
import net.minecraft.resources.ResourceLocation;
import net.pandarix.config.BeautifyConfig;
import net.pandarix.core.init.BlockInit;
import net.pandarix.core.init.ItemGroupInit;
import net.pandarix.core.init.ItemInit;
import net.pandarix.core.init.SoundInit;
import org.slf4j.Logger;

public final class BeautifyCommon
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "beautify";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final Configurator CONFIGURATOR = new Configurator(MOD_ID);

    public static void init()
    {
        CONFIGURATOR.register(BeautifyConfig.class);

        SoundInit.SOUND_EVENTS.register();
        BlockInit.BLOCKS.register();
        ItemInit.ITEMS.register();
        ItemInit.registerFuel();
        ItemGroupInit.CREATIVE_MODE_TABS.register();
        // Write common init code here.
    }

    public static ResourceLocation createResource(String path)
    {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
