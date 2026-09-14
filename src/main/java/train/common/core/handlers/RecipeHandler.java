/*******************************************************************************
 * Copyright (c) 2012 Mrbrutal. All rights reserved.
 * 
 * @name TrainCraft
 * @author Mrbrutal
 ******************************************************************************/

package train.common.core.handlers;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import train.common.inventory.TrainCraftingManager;
import train.common.library.BlockIDs;
import train.common.library.ItemIDs;
import train.common.recipes.RecipesArmorDyes;

import java.util.ArrayList;
import java.util.List;

import static train.common.recipes.AssemblyTableRecipes.waterContainers;

public class RecipeHandler {

	private static ArrayList<ItemStack> multiNameOreDict(String ... names){
		ArrayList<ItemStack> entries = new ArrayList<ItemStack>();
		for (String name : names){
			entries.addAll(OreDictionary.getOres(name));
		}
		return entries;
	}

	public static void initBlockRecipes() {
		List<ItemStack> iron = OreDictionary.getOres("ingotIron");

		TrainCraftingManager.instance.getRecipeList().add(new RecipesArmorDyes());
		/* Assembly tables */
		for (ItemStack ironingot : iron) {
			GameRegistry.addShapedRecipe(new ResourceLocation("traincraft", "recipe_1"), (ResourceLocation) null, new ItemStack(BlockIDs.assemblyTableI.block, 1), "IPI", "S S", "SPS", Character.valueOf('I'), ironingot, Character.valueOf('P'), Blocks.PISTON, Character.valueOf('S'), Blocks.STONE);
		}
		GameRegistry.addShapedRecipe(new ResourceLocation("traincraft", "recipe_2"), (ResourceLocation) null, new ItemStack(BlockIDs.assemblyTableII.block, 1),  "GPG", "O O", "OPO", Character.valueOf('G'), Items.GOLD_INGOT, Character.valueOf('P'), Blocks.PISTON, Character.valueOf('O'), Blocks.OBSIDIAN );
		GameRegistry.addShapedRecipe(new ResourceLocation("traincraft", "recipe_3"), (ResourceLocation) null, new ItemStack(BlockIDs.assemblyTableIII.block, 1),  "GPG", "DLD", "OPO", Character.valueOf('G'), Items.GOLD_INGOT, Character.valueOf('P'), Blocks.PISTON, Character.valueOf('D'), Items.DIAMOND, Character.valueOf('L'), Blocks.GLOWSTONE, Character.valueOf('O'), Blocks.OBSIDIAN );

		if (!ConfigHandler.DISABLE_TRAIN_WORKBENCH) {
			for (ItemStack ironingot : iron) {
				addDictRecipe(new ItemStack(BlockIDs.trainWorkbench.block, 1), "###", "IFI", "###", Character.valueOf('#'), "plankWood", Character.valueOf('F'), Blocks.FURNACE, Character.valueOf('I'), ironingot);
			}
		}
		addDictRecipe(new ItemStack(BlockIDs.distilIdle.block, 1),  "###", "#F#", "###", Character.valueOf('#'), "ingotSteel", Character.valueOf('F'), ItemIDs.firebox.item );

		/* Open Hearth Furnace */
		if (!ConfigHandler.MAKE_MODPACKS_GREAT_AGAIN) {
			GameRegistry.addShapedRecipe(new ResourceLocation("traincraft", "recipe_4"), (ResourceLocation) null, new ItemStack(BlockIDs.openFurnaceIdle.block, 1), "#L#", "#B#", "#I#",
					Character.valueOf('#'), Blocks.NETHER_BRICK, Character.valueOf('L'), Items.LAVA_BUCKET,
					Character.valueOf('B'), Items.BUCKET, Character.valueOf('I'), Blocks.IRON_BLOCK);
		}

		/* Lantern */
		for (ItemStack ironingot : iron) {
			GameRegistry.addShapedRecipe(new ResourceLocation("traincraft", "recipe_5"), (ResourceLocation) null, new ItemStack(BlockIDs.lantern.block, 4), "III", "PTP", "III", Character.valueOf('I'), ironingot, Character.valueOf('P'), Blocks.GLASS_PANE, Character.valueOf('T'), Blocks.TORCH);
		}
		
		/* Clothes */
		GameRegistry.addShapedRecipe(new ResourceLocation("traincraft", "recipe_6"), (ResourceLocation) null, new ItemStack(ItemIDs.overalls.item, 1),  " # ", "X$X", "X$X", Character.valueOf('X'), new ItemStack(Items.DYE, 1, 4), Character.valueOf('$'), Items.LEATHER_LEGGINGS, Character.valueOf('#'), new ItemStack(Items.DYE, 1, 1) );
		GameRegistry.addShapedRecipe(new ResourceLocation("traincraft", "recipe_7"), (ResourceLocation) null, new ItemStack(ItemIDs.jacket.item, 1),  "X X", "X$X", "X#X", Character.valueOf('X'), new ItemStack(Items.DYE, 1, 14), Character.valueOf('$'), Items.LEATHER_CHESTPLATE, Character.valueOf('#'), Items.STRING );
		GameRegistry.addShapedRecipe(new ResourceLocation("traincraft", "recipe_8"), (ResourceLocation) null, new ItemStack(ItemIDs.hat.item, 1),  " X ", "X$X", "#X#", Character.valueOf('X'), new ItemStack(Items.DYE, 1, 4), Character.valueOf('$'), Items.LEATHER_HELMET, Character.valueOf('#'), Items.STRING );

		/* Driver Clothes*/
		GameRegistry.addShapedRecipe(new ResourceLocation("traincraft", "recipe_9"), (ResourceLocation) null, new ItemStack(ItemIDs.pants_driver_paintable.item, 1),  "XXX", "XLX", "X$X", Character.valueOf('L'), Items.LEATHER_LEGGINGS,Character.valueOf('$'), new ItemStack(Items.DYE, 1, 4), Character.valueOf('X'), Items.STRING);
		GameRegistry.addShapedRecipe(new ResourceLocation("traincraft", "recipe_10"), (ResourceLocation) null, new ItemStack(ItemIDs.jacket_driver_paintable.item, 1),  "X X", "XRX", "XPX", Character.valueOf('X'), new ItemStack(Items.DYE, 1, 4), Character.valueOf('P'), Items.LEATHER_CHESTPLATE,Character.valueOf('R'),  new ItemStack(Items.DYE, 1, 1) );
		GameRegistry.addShapedRecipe(new ResourceLocation("traincraft", "recipe_11"), (ResourceLocation) null, new ItemStack(ItemIDs.hat_driver_paintable.item, 1), "#$#", "# #", Character.valueOf('$'), new ItemStack(Items.DYE, 1, 4), Character.valueOf('#'), Items.STRING );
		
		/* Ticket Man Clothes */
		GameRegistry.addShapedRecipe(new ResourceLocation("traincraft", "recipe_12"), (ResourceLocation) null, new ItemStack(ItemIDs.pants_ticketMan_paintable.item, 1),  "XXX", "XLX", "X$X", Character.valueOf('L'), Items.LEATHER_LEGGINGS,Character.valueOf('$'), new ItemStack(Items.DYE, 1, 8), Character.valueOf('X'), Items.STRING);
		GameRegistry.addShapedRecipe(new ResourceLocation("traincraft", "recipe_13"), (ResourceLocation) null, new ItemStack(ItemIDs.jacket_ticketMan_paintable.item, 1),  "X X", "XPX", "X#X", Character.valueOf('P'), Items.LEATHER_CHESTPLATE, Character.valueOf('#'), new ItemStack(Items.DYE, 1, 4), Character.valueOf('X'), Items.STRING);
		GameRegistry.addShapedRecipe(new ResourceLocation("traincraft", "recipe_14"), (ResourceLocation) null, new ItemStack(ItemIDs.hat_ticketMan_paintable.item, 1), "#$#", "# #", Character.valueOf('$'), new ItemStack(Items.DYE, 1, 0), Character.valueOf('#'), Items.STRING );
		
		/* Recipe book */
		GameRegistry.addShapedRecipe(new ResourceLocation("traincraft", "recipe_15"), (ResourceLocation) null, new ItemStack(ItemIDs.recipeBook.item, 1),  "TTT", "TBT", "TTT", Character.valueOf('T'), Blocks.RAIL, Character.valueOf('B'), Items.BOOK );

		for (ItemStack ironingot : iron) {
			addDictRecipe(new ItemStack(BlockIDs.switchStand.block, 1), " W ", " I ", " R ", Character.valueOf('W'), Blocks.LEVER, Character.valueOf('R'), Items.STICK, Character.valueOf('I'), ironingot);

			/*Buffer*/
			addDictRecipe(new ItemStack(BlockIDs.stopper.block, 1), "WWW", "I I", "RRR", Character.valueOf('W'), "plankWood", Character.valueOf('R'), Blocks.RAIL, Character.valueOf('I'), ironingot);
		}
		
		GameRegistry.addShapedRecipe(new ResourceLocation("traincraft", "recipe_16"), (ResourceLocation) null, new ItemStack(BlockIDs.oreTC.block, 1,3),  "GXG", Character.valueOf('G'), Blocks.GRAVEL, Character.valueOf('X'), Items.CLAY_BALL);

		// === CE parity: new ground/asphalt blocks + bolt/whistle/paintbrush (authentic 1.7.10 CE recipes) ===
		GameRegistry.addShapelessRecipe(new ResourceLocation("traincraft", "recipe_ce_poweredgravel"), null, new ItemStack(BlockIDs.poweredGravel.block, 1), net.minecraft.item.crafting.Ingredient.fromItem(Items.REDSTONE), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(Blocks.GRAVEL)));
		GameRegistry.addShapelessRecipe(new ResourceLocation("traincraft", "recipe_ce_snowgravel1"), null, new ItemStack(BlockIDs.snowGravel.block, 1), net.minecraft.item.crafting.Ingredient.fromItem(Items.SNOWBALL), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(Blocks.GRAVEL)));
		GameRegistry.addShapelessRecipe(new ResourceLocation("traincraft", "recipe_ce_snowgravel4"), null, new ItemStack(BlockIDs.snowGravel.block, 4), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(Blocks.SNOW)), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(Blocks.GRAVEL)), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(Blocks.GRAVEL)), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(Blocks.GRAVEL)), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(Blocks.GRAVEL)));
		GameRegistry.addShapelessRecipe(new ResourceLocation("traincraft", "recipe_ce_asphalt"), null, new ItemStack(BlockIDs.asphalt.block, 8), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(ItemIDs.coaldust.item)), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(ItemIDs.coaldust.item)), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(ItemIDs.coaldust.item)), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(ItemIDs.coaldust.item)), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(Blocks.COBBLESTONE)), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(Blocks.COBBLESTONE)), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(Blocks.COBBLESTONE)), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(Blocks.COBBLESTONE)), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(Blocks.COBBLESTONE)));
		GameRegistry.addShapedRecipe(new ResourceLocation("traincraft", "recipe_ce_asphaltslab"), null, new ItemStack(BlockIDs.asphaltSlab.block, 6), "BBB", Character.valueOf('B'), BlockIDs.asphalt.block);
		GameRegistry.addShapedRecipe(new ResourceLocation("traincraft", "recipe_ce_asphaltstairs"), null, new ItemStack(BlockIDs.asphaltStairs.block, 4), "B  ", "BB ", "BBB", Character.valueOf('B'), BlockIDs.asphalt.block);
		GameRegistry.addShapelessRecipe(new ResourceLocation("traincraft", "recipe_ce_highspeedballast"), null, new ItemStack(BlockIDs.highSpeedBallast.block, 16), net.minecraft.item.crafting.Ingredient.fromItem(Items.QUARTZ), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(Blocks.GRAVEL)), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(Blocks.GRAVEL)), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(Blocks.GRAVEL)), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(Blocks.GRAVEL)), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(Blocks.GRAVEL)), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(Blocks.GRAVEL)), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(Blocks.GRAVEL)), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(Blocks.CLAY)));
		GameRegistry.addShapelessRecipe(new ResourceLocation("traincraft", "recipe_ce_dirtyballast"), null, new ItemStack(BlockIDs.dirtyBallast.block, 16), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(Blocks.GRAVEL)), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(Blocks.GRAVEL)), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(Blocks.GRAVEL)), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(Blocks.GRAVEL)), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(Blocks.GRAVEL)), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(Blocks.GRAVEL)), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(Blocks.GRAVEL)), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(Blocks.GRAVEL)), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(Blocks.DIRT)));
		GameRegistry.addShapelessRecipe(new ResourceLocation("traincraft", "recipe_ce_dirtierballast"), null, new ItemStack(BlockIDs.dirtierBallast.block, 16), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(BlockIDs.dirtyBallast.block)), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(BlockIDs.dirtyBallast.block)), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(BlockIDs.dirtyBallast.block)), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(BlockIDs.dirtyBallast.block)), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(BlockIDs.dirtyBallast.block)), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(BlockIDs.dirtyBallast.block)), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(BlockIDs.dirtyBallast.block)), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(BlockIDs.dirtyBallast.block)), net.minecraft.item.crafting.Ingredient.fromStacks(new ItemStack(Blocks.DIRT)));
		GameRegistry.addShapedRecipe(new ResourceLocation("traincraft", "recipe_ce_bolt"), null, new ItemStack(ItemIDs.bolt.item, 16), " II", " I ", " I ", Character.valueOf('I'), Items.IRON_INGOT);
		GameRegistry.addShapedRecipe(new ResourceLocation("traincraft", "recipe_ce_paintbrush"), null, new ItemStack(ItemIDs.paintbrushThing.item, 1), "GB ", "RIS", " ST", Character.valueOf('G'), new ItemStack(Items.DYE, 1, 2), Character.valueOf('B'), new ItemStack(Items.DYE, 1, 4), Character.valueOf('R'), new ItemStack(Items.DYE, 1, 1), Character.valueOf('I'), Items.IRON_INGOT, Character.valueOf('S'), Items.STRING, Character.valueOf('T'), Items.STICK);
		
	}

	public static void initItemRecipes() {

		List<ItemStack> steel = OreDictionary.getOres("ingotSteel");
		List<ItemStack> iron = OreDictionary.getOres("ingotIron");
		List<ItemStack> planks = OreDictionary.getOres("plankWood");
		List<ItemStack> logs = OreDictionary.getOres("logWood");
		ArrayList<ItemStack> plastics	= multiNameOreDict("platePlastic", "itemPlastic", "dustPlastic");//dustPlastic for MFR support, platePlastic for GT6 support
		List<ItemStack> copper = OreDictionary.getOres("ingotCopper");
		List<ItemStack> dustCoal = OreDictionary.getOres("dustCoal");
		List<ItemStack> coal = new ArrayList<ItemStack>();
		coal.add(new ItemStack(Items.COAL));
		coal.addAll(OreDictionary.getOres("coal"));
		List<ItemStack> redstone = OreDictionary.getOres("dustRedstone");
		List<ItemStack> waterbucket = waterContainers();
		// Always do this " X " instead of this "X", and do not put "" empty brackets

		/* I placed it here because workbench should be one of the first recipe shown in the recipe book */
		for (ItemStack plank : planks) {
			for (ItemStack ironingot : iron) {
				TrainCraftingManager.instance.addRecipe(new ItemStack(BlockIDs.trainWorkbench.block, 1), "###", "IFI", "###", Character.valueOf('#'), plank, Character.valueOf('F'), Blocks.FURNACE, Character.valueOf('I'), ironingot);
			}
		}
		
		/* Recipe book */
		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.recipeBook.item, 1),  "TTT", "TBT", "TTT", Character.valueOf('T'), Blocks.RAIL, Character.valueOf('B'), Items.BOOK );

		/* Chunk Loader Activator */
		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.chunkLoaderActivator.item, 1),  "  P", " S ", "S  ", Character.valueOf('S'), Items.BLAZE_ROD, Character.valueOf('P'), Items.ENDER_PEARL );

		/* Assembly tables */

		for (ItemStack ironingot : iron) {
			TrainCraftingManager.instance.addRecipe(new ItemStack(BlockIDs.assemblyTableI.block, 1), "IPI", "S S", "SPS", Character.valueOf('I'), ironingot, Character.valueOf('P'), Blocks.PISTON, Character.valueOf('S'), Blocks.STONE);
		}
		TrainCraftingManager.instance.addRecipe(new ItemStack(BlockIDs.assemblyTableII.block, 1),  "GPG", "O O", "OPO", Character.valueOf('G'), Items.GOLD_INGOT, Character.valueOf('P'), Blocks.PISTON, Character.valueOf('O'), Blocks.OBSIDIAN );
		TrainCraftingManager.instance.addRecipe(new ItemStack(BlockIDs.assemblyTableIII.block, 1),  "GPG", "DLD", "OPO", Character.valueOf('G'), Items.GOLD_INGOT, Character.valueOf('P'), Blocks.PISTON, Character.valueOf('D'), Items.DIAMOND, Character.valueOf('L'), Blocks.GLOWSTONE, Character.valueOf('O'), Blocks.OBSIDIAN );

		/* Open Hearth Furnace */
		TrainCraftingManager.instance.addRecipe(new ItemStack(BlockIDs.openFurnaceIdle.block, 1),  "#L#", "#B#", "#I#", Character.valueOf('#'), Blocks.NETHER_BRICK, Character.valueOf('L'), Items.LAVA_BUCKET, Character.valueOf('B'), Items.BUCKET, Character.valueOf('I'), Blocks.IRON_BLOCK );

		/* Lantern */
		for (ItemStack ironingot : iron) {
			TrainCraftingManager.instance.addRecipe(new ItemStack(BlockIDs.lantern.block, 4), "III", "PTP", "III", Character.valueOf('I'), ironingot, Character.valueOf('P'), Blocks.GLASS_PANE, Character.valueOf('T'), Blocks.TORCH);
		}
		/* Clothes */
		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.overalls.item, 1),  " # ", "X$X", "X X", Character.valueOf('X'), new ItemStack(Items.DYE, 1, 4), Character.valueOf('$'), Items.LEATHER_LEGGINGS, Character.valueOf('#'), new ItemStack(Items.DYE, 1, 1) );
		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.jacket.item, 1),  "X X", "X$X", "X#X", Character.valueOf('X'), new ItemStack(Items.DYE, 1, 14), Character.valueOf('$'), Items.LEATHER_CHESTPLATE, Character.valueOf('#'), Items.STRING );
		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.hat.item, 1),  " X ", "X$X", "#X#", Character.valueOf('X'), new ItemStack(Items.DYE, 1, 4), Character.valueOf('$'), Items.LEATHER_HELMET, Character.valueOf('#'), Items.STRING );
		
		/* Driver Clothes*/
		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.pants_driver_paintable.item, 1),  "XXX", "XLX", "X$X", Character.valueOf('L'), Items.LEATHER_LEGGINGS,Character.valueOf('$'), new ItemStack(Items.DYE, 1, 4), Character.valueOf('X'), Items.STRING);
		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.jacket_driver_paintable.item, 1),  "X X", "XRX", "XPX", Character.valueOf('X'), new ItemStack(Items.DYE, 1, 4), Character.valueOf('P'), Items.LEATHER_CHESTPLATE,Character.valueOf('R'),  new ItemStack(Items.DYE, 1, 1) );
		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.hat_driver_paintable.item, 1), "#$#", "# #", Character.valueOf('$'), new ItemStack(Items.DYE, 1, 4), Character.valueOf('#'), Items.STRING );
		
		/* Ticket Man Clothes */
		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.pants_ticketMan_paintable.item, 1),  "XXX", "XLX", "X$X", Character.valueOf('L'), Items.LEATHER_LEGGINGS,Character.valueOf('$'), new ItemStack(Items.DYE, 1, 8), Character.valueOf('X'), Items.STRING);
		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.jacket_ticketMan_paintable.item, 1),  "X X", "XPX", "X#X", Character.valueOf('P'), Items.LEATHER_CHESTPLATE, Character.valueOf('#'), new ItemStack(Items.DYE, 1, 4), Character.valueOf('X'), Items.STRING);
		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.hat_ticketMan_paintable.item, 1), "#$#", "# #", Character.valueOf('$'), new ItemStack(Items.DYE, 1, 0), Character.valueOf('#'), Items.STRING );
		
		

		if (plastics != null && plastics.size() >= 0) {
			for (ItemStack plastic : plastics) {
				/* Empty canister */
				TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.emptyCanister.item, 4),  "PPP", "P P", "PPP", Character.valueOf('P'), plastic);
				for (ItemStack rs :redstone) {
					/* Electronic circuit */
					TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.electronicCircuit.item, 1), "XXX", "RPR", "XXX", Character.valueOf('X'), ItemIDs.copperWireFine.item, Character.valueOf('P'), plastic, Character.valueOf('R'), rs.getItem());
				}
				/* Composite Material*/
				TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.reinforcedPlastic.item, 16),  "LPL", "PLP", "GPG", Character.valueOf('G'), Blocks.GLASS_PANE, Character.valueOf('P'), ItemIDs.graphite.item, Character.valueOf('L'), plastic);
				
				if (copper != null && copper.size() >= 0) {
					for (ItemStack copp : copper) {
						TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.copperWireFine.item, 6),  "XXX", "XPX", "XXX", Character.valueOf('X'), copp, Character.valueOf('P'), plastic );
					}
				}	
			}
		}
		
		/* Composite Suit */
		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.boots_suit_paintable.item, 1), " D ","X X", "XFX", Character.valueOf('F'), Items.FEATHER, Character.valueOf('D'), Items.DIAMOND, Character.valueOf('X'), ItemIDs.reinforcedPlates.item);
		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.pants_suit_paintable.item, 1),  "XDX", "X$X", "X X", Character.valueOf('$'), Items.FIRE_CHARGE, Character.valueOf('X'), ItemIDs.reinforcedPlates.item,Character.valueOf('D'), Items.DIAMOND);
		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.jacket_suit_paintable.item, 1),  "X X", "XDX", "XAX", Character.valueOf('A'), Items.GOLDEN_APPLE, Character.valueOf('X'), ItemIDs.reinforcedPlates.item,Character.valueOf('D'), Blocks.DIAMOND_BLOCK);
		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.helmet_suit_paintable.item, 1), "#D#", "# #", Character.valueOf('D'), Blocks.DIAMOND_BLOCK, Character.valueOf('#'), ItemIDs.reinforcedPlates.item );

		/* Trains parts */

		for (ItemStack ironingot : iron) {
			TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.generator.item, 1), " ##", "E$$", " ##", Character.valueOf('#'), ItemIDs.copperWireFine.item, Character.valueOf('E'), ItemIDs.electronicCircuit.item, Character.valueOf('$'), ironingot);// generator
			TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.controls.item, 1), "#X#", "#E#", "$$$", Character.valueOf('#'), Blocks.LEVER, Character.valueOf('X'), Blocks.STONE_BUTTON, Character.valueOf('$'), ironingot, Character.valueOf('E'), ItemIDs.electronicCircuit.item);// train controls
			TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.electmotor.item, 1), "I#I", "#E#", "I#I", Character.valueOf('#'), ItemIDs.copperWireFine.item, Character.valueOf('I'), ironingot, Character.valueOf('E'), ItemIDs.electronicCircuit.item);// Electric motor
			for (ItemStack bucketWater : waterbucket) {
				TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.ironBoiler.item, 2), "###", "XXX", "###", Character.valueOf('#'), ironingot, Character.valueOf('X'), bucketWater.getItem());// iron Boiler
			}
			TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.ironFirebox.item, 2),  "###", "#X#", "###", Character.valueOf('#'), ironingot, Character.valueOf('X'), Items.FLINT_AND_STEEL );// iron Firebox
			TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.ironChimney.item, 2),  "# #", "# #", "# #", Character.valueOf('#'), ironingot );

		}
		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.dieselengine.item, 2), "###", "XXX", "CCC", Character.valueOf('#'), ItemIDs.piston.item, Character.valueOf('X'), ItemIDs.cylinder.item, Character.valueOf('C'), ItemIDs.camshaft.item);// diesel engine
		for (ItemStack dustStack : dustCoal) {
			TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.graphite.item, 2),  "###", "#X#", "###", Character.valueOf('#'), dustStack, Character.valueOf('X'), Items.CLAY_BALL );// Graphite
		}
		
		if (!ConfigHandler.MAKE_MODPACKS_GREAT_AGAIN) {
			for (ItemStack c : coal) {
				TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.coaldust.item, 4),
						"###", "   ", "   ", Character.valueOf('#'), c.getItem());
				TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.coaldust.item, 4),
						"   ", "###", "   ", Character.valueOf('#'), c.getItem());
				TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.coaldust.item, 4),
						"   ", "   ", "###", Character.valueOf('#'), c.getItem());
			}
		}
		
		//TrainCraftingManager.instance.addShapelessRecipe(new ItemStack(ItemIDs.coaldust.item, 4),  c.getItem(), c.getItem(), c.getItem(), c.getItem() );// coal dust
		
		//TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.signal.item, 2),  "#", "X", "X", Character.valueOf('X'), ItemIDs.steel.item, Character.valueOf('#'), rs );
		/* diesel generator */
		TrainCraftingManager.instance.addRecipe(new ItemStack(BlockIDs.generatorDiesel.block, 1),  "C  ", "DE ", Character.valueOf('C'), ItemIDs.steelchimney.item, Character.valueOf('D'), ItemIDs.dieselengine.item, Character.valueOf('E'), ItemIDs.electronicCircuit.item );
		
		/* Zepplin parts and zeppelin item */
		if (ConfigHandler.ENABLE_ZEPPELIN) {
			TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.balloon.item, 1),  "###", "# #", "###", Character.valueOf('#'), Blocks.WOOL );// Balloon
			TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.steamengine.item, 1),  "C  ", "BF ", Character.valueOf('C'), ItemIDs.steelchimney.item, Character.valueOf('B'), ItemIDs.boiler.item, Character.valueOf('F'), ItemIDs.firebox.item );// Small steam engine
			TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.airship.item, 1),  "B B", "SES", "POP", Character.valueOf('B'), ItemIDs.balloon.item, Character.valueOf('S'), Items.STICK, Character.valueOf('E'), ItemIDs.steamengine.item, Character.valueOf('P'), ItemIDs.propeller.item, Character.valueOf('O'), Items.BOAT );
			TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.zeppelin.item, 1),  "BBB", "SES", "POP", Character.valueOf('B'), ItemIDs.balloon.item, Character.valueOf('S'), ItemIDs.propeller.item, Character.valueOf('E'), ItemIDs.controls.item, Character.valueOf('P'), ItemIDs.electmotor.item, Character.valueOf('O'), ItemIDs.seats.item );
		}

		for (ItemStack plankItem : planks) {
			for (ItemStack steelItem: steel) {

				TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.reinforcedPlates.item, 1),  "RRR", "SSS", "CCC", Character.valueOf('R'), ItemIDs.reinforcedPlastic.item, Character.valueOf('S'), steelItem, Character.valueOf('C'), Items.CLAY_BALL);

				TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.composite_wrench.item, 1), "S S", " R "," R ", Character.valueOf('R'), ItemIDs.reinforcedPlastic.item, Character.valueOf('S'),steelItem );
				TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.steelcab.item, 2),  "###", "X X", "XXX", Character.valueOf('X'), steelItem, Character.valueOf('#'), plankItem );// Steel cab
				TrainCraftingManager.instance.addRecipe(new ItemStack(BlockIDs.distilIdle.block, 1),  "###", "#F#", "###", Character.valueOf('#'), steelItem, Character.valueOf('F'), ItemIDs.firebox.item );
				for (ItemStack rs :redstone) {
					TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.transformer.item, 1), "# #", "XEX", "###", Character.valueOf('#'), steelItem, Character.valueOf('E'), ItemIDs.electronicCircuit.item, Character.valueOf('X'), rs.getItem());// transformer
				}

				for (ItemStack bucketWater :waterbucket) {
					TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.boiler.item, 2), "###", "XXX", "###", Character.valueOf('#'), steelItem, Character.valueOf('X'), bucketWater.getItem());// Boiler
				}
				TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.firebox.item, 2),  "###", "#X#", "###", Character.valueOf('#'), steelItem, Character.valueOf('X'), Items.FLINT_AND_STEEL );// Firebox
				for (ItemStack ironingot : iron) {
					TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.bogie.item, 4), " # ", "#X#", " # ", Character.valueOf('#'), steelItem, Character.valueOf('X'), ironingot);// Bogie
					TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.steelframe.item, 2), "# #", "AAA", Character.valueOf('A'), steelItem, Character.valueOf('#'), ironingot);// Steel Frame
					TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.steelframe.item, 2), "   ", "# #", "AAA", Character.valueOf('A'), steelItem, Character.valueOf('#'), ironingot);// Steel Frame

					TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.stake.item, 1),  "   ", "IFI", "   ", Character.valueOf('I'), steelItem, Character.valueOf('F'), ironingot );
					TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.stake.item, 1),  "IFI", "   ", "   ", Character.valueOf('I'), steelItem, Character.valueOf('F'), ironingot );
					TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.stake.item, 1),  "   ", "   ", "IFI", Character.valueOf('I'), steelItem, Character.valueOf('F'), ironingot );

					TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.propeller.item, 2),  " # ", "#X#", " # ", Character.valueOf('#'), plankItem, Character.valueOf('X'), ironingot );// Propeller
				}
				TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.steelchimney.item, 2),  "# #", "# #", "# #", Character.valueOf('#'), steelItem );// Bogie
				TrainCraftingManager.instance.addRecipe(new ItemStack(Items.FLINT_AND_STEEL, 2),  "* ", " #", Character.valueOf('*'), steelItem, Character.valueOf('#'), Items.FLINT );


				TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.transmition.item, 1),  " # ", "#X#", " # ", Character.valueOf('#'), steelItem, Character.valueOf('X'), ItemIDs.diesel.item );// transmition
				TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.piston.item, 3),  " # ", " X ", Character.valueOf('#'), steelItem, Character.valueOf('X'), Items.STICK );// piston
				TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.camshaft.item, 3),  "###", "   ", "   ", Character.valueOf('#'), steelItem );// camshaft
				TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.camshaft.item, 3),  "   ", "###", "   ", Character.valueOf('#'), steelItem );// camshaft
				TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.camshaft.item, 3),  "   ", "   ", "###", Character.valueOf('#'), steelItem );// camshaft
				TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.cylinder.item, 3),  "# #", "# #", "###", Character.valueOf('#'), steelItem );// cylinder

			}
			for (ItemStack ironItem : iron) {
				TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.tcRailSmallStraight.item, 16), "I I", "IPI", "I I", Character.valueOf('P'), plankItem, Character.valueOf('I'), ironItem);// small straight track

				TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.tcRailSmallRoadCrossing.item, 16), "I I", "IPI", "I I", Character.valueOf('P'), new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 15), Character.valueOf('I'), ironItem);
				TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.tcRailSmallRoadCrossing1.item, 16), "I I", "IPI", "I I", Character.valueOf('P'), new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 7), Character.valueOf('I'), ironItem);
				TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.tcRailSmallRoadCrossing2.item, 16), "I I", "IPI", "I I", Character.valueOf('P'), new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 8), Character.valueOf('I'), ironItem);
			}
			for (ItemStack logStack :logs) {
				/* Water Wheel */
				TrainCraftingManager.instance.addRecipe(new ItemStack(BlockIDs.waterWheel.block, 1),  " P ", "PGP", " P ", Character.valueOf('P'), logStack,Character.valueOf('G'), ItemIDs.generator.item);

				TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.woodenBogie.item, 4),  " # ", "#X#", " # ", Character.valueOf('#'), plankItem, Character.valueOf('X'), logStack );// wooden Bogie
			}
			TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.woodenFrame.item, 2),  "# #", "AAA", Character.valueOf('A'), plankItem, Character.valueOf('#'), plankItem );// wooden Frame
			TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.woodenFrame.item, 2),  "   ", "# #", "AAA", Character.valueOf('A'), plankItem, Character.valueOf('#'), plankItem );// wooden Frame
			TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.woodenCab.item, 2),  "###", "X X", "XXX", Character.valueOf('X'), plankItem, Character.valueOf('#'), plankItem );// wooden cab

			for (ItemStack ironingot : iron) {
				TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.seats.item, 1), "#  ", "## ", "XXX", Character.valueOf('#'), plankItem, Character.valueOf('X'), ironingot);// transformer
				TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.ironBogie.item, 4), " # ", "#X#", " # ", Character.valueOf('#'), ironingot, Character.valueOf('X'), plankItem);// iron Bogie
				TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.ironFrame.item, 2), "# #", "AAA", Character.valueOf('A'), ironingot, Character.valueOf('#'), plankItem);// iron Frame
				TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.ironFrame.item, 2), "   ", "# #", "AAA", Character.valueOf('A'), ironingot, Character.valueOf('#'), plankItem);// iron Frame
				TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.ironCab.item, 2), "###", "X X", "XXX", Character.valueOf('X'), ironingot, Character.valueOf('#'), plankItem);// iron cab
				TrainCraftingManager.instance.addRecipe(new ItemStack(BlockIDs.stopper.block, 1), "WWW", "I I", "RRR", Character.valueOf('W'), plankItem, Character.valueOf('R'), Blocks.RAIL, Character.valueOf('I'), ironingot);// stopper
				TrainCraftingManager.instance.addRecipe(new ItemStack(BlockIDs.switchStand.block, 1), " W ", " I ", " R ", Character.valueOf('W'), Blocks.LEVER, Character.valueOf('R'), Items.STICK, Character.valueOf('I'), ironingot);//switchstand
			}
			// Short Wood Slope
			TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.tcRailSlopeWood.item, 1),
					 " MW", "MWW", "WWW", Character.valueOf('M'), ItemIDs.tcRailMediumStraight.item,
							Character.valueOf('W'), plankItem );
		}
				/* Wind mill */
		for (ItemStack ironingot : iron) {
			TrainCraftingManager.instance.addRecipe(new ItemStack(BlockIDs.windMill.block, 1), " R ", " G ", "B B", Character.valueOf('G'), ItemIDs.generator.item, Character.valueOf('B'), ironingot, Character.valueOf('R'), ItemIDs.propeller.item);

			if (Loader.isModLoaded("ComputerCraft") || Loader.isModLoaded("OpenComputers")) {
				TrainCraftingManager.instance.addRecipe(new ItemStack(BlockIDs.mtcTransmitterSpeed.block, 1), "SRS", "RTR", "SRS", 'S', ironingot, 'R', Items.REDSTONE, 'T', Blocks.STONE_PRESSURE_PLATE);
				TrainCraftingManager.instance.addRecipe(new ItemStack(BlockIDs.mtcReceiverMTC.block, 1), "STS", " R ", "SPS", 'S', ironingot, 'R', Items.REDSTONE, 'P', Items.REPEATER, 'T', new ItemStack(Blocks.TORCH, 1));
				TrainCraftingManager.instance.addRecipe(new ItemStack(BlockIDs.mtcTransmitterMTC.block, 1), "GSG", "TRT", "GSG", 'G', Items.GOLD_INGOT, 'S', new ItemStack(Blocks.STONE), 'R', Items.REPEATER, 'T', new ItemStack(Blocks.REDSTONE_TORCH));
				TrainCraftingManager.instance.addRecipe(new ItemStack(BlockIDs.mtcReceiverDestination.block, 1), "SRS", "RTR", "SRS", 'S', ironingot, 'R', Items.REDSTONE, 'T', Items.SIGN);
				TrainCraftingManager.instance.addRecipe(new ItemStack(BlockIDs.mtcATOStopTransmitter.block, 1), " S ", "RTS", " R ", 'S', ironingot, 'R', Items.REDSTONE, 'T', ItemIDs.electronicCircuit.item);
				TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.atoCard.item, 1), " X ", "#E#", " $ ", '#', ironingot, 'X', ItemIDs.controls.item, '$', Items.DIAMOND, 'E', ItemIDs.electronicCircuit.item);
				TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.wirelessTransmitter.item, 1), " # ", "#EX", " X ", '#', ironingot, 'X', Items.REDSTONE, '$', Items.DIAMOND, 'E', ItemIDs.electronicCircuit.item);
			}

		}


/*
		for (ItemStack s: iron) {

		}*/




		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.tcRailSmallStraight.item, 1),  "   ", " R ", "   ", Character.valueOf('R'), Item.getItemFromBlock(Blocks.RAIL));// small straight track
		/*TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.tcRailSmallRoadCrossing.item, 1),  "   ", "SRS", "   ", Character.valueOf('S'), new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 15), Character.valueOf('R'), Item.getItemFromBlock(Blocks.RAIL) );
		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.tcRailSmallRoadCrossing1.item, 1),  "   ", "SRS", "   ", Character.valueOf('S'), new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 7), Character.valueOf('R'), Item.getItemFromBlock(Blocks.RAIL) );
		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.tcRailSmallRoadCrossing2.item, 1),  "   ", "SRS", "   ", Character.valueOf('S'), new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 3), Character.valueOf('R'), Item.getItemFromBlock(Blocks.RAIL) );*/
		TrainCraftingManager.instance.addRecipe(new ItemStack(Item.getItemFromBlock(Blocks.RAIL), 1),  "   ", " R ", "   ", Character.valueOf('R'), ItemIDs.tcRailSmallStraight.item);
		TrainCraftingManager.instance.addRecipe(new ItemStack(Item.getItemFromBlock(Blocks.RAIL), 1),  "   ", " R ", "   ", Character.valueOf('R'), ItemIDs.tcRailSmallRoadCrossing.item);
		TrainCraftingManager.instance.addRecipe(new ItemStack(Item.getItemFromBlock(Blocks.RAIL), 1),  "   ", " R ", "   ", Character.valueOf('R'), ItemIDs.tcRailSmallRoadCrossing1.item);
		TrainCraftingManager.instance.addRecipe(new ItemStack(Item.getItemFromBlock(Blocks.RAIL), 1),  "   ", " R ", "   ", Character.valueOf('R'), ItemIDs.tcRailSmallRoadCrossing2.item);
		// Short Slope Gravel
		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.tcRailSlopeGravel.item, 1),
				 " MG", "MGG", "GGG", Character.valueOf('M'), ItemIDs.tcRailMediumStraight.item,
						Character.valueOf('G'), Blocks.GRAVEL );
		// Short Slope Ballast
		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.tcRailSlopeBallast.item, 1),
				 " MB", "MBB", "BBB", Character.valueOf('M'), ItemIDs.tcRailMediumStraight.item,
						Character.valueOf('B'), new ItemStack(BlockIDs.oreTC.block, 1, 3) );

		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.tcRailSlopeGravel.item, 1),
				"   ", " S ", " B ", 'S', ItemIDs.tcRailSlopeWood.item, 'B', Blocks.GRAVEL );
		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.tcRailLargeSlopeGravel.item, 1),
				"   ", " S ", " B ", 'S', ItemIDs.tcRailLargeSlopeWood.item, 'B', Blocks.GRAVEL );
		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.tcRailVeryLargeSlopeGravel.item, 1),
				"   ", " S ", " B ", 'S', ItemIDs.tcRailVeryLargeSlopeWood.item, 'B', Blocks.GRAVEL );

		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.tcRailSlopeBallast.item, 1),
				"   ", " S ", " B ", 'S', ItemIDs.tcRailSlopeWood.item, 'B', Blocks.GRAVEL );
		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.tcRailLargeSlopeBallast.item, 1),
				"   ", " S ", " B ", 'S', ItemIDs.tcRailLargeSlopeWood.item, 'B', Blocks.GRAVEL );
		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.tcRailVeryLargeSlopeBallast.item, 1),
				"   ", " S ", " B ", 'S', ItemIDs.tcRailVeryLargeSlopeWood.item, 'B', Blocks.GRAVEL );


		// Large Slope Wood
		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.tcRailLargeSlopeWood.item, 1),
				 "   ", "  S", " S ", 'S', ItemIDs.tcRailSlopeWood.item );
		// Large Slope Gravel
		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.tcRailLargeSlopeGravel.item, 1),
				 "   ", "  S", " S ", 'S', ItemIDs.tcRailSlopeGravel.item );
		// Large Slope Ballast
		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.tcRailLargeSlopeBallast.item, 1),
				 "   ", "  S", " S ", 'S', ItemIDs.tcRailSlopeBallast.item );
		// VeryLarge Slope Wood
		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.tcRailVeryLargeSlopeWood.item, 1),
				 "  S", " S ", "S  ", 'S', ItemIDs.tcRailSlopeWood.item );
		// VeryLarge Slope Gravel
		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.tcRailVeryLargeSlopeGravel.item, 1),
				 "  S", " S ", "S  ", 'S', ItemIDs.tcRailSlopeGravel.item );
		// VeryLarge Slope Ballast
		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.tcRailVeryLargeSlopeBallast.item, 1),
				 "  S", " S ", "S  ", 'S', ItemIDs.tcRailSlopeBallast.item );
		
		// Medium Straight (3 Recipes? Really?)
		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.tcRailMediumStraight.item, 1),
				 " S ", " S ", " S ", Character.valueOf('S'), ItemIDs.tcRailSmallStraight.item );
		// Long Straight (3 Recipes? Really?)
		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.tcRailLongStraight.item, 1),
				 "   ", " M ", " M ", Character.valueOf('M'), ItemIDs.tcRailMediumStraight.item );
		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.tcRailLongStraight.item, 1),
				" M ", " M ", "   ", Character.valueOf('M'), ItemIDs.tcRailMediumStraight.item );
		
		// Medium Turn
		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.tcRailMediumTurn.item, 1),
				 "SS ", "S  ", Character.valueOf('S'), ItemIDs.tcRailSmallStraight.item );
		// Large turn
		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.tcRailLargeTurn.item, 1),
				 " SS", "SS ", "S  ", Character.valueOf('S'), ItemIDs.tcRailSmallStraight.item );
		// Very Large Turn
		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.tcRailVeryLargeTurn.item, 1),
				 "MM ", "M  ", "   ", Character.valueOf('M'), ItemIDs.tcRailMediumTurn.item );
		
		// Medium Switch
		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.tcRailMediumSwitch.item, 1),
				 "S  ", "SRS", "S  ", Character.valueOf('S'), ItemIDs.tcRailSmallStraight.item,
						Character.valueOf('R'), ItemIDs.tcRailMediumTurn.item );
		// Large Switch
		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.tcRailLargeSwitch.item, 1),
				 "S  ", "MRS", "S  ", Character.valueOf('S'), ItemIDs.tcRailSmallStraight.item,
						Character.valueOf('M'), ItemIDs.tcRailMediumStraight.item, Character.valueOf('R'),
						ItemIDs.tcRailLargeTurn.item );
		// Parallel Switch
		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.tcRailMediumParallelSwitch.item, 1),
				 "M S", "MRR", "MR ", Character.valueOf('S'), ItemIDs.tcRailSmallStraight.item,
						Character.valueOf('M'), ItemIDs.tcRailMediumStraight.item, Character.valueOf('R'),
						ItemIDs.tcRailMediumTurn.item );
		// Two Way Crossing
		TrainCraftingManager.instance.addRecipe(new ItemStack(ItemIDs.tcRailTwoWaysCrossing.item, 1),
				 " S ", "SSS", " S ", Character.valueOf('S'), ItemIDs.tcRailSmallStraight.item );
		// Bridge Pillar
		TrainCraftingManager.instance.addRecipe(new ItemStack(BlockIDs.bridgePillar.block, 2),
				 "SSS", "S S", "SSS", Character.valueOf('S'), Items.STICK );




		TrainCraftingManager.instance.addRecipe(new ItemStack(Blocks.RAIL,3), "   ", " S ", "   ",
				(char)'S',new ItemStack(ItemIDs.tcRailMediumStraight.item,1));
		TrainCraftingManager.instance.addRecipe(new ItemStack(Blocks.RAIL,6), "   ", " S ", "   ",
				(char)'S',new ItemStack(ItemIDs.tcRailLongStraight.item,1));

		TrainCraftingManager.instance.addRecipe(new ItemStack(Blocks.RAIL,13), "   ", " S ", "   ",
				(char)'S',new ItemStack(ItemIDs.tcRailLargeSwitch.item,1));
		TrainCraftingManager.instance.addRecipe(new ItemStack(Blocks.RAIL,8), "   ", " S ", "   ",
				(char)'S',new ItemStack(ItemIDs.tcRailMediumSwitch.item,1));
		TrainCraftingManager.instance.addRecipe(new ItemStack(Blocks.RAIL,20), "   ", " S ", "   ",
				(char)'S',new ItemStack(ItemIDs.tcRailMediumParallelSwitch.item,1));

		TrainCraftingManager.instance.addRecipe(new ItemStack(Blocks.RAIL,5), "   ", " S ", "   ",
				(char)'S',new ItemStack(ItemIDs.tcRailMediumTurn.item,1));
		TrainCraftingManager.instance.addRecipe(new ItemStack(Blocks.RAIL,9), "   ", " S ", "   ",
				(char)'S',new ItemStack(ItemIDs.tcRailLargeTurn.item,1));
		TrainCraftingManager.instance.addRecipe(new ItemStack(Blocks.RAIL,19), "   ", " S ", "   ",
				(char)'S',new ItemStack(ItemIDs.tcRailLargeTurn.item,1));

		TrainCraftingManager.instance.addRecipe(new ItemStack(Blocks.RAIL,6), "   ", " S ", "   ",
				(char)'S',new ItemStack(ItemIDs.tcRailSlopeBallast.item,1));
		TrainCraftingManager.instance.addRecipe(new ItemStack(Blocks.RAIL,12), "   ", " S ", "   ",
				(char)'S',new ItemStack(ItemIDs.tcRailLargeSlopeBallast.item,1));
		TrainCraftingManager.instance.addRecipe(new ItemStack(Blocks.RAIL,18), "   ", " S ", "   ",
				(char)'S',new ItemStack(ItemIDs.tcRailVeryLargeSlopeBallast.item,1));

		TrainCraftingManager.instance.addRecipe(new ItemStack(Blocks.RAIL,6), "   ", " S ", "   ",
				(char)'S',new ItemStack(ItemIDs.tcRailSlopeWood.item,1));
		TrainCraftingManager.instance.addRecipe(new ItemStack(Blocks.RAIL,12), "   ", " S ", "   ",
				(char)'S',new ItemStack(ItemIDs.tcRailLargeSlopeWood.item,1));
		TrainCraftingManager.instance.addRecipe(new ItemStack(Blocks.RAIL,18), "   ", " S ", "   ",
				(char)'S',new ItemStack(ItemIDs.tcRailVeryLargeSlopeWood.item,1));

		TrainCraftingManager.instance.addRecipe(new ItemStack(Blocks.RAIL,6), "   ", " S ", "   ",
				(char)'S',new ItemStack(ItemIDs.tcRailSlopeGravel.item,1));
		TrainCraftingManager.instance.addRecipe(new ItemStack(Blocks.RAIL,12), "   ", " S ", "   ",
				(char)'S',new ItemStack(ItemIDs.tcRailLargeSlopeGravel.item,1));
		TrainCraftingManager.instance.addRecipe(new ItemStack(Blocks.RAIL,18), "   ", " S ", "   ",
				(char)'S',new ItemStack(ItemIDs.tcRailVeryLargeSlopeGravel.item,1));
		//ATO Card and W-MTC card

	}
	
	public static void initSmeltingRecipes(){

		/* OpenHearthFurnace recipes */
		if (!ConfigHandler.MAKE_MODPACKS_GREAT_AGAIN) {
			List<ItemStack> steel = OreDictionary.getOres("ingotSteel");
			List<ItemStack> iron = OreDictionary.getOres("ingotIron");
			for (ItemStack s : steel) {
				for (ItemStack ironitm : iron)
				TrainCraftingManager.instance.addHearthFurnaceRecipe(ironitm,
						new ItemStack(ItemIDs.graphite.item), s, 2F, 1000);
			}
		}
		
		/* Vanilla Furnace recipes */
		GameRegistry.addSmelting(new ItemStack(BlockIDs.oreTC.block, 0), OreDictionary.getOres("ingotCopper").get(0), 0.7f);
	}

	private static int dictRecipeCounter = 0;

	public static void addDictRecipe(ItemStack stack, Object... obj) {
		ResourceLocation name = new ResourceLocation("traincraft", "dict_recipe_" + (++dictRecipeCounter));
		ShapedOreRecipe recipe = new ShapedOreRecipe(name, stack, obj);
		recipe.setRegistryName(name);
		ForgeRegistries.RECIPES.register(recipe);
	}
}
