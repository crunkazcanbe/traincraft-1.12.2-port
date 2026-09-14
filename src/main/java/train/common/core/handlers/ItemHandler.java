/*******************************************************************************
 * Copyright (c) 2013 Mrbrutal. All rights reserved.
 *
 * @name Traincraft
 * @author Mrbrutal
 ******************************************************************************/

package train.common.core.handlers;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import train.common.api.*;
import train.common.entity.rollingStock.*;
import train.common.items.ItemTCRail;

public class ItemHandler {
	
	public static boolean handleItems(Entity entity, ItemStack itemstack) {
		if (itemstack != null) {
			if (entity instanceof Freight) {
				return handleFreight(entity, itemstack);
			} else if (entity instanceof DieselTrain) {
				return false;
			} else if (entity instanceof ElectricTrain) {
				return false;
			} else if (entity instanceof SteamTrain) {
				return false;
			} else if (entity instanceof Tender) {
				return false;
			} else {
				return false;
			}
		}
		return false;
	}

	public static boolean handleFreight(Entity entity, ItemStack itemstack) {
		int logWood = OreDictionary.getOreID("logWood");
		int plankWood = OreDictionary.getOreID("plankWood");
		int slabWood =  OreDictionary.getOreID("slabWood");
		int stairWood = OreDictionary.getOreID("stairWood");
		if(itemstack == null) {
			return false;
		}
		Block block = Block.getBlockFromItem(itemstack.getItem());
		if (block == null) {
			return false;
		}
		if (entity instanceof EntityFreightCenterbeam_Wood_1 || entity instanceof EntityFreightCenterbeam_Wood_2 ||
				entity instanceof EntityFlatCartWoodUS || entity instanceof EntityBulkheadFlatCart || entity instanceof EntityFlatCarLogs_DB ||
				entity instanceof EntityFreightWood || entity instanceof EntityFreightWood2) {
			boolean matchesOre = false;
			for (int isid : OreDictionary.getOreIDs(itemstack)) {
				if (isid == plankWood || isid == logWood || isid == slabWood || isid == stairWood) {
					matchesOre = true;
					break;
				}
			}
			return matchesOre ||
					itemstack.getItem() == Item.getItemFromBlock(Blocks.LADDER) || itemstack.getItem() == Item.getItemFromBlock(Blocks.OAK_FENCE) || itemstack.getItem() == Item.getItemFromBlock(Blocks.OAK_FENCE_GATE);
		}
		else if (entity instanceof EntityFlatCarRails_DB) {
			return block instanceof BlockRailBase || itemstack.getItem() instanceof ItemTCRail;
		}
		else if (entity instanceof EntityFreightGrain) {
			Item item = itemstack.getItem();
			if (item == Items.WHEAT || item == Items.WHEAT_SEEDS || item == Items.MELON_SEEDS
					|| item == Items.PUMPKIN_SEEDS || item instanceof ItemSeeds) {
				return true;
			}
			return cropStuff(itemstack);
		}
		else if (entity instanceof EntityFreightMinetrain) {
				return block.isOpaqueCube(block.getDefaultState());
		}
		else if (entity instanceof EntityFreightSlateWagon){
			return block.getDefaultState().getMaterial() == Material.ROCK;
		}
		else if (entity instanceof EntityFreightIceWagon){
			return block.getDefaultState().getMaterial() == Material.ICE || block.getDefaultState().getMaterial() == Material.PACKED_ICE;
		}
		else {
			return true;
		}
	}

	private static boolean cropStuff(ItemStack itemstack) {
		String[] names = new String[] { "cropCorn", "cropRice", "seedRice", "seedCorn", "listAllseed" };
		for (String name: names) {
			int nameId = OreDictionary.getOreID(name);
			for (int isid : OreDictionary.getOreIDs(itemstack)) {
				if (isid == nameId) {
					return true;
				}
			}
		}
		return false;
	}
}
