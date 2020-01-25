package net.pival81.openday;

import com.fazecast.jSerialComm.SerialPort;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.*;
import net.minecraft.util.TypedActionResult;
import net.pival81.openday.blockentities.CounterBlockEntity;
import net.pival81.openday.blockentities.RobotBlockEntity;
import net.pival81.openday.blocks.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class Openday implements ModInitializer {

	public static final Block COUNTER = new Counter();
	public static final Block ROBOT = new Robot();
	public static final Block LASAGNA = new Lasagna();
	public static final Block HUNGERBLOCK = new Block(Block.Settings.of(Material.ANVIL)){

		@Override
		public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult blockHitResult) {
			player.getHungerManager().setFoodLevel(5);
			return ActionResult.SUCCESS;
		}
	};
	public static final Item DEBUGSERIALSTICK = new Item(new Item.Settings().group(ItemGroup.REDSTONE)){
		@Override
		public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
			System.out.println(Openday.port.isOpen());
			return TypedActionResult.success(user.getStackInHand(hand));
		}
	};
	//public static final Item COSAINSEGNA = new Item(new Item.Settings().group(ItemGroup.MISC));
	public static final Block MINESWEEPERCONTROLLER = new MinesweeperController();
	public static final Block MINESWEEPERTILE = new MinesweeperTile();
	public static final Block MINESWEEPERBOMB = new MinesweeperBomb();

	public static BlockEntityType<CounterBlockEntity> COUNTERBLOCKENTITY;
	public static BlockEntityType<RobotBlockEntity> ROBOTBLOCKENTITY;

	public static SerialPort port;
	public static BlockPos serialBlock;

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

		//Registry.register(Registry.ITEM, new Identifier("openday", "cosainsegna"), COSAINSEGNA);
		Registry.register(Registry.ITEM, new Identifier("openday", "debugserial"), DEBUGSERIALSTICK);

		Registry.register(Registry.BLOCK, new Identifier("openday", "minesweeper_controller"), MINESWEEPERCONTROLLER);
		Registry.register(Registry.ITEM, new Identifier("openday", "minesweeper_controller"),
				new BlockItem(MINESWEEPERCONTROLLER, new Item.Settings().group(ItemGroup.REDSTONE)));

		Registry.register(Registry.BLOCK, new Identifier("openday", "minesweeper_tile"), MINESWEEPERTILE);
		Registry.register(Registry.ITEM, new Identifier("openday", "minesweeper_tile"),
				new BlockItem(MINESWEEPERTILE, new Item.Settings().group(ItemGroup.REDSTONE)));

		Registry.register(Registry.BLOCK, new Identifier("openday", "minesweeper_bomb"), MINESWEEPERBOMB);
		Registry.register(Registry.ITEM, new Identifier("openday", "minesweeper_bomb"),
				new BlockItem(MINESWEEPERBOMB, new Item.Settings().group(ItemGroup.REDSTONE)));

		COUNTERBLOCKENTITY = Registry.register(Registry.BLOCK_ENTITY, new Identifier("openday", "counter"),
				BlockEntityType.Builder.create(CounterBlockEntity::new, COUNTER).build(null));


		Registry.register(Registry.BLOCK, new Identifier("openday", "robot"), ROBOT);
		Registry.register(Registry.ITEM, new Identifier("openday", "robot"),
				new BlockItem(ROBOT, new Item.Settings().group(ItemGroup.REDSTONE)));

		ROBOTBLOCKENTITY = Registry.register(Registry.BLOCK_ENTITY, new Identifier("openday", "robot"),
				BlockEntityType.Builder.create(RobotBlockEntity::new, ROBOT).build(null));

		SerialPort ports[] = SerialPort.getCommPorts();
		port = ports[1];
		port.setComPortParameters(9600, 8, 1, 0);
		port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);

		if (port.openPort()) {
			System.out.println("Port is open :)");
		} else {
			System.out.println("Failed to open port :(");
			return;
		}
	}

}
