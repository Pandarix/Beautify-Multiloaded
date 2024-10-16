package net.pandarix.beautify;

import fuzs.forgeconfigapiport.forge.api.neoforge.v4.NeoForgeConfigRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@SuppressWarnings("removal")
@Mod(Constants.MOD_ID)
public class BeautifyForge
{

    public BeautifyForge()
    {
        NeoForgeConfigRegistry.INSTANCE.register(ModConfig.Type.COMMON, Config.SPEC, Constants.MOD_ID + "-common.toml");
        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.

        // Use Forge to bootstrap the Common mod.
        Constants.LOG.info("Hello Forge world!");
        BeautifyCommon.init();
    }
}