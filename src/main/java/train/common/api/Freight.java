package train.common.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import train.common.adminbook.ServerLogger;

public abstract class Freight extends EntityRollingStock implements IInventory {
	public ItemStack cargoItems[];
	protected double itemInsideCount = 0;
	private int slotsFilled=0;
	public Freight(World world) {
		super(world);
		dataWatcher.addObject(22, 0);
	}

	@Override
	public void clear() {
		if (cargoItems != null) {
			for (int i = 0; i < cargoItems.length; i++) {
				cargoItems[i] = null;
			}
		}
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float i) {
		if (this.world.isRemote) {
			return true;
		}
		if(canBeDestroyedByPlayer(damagesource))return true;
		super.attackEntityFrom(damagesource, i);
		setRollingDirection(-getRollingDirection());
		setRollingAmplitude(10);
		this.velocityChanged = true;
		setDamage(getDamage() + i * 10);
		if (getDamage() > 40) {
			if (!getPassengers().isEmpty()) {
				getPassengers().get(0).dismountRidingEntity();
			}
			this.setDead();
			ServerLogger.deleteWagon(this);
			if(damagesource.getTrueSource() instanceof EntityPlayer) {
				dropCartAsItem(((EntityPlayer)damagesource.getTrueSource()).capabilities.isCreativeMode);
			}
		}
		return true;
	}

	/* IInventory implements */
	@Override
	public ItemStack getStackInSlot(int i) {
		// 1.12.2: empty slots MUST be ItemStack.EMPTY, never null, or the container
		// GUI NPEs the moment it draws an empty cargo slot (Slot.getStack().isEmpty()).
		if (cargoItems == null || i < 0 || i >= cargoItems.length || cargoItems[i] == null) {
			return ItemStack.EMPTY;
		}
		return cargoItems[i];
	}

	@Override
	public boolean isEmpty() {
		if (cargoItems == null) return true;
		for (ItemStack stack : cargoItems) {
			if (stack != null && !stack.isEmpty()) return false;
		}
		return true;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		ItemStack stack = cargoItems[index];
		cargoItems[index] = null;
		return stack == null ? ItemStack.EMPTY : stack;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return true;
	}

	public ItemStack getStackInSlotOnClosing(int par1) {
		if (this.cargoItems[par1] != null) {
			ItemStack var2 = this.cargoItems[par1];
			this.cargoItems[par1] = null;
			return var2;
		}
		else {
			return ItemStack.EMPTY;
		}
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (cargoItems[i] != null) {
			if (cargoItems[i].getCount() <= j) {
				ItemStack itemstack = cargoItems[i];
				cargoItems[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = cargoItems[i].splitStack(j);
			if (cargoItems[i].isEmpty()) {
				cargoItems[i] = null;
			}
			return itemstack1;
		}
		else {
			return ItemStack.EMPTY;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		cargoItems[i] = itemstack;
		if (itemstack != null && itemstack.getCount() > getInventoryStackLimit()) {
			itemstack.setCount(getInventoryStackLimit());
		}
	}

	@Override
	public void markDirty() {
		if(!this.world.isRemote){
			this.slotsFilled=0;
			for (int i = 0; i < getSizeInventory(); i++) {
				ItemStack itemstack = getStackInSlot(i);
				if(itemstack != null && !itemstack.isEmpty()) {
					slotsFilled++;
				}
			}
			this.dataWatcher.updateObject(22, slotsFilled);
		}
	}

	@Override
	public String getName() {
		return "Freight cart";
	}

	@Override
	public abstract int getSizeInventory();

	/**
	 * Returns true if this cart is a storage cart Some carts may have inventories but not be storage carts and some carts without inventories may be storage carts.
	 * 
	 * @return True if this cart should be classified as a storage cart.
	 */
	@Override
	public boolean isStorageCart() {
		return true;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		handleMass();
	}

	/**
	 * Handle mass depending on item count tenders are done differently
	 */
	protected void handleMass() {
		if (this.ticksExisted % 10 != 0)
			return;
		if (this.world.isRemote)
			return;
		this.mass = this.getDefaultMass();
		this.itemInsideCount = 0;
		for (int i = 0; i < getSizeInventory(); i++) {
			ItemStack itemstack = getStackInSlot(i);
			if (itemstack != null && itemstack.getCount() > 0) {
				this.itemInsideCount += itemstack.getCount();
			}
		}
		mass += (this.itemInsideCount * 0.0001);
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
	}
	public int getAmmountOfCargo() {
		return this.dataWatcher.getWatchableObjectInt(22);
	}
	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		super.readEntityFromNBT(nbttagcompound);
		ItemStack cargoItemsCount[];
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
		cargoItemsCount = new ItemStack[getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound1.getByte("Slot") & 0xff;
			if (j >= 0 && j < cargoItemsCount.length) {
				cargoItemsCount[j] = new ItemStack(nbttagcompound1);
			}
		}
		slotsFilled = 0;
		for (int i = 0; i < getSizeInventory(); i++) {
			ItemStack itemstack = cargoItemsCount[i];
			if(itemstack != null) {
				slotsFilled++;
			}
		}
		this.dataWatcher.updateObject(22, slotsFilled);
	}

	@Override
	public void dropCartAsItem(boolean isCreative){
		if(!itemdropped) {
			super.dropCartAsItem(isCreative);
			if (!(this instanceof Tender) && cargoItems != null) {
				for (ItemStack stack : cargoItems) {
					if (stack != null) {
						entityDropItem(stack, 0);
					}
				}
			}
		}
	}

	@Override
	public ItemStack[] getInventory(){return cargoItems;}
}