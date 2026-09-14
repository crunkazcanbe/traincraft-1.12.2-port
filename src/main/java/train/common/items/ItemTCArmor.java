/*******************************************************************************
 * Copyright (c) 2012 Mrbrutal. All rights reserved.
 *
 * @name TrainCraft
 * @author Mrbrutal
 ******************************************************************************/

package train.common.items;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import train.common.Traincraft;
import train.common.library.Info;
import train.common.library.ItemIDs;

public class ItemTCArmor extends ItemArmor {
	public int color;
	public int updateTicks = 0;
	private String iconName = "";

	private static EntityEquipmentSlot slotFromIndex(int i) {
		switch (i) {
			case 0: return EntityEquipmentSlot.HEAD;
			case 1: return EntityEquipmentSlot.CHEST;
			case 2: return EntityEquipmentSlot.LEGS;
			default: return EntityEquipmentSlot.FEET;
		}
	}

	public ItemTCArmor(String iconName, ArmorMaterial material, int par3, int par4, int color) {
		super(material, par3, slotFromIndex(par4));
		setCreativeTab(Traincraft.tcTab);
		this.color = color;
		this.iconName = iconName;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		if (stack.getItem() == ItemIDs.overalls.item) {
			return Info.resourceLocation + ":" + Info.armorPrefix + "blue_overalls.png";
		} else if (stack.getItem() == ItemIDs.jacket.item) {
			return Info.resourceLocation + ":" + Info.armorPrefix + "orange_jacket.png";
		} else if (stack.getItem() == ItemIDs.hat.item) {
			return Info.resourceLocation + ":" + Info.armorPrefix + "blue_hat.png";
		} else if (stack.getItem() == ItemIDs.hat_ticketMan_paintable.item || stack.getItem() == ItemIDs.jacket_ticketMan_paintable.item) {
			if (type != null) return Info.resourceLocation + ":" + Info.armorPrefix + "ticket_man_2.png";
			return Info.resourceLocation + ":" + Info.armorPrefix + "ticket_man_1.png";
		} else if (stack.getItem() == ItemIDs.pants_ticketMan_paintable.item) {
			if (type != null) return Info.resourceLocation + ":" + Info.armorPrefix + "ticket_man_pants_2.png";
			return Info.resourceLocation + ":" + Info.armorPrefix + "ticket_man_pants_1.png";
		} else if (stack.getItem() == ItemIDs.hat_driver_paintable.item || stack.getItem() == ItemIDs.jacket_driver_paintable.item) {
			if (type != null) return Info.resourceLocation + ":" + Info.armorPrefix + "driver_2.png";
			return Info.resourceLocation + ":" + Info.armorPrefix + "driver_1.png";
		} else if (stack.getItem() == ItemIDs.pants_driver_paintable.item) {
			if (type != null) return Info.resourceLocation + ":" + Info.armorPrefix + "driver_pants_2.png";
			return Info.resourceLocation + ":" + Info.armorPrefix + "driver_pants_1.png";
		} else {
			return super.getArmorTexture(stack, entity, slot, type);
		}
	}

	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return color != 0;
	}

	public void setColor(ItemStack par1ItemStack, int par2) {
		if (color == 0) {
			throw new UnsupportedOperationException("Can't dye!");
		} else {
			NBTTagCompound nbt = par1ItemStack.getTagCompound();
			if (nbt == null) {
				nbt = new NBTTagCompound();
				par1ItemStack.setTagCompound(nbt);
			}
			NBTTagCompound display = nbt.getCompoundTag("display");
			if (!nbt.hasKey("display")) {
				nbt.setTag("display", display);
			}
			display.setInteger("color", par2);
		}
	}

	@Override
	public void removeColor(ItemStack par1ItemStack) {
		if (color == 0) {
			NBTTagCompound nbt = par1ItemStack.getTagCompound();
			if (nbt != null) {
				NBTTagCompound display = nbt.getCompoundTag("display");
				if (display.hasKey("color")) display.removeTag("color");
			}
		}
	}

	@Override
	public boolean hasColor(ItemStack par1ItemStack) {
		return color != 0 && par1ItemStack.hasTagCompound()
				&& par1ItemStack.getTagCompound().hasKey("display")
				&& par1ItemStack.getTagCompound().getCompoundTag("display").hasKey("color");
	}

	@Override
	public int getColor(ItemStack par1ItemStack) {
		if (color == 0) return -1;
		NBTTagCompound nbt = par1ItemStack.getTagCompound();
		if (nbt == null) return color;
		NBTTagCompound display = nbt.getCompoundTag("display");
		return display == null ? color : (display.hasKey("color") ? display.getInteger("color") : color);
	}

	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
		if (color == 0) return 16777215;
		if (par2 > 0) return 16777215;
		int j = this.getColor(par1ItemStack);
		if (j < 0) j = color;
		return j;
	}
}
