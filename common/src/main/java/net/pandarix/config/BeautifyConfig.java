package net.pandarix.config;

import com.teamresourceful.resourcefulconfig.api.annotations.*;
import com.teamresourceful.resourcefulconfig.api.types.options.EntryType;
import net.pandarix.BeautifyCommon;

@ConfigInfo.Provider(BeautifyConfigInfoProvider.class)
@Config(value = BeautifyCommon.MOD_ID)
public final class BeautifyConfig
{
    @ConfigOption.Separator(value = "Blinds")
    @ConfigEntry(
            id = "searchRadius",
            type = EntryType.INTEGER,
            translation = "config.beautify.searchRadius"
    )
    @ConfigOption.Slider()
    @ConfigOption.Range(min = 0, max = 24)
    @Comment("Searches X-Blocks below and X/2 to the sides of the clicked blind for others and opens/closes them too")
    public static int SEARCH_RADIUS = 6;

    @ConfigEntry(
            id = "opensFromRoot",
            type = EntryType.BOOLEAN,
            translation = "config.beautify.opensFromRoot"
    )
    @Comment("Opens blinds from the topmost blind on if true")
    public static boolean OPENS_FROM_ROOT = true;

    @ConfigOption.Separator(value = "Worldgen")
    @ConfigEntry(
            id = "botanistSpawnWeight",
            type = EntryType.INTEGER,
            translation = "config.beautify.botanistSpawnWeight"
    )
    @ConfigOption.Range(min = 0, max = Integer.MAX_VALUE)
    @Comment("Defines the chance of a Botanist Villager House inside a village")
    public static int BOTANIST_SPAWN_WEIGHT = 2;

}