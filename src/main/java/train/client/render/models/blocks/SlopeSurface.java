package train.client.render.models.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import train.common.library.Info;
import train.common.tile.TileTCRail;

/**
 * Issue #15/#16: which ground texture a slope's supports use. "snow" = Traincraft's snowy gravel (the snowy slopes
 * used plain gravel); "dynamic:<sprite>" = the texture of the block under the slope's first block (dynamic slopes
 * were always drawn as TC ballast).
 */
public final class SlopeSurface {
    private SlopeSurface() {}

    /** the surface key for a track type name (SLOPE_SNOW_GRAVEL, LARGE_SLOPE_DYNAMIC, ...) */
    public static String of(String typeName, TileTCRail tile) {
        if (typeName.contains("SNOW")) return "snow";
        if (typeName.contains("DYNAMIC")) return dynamic(tile);
        if (typeName.contains("WOOD")) return "wood";
        if (typeName.contains("GRAVEL")) return "gravel";
        return "ballast";
    }

    public static String dynamic(TileTCRail tile) {
        try {
            IBlockState below = tile.getWorld().getBlockState(tile.getPos().down());
            if (below.getBlock().isAir(below, tile.getWorld(), tile.getPos().down())) return "ballast";
            TextureAtlasSprite sp = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(below);
            String n = sp == null ? null : sp.getIconName();
            if (n == null || n.contains("missingno")) return "ballast";
            return "dynamic:" + n;
        } catch (Throwable t) {
            return "ballast";
        }
    }

    /** true when the model's own code doesn't handle this key */
    public static boolean isExtra(String type) {
        return type.equals("snow") || type.startsWith("dynamic:");
    }

    public static ResourceLocation texture(String type) {
        if (type.equals("snow")) return new ResourceLocation(Info.resourceLocation, "textures/blocks/snowgravel.png");
        ResourceLocation sprite = new ResourceLocation(type.substring("dynamic:".length()));
        return new ResourceLocation(sprite.getNamespace(), "textures/" + sprite.getPath() + ".png");
    }
}
