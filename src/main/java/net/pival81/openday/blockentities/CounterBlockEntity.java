package net.pival81.openday.blockentities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.pival81.openday.Openday;
import net.pival81.openday.blocks.Counter;

public class CounterBlockEntity extends BlockEntity implements Tickable {

    private int tickCount = 0;

    public CounterBlockEntity() {
        super(Openday.COUNTERBLOCKENTITY);
    }

    public CompoundTag toTag(CompoundTag tag){
        super.toTag(tag);
        return tag;
    }

    public void fromTag(CompoundTag tag){
        super.fromTag(tag);
    }

    @Override
    public void tick() {
        World world = this.getWorld();
        if(!(world.isClient)){
            if(tickCount < 20){
                tickCount++;
            } else {
                tickCount = 0;

                BlockPos pos = this.getPos();
                BlockState bs = world.getBlockState(pos);

                int currentNumber = bs.get(Counter.NUMBER);

                world.setBlockState(pos, bs.with(Counter.NUMBER, ( currentNumber < 9) ? currentNumber+1 : 0));
            }
        }
    }
}
