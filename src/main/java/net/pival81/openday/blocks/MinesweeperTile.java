package net.pival81.openday.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MinesweeperTile extends Block {


    public static final IntProperty NUMBER = IntProperty.of("number", 0, 8);
    public static final BooleanProperty HIDDEN = BooleanProperty.of("hidden");

    public MinesweeperTile() {
        super(Settings.of(Material.METAL).strength(3.0F, 4.8F));
        setDefaultState(getDefaultState().with(NUMBER, 0).with(HIDDEN, true));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager){
        stateManager.add(NUMBER, HIDDEN);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult blockHitResult) {
        doStuff(state, world, pos);
        return ActionResult.SUCCESS;
    }

    public void doStuff(BlockState state, World world, BlockPos pos){
        world.setBlockState(pos, state.with(HIDDEN, false), 2);
        for(int i=-1;i<2;i++){
            for(int j=-1;j<2;j++){
                BlockPos newpos = new BlockPos(pos.getX()+i, pos.getY()+j, pos.getZ());
                BlockState bs = world.getBlockState(newpos);
                if(bs.getBlock() instanceof MinesweeperTile){
                    if(bs.get(HIDDEN) && state.get(NUMBER) == 0) {
                        ((MinesweeperTile) bs.getBlock()).doStuff(bs, world, newpos);
                    }
                }
            }
        }
    }

}
