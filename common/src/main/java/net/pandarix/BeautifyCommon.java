package net.pandarix;

import com.google.common.base.Suppliers;
import com.mojang.logging.LogUtils;
import com.teamresourceful.resourcefulconfig.api.loader.Configurator;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrarManager;
import net.minecraft.resources.ResourceLocation;
import net.pandarix.config.BeautifyConfig;
import net.pandarix.core.init.*;
import net.pandarix.particle.ParticleInit;
import org.apache.commons.lang3.text.WordUtils;
import org.slf4j.Logger;

import java.util.function.Supplier;

public final class BeautifyCommon
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "beautify";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final Configurator CONFIGURATOR = new Configurator(MOD_ID);
    public static final Supplier<RegistrarManager> REGISTRIES = Suppliers.memoize(() -> RegistrarManager.get(BeautifyCommon.MOD_ID));

    public static void init()
    {
        CONFIGURATOR.register(BeautifyConfig.class);

        ParticleInit.register();
        SoundInit.register();

        BlockInit.register();
        ItemInit.register();
        ItemGroupInit.register();
        ModVillagers.register();
    }

    public static ResourceLocation createResource(String path)
    {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static void logRegistryEvent(Registrar<?> registry)
    {
        LOGGER.info("Registering {} for {}",
                WordUtils.capitalize(registry.key().location().getPath().replace("_", " ") + "s"), MOD_ID);
    }
}
