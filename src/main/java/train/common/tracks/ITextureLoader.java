package train.common.tracks;

import net.minecraftforge.client.event.TextureStitchEvent;

public interface ITextureLoader {

	void registerIcons(TextureStitchEvent.Pre event);
}
