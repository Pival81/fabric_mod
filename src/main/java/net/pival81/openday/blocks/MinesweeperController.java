package net.pival81.openday.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.pival81.openday.Openday;

import static net.pival81.openday.Openday.MINESWEEPERBOMB;

public class MinesweeperController extends Block {

    public static final int x = 14;
    public static final int y = x;

    public MinesweeperController() {
        super(Settings.of(Material.METAL).strength(3.0F, 4.8F));
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos neighborPos, boolean moved) {
        if(world.isReceivingRedstonePower(pos)) {
            for (int i = 1; i < x+1; i++) {
                for (int j = 0; j <y+1; j++) {
                    BlockPos newpos =  new BlockPos(pos.getX() + i, pos.getY() + j, pos.getZ());
                    world.setBlockState(newpos,
                        Math.random()<0.2 ? (MINESWEEPERBOMB.getDefaultState())
                           : Openday.MINESWEEPERTILE.getDefaultState());
                }
            }
            for (int i = 1; i < x+1; i++) {
                for (int j = 0; j < y+1; j++) {
                    BlockPos newpos = new BlockPos(pos.getX() + i, pos.getY() + j, pos.getZ());
                    BlockState newbs = world.getBlockState(newpos);
                    if(newbs.getBlock() instanceof MinesweeperTile) {
                        int num = 0;
                        for (int k = -1; k < 2; k++) {
                            for (int l = -1; l < 2; l++) {
                                if (k == 0 && l == 0) {
                                    continue;
                                }
                                BlockState newnewbs = world.getBlockState(new BlockPos(newpos.getX() + k, newpos.getY() + l, newpos.getZ()));
                                if (newnewbs.getBlock() instanceof MinesweeperBomb) {
                                    num++;
                                }
                            }
                        }
                        world.setBlockState(newpos, newbs.with(MinesweeperTile.NUMBER, num), 2);
                    }
                }
            }
        }
    }

}
