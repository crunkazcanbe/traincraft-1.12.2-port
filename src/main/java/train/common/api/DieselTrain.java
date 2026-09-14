package train.common.api;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.IFluidHandler;
import train.common.api.LiquidManager.StandardTank;
import train.common.entity.rollingStock.EntityBUnitDD35;
import train.common.entity.rollingStock.EntityBUnitEMDF3;
import train.common.entity.rollingStock.EntityBUnitEMDF7;

public abstract class DieselTrain extends Locomotive {

	public int fuelSlot = 1;
	private int maxTank = 7000;
	private int update = 8;
	private StandardTank theTank;

	public DieselTrain(World world, int capacity) {
		this(capacity, world, null, null);
	}

	public DieselTrain(World world, int capacity, FluidStack filter) {
		this(capacity, world, filter, null);
	}

	public DieselTrain(World world, int capacity, FluidStack[] multiFilter) {
		this(capacity, world, null, multiFilter);
	}

	private DieselTrain(int capacity, World world, FluidStack filter, FluidStack[] multiFilter) {
		super(world);
		this.maxTank = capacity;
		if (filter == null && multiFilter == null) {
			this.theTank = LiquidManager.getInstance().new StandardTank(capacity);
		}if (filter != null) {
			this.theTank = LiquidManager.getInstance().new FilteredTank(capacity, filter);
		}if (multiFilter != null) {
			this.theTank = LiquidManager.getInstance().new FilteredTank(capacity, multiFilter);
		}
		dataWatcher.addObject(4, 0);
		numCargoSlots = 3;
		numCargoSlots1 = 3;
		numCargoSlots2 = 3;
		inventorySize = numCargoSlots + numCargoSlots2 + numCargoSlots1 + fuelSlot;
		this.dataWatcher.addObject(23, "null-_-"+0);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (!world.isRemote) {
			if (theTank.getFluidAmount() != Integer.parseInt(this.dataWatcher.getWatchableObjectString(23).split("-_-")[1])){
				this.dataWatcher.updateObject(23,(theTank.getFluid()!=null?theTank.getFluid().getUnlocalizedName():"null")+"-_-"+theTank.getFluidAmount());
				fuelTrain = theTank.getFluidAmount();
				this.dataWatcher.updateObject(4, theTank.getFluid()!=null?0:0);
			}
			if (isLocoTurnedOn() && theTank.getFluidAmount() >0) {
				if (theTank.getFluid().amount <= 1) {
					motionX *= 0.94;
					motionZ *= 0.94;
				}
			}
		}
	}

	public int getDiesel() {
		return getFuel()==0?Integer.parseInt(this.dataWatcher.getWatchableObjectString(23).split("-_-")[1]):getFuel();
	}
	public String getLiquidName(){ return  this.dataWatcher.getWatchableObjectString(23).split("-_-")[0];}

	public int getLiquidItemID() {
		return (this.dataWatcher.getWatchableObjectInt(4));
	}

	public StandardTank getTank() {
		return theTank;
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		super.writeEntityToNBT(nbttagcompound);
		this.theTank.writeToNBT(nbttagcompound);
		nbttagcompound.setBoolean("canBeAdjusted", canBeAdjusted);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		super.readEntityFromNBT(nbttagcompound);
		this.theTank.readFromNBT(nbttagcompound);
		canBeAdjusted = nbttagcompound.getBoolean("canBeAdjusted");
	}

	public int getCartTankCapacity() {
		return maxTank;
	}

	private void placeInInvent(ItemStack itemstack1) {
		for (int i = 1; i < locoInvent.length; i++) {
			if (locoInvent[i] == null) {
				locoInvent[i] = itemstack1;
				return;
			}
			else if (locoInvent[i] != null && locoInvent[i].getItem() == itemstack1.getItem() && itemstack1.isStackable() && (!itemstack1.getHasSubtypes() || locoInvent[i].getItemDamage() == itemstack1.getItemDamage()) && ItemStack.areItemStackTagsEqual(locoInvent[i], itemstack1)) {
				int var9 = locoInvent[i].getCount() + itemstack1.getCount();
				if (var9 <= itemstack1.getMaxStackSize()) {
					locoInvent[i].setCount(var9);

				}
				else if (locoInvent[i].getCount() < itemstack1.getMaxStackSize()) {
					locoInvent[i].grow(1);
				}
				return;
			}
			else if (i == locoInvent.length - 1) {
				entityDropItem(itemstack1,1);
				return;
			}
		}
	}

	public void liquidInSlot(ItemStack itemstack) {
		if (world.isRemote)
			return;
		this.update += 1;
		if (this.update % 8 == 0 && itemstack != null) {
			// This mod ships NO diesel bucket — the raw diesel item (tc:diesel) IS the fuel.
			// Forge's fluid-container path can't see it (it's a plain item, not a fluid
			// handler), so accept it directly: each diesel item tops the tank up by 1000mb.
			net.minecraft.util.ResourceLocation rl = itemstack.getItem().getRegistryName();
			if (rl != null && rl.toString().equals("tc:diesel")) {
				net.minecraftforge.fluids.FluidStack add = new net.minecraftforge.fluids.FluidStack(LiquidManager.DIESEL, 1000);
				if (theTank.fill(add, false) >= 1000) {
					theTank.fill(add, true);
					decrStackSize(0, 1);
				}
				return;
			}
			ItemStack result = LiquidManager.getInstance().processContainer(this, 0, theTank, itemstack);
			if (result != null) {
				placeInInvent(result);
			}
		}
	}

	protected ItemStack checkInvent(ItemStack locoInvent0) {
		if (!this.canCheckInvent)
			return locoInvent0;

		if (getDiesel() > 0) {
			fuelTrain = (getDiesel());
		}
		if (fuelTrain <= 0) {
			motionX *= 0.88;
			motionZ *= 0.88;
		}
		if (locoInvent0 != null) {
			liquidInSlot(locoInvent0);
		}
		return locoInvent0;
	}

	@Override
	protected void updateFuelTrain(int amount) {
		if (!this.isLocoTurnedOn()) {
			motionX *= 0.8;
			motionZ *= 0.8;
		} else if (ticksExisted%5==0 &&getTank().getFluidAmount()+100 < maxTank) {
			FluidStack drain = null;
			blocksToCheck = new TileEntity[]{world.getTileEntity(new net.minecraft.util.math.BlockPos(MathHelper.floor(posX), MathHelper.floor(posY - 1), MathHelper.floor(posZ))),
					world.getTileEntity(new net.minecraft.util.math.BlockPos(MathHelper.floor(posX), MathHelper.floor(posY + 2), MathHelper.floor(posZ))),
					world.getTileEntity(new net.minecraft.util.math.BlockPos(MathHelper.floor(posX), MathHelper.floor(posY + 3), MathHelper.floor(posZ))),
					world.getTileEntity(new net.minecraft.util.math.BlockPos(MathHelper.floor(posX), MathHelper.floor(posY + 4), MathHelper.floor(posZ)))
			};

			for (TileEntity block : blocksToCheck) {
				if (drain == null && block instanceof IFluidHandler) {
					for (EnumFacing direction : EnumFacing.values()) {
						if (((IFluidHandler) block).drain(100, false) != null &&
								(getFluid()==null || ((IFluidHandler) block).drain(100, false).getFluid()==getTank().getFluid().getFluid()) &&
								((IFluidHandler) block).drain(100, false).amount == 100
						) {
							drain = ((IFluidHandler) block).drain(100, true);
						}
					}
				}
			}
			if(drain==null && cartLinked1 instanceof LiquidTank && !(cartLinked1 instanceof EntityBUnitEMDF7) && !(cartLinked1 instanceof EntityBUnitEMDF3) && !(cartLinked1 instanceof EntityBUnitDD35)){
				if (getFluid() == null) {
					drain = ((LiquidTank) cartLinked1).drain(EnumFacing.UP, new FluidStack(LiquidManager.DIESEL, 100), true);
					if (drain == null){
						drain = ((LiquidTank) cartLinked1).drain(EnumFacing.UP, new FluidStack(LiquidManager.REFINED_FUEL, 50), true);
					}
				} else if (getFluid().getFluid() == LiquidManager.DIESEL) {
					drain = ((LiquidTank) cartLinked1).drain(EnumFacing.UP, new FluidStack(LiquidManager.DIESEL, 100), true);
				} else {
					drain = ((LiquidTank) cartLinked1).drain(EnumFacing.UP, new FluidStack(LiquidManager.REFINED_FUEL, 50), true);
				}
			} else if (drain==null && cartLinked2 instanceof LiquidTank && !(cartLinked2 instanceof EntityBUnitEMDF7) && !(cartLinked2 instanceof EntityBUnitEMDF3) && !(cartLinked1 instanceof EntityBUnitDD35)){
				if (getFluid() == null) {
					drain = ((LiquidTank) cartLinked2).drain(EnumFacing.UP, new FluidStack(LiquidManager.DIESEL, 100), true);
					if (drain == null){
						drain = ((LiquidTank) cartLinked2).drain(EnumFacing.UP, new FluidStack(LiquidManager.REFINED_FUEL, 50), true);
					}
				} else if (getFluid().getFluid() == LiquidManager.DIESEL) {
					drain = ((LiquidTank) cartLinked2).drain(EnumFacing.UP, new FluidStack(LiquidManager.DIESEL, 100), true);
				} else {
					drain = ((LiquidTank) cartLinked2).drain(EnumFacing.UP, new FluidStack(LiquidManager.REFINED_FUEL, 100), true);
				}
			}
			if (drain != null){
				fill(EnumFacing.UP, drain, true);
			}
		}

		if (fuelTrain >1 && this.isLocoTurnedOn()) {
			fuelTrain -= amount;
			if (fuelTrain < 0) {
				fuelTrain = 0;
				drain(EnumFacing.UP, amount, true);
				setLocoTurnedOnFromPacket(false);
			} else {
				drain(EnumFacing.UP, amount, true);
			}
		}
	}


	public void setCapacity(int capacity) {
		this.maxTank = capacity;
	}

	public int getCapacity() {
		return this.maxTank;
	}

	public int fill(EnumFacing from, FluidStack resource, boolean doFill) {
		return theTank.fill(resource, doFill);
	}

	public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain) {
		if (resource == null || !resource.isFluidEqual(theTank.getFluid())) {
			return null;
		}
		return theTank.drain(resource.amount, doDrain);
	}

	public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain) {
		return theTank ==null? null:theTank.drain(maxDrain, doDrain);
	}

	public boolean canFill(EnumFacing from, Fluid fluid) {
		return true;
	}

	public boolean canDrain(EnumFacing from, Fluid fluid) {
		return true;
	}

	public FluidTankInfo[] getTankInfo(EnumFacing from) {
		return new FluidTankInfo[] { theTank.getInfo() };
	}

	public FluidStack getFluid() {
		return theTank.getFluid();
	}

	public int getFluidAmount() {
		return theTank.getFluidAmount();
	}
}