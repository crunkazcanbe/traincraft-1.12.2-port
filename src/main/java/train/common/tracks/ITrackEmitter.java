package train.common.tracks;

/**
 * Tracks that can emit a redstone signal should implement this interface.
 * For example a detector track.
 */
public interface ITrackEmitter extends ITrackInstance {

	/**
	 * Return the redstone output of the track.
	 */
	int getPowerOutput();
}
