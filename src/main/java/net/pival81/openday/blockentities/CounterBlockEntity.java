package net.pival81.openday.blockentities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.pival81.openday.Openday;
import net.pival81.openday.serialhandlers.CounterSerialHandler;

public class CounterBlockEntity extends BlockEntity implements Tickable {

    //private int tickCount = 0;

    public boolean firstRun = true;

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
    public void tick(){
        World world = this.getWorld();
        if(!world.isClient){
            BlockPos pos = this.getPos();
            BlockState bs = world.getBlockState(pos);

            if (firstRun){
                firstRun = false;
                if(Openday.serialBlock == null){
                    Openday.serialBlock = new BlockPos(pos.getX(), pos.getY(), pos.getZ());
                }
                if(Openday.serialBlock.getX() == pos.getX() &&
                        Openday.serialBlock.getY() == pos.getY() &&
                        Openday.serialBlock.getZ() == pos.getZ()) {
                    Openday.serialBlock = new BlockPos(pos.getX(), pos.getY(), pos.getZ());
                    Openday.port.addDataListener(new CounterSerialHandler(this.pos, this.world));
                }
            }
        }
    }


    /*@Override
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
    }*/
}
