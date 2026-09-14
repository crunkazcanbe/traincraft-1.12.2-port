package train.common.tracks;

/**
 * Implemented by the TileEntity that hosts a ITrackInstance.
 * See train.common.tile.TileEntityTraincraftTrack.
 */
public interface ITrackTile {

	ITrackInstance getTrackInstance();

	void sendUpdateToClient();
}
