/**
 * A track that provides energy to ElectricTrains
 *
 * @author Spitfire4466
 */
package train.common.blocks.tracks;

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import net.minecraftforge.fml.common.FMLCommonHandler;
import mods.railcraft.api.items.IToolCrowbar;
import train.common.tracks.ChargeHandler;
import train.common.tracks.ITrackPowered;
import train.common.tracks.ITrackTile;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import train.common.api.ElectricTrain;
import train.common.api.EntityRollingStock;
import train.common.core.handlers.ConfigHandler;
import train.common.library.Tracks;

public class BlockEnergyTrack extends TrackBaseTraincraft implements ITrackPowered, IEnergyHandler {
	public int maxEnergy = 2000;
	public int output = 500;
	public boolean isProvider = false;
	private Block thisBlock;
	private int updateTicks = 0;
	protected boolean powered = false;
	private static EnumFacing[] dirMap = new EnumFacing[] {EnumFacing.WEST, EnumFacing.EAST, EnumFacing.NORTH, EnumFacing.SOUTH};
	private ChargeHandler RFChandler;

	public BlockEnergyTrack() {
		this.speedController = SpeedControllerSteel.getInstance();
		RFChandler = new ChargeHandler(this, ChargeHandler.ConnectType.TRACK);
	}

	@Override
	public Tracks getTrackType() {
		return Tracks.ENERGY_TRACK;
	}

	private BlockPos pos() {
		return new BlockPos(getX(), getY(), getZ());
	}

	private Block getThisBlock() {
		if (thisBlock == null) {
			thisBlock = getWorld().getBlockState(pos()).getBlock();
		}
		return thisBlock;
	}

	public boolean isSimulating() {
		return !FMLCommonHandler.instance().getEffectiveSide().isClient();
	}

	@Override
	public void updateEntity() {
		if (getWorld().isRemote) {
			return;
		}
		updateTicks++;
		if (!ConfigHandler.ENERGYTRACK_USES_RF) {
			if (isPowered() && updateTicks % 2 == 0) {
				if (this.RFChandler.getCharge() < this.getMaxEnergy())
					this.RFChandler.addCharge(1);
			}

			if (updateTicks % 50 == 0)
				markBlockNeedsUpdate();

			return;
		}

		if (this.updateTicks % 10 == 0) {
			if (this.maxEnergy > this.RFChandler.getCharge()) {
				BlockPos below = pos().down();
				TileEntity belowTile = this.getWorld().getTileEntity(below);
				if (belowTile instanceof IEnergyProvider) {
					this.receiveEnergy(EnumFacing.DOWN, ((IEnergyProvider) belowTile).extractEnergy(EnumFacing.UP, 100, false), false);
				}
				int x = this.getX();
				int y = this.getY();
				int z = this.getZ();
				int ener1 = 0;
				int ener2 = 0;
				for (int[] p : new int[][]{{x - 1, z, 1}, {x + 1, z, 0}, {x, z - 1, 3}, {x, z + 1, 2}})
					if (this.maxEnergy > this.RFChandler.getCharge()) {
						TileEntity side = this.getWorld().getTileEntity(new BlockPos(p[0], y, p[1]));
						TileEntity sideBelow = this.getWorld().getTileEntity(new BlockPos(p[0], y - 1, p[1]));
						if (side instanceof IEnergyProvider)
							ener1 = ((IEnergyProvider) side).extractEnergy(dirMap[p[2]], 100, false);
						if (sideBelow instanceof IEnergyProvider)
							ener2 = ((IEnergyProvider) sideBelow).extractEnergy(dirMap[p[2]], 100, false);
						this.receiveEnergy(EnumFacing.UP, ener1 + ener2, false);
					} else break;

				for (int[] p : new int[][]{{x - 1, z}, {x + 1, z}, {x, z - 1}, {x, z + 1}}) {
					for (int dy : new int[]{0, -1, 1}) {
						TileEntity te = this.getWorld().getTileEntity(new BlockPos(p[0], y + dy, p[1]));
						if (te instanceof ITrackTile && ((ITrackTile) te).getTrackInstance() instanceof BlockEnergyTrack) {
							BlockEnergyTrack neighbor = (BlockEnergyTrack) ((ITrackTile) te).getTrackInstance();
							if ((int) neighbor.getChargeHandler().getCharge() - (int) this.RFChandler.getCharge() > 1) {
								double diff = (neighbor.getChargeHandler().getCharge() - this.RFChandler.getCharge()) / 2.0;
								this.RFChandler.addCharge(diff);
								neighbor.getChargeHandler().removeCharge(diff);
							}
						}
					}
				}
			}
		}

		if (updateTicks % 50 == 0)
			markBlockNeedsUpdate();
	}

	@Override
	public TextureAtlasSprite getIcon() {
		if (RFChandler.getCharge() > 0)
			return getIcon(1);
		return getIcon(0);
	}

	@Override
	public boolean isFlexibleRail() {
		return true;
	}

	private void notifyNeighbors() {
		Block block = getThisBlock();
		getWorld().notifyNeighborsOfStateChange(pos(), block, true);
		getWorld().notifyNeighborsOfStateChange(pos().down(), block, true);

		markBlockNeedsUpdate();
	}

	@Override
	public boolean blockActivated(EntityPlayer player) {
		if (getWorld().isRemote) {
			return false;
		}
		ItemStack current = player.getHeldItemMainhand();

		if (!current.isEmpty() && (current.getItem() instanceof IToolCrowbar)) {
			IToolCrowbar crowbar = (IToolCrowbar) current.getItem();
			player.sendMessage(new TextComponentString("stored: " + (this.RFChandler.getCharge()) + "/" + (int) this.getMaxEnergy() + " RF"));
			markBlockNeedsUpdate();
			crowbar.onWhack(player, EnumHand.MAIN_HAND, current, pos());
			sendUpdateToClient();
			return true;
		}

		return false;
	}

	@Override
	public void onMinecartPass(EntityMinecart cart) {
		if (!(cart instanceof ElectricTrain)) {
			return;
		}
		if ((this.RFChandler.getCharge() > 20) && (((ElectricTrain) cart).fuelTrain) < (((ElectricTrain) cart).maxEnergy)) {
			double transfered = this.RFChandler.getCharge() * 0.05;
			(((EntityRollingStock) cart).fuelTrain) += transfered;
			this.RFChandler.removeCharge(transfered);
		}
	}

	@Override
	public boolean canUpdate() {
		return true;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setDouble("energy", RFChandler.getCharge());
		nbttagcompound.setBoolean("powered", this.powered);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		RFChandler.setCharge(nbttagcompound.getDouble("energy"));
		this.powered = nbttagcompound.getBoolean("powered");
	}

	@Override
	public boolean isPowered() {
		return this.powered;
	}

	@Override
	public void setPowered(boolean powered) {
		this.powered = powered;
	}

	@Override
	public int getPowerPropagation() {
		return 5;
	}

	public double getMaxEnergy() {
		return this.maxEnergy;
	}

	/* ------------------------------------------------------------------------------------
	 * -                                IEnergyHandler                                    -
	 * ------------------------------------------------------------------------------------
	 */

	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}

	@Override
	public int receiveEnergy(EnumFacing dir, int ammount, boolean simulate) {
		if (this.maxEnergy > this.RFChandler.getCharge()) {
			if (this.maxEnergy - this.RFChandler.getCharge() >= ammount) {
				if (!simulate) this.RFChandler.addCharge(ammount);
				return ammount;
			} else {
				int div = (int) (this.maxEnergy - this.RFChandler.getCharge());
				if (!simulate) this.RFChandler.setCharge(this.maxEnergy);
				return div;
			}
		} else
			return 0;
	}

	@Override
	public int extractEnergy(EnumFacing dir, int ammount, boolean simulate) {
		if (this.RFChandler.getCharge() >= ammount) {
			if (!simulate) this.RFChandler.removeCharge(ammount);
			return ammount;
		} else {
			int div = (int) this.RFChandler.getCharge();
			if (!simulate) this.RFChandler.setCharge(0);
			return div;
		}
	}

	@Override
	public int getEnergyStored(EnumFacing dir) {
		return (int) this.RFChandler.getCharge();
	}

	@Override
	public int getMaxEnergyStored(EnumFacing dir) {
		return this.maxEnergy;
	}

	public ChargeHandler getChargeHandler() {
		return RFChandler;
	}
}
