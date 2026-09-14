package train.common.tile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

//client
public class TileSignal extends TileEntity implements ITickable {
	public int state;// 0=red 1=green
	public int rot;

	public double tempSpeedX;// signal
	public double tempSpeedZ;// signal
	public double fu;// signal
	private int facingMeta;

	public TileSignal() {
		// signal
		tempSpeedX = 0;
		tempSpeedZ = 0;
		fu = 0;
		facingMeta = 0;
	}

	public int getFacing() {
		return facingMeta;
	}

	public void setFacing(int facing) {
		this.facingMeta = facing;
		this.rot = facing;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		state = nbttagcompound.getInteger("state");
		rot = nbttagcompound.getInteger("rot");
		facingMeta = nbttagcompound.getByte("Orientation");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setByte("Orientation", (byte) facingMeta);
		nbttagcompound.setInteger("state", this.state);
		nbttagcompound.setInteger("rot", this.rot);
		return nbttagcompound;
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return new SPacketUpdateTileEntity(this.pos, 1, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public void update() {
		int x1 = 1;// x2
		int x2 = 1;// y2
		int x3 = 1;// z2
		int x4 = 1;// x1
		int x5 = 1;// z1

		switch (this.rot) {

		case 2:
			x4 = -1;
			x5 = -9;
			x1 = 1;
			x3 = 1;
			break;

		case 3:
			x3 = 9;
			x1 = 1;
			x4 = -1;
			x5 = 1;
			break;

		case 4:
			x4 = -9;
			x5 = -1;
			x1 = 1;
			x3 = 1;
			break;

		case 5:
			x3 = 1;
			x1 = 9;
			x4 = 1;
			x5 = -1;
			break;
		}

		int bx = this.pos.getX();
		int by = this.pos.getY();
		int bz = this.pos.getZ();
		List list = this.world.getEntitiesWithinAABB(EntityMinecart.class,
				new AxisAlignedBB(bx + x4, by, bz + x5, bx + x1, by + 1, bz + x3).expand(1.0D, 1.0D, 1.0D));
		Entity entity;

		if (list != null && list.size() > 0) {

			for (int j1 = 0; j1 < list.size(); j1++) {

				entity = (Entity) list.get(j1);

				if (entity instanceof EntityMinecart) {

					if (state == 0) {

						if (fu == 0) {

							tempSpeedX = entity.motionX;
							tempSpeedZ = entity.motionZ;
						}

						entity.motionX *= 0.85D;
						entity.motionZ *= 0.85D;
						fu++;
					}
					else if (fu > 0 && state == 1) {

						entity.motionX = tempSpeedX;
						entity.motionZ = tempSpeedZ;
						fu = 0;
					}
				}
			}
		}
	}
}
