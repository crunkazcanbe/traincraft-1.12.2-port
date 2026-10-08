package train.common.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityDetector extends TileEntity {
	public int meta = 6;

	public TileEntityDetector() {}
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		// TODO This might raise an exception upon loading; the server might try to assign an instance to some coords only to realise the TileEntity does not exist there. Please verify this does not raise any issues.
	}
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound) {
		return super.writeToNBT(nbttagcompound);
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