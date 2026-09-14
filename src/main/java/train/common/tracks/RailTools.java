package train.common.tracks;

import mods.railcraft.api.items.ITrackItem;
import net.minecraft.block.BlockRailBase;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import train.common.entity.rollingStock.EntityTracksBuilder;
import train.common.items.ItemTCRail;

import java.util.HashSet;
import java.util.Set;

/**
 * Traincraft-owned replacement for the old vendored mods.railcraft.api.tracks.RailTools.
 * Current Railcraft (1.12.2, 12.1.0-beta-8) renamed this to TrackToolsAPI and rebuilt it
 * around the TrackKit system - see train.common.tracks package-info for why we didn't
 * follow that rename here.
 */
public abstract class RailTools {

	public static boolean isRailBlockAt(IBlockAccess world, int x, int y, int z) {
		return world.getBlockState(new BlockPos(x, y, z)).getBlock() instanceof BlockRailBase;
	}

	public static boolean placeRailAt(EntityTracksBuilder builder, ItemStack stack, World world, int i, int j, int k) {
		return false;
	}

	public static boolean isTrackItem(ItemStack stack) {
		return stack != null && !stack.isEmpty() && (stack.getItem() instanceof ITrackItem || stack.getItem() instanceof ItemTCRail);
	}

	public static boolean isCartLockedDown(EntityMinecart cart) {
		int x = MathHelper.floor(cart.posX);
		int y = MathHelper.floor(cart.posY);
		int z = MathHelper.floor(cart.posZ);

		if (isRailBlockAt(cart.world, x, y - 1, z))
			y--;

		TileEntity tile = cart.world.getTileEntity(new BlockPos(x, y, z));
		if (tile instanceof ITrackTile) {
			ITrackInstance track = ((ITrackTile) tile).getTrackInstance();
			return track instanceof ITrackLockdown && ((ITrackLockdown) track).isCartLockedDown(cart);
		}
		return false;
	}

	public static int countAdjecentTracks(World world, int x, int y, int z) {
		int i = 0;
		if (isTrackFuzzyAt(world, x, y, z - 1)) ++i;
		if (isTrackFuzzyAt(world, x, y, z + 1)) ++i;
		if (isTrackFuzzyAt(world, x - 1, y, z)) ++i;
		if (isTrackFuzzyAt(world, x + 1, y, z)) ++i;
		return i;
	}

	public static boolean isTrackFuzzyAt(World world, int x, int y, int z) {
		return isRailBlockAt(world, x, y, z) || isRailBlockAt(world, x, y + 1, z) || isRailBlockAt(world, x, y - 1, z);
	}

	public static Set<ITrackTile> getAdjecentTrackTiles(World world, int x, int y, int z) {
		Set<ITrackTile> tracks = new HashSet<>();
		addTrackTile(tracks, getTrackFuzzyAt(world, x, y, z - 1));
		addTrackTile(tracks, getTrackFuzzyAt(world, x, y, z + 1));
		addTrackTile(tracks, getTrackFuzzyAt(world, x - 1, y, z));
		addTrackTile(tracks, getTrackFuzzyAt(world, x + 1, y, z));
		return tracks;
	}

	private static void addTrackTile(Set<ITrackTile> set, ITrackTile tile) {
		if (tile != null) set.add(tile);
	}

	public static ITrackTile getTrackFuzzyAt(World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		if (tile instanceof ITrackTile) return (ITrackTile) tile;
		tile = world.getTileEntity(new BlockPos(x, y + 1, z));
		if (tile instanceof ITrackTile) return (ITrackTile) tile;
		tile = world.getTileEntity(new BlockPos(x, y - 1, z));
		if (tile instanceof ITrackTile) return (ITrackTile) tile;
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getTrackObjectAt(World world, int x, int y, int z, Class<T> type) {
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		if (tile == null) return null;
		if (type.isInstance(tile)) return (T) tile;
		if (tile instanceof ITrackTile) {
			ITrackInstance track = ((ITrackTile) tile).getTrackInstance();
			if (type.isInstance(track)) return (T) track;
		}
		return null;
	}
}
