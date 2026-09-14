/**
 * @author Spitfire4466
 */
package train.common.blocks.tracks;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import train.common.library.Tracks;

public class BlockSteelTrack extends TrackBaseTraincraft {
	public BlockSteelTrack() {
		this.speedController = SpeedControllerSteel.getInstance();
	}

	@Override
	public Tracks getTrackType() {
		return Tracks.STEEL_TRACK;
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