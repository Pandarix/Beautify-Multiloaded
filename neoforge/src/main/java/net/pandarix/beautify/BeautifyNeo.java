package net.pandarix.beautify;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(Constants.MOD_ID)
public class BeautifyNeo
{

    public BeautifyNeo(ModContainer container)
    {
        container.registerConfig(ModConfig.Type.COMMON, Config.SPEC, Constants.MOD_ID + "-common.toml");
        // This method is invoked by the NeoForge mod loader when it is ready
        // to load your mod. You can access NeoForge and Common code in this
        // project.

        // Use NeoForge to bootstrap the Common mod.
        Constants.LOG.info("Hello NeoForge world!");
        BeautifyCommon.init();

    }
}