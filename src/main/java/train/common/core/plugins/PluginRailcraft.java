package train.common.core.plugins;

import net.minecraftforge.fml.common.registry.GameRegistry;
import mods.railcraft.api.crafting.Crafters;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import train.common.core.util.TraincraftUtil;
import train.common.library.ItemIDs;
import train.common.library.Tracks;

import java.util.List;

public class PluginRailcraft {

	public static void init(){
		registerRecipes();
	}

	private static void registerRecipes(){
		for(Tracks track : Tracks.values()){
			if(track.crafting != null){
				GameRegistry.addShapedRecipe(new ResourceLocation("traincraft", "track_" + track.getTag().replace(".", "_")), null, track.getOutput(), track.crafting);
			}
		}
		GameRegistry.addShapelessRecipe(new ResourceLocation("traincraft", "snowy_steel_track"), (ResourceLocation) null, Tracks.SNOWY_STEEL_TRACK.getOutput(), Ingredient.fromStacks(Tracks.STEEL_TRACK.getTrackSpec().getItem(1)), Ingredient.fromItem(Items.SNOWBALL));
		GameRegistry.addShapelessRecipe(new ResourceLocation("traincraft", "snowy_copper_track"), (ResourceLocation) null, Tracks.SNOWY_COPPER_TRACK.getOutput(), Ingredient.fromStacks(Tracks.COPPER_TRACK.getTrackSpec().getItem(1)), Ingredient.fromItem(Items.SNOWBALL));
		GameRegistry.addShapelessRecipe(new ResourceLocation("traincraft", "vanilla_snowy_track"), (ResourceLocation) null, Tracks.VANILLA_SNOWY_TRACK.getOutput(), Ingredient.fromItem(Item.getItemFromBlock(Blocks.RAIL)), Ingredient.fromItem(Items.SNOWBALL));
		List<ItemStack> copper = OreDictionary.getOres("ingotCopper");
		if (copper != null && !copper.isEmpty()) {
			int i = 0;
			for(ItemStack aCopper : copper){
				Crafters.rollingMachine().newRecipe(new ItemStack(ItemIDs.copperRail.item, 8))
						.name("traincraft", "copper_rail_" + (i++))
						.shaped("XXX", "   ", "XXX", 'X', aCopper);
			}
		}
		List<ItemStack> steel = OreDictionary.getOres("ingotSteel");
		if (steel != null && !steel.isEmpty()) {
			int i = 0;
			for(ItemStack aSteel : steel){
				Crafters.rollingMachine().newRecipe(new ItemStack(ItemIDs.steelRail.item, 16))
						.name("traincraft", "steel_rail_" + (i++))
						.shaped("XXX", "   ", "XXX", 'X', aSteel);
			}
		}
	}

	public enum RailcraftParts{
		COKE("fuel.coke", 0),
		INGOT_STEEL("ingot", 0),
		RAIL_STANDARD("part.rail", 0),
		RAIL_ADVANCED("part.rail", 1),
		RAIL_WOOD("part.rail", 2),
		RAIL_SPEED("part.rail", 3),
		RAIL_REINFORCED("part.rail", 4),
		RAILBED_WOOD("part.railbed", 0),
		RAILBED_STONE("part.railbed", 1);

		public ItemStack stack;
		RailcraftParts(String itemName, int meta){
			// NOTE: current Railcraft (1.12.2, 12.1.0-beta-8) registers items under the lowercase
			// "railcraft" domain (the old 1.7.10-era code here used "Railcraft:", which never
			// actually matched anything in the item registry - see mods.railcraft.common.core.Railcraft.MOD_ID).
			this.stack = TraincraftUtil.getItemFromUnlocalizedName("railcraft:" + itemName, meta);
		}
	}

}
