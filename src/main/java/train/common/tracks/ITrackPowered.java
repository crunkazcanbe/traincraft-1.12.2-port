package train.common.tracks;

/**
 * Implementing this interface will allow your track to be powered via Redstone.
 */
public interface ITrackPowered extends ITrackInstance {

	boolean isPowered();

	void setPowered(boolean powered);

	/**
	 * The distance that a redstone signal will be passed along from track to track.
	 */
	int getPowerPropagation();
}
