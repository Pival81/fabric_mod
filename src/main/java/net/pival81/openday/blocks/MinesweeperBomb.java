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


public class MinesweeperBomb extends MinesweeperBlock {

    public static final IntProperty PROPERTY = IntProperty.of("property", 0, 3);

    public MinesweeperBomb() {
        super(Block.Settings.of(Material.METAL).strength(3.0F, 4.8F));
        setDefaultState(getDefaultState()
                .with(PROPERTY, 0)
        );
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager){
        stateManager.add(PROPERTY);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult blockHitResult) {
        if(player.isSneaking()){
            if(state.get(PROPERTY) == 0){
                world.setBlockState(pos, state.with(PROPERTY, 2), 2);
            } else if(state.get(PROPERTY) == 2){
                world.setBlockState(pos, state.with(PROPERTY, 0), 2);
            }
        } else {
            showAll(pos, world);
        }
        return ActionResult.SUCCESS;
    }
}
