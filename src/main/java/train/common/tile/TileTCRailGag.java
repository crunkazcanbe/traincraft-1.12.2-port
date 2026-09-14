package train.common.tile;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import java.util.Random;

public class TileTCRailGag extends TileEntity {

	protected Random rand = new Random();
	protected Side side;
	public int originX;
	public int originY;
	public int originZ;
	public String type = "";
	public float bbHeight = 0.125f;
	public boolean canPlaceRollingstock = true;

	@Override
	public void readFromNBT(NBTTagCompound nbt) {

		originX = nbt.getInteger("originX");
		originY = nbt.getInteger("originY");
		originZ = nbt.getInteger("originZ");
		bbHeight = nbt.getFloat("bbHeight");
		type = nbt.getString("type");
		canPlaceRollingstock = nbt.getBoolean("canPlaceRollingstock");

		super.readFromNBT(nbt);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {

		nbt.setInteger("originX", originX);
		nbt.setInteger("originY", originY);
		nbt.setInteger("originZ", originZ);
		nbt.setFloat("bbHeight", bbHeight);
		nbt.setBoolean("canPlaceRollingstock", canPlaceRollingstock);
		if (type.equals("")){
			type = "null";
		}
		nbt.setString("type", type);

		super.writeToNBT(nbt);
		return nbt;
	}

	@Override
	public net.minecraft.network.play.server.SPacketUpdateTileEntity getUpdatePacket() {

		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);

		return new SPacketUpdateTileEntity(this.pos, 1, nbt);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt){
		this.readFromNBT(pkt.getNbtCompound());
		super.onDataPacket(net, pkt);
	}
}