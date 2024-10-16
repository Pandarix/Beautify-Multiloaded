package net.pandarix.beautify.platform;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import net.pandarix.beautify.platform.services.IPlatformHelper;

public class ForgePlatformHelper implements IPlatformHelper
{

    @Override
    public String getPlatformName()
    {

        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId)
    {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment()
    {

        return !FMLLoader.isProduction();
    }
}