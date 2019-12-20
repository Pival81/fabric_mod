package net.fabricmc.example.blockentities;

import net.fabricmc.example.Openday;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;

public class CounterBlockEntity extends BlockEntity {


    public CounterBlockEntity() {
        super(Openday.COUNTER_BLOCK_ENTITY);
    }
}
