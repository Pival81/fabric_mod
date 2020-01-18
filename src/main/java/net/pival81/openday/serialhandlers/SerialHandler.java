package net.pival81.openday.serialhandlers;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.pival81.openday.Openday;
import net.pival81.openday.blockentities.RobotBlockEntity;

import java.io.IOException;
import java.io.InputStream;

import static net.minecraft.util.math.Direction.*;

public class SerialHandler implements SerialPortDataListener {

    private BlockPos pos;
    private World world;

    public SerialHandler(BlockPos pos, World world){
        super();
        this.pos = pos;
        this.world = world;
    }

    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
    }
    public void serialEvent(SerialPortEvent event) {
        if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
            return;
        char numRead = 0;
        InputStream in = Openday.port.getInputStream();
        try {
            numRead = (char) in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(numRead);
        int number = Character.getNumericValue(numRead);
        BlockEntity be = world.getBlockEntity(pos);
        BlockState bs = world.getBlockState(pos);
        if(number == 0){
            ((RobotBlockEntity) be).breakBlock();
        } else if (number == 1){ //sx
            switch(bs.get(Properties.FACING)){
                case NORTH:
                    world.setBlockState(pos, bs.with(Properties.FACING, WEST));
                    break;
                case SOUTH:
                    world.setBlockState(pos, bs.with(Properties.FACING, EAST));
                    break;
                case WEST:
                    world.setBlockState(pos, bs.with(Properties.FACING, SOUTH));
                    break;
                case EAST:
                    world.setBlockState(pos, bs.with(Properties.FACING, NORTH));
                    break;
            }
        } else if (number == 2){ //dx
            switch(bs.get(Properties.FACING)){
                case NORTH:
                    world.setBlockState(pos, bs.with(Properties.FACING, EAST));
                    break;
                case SOUTH:
                    world.setBlockState(pos, bs.with(Properties.FACING, WEST));
                    break;
                case WEST:
                    world.setBlockState(pos, bs.with(Properties.FACING, NORTH));
                    break;
                case EAST:
                    world.setBlockState(pos, bs.with(Properties.FACING, SOUTH));
                    break;
            }
        }
    }
}
