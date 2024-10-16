package net.pandarix.world.structure;

import net.pandarix.BeautifyCommon;

@SuppressWarnings("removal")
public class ModStructuresMain
{

    public static final String MODID = BeautifyCommon.MOD_ID;

    public ModStructuresMain()
    {
        // For registration and init stuff.
        ModStructures.DEFERRED_REGISTRY_STRUCTURE.register();
    }

    public static void register()
    {
        ModStructures.DEFERRED_REGISTRY_STRUCTURE.register();
    }
}