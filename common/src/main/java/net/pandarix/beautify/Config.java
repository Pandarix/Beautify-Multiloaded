package net.pandarix.beautify;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config
{
    public static ModConfigSpec.IntValue SEARCHRADIUS;
    public static ModConfigSpec.BooleanValue OPENS_FROM_ROOT;
    public static ModConfigSpec.IntValue BOTANIST_SPAWN_WEIGHT;
    public static ModConfigSpec SPEC;

    static
    {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        setupConfig(builder);
        SPEC = builder.build();
    }

    private static void setupConfig(ModConfigSpec.Builder builder)
    {
        builder.push("Blinds");

        SEARCHRADIUS = builder.comment(
                        "Searches X-Blocks below and X/2 to the sides of the clicked blind for others and opens/closes them too")
                .defineInRange("searchRadius", 6, 0, 100);

        OPENS_FROM_ROOT = builder.comment(
                "Opens blinds from the topmost blind on if true").define("opensFromRoot", true);

        builder.pop();

        builder.push("Generation");

        BOTANIST_SPAWN_WEIGHT = builder.comment(
                        "Defines the chance of a Botanist Villager House inside a village")
                .defineInRange("botanistSpawnWeight", 2, 0, Integer.MAX_VALUE);

        builder.pop();
    }
}
