package train.client.render.models.blocks;

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
 * Diagonal straight track. All eight diagonal types (small/medium/long/very-long,
 * plain and embedded) used to render NOTHING: RenderTCRail had no case for them and
 * its fallback explicitly skips straights and diagonals, while tcrail's blockstate
 * points at the empty model tc_empty. The OBJs shipped in this repo all along.
 *
 * Geometry and the per-facing transforms are taken from Traincraft 1.7.10-CE's
 * ModelSmallDiagonalStraightTCTrack / ModelMediumDiagonalStraightTCTrack, which the
 * 1.12.2 port dropped. As there, "embedded" is the SAME geometry with the embedded
 * texture, so both share one renderer.
 *
 * Facing is the placement metadata l (4..7) set by ItemTCRail#smallDiagonalStraight /
 * #diagonalStraight, where l picks the dx/dz sign pair the run steps along.
 */
@SideOnly(Side.CLIENT)
public class ModelDiagonalStraightTCTrack extends ModelBase {

	private static final IModelCustom SMALL =
			AdvancedModelLoader.loadModel(new ResourceLocation(Info.modelPrefix + "track_straight_diagonal.obj"));
	private static final IModelCustom MEDIUM =
			AdvancedModelLoader.loadModel(new ResourceLocation(Info.modelPrefix + "track_straight_diagonal_medium.obj"));

	/**
	 * @param size "small" for the 1x1 diagonal, "medium" for the 3x3 one. Long (1x6)
	 *             and very long (1x12) diagonals lay a tcrail node every 3 diagonal
	 *             steps (ItemTCRail#diagonalStraight walks i += 3 and gives each node
	 *             railLength 3), so drawing the 3x3 model at every node tiles the whole
	 *             run with no gaps and needs no extra assets.
	 * @param typeName the TrackTypes constant name, used only to pick the texture.
	 */
	public void render(String size, String typeName, TileTCRail tcRail, double x, double y, double z) {
		Tessellator.bindTexture(new ResourceLocation(Info.resourceLocation, Info.modelTexPrefix
				+ (typeName.contains("EMBEDDED") ? "track_embedded.png" : "track_normal.png")));
		GL11.glColor4f(1, 1, 1, 1);

		// RenderTCRail has already translated to the block CENTRE, but these OBJs are
		// authored from the block corner (they span -0.3 .. +1.3 / +3.3), which is what
		// the 1.7.10 transforms below assume. Step back to the corner first.
		GL11.glTranslatef(-0.5f, 0.0f, -0.5f);

		int facing = tcRail.getFacing();
		if ("small".equals(size)) {
			// The 1x1 diagonal is symmetric about its own axis, so 4/6 and 5/7 pair up.
			if (facing == 4 || facing == 6) {
				GL11.glTranslatef(0.0f, 0.0f, 1.0f);
				GL11.glRotatef(90, 0, 1, 0);
			}
			SMALL.renderAll();
			return;
		}

		switch (facing) {
			case 4: {
				GL11.glTranslatef(1.0f, 0.0f, 0.0f);
				GL11.glRotatef(-90, 0, 1, 0);
				break;
			}
			case 5: {
				GL11.glTranslatef(1.0f, 0.0f, 1.0f);
				GL11.glRotatef(180, 0, 1, 0);
				break;
			}
			case 6: {
				GL11.glTranslatef(0.0f, 0.0f, 1.0f);
				GL11.glRotatef(90, 0, 1, 0);
				break;
			}
			default:
				break; // 7: identity
		}
		MEDIUM.renderAll();
	}
}
