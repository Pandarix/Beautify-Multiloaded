package net.pandarix.common.block;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.ChainBlock;

import java.util.List;

public class Rope extends ChainBlock
{
    public Rope(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.valueOf(false))
                .setValue(AXIS, Direction.Axis.Y));
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components, TooltipFlag flag)
    {
        if (!Screen.hasShiftDown())
        {
            components.add(Component.translatable("tooltip.shift").withStyle(ChatFormatting.YELLOW));
        }

        if (Screen.hasShiftDown())
        {
            components.add(Component.translatable("rope.description1").withStyle(ChatFormatting.GRAY));
            components.add(Component.translatable("rope.description2").withStyle(ChatFormatting.GRAY));
        }
        super.appendHoverText(stack, context, components, flag);
    }
}
