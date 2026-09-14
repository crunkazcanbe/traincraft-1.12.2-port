package train.common.api;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import mods.railcraft.api.fuel.FluidFuelManager;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import train.common.blocks.BlockTraincraftFluid;
import train.common.items.ItemBlockFluid;
import train.common.library.BlockIDs;
import train.common.library.ItemIDs;

public class LiquidManager {

	public static final int BUCKET_VOLUME = 1000;
	private static LiquidManager instance;
	public static FluidStack WATER_FILTER = new FluidStack(FluidRegistry.WATER, 1);
	public static FluidStack LAVA_FILTER = new FluidStack(FluidRegistry.LAVA, 1);
	public static Fluid oil;
	public static Fluid steam;
	public static Fluid fuel;
	public static Fluid biomass;
	public static Fluid biofuel;
	public static Fluid bioDiesel;
	public static Fluid bioethanol;

	public static final Fluid DIESEL = new Fluid("Diesel", new ResourceLocation("traincraft", "blocks/diesel_still"), new ResourceLocation("traincraft", "blocks/diesel_flow")).setUnlocalizedName("diesel.name").setDensity(860);
	public static final Fluid REFINED_FUEL = new Fluid("RefinedFuel", new ResourceLocation("traincraft", "blocks/refinedfuel_still"), new ResourceLocation("traincraft", "blocks/refinedfuel_flow")).setDensity(820).setUnlocalizedName("refinedfuel.name");

	public static LiquidManager getInstance() {
		if (instance == null) {
			instance = new LiquidManager();
		}
		return instance;
	}

	public static void registerFluidBlock(BlockTraincraftFluid block){
		// Block registration handled via registry events in 1.12.2
	}

	public void registerLiquids() {
		FluidRegistry.registerFluid(DIESEL);
		FluidRegistry.registerFluid(REFINED_FUEL);
		BlockIDs.diesel.block = new BlockTraincraftFluid(DIESEL, Material.WATER).setFlammable(true).setFlammability(5);
		DIESEL.setBlock(BlockIDs.diesel.block);
		BlockIDs.refinedFuel.block = new BlockTraincraftFluid(REFINED_FUEL, Material.WATER).setFlammable(true).setFlammability(4);
		REFINED_FUEL.setBlock(BlockIDs.refinedFuel.block);
		// Fluid container registration handled via IFluidHandlerItem capabilities in 1.12.2
		dieselFilter();
		if (Loader.isModLoaded("railcraft")) {
			addRCFluids();
		}
		MinecraftForge.EVENT_BUS.register(this);

		registerFluidBlock((BlockTraincraftFluid) BlockIDs.diesel.block);
		registerFluidBlock((BlockTraincraftFluid) BlockIDs.refinedFuel.block);
	}

	@Optional.Method(modid = "railcraft")
	private void addRCFluids() {
		FluidFuelManager.addFuel(DIESEL, 60000);
		FluidFuelManager.addFuel(REFINED_FUEL, 96000);
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void textureHook(TextureStitchEvent.Post event) {
		// Fluid textures are set via ResourceLocations in the Fluid constructor in 1.12.2
	}

	public static void getLiquidsFromDictionnary() {
		oil = FluidRegistry.getFluid("oil");
		steam = FluidRegistry.getFluid("steam");
		fuel = FluidRegistry.getFluid("fuel");
		biomass = FluidRegistry.getFluid("biomass");
		bioethanol = FluidRegistry.getFluid("bioethanol");
		biofuel = FluidRegistry.getFluid("biofuel");
		bioDiesel = FluidRegistry.getFluid("biodiesel");
	}

	public boolean isDieselLocoFuel(ItemStack stack) {
		FluidStack[] multiFilter;
		FluidStack bucketLiquid = getFluidInContainer(stack);
		LiquidManager.getInstance();
		multiFilter = LiquidManager.dieselFilter();
		if (multiFilter != null) {
			for(FluidStack aMultiFilter : multiFilter){
				if(aMultiFilter != null && bucketLiquid != null && aMultiFilter.isFluidEqual(bucketLiquid))
					return true;
				if(isEmptyContainer(stack))
					return true;
			}
		}
		return false;
	}

	public static FluidStack[] dieselFilter() {
		FluidStack[] fuels = new FluidStack[4];
		if (DIESEL != null)
			fuels[0] = new FluidStack(DIESEL, 1);
		if (REFINED_FUEL != null)
			fuels[1] = new FluidStack(REFINED_FUEL, 1);
		if (biofuel != null)
			fuels[2] = new FluidStack(biofuel, 1);
		if (bioDiesel != null)
			fuels[2] = new FluidStack(bioDiesel, 1);
		if (fuel != null)
			fuels[3] = new FluidStack(fuel, 1);
		if (bioethanol != null)
			fuels[2] = new FluidStack(bioethanol, 1);
		return fuels;
	}

	public boolean isContainer(ItemStack stack) {
		return FluidUtil.getFluidHandler(stack) != null;
	}

	public boolean isFilledContainer(ItemStack stack) {
		return FluidUtil.getFluidContained(stack) != null;
	}

	public boolean isEmptyContainer(ItemStack stack) {
		IFluidHandlerItem h = FluidUtil.getFluidHandler(stack);
		return h != null && FluidUtil.getFluidContained(stack) == null;
	}

	public ItemStack fillFluidContainer(FluidStack liquid, ItemStack empty) {
		if (liquid == null || empty == null) return null;
		IFluidHandlerItem handler = FluidUtil.getFluidHandler(empty.copy());
		if (handler != null) {
			int filled = handler.fill(liquid, true);
			if (filled > 0) return handler.getContainer();
		}
		return null;
	}

	public FluidStack getFluidInContainer(ItemStack stack) {
		return FluidUtil.getFluidContained(stack);
	}

	public boolean containsFluid(ItemStack stack, FluidStack liquid) {
		FluidStack found = FluidUtil.getFluidContained(stack);
		return found != null && liquid != null && liquid.isFluidEqual(found);
	}

	public boolean isFluidEqual(FluidStack L1, FluidStack L2) {
		return !((L1 == null) || (L2 == null)) && L1.isFluidEqual(L2);
	}

	public ItemStack processContainer(IInventory inventory, IFluidTank tank, ItemStack itemstack, int tankIndex) {
		FluidStack bucketLiquid = getFluidInContainer(itemstack);
		ItemStack emptyItem = itemstack.getItem().getContainerItem(itemstack);

		if ((bucketLiquid != null) && (emptyItem == null)) {
			int used = tank.fill(bucketLiquid, false);
			if (used >= bucketLiquid.amount) {
				tank.fill(bucketLiquid, true);
				inventory.decrStackSize(0, 1);
				return null;
			}
		}
		else if ((getInstance().isEmptyContainer(itemstack))) {
			ItemStack filled = getInstance().fillFluidContainer(tank.getFluid(), itemstack);
			if ((filled != null)) {
				FluidStack liquid = getFluidInContainer(filled);
				FluidStack drain = tank.drain(liquid.amount, false);
				if ((drain != null) && (drain.amount > 0)) {
					tank.drain(liquid.amount, true);
					inventory.decrStackSize(0, 1);
					return filled;
				}
			}
		}
		return null;
	}

	public ItemStack processContainer(IInventory inventory, int inventoryIndex, IFluidTank tank, ItemStack itemstack) {
		FluidStack bucketLiquid = getFluidInContainer(itemstack);
		ItemStack emptyItem = itemstack.getItem().getContainerItem(itemstack);
		if (bucketLiquid != null) {
			int used = tank.fill(bucketLiquid, false);
			if (used >= bucketLiquid.amount) {
				tank.fill(bucketLiquid, true);
				if (itemstack.getItem() == Items.POTIONITEM) {
					return new ItemStack(Items.GLASS_BOTTLE, 1);
				}
				inventory.decrStackSize(inventoryIndex, 1);
				return emptyItem;
			}
		} else if (getInstance().isEmptyContainer(itemstack)) {
			ItemStack filled = getInstance().fillFluidContainer(tank.drain(1000, false), itemstack);
			if (filled != null) {
				FluidStack liquid = getFluidInContainer(filled);
				FluidStack drain = tank.drain(liquid.amount, false);
				if (drain != null && drain.amount > 0) {
					tank.drain(liquid.amount, true);
					inventory.decrStackSize(inventoryIndex, 1);
					return filled;
				}
			}
		}
		return null;
	}

	public class StandardTank extends FluidTank {
		private int tankIndex;

		public StandardTank(int capacity) {
			super(capacity);
		}

		public void setTankIndex(int index) {
			this.tankIndex = index;
		}

		public int getTankIndex() {
			return this.tankIndex;
		}

		public boolean isEmpty() {
			return (getFluid() == null) || (getFluid().amount <= 0);
		}
	}

	public class FilteredTank extends StandardTank {
		private final FluidStack filter;
		private final FluidStack[] multiFilter;

		public FilteredTank(int capacity, FluidStack filter) {
			super(capacity);
			this.filter = filter;
			this.multiFilter = null;
		}

		public FilteredTank(int capacity, FluidStack filter, int pressure) {
			this(capacity, filter);
		}

		public FilteredTank(int capacity, FluidStack[] filter) {
			super(capacity);
			this.multiFilter = filter;
			this.filter = null;
		}

		public FilteredTank(int capacity, FluidStack[] filter, int pressure) {
			this(capacity, filter);
		}

		@Override
		public int fill(FluidStack resource, boolean doFill) {
			if (multiFilter != null) {
				for (int i = 0; i < multiFilter.length; i++) {
					if (multiFilter[i] != null && isFluidEqual(this.multiFilter[i], resource)) {
						return super.fill(resource, doFill);
					}
				}
			}
			else
			if (this.filter.isFluidEqual(resource)) {
				return super.fill(resource, doFill);
			}
			return 0;
		}

		public FluidStack getFilter() {
			return this.filter.copy();
		}

		public boolean liquidMatchesFilter(FluidStack resource) {
			return !((resource == null) || (this.filter == null)) && this.filter.isFluidEqual(resource);
		}
	}

	public class ReverseFilteredTank extends StandardTank {
		private final FluidStack filter;
		private final FluidStack[] multiFilter;

		public ReverseFilteredTank(int capacity, FluidStack filter) {
			super(capacity);
			this.filter = filter;
			this.multiFilter = null;
		}

		public ReverseFilteredTank(int capacity, FluidStack filter, int pressure) {
			this(capacity, filter);
		}

		public ReverseFilteredTank(int capacity, FluidStack[] filter) {
			super(capacity);
			this.multiFilter = filter;
			this.filter = null;
		}

		public ReverseFilteredTank(int capacity, FluidStack[] filter, int pressure) {
			this(capacity, filter);
		}

		@Override
		public int fill(FluidStack resource, boolean doFill) {
			if (multiFilter != null) {
				for (int i = 0; i < multiFilter.length; i++) {
					if (multiFilter[i] != null && (resource.getFluid() != multiFilter[i].getFluid())) {
						return super.fill(resource, doFill);
					}
				}
			}
			else if (filter.getFluid() != resource.getFluid()) {
				return super.fill(resource, doFill);
			}
			return 0;
		}

		public FluidStack getFilter() {
			return filter.copy();
		}

		public boolean liquidMatchesFilter(FluidStack resource) {
			return !((resource == null) || (filter == null)) && filter.isFluidEqual(resource);
		}
	}
}
