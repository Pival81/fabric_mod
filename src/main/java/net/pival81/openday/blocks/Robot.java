package net.pival81.openday.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.pival81.openday.Openday;
import net.pival81.openday.blockentities.RobotBlockEntity;

public class Robot extends Block implements BlockEntityProvider {


    public Robot() {
        super(Settings.of(Material.METAL).strength(3.0F, 4.8F));
        setDefaultState(getDefaultState().with(Properties.FACING, Direction.NORTH));
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView){
        return new RobotBlockEntity();
    }

    @Override
    protected void appendProperties(StateManager.Builder<net.minecraft.block.Block, BlockState> stateManager){
        stateManager.add(Properties.FACING);
    }

    @Override
    public void onBroken(IWorld world, BlockPos pos, BlockState state){
        Openday.serialBlock = null;
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult blockHitResult) {
        if(!player.isSneaking()){
            System.out.println(Openday.port);
        } else {
            System.out.println(Openday.serialBlock);
        }
        return ActionResult.SUCCESS;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext ePos) {
        return Block.createCuboidShape(2, 2, 2, 14, 14, 14);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(Properties.FACING, ctx.getPlayerFacing());
    }
}
