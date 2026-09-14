package train.common.tracks;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public interface ITrackItemIconProvider {

	TextureAtlasSprite getTrackItemIcon(TrackSpec spec);
}
