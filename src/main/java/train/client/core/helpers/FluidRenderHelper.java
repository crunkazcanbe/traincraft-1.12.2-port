package train.client.core.helpers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidRenderHelper {
	private static final ResourceLocation BLOCK_TEXTURE = TextureMap.LOCATION_BLOCKS_TEXTURE;

	public static TextureAtlasSprite getFluidTexture(Fluid fluid, boolean flowing) {
		if (fluid == null) {
			return null;
		}
		try {
			ResourceLocation texLoc = flowing ? fluid.getFlowing() : fluid.getStill();
			if (texLoc != null) {
				TextureMap blockMap = (TextureMap) Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
				TextureAtlasSprite icon = blockMap.getAtlasSprite(texLoc.toString());
				return icon;
			}
		} catch (Exception e) {
			// fallthrough to null
		}
		return null;
	}

	public static ResourceLocation getFluidSheet(Fluid liquid) {
		return BLOCK_TEXTURE;
	}
}
