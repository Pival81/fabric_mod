package net.pival81.openday.blocks;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MinesweeperBlock extends Block {
    public MinesweeperBlock(Settings settings) {
        super(settings);
    }

    public void showAll(BlockPos pos, World world){
        for(int i=-1;i<2;i++){
            for(int j=-1;j<2; j++){
                BlockPos newpos = new BlockPos(pos.getX()+i, pos.getY()+j, pos.getZ());
                BlockState newbs = world.getBlockState(newpos);
                if(newbs.getBlock() instanceof MinesweeperTile && newbs.get(MinesweeperTile.PROPERTY) != 3){
                    world.setBlockState(newpos, newbs.with(MinesweeperTile.PROPERTY, 3), 2);
                    ((MinesweeperBlock) newbs.getBlock()).showAll(newpos, world);
                } else if(newbs.getBlock() instanceof MinesweeperBomb && newbs.get(MinesweeperBomb.PROPERTY) != 3){
                    world.setBlockState(newpos, newbs.with(MinesweeperBomb.PROPERTY, 3), 2);
                    ((MinesweeperBlock) newbs.getBlock()).showAll(newpos, world);
                }
            }
        }
    }

}
