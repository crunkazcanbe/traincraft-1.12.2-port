package train.common.tile;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import train.common.items.ItemTCRail;

import java.util.ArrayList;
import java.util.List;

public class TileTCRail extends TileEntity implements net.minecraft.util.ITickable {

	public double r;
	public double cx;
	public double cy;
	public double cz;
	public double slopeHeight;
	public double slopeLength;
	public double slopeAngle;
	public double railLength;
	public int ballastMaterial;
	public int ballastMetadata;
	public int ballastColour;
	private String type;
	private int facingMeta;
	public boolean isLinkedToRail = false;
	public int linkedX;
	public int linkedY;
	public int linkedZ;
	public boolean hasModel = true;
	private boolean switchActive = false;
	/** stores the latest redstone state */
	public boolean previousRedstoneState;
	public boolean canTypeBeModifiedBySwitch = false;
	private boolean manualOverride = false;
	private int updateTicks;
	private int updateTicks2;
	public Item		idDrop;
	private static final float f = 0.125F;
	public boolean hasRotated = false;
	private int isLeftFlag = -5;

	public TileTCRail() {
		// facingMeta initialized later
	}

	public int getFacing() {

		return facingMeta;
	}

	public void setFacing(int facing) {

		this.facingMeta = facing;
	}

	public void setType(String type) {
		world.notifyBlockUpdate(getPos(), world.getBlockState(getPos()), world.getBlockState(getPos()), 3);
		this.type = type;
	}

	public String getType() {

		return this.type;
	}

	public void setRailLength(Double railLength){
		world.notifyBlockUpdate(getPos(), world.getBlockState(getPos()), world.getBlockState(getPos()), 3);
		this.railLength = railLength;
	}

	public double getRailLength() {
		return this.railLength;
	}

	public void setBallastMaterial(int ballast) {
		world.notifyBlockUpdate(getPos(), world.getBlockState(getPos()), world.getBlockState(getPos()), 3);
		this.ballastMaterial = ballast;
	}

	public int getBallastMaterial(){
		if (ballastMaterial != 0){
			return ballastMaterial;
		}
		else {
			return (0);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		AxisAlignedBB bb = INFINITE_EXTENT_AABB;
		net.minecraft.block.Block blockType = getBlockType();
		if (blockType == train.common.library.BlockIDs.tcRail.block) {
			// Must be a real 3D box: the old box had zero height (minY==maxY), a degenerate
			// volume the frustum culler dropped when viewed from above/at an angle (tracks
			// vanished when flying up and looking down). Give it generous height + reach so
			// big curves (32x32), slopes and the train on top always stay in view.
			double x = getPos().getX(), y = getPos().getY(), z = getPos().getZ();
			bb = new AxisAlignedBB(x - 40, y - 8, z - 40, x + 41, y + 9, z + 41);
		}
		return bb;
	}

	private ItemTCRail.TrackTypes renderType = null;
	public ItemTCRail.TrackTypes getTrackType(){
		if (renderType == null && hasModel && getType() != null){
			for(ItemTCRail.TrackTypes rail : ItemTCRail.TrackTypes.values()){
				if (rail.getLabel().equals(getType())){
					renderType = rail;
				}
			}
		}
		return renderType;
	}

	public boolean getSwitchState() {

		return switchActive;
	}

	public void printInfo() {
		System.out.println(type);
		System.out.println(getSwitchState());
		System.out.println(ItemTCRail.isTCStraightTrack(this));
	}

	@Override
	public void update() {
		if (world.isRemote || !canTypeBeModifiedBySwitch) {

			return;
		}

		updateTicks2++;

		/*if (updateTicks2 % 20 == 0 && !isLinkedToRail && getType() != null && getType().equals(TrackTypes.SMALL_STRAIGHT.getLabel()) && !hasRotated) {
			TileEntity tileNorth = world.getBlockTileEntity(getPos().getX(), getPos().getY(), getPos().getZ() - 1);
			TileEntity tileSouth = world.getBlockTileEntity(getPos().getX(), getPos().getY(), getPos().getZ() + 1);
			TileEntity tileEast = world.getBlockTileEntity(getPos().getX() + 1, getPos().getY(), getPos().getZ());
			TileEntity tileWest = world.getBlockTileEntity(getPos().getX() - 1, getPos().getY(), getPos().getZ());
			if (tileNorth != null && (tileNorth instanceof TileTCRail)) {//&& (tileNorth.getBlockMetadata() == 2 || tileNorth.getBlockMetadata() == 0)) {
				world.setBlockMetadataWithNotify(getPos().getX(), getPos().getY(), getPos().getZ(), 2, 2);
				hasRotated = true;
			}
			if (tileSouth != null && (tileSouth instanceof TileTCRail)) {//&& (tileSouth.getBlockMetadata() == 0 || tileSouth.getBlockMetadata() == 2)) {
				world.setBlockMetadataWithNotify(getPos().getX(), getPos().getY(), getPos().getZ(), 0, 2);
				hasRotated = true;
			}
			if (tileEast != null && (tileEast instanceof TileTCRail)) {// && ( tileEast.getBlockMetadata() == 3 || tileEast.getBlockMetadata() == 1)) {
				world.setBlockMetadataWithNotify(getPos().getX(), getPos().getY(), getPos().getZ(), 3, 2);
				hasRotated = true;
			}
			if (tileWest != null && (tileWest instanceof TileTCRail)) {//&& ( tileWest.getBlockMetadata() == 1 || tileWest.getBlockMetadata() == 3)) {
				world.setBlockMetadataWithNotify(getPos().getX(), getPos().getY(), getPos().getZ(), 1, 2);
				hasRotated = true;
			}
		}*/

		if (updateTicks2 % 11 == 0) {
			updateTicks2 =0;
			TileEntity tile1 = null;

			switch (world.getBlockState(getPos()).getBlock().getMetaFromState(world.getBlockState(getPos()))) {

				case 0: {
					tile1 = world.getTileEntity(getPos().add(0, 0, -1));
					break;
				}
				case 1: {
					tile1 = world.getTileEntity(getPos().add(1, 0, 0));
					break;
				}
				case 2: {
					tile1 = world.getTileEntity(getPos().add(0, 0, 1));
					break;
				}
				case 3: {
					tile1 = world.getTileEntity(getPos().add(-1, 0, 0));
					break;
				}
			}
			if (tile1 instanceof TileTCRail && ItemTCRail.isTCSwitch((TileTCRail) tile1)) {

				TileTCRail tileSwitch = (TileTCRail) tile1;
				boolean flag1 = world.isBlockPowered(getPos());

				if (tileSwitch.previousRedstoneState != flag1 && ! world.isBlockPowered(tileSwitch.getPos())) {

					tileSwitch.changeSwitchState(world, tileSwitch, tile1.getPos().getX(), tile1.getPos().getY(), tile1.getPos().getZ());
					tileSwitch.previousRedstoneState = flag1;
				}
			}
			/*
			 * if (tile2 != null && tile2 instanceof TileTCRail &&
			 * ItemTCRail.isTCSwitch((TileTCRail) tile2)) { TileTCRail
			 * tileSwitch = (TileTCRail) tile2; boolean flag1 =
			 * world.isBlockPowered(getPos().getX(), getPos().getY(),
			 * getPos().getZ()); boolean flag2 =
			 * world.isBlockPowered(tileSwitch.getPos().getX(),
			 * tileSwitch.getPos().getY(), tileSwitch.getPos().getZ());
			 * //System.out.println(flag2+" flag2"); //boolean switchState2
			 * = tileSwitch.getSwitchState(); if
			 * (tileSwitch.previousRedstoneState != flag1 && !flag2) {
			 * tileSwitch.changeSwitchState(world, tile2, tile2.getPos().getX(),
			 * tile2.getPos().getY(), tile2.getPos().getZ()); tileSwitch.previousRedstoneState
			 * = flag1; } }
			 */
		}

		if (manualOverride) {

			updateTicks++;

			if (updateTicks > 60) {
				List list = world.getEntitiesWithinAABB(EntityMinecart.class, new AxisAlignedBB(getPos().getX() + f,
						getPos().getY(), getPos().getZ() + f, getPos().getX() + 1 - f, getPos().getY() + 1 - f, getPos().getZ() + 1 - f));

				if (list.isEmpty()) {

					manualOverride = false;
					//setSwitchState(false,false);
					// world.setBlockMetadataWithNotify(getPos().getX(), getPos().getY(), getPos().getZ(), facingMeta, 2);
					// System.out.println("X: " + getPos().getX() + " Y: " + getPos().getY() + " Z: " + getPos().getZ());
					changeSwitchState(world, this, getPos().getX(), getPos().getY(), getPos().getZ());
					setSwitchState(previousRedstoneState, false);
					updateTicks = 0;
				} else {
					updateTicks -=20;
				}
			}
		}

		if (!getSwitchState() && updateTicks2 % 10 ==0) {

			/* Right-handed switch types create a value of 1, left-handed switch types a value of type -1. If neither cases match, value is set to 0. */
			if (isLeftFlag == -5) {
				if (ItemTCRail.TrackTypes.MEDIUM_RIGHT_SWITCH.getLabel().equals(type) || ItemTCRail.TrackTypes.LARGE_RIGHT_SWITCH.getLabel().equals(type) || ItemTCRail.TrackTypes.VERY_LARGE_RIGHT_SWITCH.getLabel().equals(type) || ItemTCRail.TrackTypes.MEDIUM_RIGHT_PARALLEL_SWITCH.getLabel().equals(type) || ItemTCRail.TrackTypes.MEDIUM_RIGHT_45DEGREE_SWITCH.getLabel().equals(type)
						|| ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_RIGHT_SWITCH.getLabel().equals(type) || ItemTCRail.TrackTypes.EMBEDDED_LARGE_RIGHT_SWITCH.getLabel().equals(type) || ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_RIGHT_SWITCH.getLabel().equals(type) || ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_RIGHT_PARALLEL_SWITCH.getLabel().equals(type) || ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_RIGHT_45DEGREE_SWITCH.getLabel().equals(type)){
					isLeftFlag =1;
				} else if (ItemTCRail.TrackTypes.MEDIUM_LEFT_SWITCH.getLabel().equals(type) || ItemTCRail.TrackTypes.LARGE_LEFT_SWITCH.getLabel().equals(type) || ItemTCRail.TrackTypes.VERY_LARGE_LEFT_SWITCH.getLabel().equals(type) || ItemTCRail.TrackTypes.MEDIUM_LEFT_PARALLEL_SWITCH.getLabel().equals(type) || ItemTCRail.TrackTypes.MEDIUM_LEFT_45DEGREE_SWITCH.getLabel().equals(type)
						|| ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_LEFT_SWITCH.getLabel().equals(type) || ItemTCRail.TrackTypes.EMBEDDED_LARGE_LEFT_SWITCH.getLabel().equals(type) || ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_LEFT_SWITCH.getLabel().equals(type) || ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_LEFT_PARALLEL_SWITCH.getLabel().equals(type) || ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_LEFT_45DEGREE_SWITCH.getLabel().equals(type)){
					isLeftFlag = -1;
				} else {
					isLeftFlag=0;
				}
			}

			if (isLeftFlag != 0) {
				List list;

				switch (facingMeta) {

					case 0: {
						if (isLeftFlag == 1) {

							list = world.getEntitiesWithinAABB(EntityMinecart.class, new AxisAlignedBB(getPos().getX() - 2.0D, getPos().getY(), getPos().getZ() + 2.0D, getPos().getX() - f, getPos().getY() + 1.0D - f, getPos().getZ() + 2.0D - f));
						} else {

							list = world.getEntitiesWithinAABB(EntityMinecart.class, new AxisAlignedBB(getPos().getX() + 1.0D, getPos().getY(), getPos().getZ() + 1.0D, getPos().getX() + 2.0D - f, getPos().getY() + 1.0D - f, getPos().getZ() + 2.0D - f));
						}
						break;
					}
					case 1: {
						if (isLeftFlag == 1) {

							list = world.getEntitiesWithinAABB(EntityMinecart.class, new AxisAlignedBB(getPos().getX() - 1.0D, getPos().getY(), getPos().getZ() - 1.0D, getPos().getX() - f, getPos().getY() + 1.0D - f, getPos().getZ() - f));
						} else {

							list = world.getEntitiesWithinAABB(EntityMinecart.class, new AxisAlignedBB(getPos().getX() - 1.0D, getPos().getY(), getPos().getZ() + 1.0D, getPos().getX() - f, getPos().getY() + 1.0D - f, getPos().getZ() + 2.0D - f));
						}
						break;
					}
					case 2: {
						if (isLeftFlag == 1) {

							list = world.getEntitiesWithinAABB(EntityMinecart.class, new AxisAlignedBB(getPos().getX() + 1.0D, getPos().getY(), getPos().getZ() - 1.0D, getPos().getX() + 2.0D - f, getPos().getY() + 1.0D - f, getPos().getZ() - f));
						} else {

							list = world.getEntitiesWithinAABB(EntityMinecart.class, new AxisAlignedBB(getPos().getX() - 1.0D, getPos().getY(), getPos().getZ() - 1.0D, getPos().getX() - f, getPos().getY() + 1.0D - f, getPos().getZ() - f));
						}
						break;
					}
					case 3: {
						if (isLeftFlag == 1) {

							list = world.getEntitiesWithinAABB(EntityMinecart.class, new AxisAlignedBB(getPos().getX() + 1.0D, getPos().getY(), getPos().getZ() + 1.0D, getPos().getX() + 3.0D - f, getPos().getY() + 1.0D - f, getPos().getZ() + 2.0D - f));
						} else {

							list = world.getEntitiesWithinAABB(EntityMinecart.class, new AxisAlignedBB(getPos().getX() + 1.0D, getPos().getY(), getPos().getZ() - 1.0D, getPos().getX() + 3.0D - f, getPos().getY() + 1.0 - f, getPos().getZ() - f));
						}
						break;
					}
					default: {
						list = new ArrayList();
						break;
					}
				}
				if (!list.isEmpty()) {

					changeSwitchState(world, this, getPos().getX(), getPos().getY(), getPos().getZ());
					setSwitchState(true, true);
				}
			}
		}
	}

	public void setSwitchState(boolean state, boolean manualOverride) {
		previousRedstoneState = world.isBlockPowered(getPos());
		this.switchActive = state;
		this.manualOverride = manualOverride;

		if (manualOverride) {
			updateTicks = 0;
		}

		this.markDirty();
		this.world.notifyBlockUpdate(getPos(), world.getBlockState(getPos()), world.getBlockState(getPos()), 3);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		this.renderType = null; // force the client to re-resolve the model after a (re)load
		facingMeta = nbt.getByte("Orientation");
		r = nbt.getDouble("r");
		cx = nbt.getDouble("cx");
		cy = nbt.getDouble("cy");
		cz = nbt.getDouble("cz");
		cy = nbt.getDouble("cy");
		slopeHeight = nbt.getDouble("slopeHeight");
		slopeLength = nbt.getDouble("slopeLength");
		slopeAngle = nbt.getDouble("slopeAngle");
		railLength = nbt.getDouble("railLength");
		linkedX = nbt.getInteger("linkedX");
		linkedY = nbt.getInteger("linkedY");
		linkedZ = nbt.getInteger("linkedZ");
		ballastMetadata = nbt.getInteger("ballastMetadata");
		ballastColour = nbt.getInteger("ballastColour");
		String tempType = nbt.getString("type");
		if (tempType != null) {
			type = tempType;
		} else {
			type = ItemTCRail.TrackTypes.SMALL_STRAIGHT.getLabel();
		}
		if(nbt.hasKey("ballastMaterial")) {
			ballastMaterial = nbt.getInteger("ballastMaterial");
		} else {
			ballastMaterial = 0;
		}
		/**
		 * Hacky TC Code to fix already placed slopes
		 */
		if (type.equals(ItemTCRail.TrackTypes.SLOPE_WOOD.getLabel())
				|| type.equals(ItemTCRail.TrackTypes.SLOPE_GRAVEL.getLabel())
				|| type.equals(ItemTCRail.TrackTypes.SLOPE_BALLAST.getLabel())
				|| type.equals(ItemTCRail.TrackTypes.SLOPE_SNOW_GRAVEL.getLabel())
				|| type.equals(ItemTCRail.TrackTypes.SLOPE_DYNAMIC.getLabel())) {
			slopeAngle = 0.13;
		}

		if (type.equals(ItemTCRail.TrackTypes.LARGE_SLOPE_WOOD.getLabel())
				|| type.equals(ItemTCRail.TrackTypes.LARGE_SLOPE_GRAVEL.getLabel())
				|| type.equals(ItemTCRail.TrackTypes.LARGE_SLOPE_BALLAST.getLabel())
				|| type.equals(ItemTCRail.TrackTypes.LARGE_SLOPE_SNOW_GRAVEL.getLabel())
				|| type.equals(ItemTCRail.TrackTypes.LARGE_SLOPE_DYNAMIC.getLabel())) {
			slopeAngle = 0.0666;
		}

		if (type.equals(ItemTCRail.TrackTypes.VERY_LARGE_SLOPE_WOOD.getLabel())
				|| type.equals(ItemTCRail.TrackTypes.VERY_LARGE_SLOPE_GRAVEL.getLabel())
				|| type.equals(ItemTCRail.TrackTypes.VERY_LARGE_SLOPE_BALLAST.getLabel())
				|| type.equals(ItemTCRail.TrackTypes.VERY_LARGE_SLOPE_SNOW_GRAVEL.getLabel())
				|| type.equals(ItemTCRail.TrackTypes.VERY_LARGE_SLOPE_DYNAMIC.getLabel())) {
			slopeAngle = 0.0444;
		}

		if (type.equals(ItemTCRail.TrackTypes.LARGE_CURVED_SLOPE_DYNAMIC.getLabel())) {
			slopeAngle = 0.1558;
		}
		isLinkedToRail = nbt.getBoolean("isLinkedToRail");
		hasModel = nbt.getBoolean("hasModel");
		switchActive = nbt.getBoolean("switchActive");
		canTypeBeModifiedBySwitch = nbt.getBoolean("canTypeBeModifiedBySwitch");
		manualOverride = nbt.getBoolean("manualOverride");
		idDrop = Item.getItemById(nbt.getInteger("idDrop"));
		hasRotated = nbt.getBoolean("hasRotated");
		previousRedstoneState = nbt.getBoolean("previousRedstoneState");
		super.readFromNBT(nbt);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setByte("Orientation", (byte) facingMeta);
		nbt.setDouble("r", r);
		nbt.setDouble("cx", cx);
		nbt.setDouble("cy", cy);
		nbt.setDouble("cz", cz);
		nbt.setDouble("slopeHeight", slopeHeight);
		nbt.setDouble("slopeLength", slopeLength);
		nbt.setDouble("slopeAngle", slopeAngle);
		nbt.setDouble("railLength", railLength);
		nbt.setInteger("linkedX", linkedX);
		nbt.setInteger("linkedY", linkedY);
		nbt.setInteger("linkedZ", linkedZ);
		nbt.setInteger("ballastMetadata", ballastMetadata);
		nbt.setInteger("ballastColour", ballastColour);
		if (ballastMaterial != 0) {
			nbt.setInteger("ballastMaterial", ballastMaterial);
		}
		if (type != null) {
			nbt.setString("type", type);
		}
		nbt.setBoolean("isLinkedToRail", isLinkedToRail);
		nbt.setBoolean("hasModel", hasModel);
		nbt.setBoolean("switchActive", switchActive);
		nbt.setBoolean("canTypeBeModifiedBySwitch", canTypeBeModifiedBySwitch);
		nbt.setBoolean("manualOverride", manualOverride);
		nbt.setBoolean("hasRotated", hasRotated);
		nbt.setInteger("idDrop", Item.getIdFromItem(idDrop));
		nbt.setBoolean("previousRedstoneState", previousRedstoneState);
		return super.writeToNBT(nbt);
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {

		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);

		return new SPacketUpdateTileEntity(getPos(), 1, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt){
		this.readFromNBT(pkt.getNbtCompound());
		super.onDataPacket(net, pkt);
	}

	/**
	 * CHUNK-LOAD SYNC (the "tracks go invisible after restart / until I break+replace" fix).
	 * When a chunk (re)loads, the client builds its tile entities from getUpdateTag(), NOT from
	 * getUpdatePacket(). Without a full getUpdateTag the client tile had no {@code type}, so the
	 * renderer's {@code hasModel && getTrackType()!=null} guard failed and the track drew nothing.
	 * Send/read the complete NBT so freshly-loaded tracks render exactly like just-placed ones.
	 */
	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		this.readFromNBT(tag);
	}

	public void changeSwitchState(World world, TileTCRail tileEntity, int i, int j, int k) {
		if (tileEntity.getType() != null && (tileEntity.getType().contains("SWITCH"))) {
			if (tileEntity.getSwitchState()) {
				tileEntity.setSwitchState(false, false);
				if (tileEntity.getBlockMetadata() == 2) {
					TileEntity te1 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k - 1));
					if (te1 != null) {
						((TileTCRail) te1).setType(ItemTCRail.TrackTypes.SMALL_STRAIGHT.getLabel());

						if (       tileEntity.getType().equals(ItemTCRail.TrackTypes.MEDIUM_RIGHT_PARALLEL_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.MEDIUM_LEFT_PARALLEL_SWITCH.getLabel())
						 		|| tileEntity.getType().equals(ItemTCRail.TrackTypes.LARGE_RIGHT_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.LARGE_LEFT_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_LARGE_RIGHT_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_LARGE_LEFT_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_RIGHT_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_LEFT_SWITCH.getLabel())){
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k - 2));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.SMALL_STRAIGHT.getLabel());

						}
						if (tileEntity.getType().equals(ItemTCRail.TrackTypes.VERY_LARGE_RIGHT_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.VERY_LARGE_LEFT_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_RIGHT_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_LEFT_SWITCH.getLabel())){
							TileEntity te3 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k - 3));
							if (te3 != null) ((TileTCRail) te3).setType(ItemTCRail.TrackTypes.SMALL_STRAIGHT.getLabel());
						}
					}
				}
				if (tileEntity.getBlockMetadata() == 0) {
					TileEntity te1 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k + 1));
					if (te1 instanceof TileTCRail) {
						((TileTCRail) te1).setType(ItemTCRail.TrackTypes.SMALL_STRAIGHT.getLabel());
						if (       tileEntity.getType().equals(ItemTCRail.TrackTypes.MEDIUM_RIGHT_PARALLEL_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.MEDIUM_LEFT_PARALLEL_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.LARGE_RIGHT_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.LARGE_LEFT_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.VERY_LARGE_RIGHT_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.VERY_LARGE_LEFT_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_LARGE_RIGHT_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_LARGE_LEFT_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_RIGHT_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_LEFT_SWITCH.getLabel())){
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k + 2));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.SMALL_STRAIGHT.getLabel());
						}
						if (tileEntity.getType().equals(ItemTCRail.TrackTypes.VERY_LARGE_RIGHT_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.VERY_LARGE_LEFT_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_RIGHT_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_LEFT_SWITCH.getLabel())){
							TileEntity te3 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k + 3));
							if (te3 != null) ((TileTCRail) te3).setType(ItemTCRail.TrackTypes.SMALL_STRAIGHT.getLabel());
						}
					}
				}
				if (tileEntity.getBlockMetadata() == 1) {
					TileEntity te1 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i - 1, j, k));
					if (te1 != null) {
						((TileTCRail) te1).setType(ItemTCRail.TrackTypes.SMALL_STRAIGHT.getLabel());
						if (tileEntity.getType().equals(ItemTCRail.TrackTypes.MEDIUM_RIGHT_PARALLEL_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.MEDIUM_LEFT_PARALLEL_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.LARGE_RIGHT_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.LARGE_LEFT_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.VERY_LARGE_RIGHT_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.VERY_LARGE_LEFT_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_LARGE_RIGHT_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_LARGE_LEFT_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_RIGHT_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_LEFT_SWITCH.getLabel())){
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i - 2, j, k));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.SMALL_STRAIGHT.getLabel());
						}
						if (tileEntity.getType().equals(ItemTCRail.TrackTypes.VERY_LARGE_RIGHT_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.VERY_LARGE_LEFT_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_RIGHT_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_LEFT_SWITCH.getLabel())){
							TileEntity te3 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i - 3, j, k));
							if (te3 != null) ((TileTCRail) te3).setType(ItemTCRail.TrackTypes.SMALL_STRAIGHT.getLabel());
						}
					}
				}
				if (tileEntity.getBlockMetadata() == 3) {
					TileEntity te1 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i + 1, j, k));
					if (te1 != null) {
						((TileTCRail) te1).setType(ItemTCRail.TrackTypes.SMALL_STRAIGHT.getLabel());
						if (tileEntity.getType().equals(ItemTCRail.TrackTypes.MEDIUM_RIGHT_PARALLEL_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.MEDIUM_LEFT_PARALLEL_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.LARGE_RIGHT_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.LARGE_LEFT_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.VERY_LARGE_RIGHT_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.VERY_LARGE_LEFT_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_LARGE_RIGHT_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_LARGE_LEFT_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_RIGHT_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_LEFT_SWITCH.getLabel())){
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i + 2, j, k));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.SMALL_STRAIGHT.getLabel());
						}
						if (tileEntity.getType().equals(ItemTCRail.TrackTypes.VERY_LARGE_RIGHT_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.VERY_LARGE_LEFT_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_RIGHT_SWITCH.getLabel())
								|| tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_LEFT_SWITCH.getLabel())){
							TileEntity te3 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i + 3, j, k));
							if (te3 != null) ((TileTCRail) te3).setType(ItemTCRail.TrackTypes.SMALL_STRAIGHT.getLabel());
						}
					}
				}
			}
			else if (!tileEntity.getSwitchState()) {
				tileEntity.setSwitchState(true, false);
				if (tileEntity.getBlockMetadata() == 2) {
					TileEntity te1 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k - 1));

					if (te1 != null) {
						if (tileEntity.getType().equals(ItemTCRail.TrackTypes.MEDIUM_LEFT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.MEDIUM_LEFT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.MEDIUM_RIGHT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.MEDIUM_RIGHT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.MEDIUM_LEFT_45DEGREE_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.MEDIUM_LEFT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.MEDIUM_RIGHT_45DEGREE_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.MEDIUM_RIGHT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.MEDIUM_RIGHT_PARALLEL_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.MEDIUM_RIGHT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k - 2));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.MEDIUM_RIGHT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.MEDIUM_LEFT_PARALLEL_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.MEDIUM_LEFT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k - 2));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.MEDIUM_LEFT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.LARGE_RIGHT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.LARGE_RIGHT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k - 2));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.LARGE_RIGHT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.LARGE_LEFT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.LARGE_LEFT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k - 2));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.LARGE_LEFT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.VERY_LARGE_RIGHT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.VERY_LARGE_RIGHT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k - 2));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.VERY_LARGE_RIGHT_TURN.getLabel());
							TileEntity te3 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k - 3));
							if (te3 != null) ((TileTCRail) te3).setType(ItemTCRail.TrackTypes.VERY_LARGE_RIGHT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.VERY_LARGE_LEFT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.VERY_LARGE_LEFT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k - 2));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.VERY_LARGE_LEFT_TURN.getLabel());
							TileEntity te3 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k - 3));
							if (te3 != null) ((TileTCRail) te3).setType(ItemTCRail.TrackTypes.VERY_LARGE_LEFT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_LEFT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_LEFT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_RIGHT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_RIGHT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_LEFT_45DEGREE_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_LEFT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_RIGHT_45DEGREE_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_RIGHT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_RIGHT_PARALLEL_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_RIGHT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k - 2));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_RIGHT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_LEFT_PARALLEL_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_LEFT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k - 2));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_LEFT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_LARGE_RIGHT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.EMBEDDED_LARGE_RIGHT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k - 2));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.EMBEDDED_LARGE_RIGHT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_LARGE_LEFT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.EMBEDDED_LARGE_LEFT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k - 2));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.EMBEDDED_LARGE_LEFT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_RIGHT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_RIGHT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k - 2));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_RIGHT_TURN.getLabel());
							TileEntity te3 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k - 3));
							if (te3 != null) ((TileTCRail) te3).setType(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_RIGHT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_LEFT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_LEFT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k - 2));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_LEFT_TURN.getLabel());
							TileEntity te3 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k - 3));
							if (te3 != null) ((TileTCRail) te3).setType(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_LEFT_TURN.getLabel());
						}


					}
				}
				if (tileEntity.getBlockMetadata() == 0) {
					TileEntity te1 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k + 1));
					if (te1 != null) {
						if (tileEntity.getType().equals(ItemTCRail.TrackTypes.MEDIUM_LEFT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.MEDIUM_LEFT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.MEDIUM_RIGHT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.MEDIUM_RIGHT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.MEDIUM_LEFT_45DEGREE_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.MEDIUM_LEFT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.MEDIUM_RIGHT_45DEGREE_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.MEDIUM_RIGHT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.MEDIUM_RIGHT_PARALLEL_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.MEDIUM_RIGHT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k + 2));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.MEDIUM_RIGHT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.MEDIUM_LEFT_PARALLEL_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.MEDIUM_LEFT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k + 2));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.MEDIUM_LEFT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.LARGE_RIGHT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.LARGE_RIGHT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k + 2));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.LARGE_RIGHT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.LARGE_LEFT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.LARGE_LEFT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k + 2));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.LARGE_LEFT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.VERY_LARGE_RIGHT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.VERY_LARGE_RIGHT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k + 2));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.VERY_LARGE_RIGHT_TURN.getLabel());
							TileEntity te3 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k + 3));
							if (te3 != null) ((TileTCRail) te3).setType(ItemTCRail.TrackTypes.VERY_LARGE_RIGHT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.VERY_LARGE_LEFT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.VERY_LARGE_LEFT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k + 2));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.VERY_LARGE_LEFT_TURN.getLabel());
							TileEntity te3 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k + 3));
							if (te3 != null) ((TileTCRail) te3).setType(ItemTCRail.TrackTypes.VERY_LARGE_LEFT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_LEFT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_LEFT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_RIGHT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_RIGHT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_LEFT_45DEGREE_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_LEFT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_RIGHT_45DEGREE_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_RIGHT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_RIGHT_PARALLEL_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_RIGHT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k + 2));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_RIGHT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_LEFT_PARALLEL_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_LEFT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k + 2));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_LEFT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_LARGE_RIGHT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.EMBEDDED_LARGE_RIGHT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k + 2));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.EMBEDDED_LARGE_RIGHT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_LARGE_LEFT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.EMBEDDED_LARGE_LEFT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k + 2));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.EMBEDDED_LARGE_LEFT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_RIGHT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_RIGHT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k + 2));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_RIGHT_TURN.getLabel());
							TileEntity te3 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k + 3));
							if (te3 != null) ((TileTCRail) te3).setType(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_RIGHT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_LEFT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_LEFT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k + 2));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_LEFT_TURN.getLabel());
							TileEntity te3 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k + 3));
							if (te3 != null) ((TileTCRail) te3).setType(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_LEFT_TURN.getLabel());
						}


					}
				}
				if (tileEntity.getBlockMetadata() == 1) {
					TileEntity te1 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i - 1, j, k));
					if (te1 != null) {
						if (tileEntity.getType().equals(ItemTCRail.TrackTypes.MEDIUM_LEFT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.MEDIUM_LEFT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.MEDIUM_RIGHT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.MEDIUM_RIGHT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.MEDIUM_LEFT_45DEGREE_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.MEDIUM_LEFT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.MEDIUM_RIGHT_45DEGREE_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.MEDIUM_RIGHT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.MEDIUM_RIGHT_PARALLEL_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.MEDIUM_RIGHT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i - 2, j, k));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.MEDIUM_RIGHT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.MEDIUM_LEFT_PARALLEL_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.MEDIUM_LEFT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i - 2, j, k));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.MEDIUM_LEFT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.LARGE_RIGHT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.LARGE_RIGHT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i - 2, j, k));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.LARGE_RIGHT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.LARGE_LEFT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.LARGE_LEFT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i - 2, j, k));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.LARGE_LEFT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.VERY_LARGE_RIGHT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.VERY_LARGE_RIGHT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i - 2, j, k));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.VERY_LARGE_RIGHT_TURN.getLabel());
							TileEntity te3 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i - 3, j, k));
							if (te3 != null) ((TileTCRail) te3).setType(ItemTCRail.TrackTypes.VERY_LARGE_RIGHT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.VERY_LARGE_LEFT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.VERY_LARGE_LEFT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i - 2, j, k));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.VERY_LARGE_LEFT_TURN.getLabel());
							TileEntity te3 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i - 3, j, k));
							if (te3 != null) ((TileTCRail) te3).setType(ItemTCRail.TrackTypes.VERY_LARGE_LEFT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_LEFT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_LEFT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_RIGHT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_RIGHT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_LEFT_45DEGREE_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_LEFT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_RIGHT_45DEGREE_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_RIGHT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_RIGHT_PARALLEL_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_RIGHT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i - 2, j, k));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_RIGHT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_LEFT_PARALLEL_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_LEFT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i - 2, j, k));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_LEFT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_LARGE_RIGHT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.EMBEDDED_LARGE_RIGHT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i - 2, j, k));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.EMBEDDED_LARGE_RIGHT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_LARGE_LEFT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.EMBEDDED_LARGE_LEFT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i - 2, j, k));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.EMBEDDED_LARGE_LEFT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_RIGHT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_RIGHT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i - 2, j, k));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_RIGHT_TURN.getLabel());
							TileEntity te3 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i - 3, j, k));
							if (te3 != null) ((TileTCRail) te3).setType(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_RIGHT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_LEFT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_LEFT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i - 2, j, k));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_LEFT_TURN.getLabel());
							TileEntity te3 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i - 3, j, k));
							if (te3 != null) ((TileTCRail) te3).setType(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_LEFT_TURN.getLabel());
						}

					}
				}
				if (tileEntity.getBlockMetadata() == 3) {
					TileEntity te1 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i + 1, j, k));
					if (te1 != null) {
						if (tileEntity.getType().equals(ItemTCRail.TrackTypes.MEDIUM_LEFT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.MEDIUM_LEFT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.MEDIUM_RIGHT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.MEDIUM_RIGHT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.MEDIUM_LEFT_45DEGREE_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.MEDIUM_LEFT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.MEDIUM_RIGHT_45DEGREE_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.MEDIUM_RIGHT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.MEDIUM_RIGHT_PARALLEL_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.MEDIUM_RIGHT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i + 2, j, k));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.MEDIUM_RIGHT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.MEDIUM_LEFT_PARALLEL_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.MEDIUM_LEFT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i + 2, j, k));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.MEDIUM_LEFT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.LARGE_RIGHT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.LARGE_RIGHT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i + 2, j, k));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.LARGE_RIGHT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.LARGE_LEFT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.LARGE_LEFT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i + 2, j, k));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.LARGE_LEFT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.VERY_LARGE_RIGHT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.VERY_LARGE_RIGHT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i + 2, j, k));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.VERY_LARGE_RIGHT_TURN.getLabel());
							TileEntity te3 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i + 3, j, k));
							if (te3 != null) ((TileTCRail) te3).setType(ItemTCRail.TrackTypes.VERY_LARGE_RIGHT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.VERY_LARGE_LEFT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.VERY_LARGE_LEFT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i + 2, j, k));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.VERY_LARGE_LEFT_TURN.getLabel());
							TileEntity te3 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i + 3, j, k));
							if (te3 != null) ((TileTCRail) te3).setType(ItemTCRail.TrackTypes.VERY_LARGE_LEFT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_LEFT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_LEFT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_RIGHT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_RIGHT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_LEFT_45DEGREE_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_LEFT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_RIGHT_45DEGREE_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_RIGHT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_RIGHT_PARALLEL_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_RIGHT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i + 2, j, k));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_RIGHT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_LEFT_PARALLEL_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_LEFT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i + 2, j, k));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.EMBEDDED_MEDIUM_LEFT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_LARGE_RIGHT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.EMBEDDED_LARGE_RIGHT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i + 2, j, k));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.EMBEDDED_LARGE_RIGHT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_LARGE_LEFT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.EMBEDDED_LARGE_LEFT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i + 2, j, k));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.EMBEDDED_LARGE_LEFT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_RIGHT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_RIGHT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i + 2, j, k));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_RIGHT_TURN.getLabel());
							TileEntity te3 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i + 3, j, k));
							if (te3 != null) ((TileTCRail) te3).setType(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_RIGHT_TURN.getLabel());
						}
						else if (tileEntity.getType().equals(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_LEFT_SWITCH.getLabel())) {
							((TileTCRail) te1).setType(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_LEFT_TURN.getLabel());
							TileEntity te2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i + 2, j, k));
							if (te2 != null) ((TileTCRail) te2).setType(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_LEFT_TURN.getLabel());
							TileEntity te3 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i + 3, j, k));
							if (te3 != null) ((TileTCRail) te3).setType(ItemTCRail.TrackTypes.EMBEDDED_VERY_LARGE_LEFT_TURN.getLabel());
						}


					}
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 16384.0D;
	}
}
