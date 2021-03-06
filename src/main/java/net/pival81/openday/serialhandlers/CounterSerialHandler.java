package net.pival81.openday.serialhandlers;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.pival81.openday.Openday;
import net.pival81.openday.blockentities.RobotBlockEntity;
import net.pival81.openday.blocks.Counter;
import net.pival81.openday.blocks.Robot;

import java.io.IOException;
import java.io.InputStream;

public class CounterSerialHandler implements SerialPortDataListener {

    private BlockPos pos;
    private World world;

    public CounterSerialHandler(BlockPos pos, World world){
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
        BlockState bs = world.getBlockState(pos);
        world.setBlockState(pos, bs.with(Counter.NUMBER, number), 2);
    }
}
