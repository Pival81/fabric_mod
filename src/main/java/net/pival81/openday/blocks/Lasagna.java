package net.pival81.openday.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import static net.minecraft.state.property.Properties.WATERLOGGED;

public class Lasagna extends Block implements Waterloggable {

    public static final IntProperty BITES = IntProperty.of("bites", 1, 4);

    public Lasagna(){
        super(Settings.of(Material.CAKE));
        setDefaultState(getStateManager().getDefaultState().with(BITES, 4).with(WATERLOGGED, Boolean.FALSE));
    }


    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager){
        stateManager.add(BITES, WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return (Boolean) state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            ItemStack itemStack = player.getStackInHand(hand);
            if (this.tryEat(world, pos, state, player) == ActionResult.SUCCESS) {
                return ActionResult.SUCCESS;
            }

            if (itemStack.isEmpty()) {
                return ActionResult.CONSUME;
            }
        }

        return this.tryEat(world, pos, state, player);
    }

    private ActionResult tryEat(IWorld world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!player.canConsume(false)) {
            return ActionResult.PASS;
        } else {
            //player.incrementStat(Stats.EAT_CAKE_SLICE); //This is no cake, cakes doesn't deserve to take the credit for it.
            player.getHungerManager().add(3, 0.1F);
            int i = (Integer)state.get(BITES);
            if (i > 1) {
                world.setBlockState(pos, (BlockState)state.with(BITES, i - 1), 3);
            } else {
                world.removeBlock(pos, false);
            }

            return ActionResult.SUCCESS;
        }
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext ePos) {
        switch(state.get(BITES)){
            case 4:
                return Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D);
            case 3:
                return VoxelShapes.union(Block.createCuboidShape(1.0D, 0.0D, 1.0D, 8.0D, 8.0D, 15.0D),
                                        Block.createCuboidShape(1.0D, 0.0D, 8.0D, 15.0D, 8.0D, 15.0D)
                );
            case 2:
                return Block.createCuboidShape(1.0D, 0.0D, 1.0D, 8.0D, 8.0D, 15.0D);
            case 1:
                return Block.createCuboidShape(1.0D, 0.0D, 8.0D, 8.0D, 8.0D, 15.0D);
            default:
                return Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D);
        }
    }
}
