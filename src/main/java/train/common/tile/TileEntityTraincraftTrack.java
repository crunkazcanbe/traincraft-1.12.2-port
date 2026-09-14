package train.common.tile;

import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import train.common.tracks.ITrackInstance;
import train.common.tracks.ITrackTile;
import train.common.tracks.TrackRegistry;
import train.common.tracks.TrackSpec;

/**
 * Generic host TileEntity for all of Traincraft's "outfitted track" types
 * (detector/coupler/energy/holding/speed-controller/station/disembark/boarding/
 * snowy variants/copper/steel - see train.common.library.Tracks).
 *
 * This plays the role that Railcraft's own internal generic track block used to play
 * for these track types back when Traincraft vendored Railcraft's old (pre-TrackKit)
 * tracks API. See train.common.tracks package-info for the full rationale.
 */
public class TileEntityTraincraftTrack extends TileEntity implements ITrackTile, ITickable {

	private ITrackInstance trackInstance;
	private String trackTag;

	public TileEntityTraincraftTrack() {
	}

	/** Called by BlockTraincraftTrack right after this tile entity is created and its position is known. */
	public void initFromTag(String tag) {
		this.trackTag = tag;
		TrackSpec spec = TrackRegistry.getTrackSpec(tag);
		if (spec != null) {
			this.trackInstance = spec.createInstanceFromSpec();
			this.trackInstance.setTile(this);
			this.trackInstance.onBlockPlaced();
		}
	}

	@Override
	public ITrackInstance getTrackInstance() {
		if (trackInstance == null && trackTag != null) {
			// world load path: tile deserialized from NBT before we had a chance to construct the instance
			initFromTag(trackTag);
		}
		return trackInstance;
	}

	public String getTrackTag() {
		return trackTag;
	}

	@Override
	public void sendUpdateToClient() {
		if (world != null && !world.isRemote) {
			world.markBlockRangeForRenderUpdate(pos, pos);
			SPacketUpdateTileEntity packet = getUpdatePacket();
			if (packet != null) {
				for (net.minecraft.entity.player.EntityPlayerMP player : world.getMinecraftServer().getPlayerList().getPlayers()) {
					if (player.getServerWorld() == world && player.getDistanceSq(pos) < 64 * 64) {
						player.connection.sendPacket(packet);
					}
				}
			}
		}
	}

	@Override
	public void update() {
		if (trackInstance != null && trackInstance.canUpdate()) {
			trackInstance.updateEntity();
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		if (trackTag != null) {
			compound.setString("track", trackTag);
		}
		if (trackInstance != null) {
			NBTTagCompound trackData = new NBTTagCompound();
			trackInstance.writeToNBT(trackData);
			compound.setTag("trackData", trackData);
		}
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("track")) {
			this.trackTag = compound.getString("track");
			initFromTag(this.trackTag);
		}
		if (trackInstance != null && compound.hasKey("trackData")) {
			trackInstance.readFromNBT(compound.getCompoundTag("trackData"));
		}
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		return new SPacketUpdateTileEntity(pos, 0, nbt);
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		readFromNBT(tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}

	public void onMinecartPass(EntityMinecart cart) {
		if (trackInstance != null) {
			trackInstance.onMinecartPass(cart);
		}
	}
}
