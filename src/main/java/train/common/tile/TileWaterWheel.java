package train.common.tile;

import cofh.api.energy.IEnergyProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import train.common.core.util.Energy;

public class TileWaterWheel extends Energy implements IEnergyProvider, ITickable {

	public int facingMeta;

	public TileWaterWheel() {
		super(0, "WaterWheel", 80, 80);
		super.setSides(new EnumFacing[]{EnumFacing.EAST, EnumFacing.WEST, EnumFacing.NORTH, EnumFacing.SOUTH});
		facingMeta = 0;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTag, boolean forSyncing){
		super.readFromNBT(nbtTag, forSyncing);
		facingMeta = nbtTag.getByte("Orientation");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbtTag, boolean forSyncing){
		super.writeToNBT(nbtTag, forSyncing);
		nbtTag.setByte("Orientation", (byte) facingMeta);
		return nbtTag;
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.getNbtCompound(), false);
	}

	@Override
	public void update() {
		if (!world.isRemote) {
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();

			Block blockXP = world.getBlockState(new BlockPos(x+1, y, z)).getBlock();
			Block blockXN = world.getBlockState(new BlockPos(x-1, y, z)).getBlock();
			Block blockZP = world.getBlockState(new BlockPos(x, y, z+1)).getBlock();
			Block blockZN = world.getBlockState(new BlockPos(x, y, z-1)).getBlock();
			Block blockTop = world.getBlockState(new BlockPos(x, y+1, z)).getBlock();
			Block blockBottom = world.getBlockState(new BlockPos(x, y-1, z)).getBlock();

			if (blockXP instanceof BlockLiquid && blockXP.getMaterial(world.getBlockState(new BlockPos(x+1,y,z))).isLiquid()
					&& blockXP.getMaterial(world.getBlockState(new BlockPos(x+1,y,z))) != Material.LAVA) {
				this.energy.receiveEnergy(5, false);
				facingMeta = 2; markDirty();
			} else if (blockXN instanceof BlockLiquid && blockXN.getMaterial(world.getBlockState(new BlockPos(x-1,y,z))).isLiquid()
					&& blockXN.getMaterial(world.getBlockState(new BlockPos(x-1,y,z))) != Material.LAVA) {
				this.energy.receiveEnergy(5, false);
				facingMeta = 0; markDirty();
			} else if (blockZN instanceof BlockLiquid && blockZN.getMaterial(world.getBlockState(new BlockPos(x,y,z-1))).isLiquid()
					&& blockZN.getMaterial(world.getBlockState(new BlockPos(x,y,z-1))) != Material.LAVA) {
				this.energy.receiveEnergy(5, false);
				facingMeta = 1; markDirty();
			} else if (blockZP instanceof BlockLiquid && blockZP.getMaterial(world.getBlockState(new BlockPos(x,y,z+1))).isLiquid()
					&& blockZP.getMaterial(world.getBlockState(new BlockPos(x,y,z+1))) != Material.LAVA) {
				this.energy.receiveEnergy(5, false);
				facingMeta = 3; markDirty();
			} else if (blockTop instanceof BlockLiquid && blockTop.getMaterial(world.getBlockState(new BlockPos(x,y+1,z))).isLiquid()
					&& blockTop.getMaterial(world.getBlockState(new BlockPos(x,y+1,z))) != Material.LAVA) {
				this.energy.receiveEnergy(5, false);
			} else if (blockBottom instanceof BlockLiquid && blockBottom.getMaterial(world.getBlockState(new BlockPos(x,y-1,z))).isLiquid()
					&& blockBottom.getMaterial(world.getBlockState(new BlockPos(x,y-1,z))) != Material.LAVA) {
				this.energy.receiveEnergy(5, false);
			} else {
				setWaterDir(-1);
			}

			if (this.energy.getEnergyStored() > 0) {
				pushEnergy(world, x, y, z, this.energy);
			}

			this.markDirty();
			this.syncTileEntity();
		}
	}

	private void setWaterDir(int i) {
		facingMeta = i;
	}

	public int getWaterDir() {
		return facingMeta;
	}

	@Override
	public boolean canConnectEnergy(EnumFacing direction){
		if ((facingMeta==1||facingMeta==3) && (direction == EnumFacing.WEST||direction == EnumFacing.EAST)) {
			return true;
		} else if ((facingMeta==0||facingMeta==2) && (direction == EnumFacing.NORTH||direction == EnumFacing.SOUTH)){
			return true;
		} else {return false;}
	}
}
