/**
 * @author Spitfire4466
 */
package train.common.blocks.tracks;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import train.common.library.Tracks;

public class BlockSnowyCopperTrack extends TrackBaseTraincraft {
	public BlockSnowyCopperTrack() {
		this.speedController = SpeedControllerCopper.getInstance();
	}

	@Override
	public Tracks getTrackType() {
		return Tracks.SNOWY_COPPER_TRACK;
	}
	@Override
	public TextureAtlasSprite getIcon() {
		int meta = this.tileEntity.getBlockMetadata();
		if (meta >= 6) {
			return getIcon(1);
		}
		return getIcon(0);
	}
	@Override
	public boolean isFlexibleRail() {
		return true;
	}
}