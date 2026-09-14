package train.common.blocks.tracks;

import train.common.tracks.ITextureLoader;
import train.common.tracks.ITrackItemIconProvider;
import train.common.tracks.TrackSpec;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.client.event.TextureStitchEvent;
import train.common.library.Tracks;

import java.util.HashMap;
import java.util.Map;

public class TrackTextureLoader implements ITextureLoader, ITrackItemIconProvider {
	public static final TrackTextureLoader INSTANCE = new TrackTextureLoader();
	public final Map textures = new HashMap();
	//public final Map itemIcon = new HashMap();
	@Override
	public void registerIcons(TextureStitchEvent.Pre event) {
		TextureMap iconRegister = event.getMap();
		for (Tracks track : Tracks.values()) {
			TextureAtlasSprite[] icons = SheetTextureStitched.unstitchIcons(iconRegister, track.getTextureTag(), track.getNumIcons());
			this.textures.put(track.getTrackSpec(), icons);
			//this.itemIcon.put(track.getTrackSpec(), icons[track.getNumIcons()]);
		}
	}
	@Override
	public TextureAtlasSprite getTrackItemIcon(TrackSpec spec) {
		return getTrackIcons(spec)[0];
	}

	public TextureAtlasSprite[] getTrackIcons(TrackSpec spec) {
		return (TextureAtlasSprite[]) this.textures.get(spec);
	}

}
