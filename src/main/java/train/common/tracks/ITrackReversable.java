package train.common.tracks;

/**
 * Implementing this interface will allow your track to be direction specific.
 * If you inherit from TrackInstanceBase it will automatically be reversable via
 * the crowbar.
 */
public interface ITrackReversable extends ITrackInstance {

	boolean isReversed();

	void setReversed(boolean reversed);
}
