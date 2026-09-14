package train.common.tile;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

import java.util.Random;

public class TileLantern extends TileEntity implements ITickable {

	protected static final Random rand = new Random();

	private int randomColor = (rand.nextInt() * 0xFFFFFF);
	private int oldColor = 0;

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		randomColor = nbt.getInteger("randomColor");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("randomColor", randomColor);
		return nbt;
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return new SPacketUpdateTileEntity(this.pos, 1, nbt);
	}

	public String getColor() {
		return String.format("#%06X", (0xFFFFFF & this.randomColor));
	}

	public int getRandomColor() {
		return this.oldColor;
	}

	public void setColor(int col) {
		randomColor = col;
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public void update() {
		if (oldColor != randomColor) {
			oldColor = randomColor;
			this.markDirty();
			this.syncTileEntity();
		}
	}

	public void syncTileEntity() {
		for (Object o : this.world.playerEntities) {
			if (o instanceof EntityPlayerMP) {
				EntityPlayerMP player = (EntityPlayerMP) o;
				if (player.getDistance(pos.getX(), pos.getY(), pos.getZ()) <= 64) {
					SPacketUpdateTileEntity pkt = this.getUpdatePacket();
					if (pkt != null) player.connection.sendPacket(pkt);
				}
			}
		}
	}
}
