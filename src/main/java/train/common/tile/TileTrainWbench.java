/*******************************************************************************
 * Copyright (c) 2012 Mrbrutal. All rights reserved.
 * 
 * @name TrainCraft
 * @author Mrbrutal
 ******************************************************************************/

package train.common.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;
import net.minecraft.util.EnumFacing;
import java.util.Arrays;

public class TileTrainWbench extends TileEntity implements IInventory {

	private ItemStack[] workbenchItemStacks;
	private EnumFacing facing;

	public TileTrainWbench() {

		workbenchItemStacks = new ItemStack[9];
	}

	@Override
	public void closeInventory(net.minecraft.entity.player.EntityPlayer player) {}

	@Override
	public String getName() {

		return "TrainWorkbench";
	}

	@Override
	public boolean hasCustomName() {

		return false;
	}

	@Override
	public void openInventory(net.minecraft.entity.player.EntityPlayer player) {}

	@Override
	public int getSizeInventory() {

		return this.workbenchItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		if (i < 0 || i >= this.workbenchItemStacks.length || this.workbenchItemStacks[i] == null) return ItemStack.EMPTY;
		return this.workbenchItemStacks[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {

		if (workbenchItemStacks[i] != null) {

			if (workbenchItemStacks[i].getCount() <= j) {

				ItemStack itemstack = workbenchItemStacks[i];
				workbenchItemStacks[i] = null;

				return itemstack;
			}

			ItemStack itemstack1 = workbenchItemStacks[i].splitStack(j);

			if (workbenchItemStacks[i].isEmpty()) {

				workbenchItemStacks[i] = null;
			}

			return itemstack1;
		}
		else {

			return ItemStack.EMPTY;
		}
	}

	@Override
	public ItemStack removeStackFromSlot(int i) {

		if (this.workbenchItemStacks[i] != null) {

			ItemStack stack = this.workbenchItemStacks[i];
			this.workbenchItemStacks[i] = null;

			return stack;
		}
		else {

			return ItemStack.EMPTY;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack stack) {

		workbenchItemStacks[i] = (stack == null || stack.isEmpty()) ? null : stack;

		if (workbenchItemStacks[i] != null && stack.getCount() > getInventoryStackLimit()) {

			stack.setCount(getInventoryStackLimit());
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTag) {

		super.readFromNBT(nbtTag);

		facing = EnumFacing.byIndex(nbtTag.getByte("Orientation"));
		NBTTagList tagList = nbtTag.getTagList("Items", Constants.NBT.TAG_COMPOUND);
		workbenchItemStacks = new ItemStack[getSizeInventory()];

		for (int i = 0; i < tagList.tagCount(); ++i) {

			NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
			byte slot = tagCompound.getByte("Slot");

			if (slot >= 0 && slot < workbenchItemStacks.length) {

				workbenchItemStacks[slot] = new ItemStack(tagCompound);
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbtTag) {

		super.writeToNBT(nbtTag);

		if (facing != null) {

			nbtTag.setByte("Orientation", (byte) facing.ordinal());
		}
		else {

			nbtTag.setByte("Orientation", (byte) EnumFacing.NORTH.ordinal());
		}

		NBTTagList tagList = new NBTTagList();

		for (int i = 0; i < workbenchItemStacks.length; ++i) {

			if (workbenchItemStacks[i] != null) {

				NBTTagCompound tagCompound = new NBTTagCompound();
				tagCompound.setByte("Slot", (byte) i);
				workbenchItemStacks[i].writeToNBT(tagCompound);
				tagList.appendTag(tagCompound);
			}
		}

		nbtTag.setTag("Items", tagList);
		return nbtTag;
	}

	@Override
	public int getInventoryStackLimit() {

		return 64;
	}


	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {

		if (world == null || world.getTileEntity(this.pos) != this) {

			return false;
		}

		return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
	}

	public EnumFacing getFacing() {

		return (facing != null ? this.facing : EnumFacing.NORTH);
	}

	public void setFacing(EnumFacing face) {

		this.facing = face;
	}

	@Override
	public net.minecraft.network.play.server.SPacketUpdateTileEntity getUpdatePacket() {

		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);

		return new SPacketUpdateTileEntity(this.pos, 1, nbt);
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {

		return true;
	}

	@Override
	public void clear() { Arrays.fill(workbenchItemStacks, null); }

	@Override
	public boolean isEmpty() {
		for (ItemStack s : workbenchItemStacks) { if (s != null && !s.isEmpty()) return false; }
		return true;
	}

	@Override
	public int getField(int id) { return 0; }

	@Override
	public void setField(int id, int value) {}

	@Override
	public int getFieldCount() { return 0; }
}