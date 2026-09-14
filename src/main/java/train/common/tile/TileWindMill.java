package train.common.tile;

import cofh.api.energy.IEnergyProvider;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import train.common.core.handlers.ConfigHandler;
import train.common.core.handlers.WorldEvents;
import train.common.core.util.Energy;
import train.common.library.BlockIDs;

import java.util.Random;

public class TileWindMill extends Energy implements IEnergyProvider, ITickable {
	private int facingMeta;
	private int updateTicks = 0;
	private static Random rand = new Random();
	public int windClient = 0;
    public int standsOpen = 0;


	public TileWindMill() {
		super(0,"Wind Mill", 240, 80);
		super.setSides(new EnumFacing[]{EnumFacing.EAST, EnumFacing.WEST, EnumFacing.SOUTH, EnumFacing.NORTH, EnumFacing.DOWN});
		facingMeta = 0;
	}

	public int getFacing() {
		return facingMeta;
	}

	public void setFacing(int facing) {
		this.facingMeta = facing;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, boolean synced) {
		super.readFromNBT(nbt, synced);
		facingMeta = nbt.getByte("Orientation");
		this.windClient = nbt.getInteger("Wind");
        this.standsOpen = nbt.getInteger("standsOpen");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt, boolean synced) {
		super.writeToNBT(nbt, synced);
		nbt.setByte("Orientation", (byte) facingMeta);
		nbt.setInteger("Wind", this.windClient);
        nbt.setInteger("standsOpen", this.standsOpen);
		return nbt;
	}

	@Override
	public void update() {
		updateTicks++;
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();

		if (!world.isRemote) {
			if (updateTicks % 20 == 0) {
				BlockPos above = new BlockPos(x, y + 1, z);
				if (!this.world.isAirBlock(above)) {
					Block block = this.world.getBlockState(above).getBlock();
					if (block != null) {
						EntityItem entityitem = new EntityItem(world, x, y + 1, z, new ItemStack(Item.getItemFromBlock(BlockIDs.windMill.block), 1));
						float f3 = 0.05F;
						entityitem.motionX = (float) rand.nextGaussian() * f3;
						entityitem.motionY = (float) rand.nextGaussian() * f3 + 0.2F;
						entityitem.motionZ = (float) rand.nextGaussian() * f3;
						this.world.spawnEntity(entityitem);
					}
					this.world.setBlockToAir(this.pos);
				}
			}

			if (this.updateTicks % 120 == 0) {
				this.standsOpen = 0;
				int st = ConfigHandler.WINDMILL_CHECK_RADIUS;
				if (st >= 0) {
					int en = st + 1;
					louter:
					for (int dx = -st; dx < en; dx++)
						for (int dz = -st; dz < en; dz++)
							if (!this.world.canBlockSeeSky(new BlockPos(x + dx, y + 1, z + dz))) {
								this.standsOpen++;
								break louter;
							}
				}
			}

			if (this.standsOpen == 0 && updateTicks % 4 == 0) {
				this.energy.receiveEnergy((WorldEvents.windStrength + (Math.round(y * 0.25f)) * 10), false);
				if (this.world.isThundering()) {
					this.energy.receiveEnergy(Math.round(this.energy.getEnergyStored() * 3.5f), false);
				} else if (this.world.isRaining()) {
					this.energy.receiveEnergy(Math.round(this.energy.getEnergyStored() * 2.2f), false);
				}
			}
			if (this.energy.getEnergyStored() > 0) {
				pushEnergy(world, x, y, z, this.energy);
			}

			this.markDirty();
			this.syncTileEntity();
		}
	}
}
