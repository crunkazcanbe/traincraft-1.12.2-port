package train.common.tracks;

import mods.railcraft.api.items.IToolCrowbar;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class TrackInstanceBase implements ITrackInstance {

	public TileEntity tileEntity;

	@Override
	public void setTile(TileEntity tile) {
		tileEntity = tile;
	}

	@Override
	public TileEntity getTile() {
		return tileEntity;
	}

	@Override
	public List<ItemStack> getDrops(int fortune) {
		List<ItemStack> drops = new ArrayList<>();
		drops.add(getTrackSpec().getItem());
		return drops;
	}

	@Override
	public int getBasicRailMetadata(EntityMinecart cart) {
		return 0;
	}

	@Override
	public void onMinecartPass(EntityMinecart cart) {
	}

	@Override
	public boolean blockActivated(EntityPlayer player) {
		if (this instanceof ITrackReversable) {
			ItemStack current = player.getHeldItemMainhand();
			if (!current.isEmpty() && current.getItem() instanceof IToolCrowbar) {
				IToolCrowbar crowbar = (IToolCrowbar) current.getItem();
				BlockPos pos = tileEntity.getPos();
				if (crowbar.canWhack(player, net.minecraft.util.EnumHand.MAIN_HAND, current, pos)) {
					ITrackReversable track = (ITrackReversable) this;
					track.setReversed(!track.isReversed());
					markBlockNeedsUpdate();
					crowbar.onWhack(player, net.minecraft.util.EnumHand.MAIN_HAND, current, pos);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void onBlockPlaced() {
		markBlockNeedsUpdate();
	}

	@Override
	public void onBlockPlacedBy(EntityLivingBase entityliving) {
		if (entityliving == null) return;
		if (this instanceof ITrackReversable) {
			int dir = MathHelper.floor((double) ((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3;
			((ITrackReversable) this).setReversed(dir == 0 || dir == 1);
		}
		markBlockNeedsUpdate();
	}

	@Override
	public void onBlockRemoved() {
	}

	public void sendUpdateToClient() {
		((ITrackTile) tileEntity).sendUpdateToClient();
	}

	public void markBlockNeedsUpdate() {
		BlockPos pos = tileEntity.getPos();
		getWorld().markBlockRangeForRenderUpdate(pos, pos);
	}

	@Override
	public void onNeighborBlockChange(Block blockChanged) {
	}

	@Override
	public boolean canPropagatePowerTo(ITrackInstance track) {
		return true;
	}

	@Override
	public TextureAtlasSprite getIcon() {
		return getTrackSpec().getItemIcon();
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
	}

	@Override
	public boolean canUpdate() {
		return false;
	}

	@Override
	public void updateEntity() {
	}

	@Override
	public float getHardness() {
		return 1.05F;
	}

	@Override
	public float getExplosionResistance(double srcX, double srcY, double srcZ, Entity exploder) {
		return 3.5f;
	}

	public World getWorld() {
		return tileEntity.getWorld();
	}

	@Override
	public int getX() {
		return tileEntity.getPos().getX();
	}

	@Override
	public int getY() {
		return tileEntity.getPos().getY();
	}

	@Override
	public int getZ() {
		return tileEntity.getPos().getZ();
	}

	@Override
	public boolean isFlexibleRail() {
		return false;
	}

	@Override
	public boolean canMakeSlopes() {
		return true;
	}

	@Override
	public float getRailMaxSpeed(EntityMinecart cart) {
		return 0.4f;
	}

	@Override
	public void writePacketData(DataOutputStream data) throws IOException {
	}

	@Override
	public void readPacketData(DataInputStream data) throws IOException {
	}
}
