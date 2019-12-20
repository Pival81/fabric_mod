package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.example.blockentities.CounterBlockEntity;
import net.fabricmc.example.blocks.CakeBase;
import net.fabricmc.example.blocks.Counter;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.CakeBlock;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;


public class Openday implements ModInitializer {

	public static final Block COUNTER = new Counter(FabricBlockSettings.of(Material.METAL).build());
	public static final Block LASAGNA = new CakeBase(FabricBlockSettings.of(Material.CAKE).build());
	public static BlockEntityType<CounterBlockEntity> COUNTER_BLOCK_ENTITY;


	@Override
	public void onInitialize() {
		System.out.println("Hello Fabric world!");
		Registry.register(Registry.BLOCK, new Identifier("openday", "counter"), COUNTER);
		Registry.register(Registry.ITEM, new Identifier("openday", "counter"),
				new BlockItem(COUNTER, new Item.Settings().group(ItemGroup.REDSTONE)));
		Registry.register(Registry.BLOCK, new Identifier("openday", "lasagna"), LASAGNA);
		Registry.register(Registry.ITEM, new Identifier("openday", "lasagna"),
				new BlockItem(LASAGNA, new Item.Settings().group(ItemGroup.FOOD)));
	}

}
