package train.common.core.util;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.util.EnumFacing;
import train.common.tile.TileTraincraft;

import java.util.Arrays;

public class Energy extends TileTraincraft implements IEnergyProvider {
	public EnergyStorage energy = new EnergyStorage(3000,80); //core energy value the first value is max storage and the second is transfer max.
	private EnumFacing[] sides = new EnumFacing[]{}; //defines supported sides

	public Energy(int inventorySlots, String name, int maxEnergy, int maxTransfer){
		super(inventorySlots, name);
		this.energy.setCapacity(maxEnergy);
		this.energy.setMaxTransfer(maxTransfer);
	}
	public Energy(){}

	public void pushEnergy(World world, int x, int y, int z, EnergyStorage storage){
		for (EnumFacing side : getSides()) {
			TileEntity tile = world.getTileEntity(new net.minecraft.util.math.BlockPos(x + side.getXOffset(), y + side.getYOffset(), z + side.getZOffset()));
			if (tile != null && tile instanceof IEnergyReceiver && storage.getEnergyStored() > 0) {
				if (((IEnergyReceiver) tile).canConnectEnergy(side.getOpposite())) {
					int receive = ((IEnergyReceiver) tile).receiveEnergy(side.getOpposite(), Math.min(storage.getMaxExtract(), storage.getEnergyStored()), false);
					storage.extractEnergy(receive, false);
				}
			}
		}
	}



	//Implemented parts from the diesel generator
	@Override
	public void readFromNBT(NBTTagCompound nbtTag, boolean forSyncing) {
		super.readFromNBT(nbtTag, forSyncing);
		this.energy.readFromNBT(nbtTag);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbtTag, boolean forSyncing) {
		super.writeToNBT(nbtTag, forSyncing);
		this.energy.writeToNBT(nbtTag);
		return nbtTag;
	}

	public void setSides(EnumFacing[] listOfSides){
		this.sides = listOfSides;
	}
	public EnumFacing[] getSides(){
		return this.sides;
	}

	//RF Overrides
	@Override
	public boolean canConnectEnergy(EnumFacing dir) {
		if(Arrays.asList(sides).contains(dir)) {
			return true;
		} else {
			return false;
		}
	}
	@Override
	public int extractEnergy(EnumFacing dir, int amount, boolean simulate) {
		return energy.extractEnergy(amount, simulate);
	}
	@Override
	public int getEnergyStored(EnumFacing dir) {
		return energy.getEnergyStored();
	}
	@Override
	public int getMaxEnergyStored(EnumFacing dir) {
		return this.energy.getMaxEnergyStored();
	}




	// 1.12 sends a block's data to the client through these (chunk load / block update); without them the client
	// forgets facings, colours, settings after a reload (GitHub issue #5)
	@Override
	public net.minecraft.nbt.NBTTagCompound getUpdateTag() { return writeToNBT(new net.minecraft.nbt.NBTTagCompound()); }
	@Override
	public net.minecraft.network.play.server.SPacketUpdateTileEntity getUpdatePacket() { return new net.minecraft.network.play.server.SPacketUpdateTileEntity(pos, 0, getUpdateTag()); }
	@Override
	public void onDataPacket(net.minecraft.network.NetworkManager net, net.minecraft.network.play.server.SPacketUpdateTileEntity pkt) { readFromNBT(pkt.getNbtCompound()); }
}
