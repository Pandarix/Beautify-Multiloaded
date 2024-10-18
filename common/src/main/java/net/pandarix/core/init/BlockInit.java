package net.pandarix.core.init;

import dev.architectury.registry.fuel.FuelRegistry;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.pandarix.BeautifyCommon;
import net.pandarix.common.block.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class BlockInit
{
    // REGISTRY ────────────────────────────────────────────────────────────────────────
    public static final Registrar<Block> BLOCKS = BeautifyCommon.REGISTRIES.get().get(Registries.BLOCK);
    public static final List<Block> TEST = new ArrayList<>();

    // BLOCKS ──────────────────────────────────────────────────────────────────────────

    // ──────── MISC ───────────────────────────────────────────────────────────────────
    public static final RegistrySupplier<Block> BOTANIST_WORKBENCH = registerBlockWithItem(
            "botanist_workbench", () -> new BotanistWorkbench(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE)
            ), 300);

    public static final RegistrySupplier<Block> BOOKSTACK = registerBlockWithItem(
            "bookstack", () -> new BookStack(
                    BlockBehaviour.Properties.of().strength(0.2F, 0.2F).sound(SoundType.WOOL).noOcclusion()
            ), 0);

    public static final RegistrySupplier<Block> ROPE = registerBlockWithItem(
            "rope", () -> new Rope(
                    BlockBehaviour.Properties.of().strength(0.2F, 0.2F).sound(SoundType.WOOL).noOcclusion()
            ), 100);

    public static final RegistrySupplier<Block> HANGING_POT = registerBlockWithItem(
            "hanging_pot", () -> new HangingPot(
                    BlockBehaviour.Properties.of().noOcclusion().strength(0.1f, 0.1f).sound(SoundType.STONE).lightLevel((state) ->
                    {
                        if (state.getValue(HangingPot.POTFLOWER) == 15)
                        {
                            return 7;
                        } else if (state.getValue(HangingPot.POTFLOWER) == 22)
                        {
                            return 14;
                        } else
                        {
                            return 0;
                        }
                    })
            ), 0);

    // ──────── WOODEN (TRELLIS, BLINDS, PICTURE FRAMES) ────────────────────────────────
    static
    {
        WoodType.values().forEach(type ->
        {
            registerBlockWithItem(type.name() + "_trellis", () -> new Trellis(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(0.3F, 0.3F)
                            .sound(SoundType.BAMBOO).noOcclusion()
            ), 300);

            registerBlockWithItem(type.name() + "_blinds", () -> new Blinds(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.JUNGLE_PLANKS).noOcclusion()
                            .strength(0.4f, 0.4f).sound(SoundType.WOOD)
            ), 300);

            registerBlockWithItem(type.name() + "_picture_frame", () -> new PictureFrame(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_PLANKS).noOcclusion()
                            .strength(0.1f, 0.1f).sound(SoundType.WOOD).noOcclusion()
            ), 300);
        });
    }

    // ──────── MISC (TRELLIS, BLINDS, PICTURE FRAMES) ─────────────────────────────────
    public static final RegistrySupplier<Block> IRON_BLINDS = registerBlockWithItem(
            "iron_blinds", () -> new Blinds(
                    BlockBehaviour.Properties.of().mapColor(MapColor.METAL).noOcclusion()
                            .strength(0.4f, 0.4f).sound(SoundType.CHAIN).requiresCorrectToolForDrops()
            ), 0);

    public static final RegistrySupplier<Block> QUARTZ_PICTURE_FRAME = registerBlockWithItem(
            "quartz_picture_frame", () -> new PictureFrame(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.QUARTZ_BLOCK)
                            .noOcclusion().strength(0.1f, 0.1f).sound(SoundType.STONE).noOcclusion()
            ), 0);

    // ──────── LAMPS ──────────────────────────────────────────────────────────────────
    public static final RegistrySupplier<Block> LAMP_LIGHT_BULB = registerBlockWithItem(
            "lamp_light_bulb", () -> new LampLightBulb(
                    BlockBehaviour.Properties.of().mapColor(MapColor.METAL).noOcclusion().strength(0.2f, 0.2f).sound(SoundType.LANTERN)
                            .lightLevel((state) ->
                            {
                                if (state.getValue(LampLightBulb.ON))
                                {
                                    return 14;
                                } else
                                {
                                    return 0;
                                }
                            })), 0);

    public static final RegistrySupplier<Block> LAMP_BAMBOO = registerBlockWithItem(
            "lamp_bamboo", () -> new LampBamboo(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion()
                    .strength(0.1f, 0.1f).sound(SoundType.SCAFFOLDING).lightLevel((state) ->
                    {
                        if (state.getValue(LampBamboo.ON))
                        {
                            return 14;
                        } else
                        {
                            return 0;
                        }
                    })
            ), 50);

    public static final RegistrySupplier<Block> LAMP_JAR = registerBlockWithItem(
            "lamp_jar", () -> new LampJar(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).noOcclusion()
                            .strength(0.05f, 0.05f).sound(SoundType.GLASS).lightLevel((state) ->
                            {
                                final int fill = state.getValue(LampJar.FILL_LEVEL);
                                return switch (fill)
                                {
                                    case 0 -> 0;
                                    case 5 -> 8;
                                    case 10 -> 11;
                                    case 15 -> 14;
                                    default -> 0;
                                };
                            })
            ), 0);

    public static final RegistrySupplier<Block> LAMP_CANDELABRA = registerBlockWithItem(
            "lamp_candelabra", () -> new LampCandelabra(
                    BlockBehaviour.Properties.of().mapColor(MapColor.METAL).noOcclusion()
                            .strength(0.2f, 0.2f).sound(SoundType.LANTERN).lightLevel((state) ->
                            {
                                if (state.getValue(LampCandelabra.ON))
                                {
                                    return 14;
                                } else
                                {
                                    return 0;
                                }
                            })
            ), 0);


    // ──────── CANDELABRAS ────────────────────────────────────────────────────────────
    static
    {
        Arrays.stream(DyeColor.values()).
                forEach(dyeColor ->
                        registerBlockWithItem(
                                "lamp_candelabra_" + dyeColor.getName(), () -> new LampCandelabra(
                                        BlockBehaviour.Properties.of().mapColor(MapColor.METAL).noOcclusion()
                                                .strength(0.2f, 0.2f).sound(SoundType.LANTERN).lightLevel((state) ->
                                                {
                                                    if (state.getValue(LampCandelabra.ON))
                                                    {
                                                        return 14;
                                                    } else
                                                    {
                                                        return 0;
                                                    }
                                                })
                                ), 0));
    }

    // REGISTERING ─────────────────────────────────────────────────────────────────────
    private static RegistrySupplier<Block> registerBlockWithItem(String name, Supplier<Block> supplier, int fuelValue)
    {
        ResourceLocation id = BeautifyCommon.createResource(name);

        RegistrySupplier<Block> block = BLOCKS.register(id, supplier);
        RegistrySupplier<Item> item = registerItem(id, () ->
                new BlockItem(block.get(), new Item.Properties().arch$tab(ItemGroupInit.BEAUTIFY_TAB)), fuelValue);

        return block;
    }

    private static RegistrySupplier<Item> registerItem(ResourceLocation id, Supplier<Item> supplier, int fuelValue)
    {
        return ItemInit.ITEMS.register(id, () ->
        {
            Item item = supplier.get();
            if (fuelValue > 0)
            {
                FuelRegistry.register(fuelValue, item);
            }
            return item;
        });
    }

    public static void register()
    {
        BeautifyCommon.logRegistryEvent(BLOCKS);
    }
}
