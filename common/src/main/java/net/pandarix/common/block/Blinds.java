package net.pandarix.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.pandarix.config.BeautifyConfig;
import net.pandarix.core.init.SoundInit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BiConsumer;

public class Blinds extends HorizontalDirectionalBlock
{
    public static final MapCodec<Blinds> BLINDS_MAP_CODEC = simpleCodec(Blinds::new);
    // Voxelshapes; Hidden = Blind not visible
    private static final VoxelShape OPEN_NORTH = Block.box(0, 13, 13, 16, 16, 16);
    private static final VoxelShape OPEN_SOUTH = Block.box(0, 13, 0, 16, 16, 3);
    private static final VoxelShape OPEN_WEST = Block.box(13, 13, 0, 16, 16, 16);
    private static final VoxelShape OPEN_EAST = Block.box(0, 13, 0, 3, 16, 16);
    private static final VoxelShape CLOSED_SOUTH = Block.box(0, 0, 0, 16, 16, 2);
    private static final VoxelShape CLOSED_NORTH = Block.box(0, 0, 13, 16, 16, 16);
    private static final VoxelShape CLOSED_EAST = Block.box(0, 0, 0, 3, 16, 16);
    private static final VoxelShape CLOSED_WEST = Block.box(13, 0, 0, 16, 16, 16);
    private static final VoxelShape SHAPE_HIDDEN = Block.box(0, 0, 0, 0, 0, 0);
    // Open = true if blinds are down; Hidden = true if blinds are closed and there
    // is a blind above
    public static final BooleanProperty OPEN = BooleanProperty.create("open");
    public static final BooleanProperty HIDDEN = BooleanProperty.create("hidden");

    // constructor
    public Blinds(Properties p_54120_)
    {
        super(p_54120_);
        this.registerDefaultState(this.defaultBlockState().setValue(OPEN, false).setValue(FACING, Direction.NORTH)
                .setValue(HIDDEN, false));
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec()
    {
        return BLINDS_MAP_CODEC;
    }

    @Override
    public boolean isCollisionShapeFullBlock(BlockState blockState, BlockGetter blockGetter, BlockPos pos)
    {
        return false;
    }

    @Override
    @NotNull
    public VoxelShape getBlockSupportShape(BlockState blockState, BlockGetter blockGetter, BlockPos pos)
    {
        return Shapes.empty();
    }

    @Override
    public boolean propagatesSkylightDown(BlockState blockState, BlockGetter blockGetter, BlockPos pos)
    {
        return true;
    }

    // check for facing placement, hidden is false per default
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(OPEN, false).setValue(HIDDEN, false);
    }

    // creating blockstates
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(OPEN, FACING, HIDDEN);
    }

    // INTERACTION
    // changes blockstates:
    // OPEN: open <-> closed
    // HIDDEN: false <-> true if below root
    @Override
    @NotNull
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pResult)
    {
        if (pLevel.isClientSide())
        {
            return InteractionResult.SUCCESS;
        }

        // stores last value of blind
        final boolean currentlyOpen = pState.getValue(OPEN);

        // if the blinds open from the root
        // search for the position of the topmost blind and set the pPos
        if (BeautifyConfig.OPENS_FROM_ROOT)
        {
            int step = 1;
            while (sameBlindType(pLevel, pPos.above(step), pState))
            {
                ++step;
            }
            pPos = pPos.above(step - 1);
        }

        {
            // changes clicked blind: open <-> closed
            pLevel.setBlock(pPos, pState.setValue(OPEN, !currentlyOpen), 3);

            // CODE BELOW IS DISABLED IF SEARCHRADIUS = 0
            // checks for blinds below clicked blind: open <-> closed, hidden=true
            if (BeautifyConfig.SEARCH_RADIUS > 0)
            {
                for (int offsetDown = 1; offsetDown <= BeautifyConfig.SEARCH_RADIUS; ++offsetDown)
                {
                    if (sameBlindType(pLevel, pPos.below(offsetDown), pState))
                    {
                        switchOpenUpdateHidden(pLevel, pPos.below(offsetDown), pState, false);
                    } else
                    {
                        break;
                    }
                }
            }

            if (BeautifyConfig.SEARCH_RADIUS > 0)
            {
                // FOR BLINDS ON NORTH-SOUTH AXIS
                if (pState.getValue(FACING) == Direction.NORTH || pState.getValue(FACING) == Direction.SOUTH)
                {

                    // checks blinds east of clicked blind
                    for (int offsetEast = 1; offsetEast <= (int) BeautifyConfig.SEARCH_RADIUS / 2; ++offsetEast)
                    {
                        if (sameBlindType(pLevel, pPos.east(offsetEast), pState))
                        {
                            // changes east blinds: open <-> closed
                            pLevel.setBlock(pPos.east(offsetEast), pState.setValue(OPEN, !currentlyOpen), 3);
                            // checks for blinds below east blinds: open <-> closed, hidden=true
                            for (int offsetDown = 1; offsetDown <= BeautifyConfig.SEARCH_RADIUS; ++offsetDown)
                            {
                                if (sameBlindType(pLevel, pPos.below(offsetDown).east(offsetEast), pState))
                                {
                                    switchOpenUpdateHidden(pLevel, pPos.below(offsetDown).east(offsetEast), pState,
                                            false);
                                } else
                                {
                                    break;
                                }
                            }
                        } else
                        {
                            break;
                        }
                    }

                    // checks blinds west of clicked blind
                    for (int offsetWest = 1; offsetWest <= BeautifyConfig.SEARCH_RADIUS / 2; ++offsetWest)
                    {
                        if (sameBlindType(pLevel, pPos.west(offsetWest), pState))
                        {
                            // changes west blinds: open <-> closed
                            pLevel.setBlock(pPos.west(offsetWest), pState.setValue(OPEN, !currentlyOpen), 3);
                            // checks for blinds below west blinds: open <-> closed, hidden=true
                            for (int offsetDown = 1; offsetDown <= BeautifyConfig.SEARCH_RADIUS; ++offsetDown)
                            {
                                if (sameBlindType(pLevel, pPos.below(offsetDown).west(offsetWest), pState))
                                {
                                    switchOpenUpdateHidden(pLevel, pPos.below(offsetDown).west(offsetWest), pState,
                                            false);
                                } else
                                {
                                    break;
                                }
                            }
                        } else
                        {
                            break;
                        }
                    }
                }

                // FOR BLINDS ON EAST-WEST AXIS
                if (pState.getValue(FACING) == Direction.EAST || pState.getValue(FACING) == Direction.WEST)
                {

                    // checks blinds north of clicked blind
                    for (int offsetNorth = 1; offsetNorth <= BeautifyConfig.SEARCH_RADIUS / 2; ++offsetNorth)
                    {
                        if (sameBlindType(pLevel, pPos.north(offsetNorth), pState))
                        {
                            // changes north blinds: open <-> closed
                            pLevel.setBlock(pPos.north(offsetNorth), pState.setValue(OPEN, !currentlyOpen), 3);
                            // checks for blinds below north blinds: open <-> closed, hidden=true
                            for (int offsetDown = 1; offsetDown <= BeautifyConfig.SEARCH_RADIUS; ++offsetDown)
                            {
                                if (sameBlindType(pLevel, pPos.below(offsetDown).north(offsetNorth), pState))
                                {
                                    switchOpenUpdateHidden(pLevel, pPos.below(offsetDown).north(offsetNorth),
                                            pState, false);
                                } else
                                {
                                    break;
                                }
                            }
                        } else
                        {
                            break;
                        }
                    }

                    // checks blinds south of clicked blind
                    for (int offsetSouth = 1; offsetSouth <= BeautifyConfig.SEARCH_RADIUS / 2; ++offsetSouth)
                    {
                        if (sameBlindType(pLevel, pPos.south(offsetSouth), pState))
                        {
                            // changes south blinds: open <-> closed
                            pLevel.setBlock(pPos.south(offsetSouth), pState.setValue(OPEN, !currentlyOpen), 3);
                            // checks for blinds below south blinds: open <-> closed, hidden=true
                            for (int offsetDown = 1; offsetDown <= BeautifyConfig.SEARCH_RADIUS; ++offsetDown)
                            {
                                if (sameBlindType(pLevel, pPos.below(offsetDown).south(offsetSouth), pState))
                                {
                                    switchOpenUpdateHidden(pLevel, pPos.below(offsetDown).south(offsetSouth),
                                            pState, false);
                                } else
                                {
                                    break;
                                }
                            }
                        } else
                        {
                            break;
                        }
                    }
                }
                pLevel.playSound(null, pPos,
                        currentlyOpen ? SoundInit.BLINDS_CLOSE.get() : SoundInit.BLINDS_OPEN.get(),
                        SoundSource.BLOCKS, 1, 1);
                return InteractionResult.SUCCESS;
            }
        }
        return super.useWithoutItem(pState, pLevel, pPos, pPlayer, pResult);
    }


    // returns: true/false if
    // block in pLevel at pPos is the same kind of blind
    // and facing is the same as pState
    private boolean sameBlindType(Level pLevel, BlockPos pPos, BlockState pState)
    {
        return pLevel.getBlockState(pPos).getBlock() == this
                && pLevel.getBlockState(pPos).getValue(FACING) == pState.getValue(FACING);
    }

    // method for changing the blockstates of blinds
    // if updateOnly is true, only HIDDEN is changed
    // if updateOnly is false, the blind will also open or close
    private void switchOpenUpdateHidden(Level pLevel, BlockPos pPos, BlockState pState, boolean updateOnly)
    {
        if (updateOnly)
        {
            pLevel.setBlock(pPos, pState.setValue(HIDDEN, false), 3);
            return;
        }

        if (!pState.getValue(OPEN))
        {
            pLevel.setBlock(pPos, pState.setValue(OPEN, true).setValue(HIDDEN, false), 3);
        } else
        {
            pLevel.setBlock(pPos, pState.setValue(OPEN, false).setValue(HIDDEN, true), 3);
        }
    }

    // method to prevent hidden blinds from being unaccessible
    // after root block is destroyed.
    // updates blind below the destroyed block.
    @Override
    public void playerDestroy(Level level, Player player, BlockPos blockPos, BlockState blockState, @Nullable BlockEntity blockEntity, ItemStack itemStack)
    {
        if (sameBlindType(level, blockPos.below(), blockState))
        {
            switchOpenUpdateHidden(level, blockPos.below(), blockState, true);
        }
        super.playerDestroy(level, player, blockPos, blockState, blockEntity, itemStack);
    }

    @Override
    protected void onExplosionHit(BlockState blockState, Level level, BlockPos blockPos, Explosion explosion, BiConsumer<ItemStack, BlockPos> biConsumer)
    {
        if (sameBlindType(level, blockPos.below(), blockState))
        {
            switchOpenUpdateHidden(level, blockPos.below(), blockState, true);
        }
        super.onExplosionHit(blockState, level, blockPos, explosion, biConsumer);
    }

    // Shape switch
    // hidden = invisible model
    // OPEN_X = models of blinds that are down
    // CLOSED_X= models of blinds that are up
    @Override
    @NotNull
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context)
    {
        if (state.getValue(HIDDEN))
        {
            return SHAPE_HIDDEN;
        }

        if (!state.getValue(OPEN))
        {
            return switch (state.getValue(FACING))
            {
                case NORTH -> OPEN_NORTH;
                case SOUTH -> OPEN_SOUTH;
                case WEST -> OPEN_WEST;
                case EAST -> OPEN_EAST;
                default -> OPEN_NORTH;
            };
        }

        return switch (state.getValue(FACING))
        {
            case NORTH -> CLOSED_NORTH;
            case SOUTH -> CLOSED_SOUTH;
            case WEST -> CLOSED_WEST;
            case EAST -> CLOSED_EAST;
            default -> CLOSED_NORTH;
        };
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components, TooltipFlag flag)
    {
        if (!Screen.hasShiftDown())
        {
            components.add(Component.translatable("tooltip.shift").withStyle(ChatFormatting.YELLOW));
        } else
        {
            components.add(Component.translatable("blinds.description1").withStyle(ChatFormatting.GRAY));
            components.add(Component.translatable("blinds.description2").withStyle(ChatFormatting.GRAY));
        }
        super.appendHoverText(stack, context, components, flag);
    }
}
