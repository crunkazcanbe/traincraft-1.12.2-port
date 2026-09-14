package train.client.render.models.blocks;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.model.ModelBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import tmt.Tessellator;
import train.common.library.Info;
import train.common.tile.TileTCRail;

/**
 * Generic renderer for the new (1.17-ported) CE track types. GLM shipped the OBJ
 * models (assets/tc/models/track_*.obj) but never wired the track TYPES to them —
 * RenderTCRail's fallback substituted the wrong old model. This loads the CORRECT
 * OBJ per type on demand (cached), binds the real track texture, and rotates by the
 * tile's facing. Mappings live in {@link RenderCEDispatch} and grow one batch at a time.
 */
@SideOnly(Side.CLIENT)
public class ModelCETrack extends ModelBase {
    private static final Map<String, IModelCustom> CACHE = new HashMap<>();
    private static final ResourceLocation TRACK_TEX =
            new ResourceLocation(Info.resourceLocation, Info.modelTexPrefix + "track_normal.png");

    /** Load (and cache) an OBJ by bare name, e.g. "track_diamond_crossing". */
    private static IModelCustom model(String objName) {
        IModelCustom m = CACHE.get(objName);
        if (m == null) {
            m = AdvancedModelLoader.loadModel(new ResourceLocation(Info.modelPrefix + objName + ".obj"));
            CACHE.put(objName, m);
        }
        return m;
    }

    /**
     * Render a crossing-style (single-block, no rotation) CE track: just bind the
     * texture and draw the OBJ, same shape as the working two-ways crossing path.
     */
    public void renderFlat(String objName, double x, double y, double z) {
        Tessellator.bindTexture(TRACK_TEX);
        GL11.glColor4f(1, 1, 1, 1);
        model(objName).renderAll();
    }

    /**
     * Render a directional CE track (curves/slopes/switches): bind texture, rotate to
     * the tile's facing, then draw. Rotation mirrors the existing turn models.
     */
    public void renderFacing(String objName, TileTCRail tcRail, double x, double y, double z) {
        Tessellator.bindTexture(TRACK_TEX);
        GL11.glColor4f(1, 1, 1, 1);
        switch (tcRail.getFacing()) {
            case 0: { GL11.glRotatef(-90, 0, 1, 0); break; }
            case 1: { GL11.glRotatef(180, 0, 1, 0); break; }
            case 2: { GL11.glRotatef(90, 0, 1, 0); break; }
        }
        model(objName).renderAll();
    }
}
