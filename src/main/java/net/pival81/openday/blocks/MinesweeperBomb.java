package net.pival81.openday.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.pival81.openday.Openday;

public class MinesweeperBomb extends Block {

    public static final IntProperty PROPERTY = IntProperty.of("property", 0, 2);
    public static final IntProperty SOURCEX = IntProperty.of("sourcex", 0, 255);
    public static final IntProperty SOURCEY = IntProperty.of("sourcey", 0, 255);
    public static final IntProperty SOURCEZ = IntProperty.of("sourcez", 0, 255);

    public MinesweeperBomb() {
        super(Settings.of(Material.METAL).strength(3.0F, 4.8F));
        setDefaultState(getDefaultState()
                .with(PROPERTY, 0)
                .with(SOURCEX, 0)
                .with(SOURCEY, 0)
                .with(SOURCEZ, 0)
        );
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager){
        stateManager.add(PROPERTY, SOURCEX, SOURCEY, SOURCEZ);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult blockHitResult) {
        int x = state.get(SOURCEX);
        int y = state.get(SOURCEY);
        int z = state.get(SOURCEZ);
        for(int i=1;i< MinesweeperController.x;i++){
            for(int j=0;j<MinesweeperController.y;j++){
                BlockPos newpos = new BlockPos(x + i, y + j, z);
                BlockState newbs = world.getBlockState(newpos);
                if(newbs.getBlock() instanceof MinesweeperBomb){
                    if(!player.isSneaking()){
                        world.setBlockState(pos, state.with(PROPERTY, 1), 2);
                    } else {
                        world.setBlockState(pos, state.with(PROPERTY, 2), 2);
                    }
                } else if (newbs.getBlock() instanceof MinesweeperTile){
                    ((MinesweeperTile) newbs.getBlock()).doStuff(newbs, world, newpos);
                }
            }
        }
        return ActionResult.FAIL;
    }
}
