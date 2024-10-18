package net.pandarix.core.init;

import com.google.common.collect.ImmutableSet;
import dev.architectury.registry.level.entity.trade.TradeRegistry;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.pandarix.BeautifyCommon;

import java.util.Arrays;
import java.util.Objects;

public class ModVillagers
{
    public static final Registrar<PoiType> POI_TYPES = BeautifyCommon.REGISTRIES.get().get(Registries.POINT_OF_INTEREST_TYPE);
    public static final Registrar<VillagerProfession> VILLAGER_PROFESSIONS = BeautifyCommon.REGISTRIES.get().get(Registries.VILLAGER_PROFESSION);

    public static final RegistrySupplier<PoiType> BOTANIST_WORKBENCH_POI = POI_TYPES.register(
            BeautifyCommon.createResource("botanist_workbench_poi"), () -> new PoiType(
                    ImmutableSet.copyOf(BlockInit.BOTANIST_WORKBENCH.get().getStateDefinition().getPossibleStates()), 1,
                    1));

    public static final RegistrySupplier<VillagerProfession> BOTANIST = VILLAGER_PROFESSIONS.register(
            BeautifyCommon.createResource("botanist"),
            () -> new VillagerProfession("botanist", holder -> holder.value() == BOTANIST_WORKBENCH_POI.get(), holder -> holder.value() == BOTANIST_WORKBENCH_POI.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.CAVE_VINES_PLACE));

    public static void registerTrades()
    {
        TradeRegistry.registerVillagerTrade(
                BOTANIST.get(), 1,
                new VillagerTrades.ItemsForEmeralds(BlockInit.HANGING_POT.get().asItem(), 2, 1, 16, 6, .02F),
                new VillagerTrades.ItemsForEmeralds(Items.FLOWER_POT, 3, 1, 12, 5, .02F),
                new VillagerTrades.ItemsForEmeralds(Items.FLOWER_POT, 3, 1, 12, 5, .02F)
        );

        TradeRegistry.registerVillagerTrade(
                BOTANIST.get(), 2,

                Arrays.stream(WoodType.values().toArray())
                        .map(type ->
                        {
                            Block block = BlockInit.BLOCKS.get(BeautifyCommon.createResource(((WoodType) type).name() + "_trellis"));
                            return block != null ? new VillagerTrades.ItemsForEmeralds(block.asItem(), 1, 2, 16, 3, .02F) : null;
                        })
                        .filter(Objects::nonNull)
                        .toArray(VillagerTrades.ItemsForEmeralds[]::new)
        );

        TradeRegistry.registerVillagerTrade(
                BOTANIST.get(), 3,
                new VillagerTrades.ItemsForEmeralds(Items.LILY_PAD, 1, 4, 8, 5, .02F),
                new VillagerTrades.ItemsForEmeralds(Items.SPORE_BLOSSOM, 3, 1, 12, 10, .02F),
                new VillagerTrades.ItemsForEmeralds(Items.BIG_DRIPLEAF, 4, 3, 6, 9, .02F),
                new VillagerTrades.ItemsForEmeralds(Items.SMALL_DRIPLEAF, 3, 4, 6, 9, .02F)
        );

        TradeRegistry.registerVillagerTrade(
                BOTANIST.get(), 4,
                new VillagerTrades.ItemsForEmeralds(Items.MOSS_BLOCK, 1, 2, 48, 3, .02F),
                new VillagerTrades.ItemsForEmeralds(Items.FLOWERING_AZALEA, 2, 1, 16, 7, .02F)
        );

        TradeRegistry.registerVillagerTrade(
                BOTANIST.get(), 5,
                new VillagerTrades.ItemsForEmeralds(Items.ROOTED_DIRT, 1, 4, 24, 3, .02F),
                new VillagerTrades.ItemsForEmeralds(Items.HANGING_ROOTS, 1, 3, 10, 7, .02F)
        );
    }

    public static void register()
    {
        BeautifyCommon.logRegistryEvent(POI_TYPES);
        BeautifyCommon.logRegistryEvent(VILLAGER_PROFESSIONS);

        BOTANIST.listen(prof -> registerTrades());
    }
}
