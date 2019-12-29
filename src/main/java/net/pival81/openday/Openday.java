package net.pival81.openday;

import net.fabricmc.api.ModInitializer;
import net.pival81.openday.blockentities.CounterBlockEntity;
import net.pival81.openday.blocks.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class Openday implements ModInitializer {

	public static final Block COUNTER = new Counter();
	public static final Block LASAGNA = new Lasagna();
	public static final Block HUNGERBLOCK = new Block(Block.Settings.of(Material.ANVIL)){

		@Override
		public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult blockHitResult) {
			player.getHungerManager().setFoodLevel(5);
			return ActionResult.SUCCESS;
		}
	};
	public static final Item COSAINSEGNA = new Item(new Item.Settings().group(ItemGroup.MISC));
	public static BlockEntityType<CounterBlockEntity> COUNTER_BLOCK_ENTITY;
	public static final Block MINESWEEPERCONTROLLER = new MinesweeperController();
	public static final Block MINESWEEPERTILE = new MinesweeperTile();
	public static final Block MINESWEEPERBOMB = new MinesweeperBomb();



	@Override
	public void onInitialize() {
		Registry.register(Registry.BLOCK, new Identifier("openday", "counter"), COUNTER);
		Registry.register(Registry.ITEM, new Identifier("openday", "counter"),
				new BlockItem(COUNTER, new Item.Settings().group(ItemGroup.REDSTONE)));

		Registry.register(Registry.BLOCK, new Identifier("openday", "lasagna"), LASAGNA);
		Registry.register(Registry.ITEM, new Identifier("openday", "lasagna"),
				new BlockItem(LASAGNA, new Item.Settings().group(ItemGroup.FOOD).food(
						new FoodComponent.Builder().hunger(5).saturationModifier(0.6f).build())));

		Registry.register(Registry.BLOCK, new Identifier("openday", "hunger"), HUNGERBLOCK);
		Registry.register(Registry.ITEM, new Identifier("openday", "hunger"),
				new BlockItem(HUNGERBLOCK, new Item.Settings().group(ItemGroup.FOOD)));

		Registry.register(Registry.ITEM, new Identifier("openday", "cosainsegna"), COSAINSEGNA);

		Registry.register(Registry.BLOCK, new Identifier("openday", "minesweeper_controller"), MINESWEEPERCONTROLLER);
		Registry.register(Registry.ITEM, new Identifier("openday", "minesweeper_controller"),
				new BlockItem(MINESWEEPERCONTROLLER, new Item.Settings().group(ItemGroup.REDSTONE)));

		Registry.register(Registry.BLOCK, new Identifier("openday", "minesweeper_tile"), MINESWEEPERTILE);
		Registry.register(Registry.ITEM, new Identifier("openday", "minesweeper_tile"),
				new BlockItem(MINESWEEPERTILE, new Item.Settings().group(ItemGroup.REDSTONE)));

		Registry.register(Registry.BLOCK, new Identifier("openday", "minesweeper_bomb"), MINESWEEPERBOMB);
		Registry.register(Registry.ITEM, new Identifier("openday", "minesweeper_bomb"),
				new BlockItem(MINESWEEPERBOMB, new Item.Settings().group(ItemGroup.REDSTONE)));
	}

}
