package net.pival81.openday.blockentities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.pival81.openday.Openday;
import net.pival81.openday.serialhandlers.SerialHandler;

public class RobotBlockEntity extends BlockEntity implements Tickable {

    public boolean firstRun = true;

    public RobotBlockEntity() {
        super(Openday.ROBOTBLOCKENTITY);
    }

    public CompoundTag toTag(CompoundTag tag){
        super.toTag(tag);
        return tag;
    }

    public void fromTag(CompoundTag tag){
        super.fromTag(tag);
    }

    public void breakBlock(){
        if(!world.isClient){
            Direction direction = getCachedState().get(Properties.FACING);
            BlockPos newpos = getPos().offset(direction);
            world.breakBlock(newpos, true);
        }
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
                        Openday.port.addDataListener(new SerialHandler(this.pos, this.world));
                }
            }
        }
    }
}
