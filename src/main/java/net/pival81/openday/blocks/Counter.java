package net.pival81.openday.blocks;

import net.pival81.openday.Openday;
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
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class Counter extends Block {

    public static final IntProperty NUMBER = IntProperty.of("number", 0, 9);

    public Counter() {
        super(Settings.of(Material.METAL).strength(3.0F, 4.8F));
        setDefaultState(getStateManager().getDefaultState().with(NUMBER, 0));
    }

    public boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    public int getWeakRedstonePower(BlockState state, BlockView view, BlockPos pos, Direction facing) {
        return state.get(NUMBER);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager){
        stateManager.add(NUMBER);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult blockHitResult) {
        if(!player.isSneaking()) {
            world.setBlockState(pos, Openday.COUNTER.getDefaultState().with(NUMBER, (state.get(NUMBER) < 9) ? state.get(NUMBER) + 1 : 0));
        } else {
            world.setBlockState(pos, Openday.COUNTER.getDefaultState().with(NUMBER, (state.get(NUMBER) > 0) ? state.get(NUMBER) - 1 : 9));
        }
            return ActionResult.SUCCESS;
    }
}