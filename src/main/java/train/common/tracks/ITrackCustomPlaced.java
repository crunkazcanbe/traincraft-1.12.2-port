package train.common.tracks;

import net.minecraft.world.World;

/**
 * Have your ITrackInstance implement this to override normal track placement.
 */
public interface ITrackCustomPlaced extends ITrackInstance {

	/**
	 * Warning: this is called before the TileEntity is set.
	 *
	 * @return true if the rail can be placed at the specified location, false to prevent placement
	 */
	boolean canPlaceRailAt(World world, int i, int j, int k);
}
