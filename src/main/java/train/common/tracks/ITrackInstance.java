/**
 * Traincraft-owned track instance API.
 *
 * This package (train.common.tracks) replaces the old vendored copy of Railcraft's
 * *1.7.10-era* mods.railcraft.api.tracks.* API that used to live in this codebase
 * under src/main/java/mods/railcraft/api. That API (TrackSpec/TrackInstanceBase/
 * ITrackTile/ITrackPowered/etc, hosted by a single generic block that REAL Railcraft
 * used to provide at runtime) was completely redesigned in current Railcraft
 * (12.1.0-beta-8, the actual 1.12.2 release): tracks are now "TrackKit" items applied
 * to Railcraft's own outfitted rail block/tile, registered through Railcraft's own
 * IForgeRegistry and hosted by Railcraft-internal classes that third-party mods are
 * not meant to subclass directly.
 *
 * Fully re-hosting Traincraft's ~20 track types on Railcraft's TrackKit system would
 * require Traincraft to become a registered "Railcraft Module" and to ship new
 * blockstate/model assets for Railcraft's TrackKit renderer - a much larger project
 * than a compile-error fix. Instead, this package keeps the same shapes/contracts
 * the ~25 dependent classes already used (mechanical, low risk - their gameplay
 * logic is unchanged) but has Traincraft host its own generic outfitted-track block
 * (see train.common.blocks.BlockTraincraftTrack) instead of leaning on Railcraft to
 * provide one. This also fixes a latent bug: TrackSpec.blockTrack was never actually
 * assigned anywhere in this codebase (it relied on real Railcraft's internals to set
 * it), so these track types were non-functional even when Railcraft was installed.
 *
 * Where the current Railcraft API is still a clean, unchanged surface (carts, events,
 * items, fuel), real Railcraft classes are used directly instead - see
 * train.common.core.plugins.PluginRailcraft and the mods.railcraft.api.carts/items/
 * fuel/events imports elsewhere in train.common.
 */
package train.common.tracks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public interface ITrackInstance {

	TrackSpec getTrackSpec();

	List<ItemStack> getDrops(int fortune);

	int getBasicRailMetadata(EntityMinecart cart);

	void onMinecartPass(EntityMinecart cart);

	boolean canPropagatePowerTo(ITrackInstance track);

	TextureAtlasSprite getIcon();

	void writeToNBT(NBTTagCompound data);

	void readFromNBT(NBTTagCompound data);

	boolean canUpdate();

	void updateEntity();

	boolean blockActivated(EntityPlayer player);

	void onBlockPlaced();

	void onBlockRemoved();

	void onBlockPlacedBy(EntityLivingBase entity);

	void onNeighborBlockChange(Block blockChanged);

	void setTile(TileEntity tile);

	TileEntity getTile();

	int getX();

	int getY();

	int getZ();

	float getHardness();

	float getExplosionResistance(double srcX, double srcY, double srcZ, Entity exploder);

	boolean isFlexibleRail();

	boolean canMakeSlopes();

	float getRailMaxSpeed(EntityMinecart cart);

	/**
	 * Optional supplementary sync channel some track instances use in addition to
	 * writeToNBT/readFromNBT. TileEntityTraincraftTrack does not call these itself
	 * (it syncs via NBT), they exist purely so subclasses that want a lightweight
	 * binary format for frequently-changing fields still have somewhere to put it.
	 */
	void writePacketData(DataOutputStream data) throws IOException;

	void readPacketData(DataInputStream data) throws IOException;
}
