package net.pandarix.beautify;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config
{
    public static ModConfigSpec.DoubleValue TEST;
    public static ModConfigSpec SPEC;

    static
    {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        setupConfig(builder);
        SPEC = builder.build();
    }

    private static void setupConfig(ModConfigSpec.Builder builder)
    {
        builder.push("test");

        TEST = builder
                .comment("Testcomment. Default: 0.75")
                .defineInRange("Test", 0.75, 0.0, 1.0);

        builder.pop();
    }
}
