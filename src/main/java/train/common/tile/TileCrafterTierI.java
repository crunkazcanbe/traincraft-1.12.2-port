package train.common.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;
import net.minecraft.util.EnumFacing;
import train.common.core.interfaces.ITier;
import train.common.core.managers.TierRecipe;
import train.common.core.managers.TierRecipeManager;
import train.common.library.Info;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class TileCrafterTierI extends TileEntity implements IInventory, ITier {
	private ItemStack[] crafterInventory;

	private EnumFacing facing;
	private final int Tier = 1;
	private List<Item> resultList;
	private static List<Item> knownRecipes = new ArrayList<Item>();
	private static int[] slotSelected;

	public TileCrafterTierI() {
		crafterInventory = new ItemStack[26];
		this.resultList = new ArrayList<Item>();
		slotSelected = new int[8];
	}

	@Override
	public int getSizeInventory() {
		return crafterInventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		if (i < 0 || i >= crafterInventory.length || crafterInventory[i] == null) return ItemStack.EMPTY;
		return crafterInventory[i];
	}

	@Override
	public List<Item> getResultList() {
		return resultList;
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (crafterInventory[i] != null) {
			if (crafterInventory[i].getCount() <= j) {
				ItemStack itemstack = crafterInventory[i];
				crafterInventory[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = crafterInventory[i].splitStack(j);
			if (crafterInventory[i].isEmpty()) {
				crafterInventory[i] = null;
			}
			return itemstack1;
		}
		else {
			return ItemStack.EMPTY;
		}
	}

	@Override
	public ItemStack removeStackFromSlot(int i) {
		if (crafterInventory[i] != null) {
			ItemStack stack = this.crafterInventory[i];
			crafterInventory[i] = null;
			return stack;
		}
		else {
			return ItemStack.EMPTY;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		crafterInventory[i] = (itemstack == null || itemstack.isEmpty()) ? null : itemstack;
		if (crafterInventory[i] != null && itemstack.getCount() > getInventoryStackLimit()) {
			itemstack.setCount(getInventoryStackLimit());
		}
	}

	@Override
	public String getName() {
		return "TierI";
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTag) {

		super.readFromNBT(nbtTag);

		facing = EnumFacing.byIndex(nbtTag.getByte("Orientation"));
		slotSelected = nbtTag.getIntArray("Selected");
		NBTTagList nbttaglist = nbtTag.getTagList("Items", Constants.NBT.TAG_COMPOUND);

		this.crafterInventory = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); i++) {

			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");

			if (byte0 >= 0 && byte0 < crafterInventory.length) {

				this.crafterInventory[byte0] = new ItemStack(nbttagcompound1);
			}
		}

		NBTTagList nbttaglist2 = nbtTag.getTagList("Known", Constants.NBT.TAG_COMPOUND);

		for (int i = 0; i < nbttaglist2.tagCount(); i++) {

			NBTTagCompound nbttagcompound2 = nbttaglist2.getCompoundTagAt(i);
			byte byte1 = nbttagcompound2.getByte("Recipe");

			if (byte1 >= 0) {

				if (!listContainsItem(knownRecipes, new ItemStack(nbttagcompound2).getItem())) {

					knownRecipes.add(new ItemStack(nbttagcompound2).getItem());
				}
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

		nbtTag.setIntArray("Selected", slotSelected);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < this.crafterInventory.length; i++) {

			if (this.crafterInventory[i] != null) {

				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.crafterInventory[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbtTag.setTag("Items", nbttaglist);
		NBTTagList nbttaglist2 = new NBTTagList();

		if (knownRecipes != null) {

			for (int i = 0; i < knownRecipes.size(); i++) {

				NBTTagCompound nbttagcompound2 = new NBTTagCompound();
				nbttagcompound2.setByte("Recipe", (byte) i);
				new ItemStack(knownRecipes.get(i)).writeToNBT(nbttagcompound2);
				nbttaglist2.appendTag(nbttagcompound2);
			}

			nbtTag.setTag("Known", nbttaglist2);
		}
		return nbtTag;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() {
		resultList.clear();
		for (int i = 10; i < crafterInventory.length - 8; i++) {
			crafterInventory[i] = null;
		}

		List<TierRecipe> recipes = TierRecipeManager.getInstance().getTierRecipeList(1);
		int count = 0;
		for (TierRecipe recipe : recipes) {
			ItemStack stack = recipe.hasComponents(crafterInventory);
			if (stack != null && !resultList.contains(stack.getItem()) && (count + 10) < crafterInventory.length - 8) {
				resultList.add(stack.getItem());
				crafterInventory[count + 10] = stack;
				count++;
			}
		}

		for (Item item : resultList) {
			if (!listContainsItem(knownRecipes, item)) {
				knownRecipes.add(item);
			}
		}
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer entityplayer) {
		if (world == null) {
			return true;
		}
		if (world.getTileEntity(this.pos) != this) {
			return false;
		}
		return entityplayer.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64D;
	}

	public EnumFacing getFacing() {

		if (facing != null) {

			return this.facing;
		}

		return EnumFacing.NORTH;
	}

	public void setFacing(EnumFacing face) {

		if (facing != face)
			this.facing = face;
	}

	@Override
	public void openInventory(net.minecraft.entity.player.EntityPlayer player) {}

	@Override
	public void closeInventory(net.minecraft.entity.player.EntityPlayer player) {}

	@Override
	public net.minecraft.network.play.server.SPacketUpdateTileEntity getUpdatePacket() {

		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);

		return new SPacketUpdateTileEntity(this.pos, 1, nbt);
	}

	private boolean listContainsItem(List<Item> list, Item stack) {
		for (Item item : list) {
			if (Item.getIdFromItem(item) == Item.getIdFromItem(stack)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int Tier() {
		return Tier;
	}

	@Override
	public List knownRecipes() {
		return knownRecipes;
	}

	@Override
	public int[] getSlotSelected() {
		return slotSelected;
	}

	@Override
	public void setSlotSelected(int[] selected) {
		slotSelected = selected;
	}

	@Override
	public String getGUIName() {
		return "Iron age";
	}

	@Override
	public String getGUITexture() {
		return Info.TEX_TIER_I;
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		if(i>9)
			return false;

		List<TierRecipe> recipeList = TierRecipeManager.getInstance().getTierRecipeList(this.Tier);
		for(TierRecipe recipe : recipeList){
			ItemStack stack2 = recipe.getInput().get(i);
			if (stack2 != null && TierRecipe.areItemsIdentical(stack, stack2)) {
				return true;
			}
		}

		return false;
	}

	/*
	@Override // Just no.
	public void updateEntity() {
		super.updateEntity();
	}*/

	@Override
	public void clear() {
		Arrays.fill(crafterInventory, null);
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack s : crafterInventory) {
			if (s != null && !s.isEmpty()) return false;
		}
		return true;
	}

	@Override
	public int getField(int id) { return 0; }

	@Override
	public void setField(int id, int value) {}

	@Override
	public int getFieldCount() { return 0; }

}