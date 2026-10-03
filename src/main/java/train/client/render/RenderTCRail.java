package train.client.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;
import train.client.render.models.blocks.*;
import train.common.tile.TileTCRail;

public class RenderTCRail extends TileEntitySpecialRenderer {

	private static final ModelSmallStraightTCTrack modelSmallStraight = new ModelSmallStraightTCTrack();
	private static final ModelMediumStraightTCTrack modelMediumStraight = new ModelMediumStraightTCTrack();
	private static final ModelRightTurnTCTrack modelRightTurn = new ModelRightTurnTCTrack();
	private static final ModelLeftTurnTCTrack modelLeftTurn = new ModelLeftTurnTCTrack();
	private static final ModelRightSwitchTCTrack modelRightSwitchTurn = new ModelRightSwitchTCTrack();
	private static final ModelLeftSwitchTCTrack modelLeftSwitchTurn = new ModelLeftSwitchTCTrack();
	private static final ModelTwoWaysCrossingTCTrack modelTwoWaysCrossing = new ModelTwoWaysCrossingTCTrack();
	private static final ModelSlopeTCTrack modelSlope = new ModelSlopeTCTrack();
	private static final ModelLargeSlopeTCTrack modelLargeSlope = new ModelLargeSlopeTCTrack();
	private static final ModelVeryLargeSlopeTCTrack	modelVeryLargeSlope = new ModelVeryLargeSlopeTCTrack();
	private static final ModelDiagonalStraightTCTrack modelDiagonal = new ModelDiagonalStraightTCTrack();

	/** Generic renderer for new (1.17-ported) CE track types, using their REAL OBJ models. */
	private static final ModelCETrack modelCE = new ModelCETrack();
	/**
	 * BATCH PORT MAP: new track-type name -> its real OBJ model (assets/tc/models/<obj>.obj),
	 * with a rendering style. Grows one batch at a time; anything not here still hits the old
	 * approximate renderFallback. Style "flat" = single-block no rotation (crossings).
	 * Batch 1 (2026-07-07): diamond / four-ways / diagonal crossings.
	 */
	private static final java.util.Map<String, String> CE_FLAT = new java.util.HashMap<String, String>();
	static {
		CE_FLAT.put("DIAMOND_CROSSING",           "track_diamond_crossing");
		CE_FLAT.put("RIGHT_DIAMOND_CROSSING",     "track_diamond_crossing");
		CE_FLAT.put("LEFT_DIAMOND_CROSSING",      "track_diamond_crossing_l");
		CE_FLAT.put("DOUBLE_DIAMOND_CROSSING",    "track_double_diamond_crossing");
		CE_FLAT.put("FOUR_WAYS_CROSSING",         "track_diagonal_fourways_crossing");
		CE_FLAT.put("DIAGONAL_TWO_WAYS_CROSSING", "track_diagonal_twoways_crossing");
		// Embedded crossings share the same crossing OBJs (asphalt bed is separate).
		CE_FLAT.put("EMBEDDED_DIAMOND_CROSSING",           "track_diamond_crossing");
		CE_FLAT.put("EMBEDDED_RIGHT_DIAMOND_CROSSING",     "track_diamond_crossing");
		CE_FLAT.put("EMBEDDED_LEFT_DIAMOND_CROSSING",      "track_diamond_crossing_l");
		CE_FLAT.put("EMBEDDED_DOUBLE_DIAMOND_CROSSING",    "track_double_diamond_crossing");
		CE_FLAT.put("EMBEDDED_FOUR_WAYS_CROSSING",         "track_diagonal_fourways_crossing");
		CE_FLAT.put("EMBEDDED_DIAGONAL_TWO_WAYS_CROSSING", "track_diagonal_twoways_crossing");
	}

	public RenderTCRail() {
		
	}

	@Override
	public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		renderTileEntityAt(te, x, y, z, partialTicks);
	}

	public void renderTileEntityAt(TileEntity var1, double x, double y, double z, float var8) {
		if(var1 instanceof TileTCRail){
			TileTCRail railTile = (TileTCRail) var1;

			// Push a blank matrix onto the stack
			GL11.glPushMatrix();


			if (railTile.hasModel && railTile.getTrackType() != null) {
				// Move the object into the correct position on the block (because the OBJ's origin is the center of the object)
				GL11.glTranslated( x + 0.5,  y,  z + 0.5);
				switch (railTile.getTrackType()) {
					// LONG (1x6) and VERY_LONG (1x12) straights drew NOTHING: this case was a
					// bare `break`, VERY_LONG was not handled at all, and tcrail's blockstate
					// points at the empty model tc_empty -- so neither the TESR nor the block
					// model produced any geometry and the track was invisible on the ground
					// while still being solid and rideable.
					//
					// A long straight lays its tcrail nodes 3 blocks apart (a 1x6 gives nodes at
					// +0 and +3), which is exactly the span of the existing 1x3 medium model, so
					// drawing that at every node tiles the run with no gaps and no new assets.
					case LONG_STRAIGHT:
					case VERY_LONG_STRAIGHT: {
						modelMediumStraight.render(railTile, x, y, z);
						break;
					}
					// Same invisible-track bug as LONG_STRAIGHT above, for the other 12
					// cases of it: every DIAGONAL straight and every EMBEDDED straight had
					// no case here, and renderFallback deliberately skips straights and
					// diagonals, so they were solid and rideable but drew nothing at all.
					// Geometry/transforms restored from Traincraft 1.7.10-CE; "embedded" is
					// the same bed with track_embedded.png.
					case SMALL_DIAGONAL_STRAIGHT:
					case EMBEDDED_SMALL_DIAGONAL_STRAIGHT: {
						modelDiagonal.render("small", railTile.getTrackType().name(), railTile, x, y, z);
						break;
					}
					// A long/very-long diagonal lays a node every 3 diagonal steps, which is
					// exactly the 3x3 model's span, so tiling it fills the run.
					case MEDIUM_DIAGONAL_STRAIGHT:
					case LONG_DIAGONAL_STRAIGHT:
					case VERY_LONG_DIAGONAL_STRAIGHT:
					case EMBEDDED_MEDIUM_DIAGONAL_STRAIGHT:
					case EMBEDDED_LONG_DIAGONAL_STRAIGHT:
					case EMBEDDED_VERY_LONG_DIAGONAL_STRAIGHT: {
						modelDiagonal.render("medium", railTile.getTrackType().name(), railTile, x, y, z);
						break;
					}
					case EMBEDDED_SMALL_STRAIGHT: {
						modelSmallStraight.render("straight", railTile, x, y, z, "track_embedded.png");
						break;
					}
					case EMBEDDED_MEDIUM_STRAIGHT:
					case EMBEDDED_LONG_STRAIGHT:
					case EMBEDDED_VERY_LONG_STRAIGHT: {
						modelMediumStraight.render(railTile, x, y, z, "track_embedded.png");
						break;
					}
					case MEDIUM_TURN:
					case MEDIUM_RIGHT_TURN: {
						modelRightTurn.render("medium", railTile, x, y, z);
						break;
					}
					case MEDIUM_LEFT_TURN: {
						modelLeftTurn.render("medium", railTile, x, y, z);
						break;
					}
					case MEDIUM_SWITCH:
					case MEDIUM_RIGHT_SWITCH: {
						modelRightSwitchTurn.render("medium", railTile, x, y, z);
						break;
					}
					case MEDIUM_LEFT_SWITCH: {
						modelLeftSwitchTurn.render("medium", railTile, x, y, z);
						break;
					}
					case LARGE_SWITCH:
					case LARGE_RIGHT_SWITCH: {
						modelRightSwitchTurn.render("large_90", railTile, x, y, z);
						break;
					}
					case LARGE_LEFT_SWITCH: {
						modelLeftSwitchTurn.render("large_90", railTile, x, y, z);
						break;
					}
					case MEDIUM_PARALLEL_SWITCH:
					case MEDIUM_RIGHT_PARALLEL_SWITCH: {
						modelRightSwitchTurn.render("medium_parallel", railTile, x, y, z);
						break;
					}
					case MEDIUM_LEFT_PARALLEL_SWITCH: {
						modelLeftSwitchTurn.render("medium_parallel", railTile, x, y, z);
						break;
					}
					case LARGE_TURN:
					case LARGE_RIGHT_TURN: {
						modelRightTurn.render("large", railTile, x, y, z);
						break;
					}
					case LARGE_LEFT_TURN: {
						modelLeftTurn.render("large", railTile, x, y, z);
						break;
					}
					case VERY_LARGE_TURN:
					case VERY_LARGE_RIGHT_TURN: {
						modelRightTurn.render("very_large", railTile, x, y, z);
						break;
					}
					case VERY_LARGE_LEFT_TURN: {
						modelLeftTurn.render("very_large", railTile, x, y, z);
						break;
					}
					case MEDIUM_STRAIGHT: {
						modelMediumStraight.render(railTile, x, y, z);
						break;
					}
					case SMALL_STRAIGHT: {
						modelSmallStraight.render("straight", railTile, x, y, z);
						break;
					}
					case SMALL_ROAD_CROSSING: {
						modelSmallStraight.render("crossing", railTile, x, y, z);
						break;
					}
					case SMALL_ROAD_CROSSING_1: {
						modelSmallStraight.render("crossing1", railTile, x, y, z);
						break;
					}
					case SMALL_ROAD_CROSSING_2: {
						modelSmallStraight.render("crossing2", railTile, x, y, z);
						break;
					}
					case TWO_WAYS_CROSSING: {
						modelTwoWaysCrossing.render(x, y, z);
						break;
					}
					case SLOPE_WOOD: {
						modelSlope.render("wood", railTile, x, y, z);
						break;
					}
					case SLOPE_GRAVEL: {
						modelSlope.render("gravel", railTile, x, y, z);
						break;
					}
					case SLOPE_BALLAST: {
						modelSlope.render("ballast", railTile, x, y, z);
						break;
					}
					case LARGE_SLOPE_WOOD: {
						modelLargeSlope.render("wood", railTile, x, y, z);
						break;
					}
					case LARGE_SLOPE_GRAVEL: {
						modelLargeSlope.render("gravel", railTile, x, y, z);
						break;
					}
					case LARGE_SLOPE_BALLAST: {
						modelLargeSlope.render("ballast", railTile, x, y, z);
						break;
					}
					case VERY_LARGE_SLOPE_WOOD: {
						modelVeryLargeSlope.render("wood", railTile, x, y, z);
						break;
					}
					case VERY_LARGE_SLOPE_GRAVEL: {
						modelVeryLargeSlope.render("gravel", railTile, x, y, z);
						break;
					}
					case VERY_LARGE_SLOPE_BALLAST: {
						modelVeryLargeSlope.render("ballast", railTile, x, y, z);
						break;
					}
					default: {
						// ponytail: CE track types have no dedicated models in this repo;
						// approximate with the nearest existing model so nothing renders invisible
						renderFallback(railTile.getTrackType().name(), railTile, x, y, z);
						break;
					}
				}

			}
			GL11.glPopMatrix();
		}
	}

	/**
	 * 45-degree turns and parallel curves -> their REAL OBJ. The 1.7.10 mod had
	 * ModelLeft/Right45DegreeTurnTCTrack and ModelLeft/RightParallelCurveTCTrack; the
	 * 1.12.2 port dropped all four, so these 34 track types fell through to the
	 * approximation below and a 3x4 FORTY-FIVE degree curve was drawn as a 3x3 NINETY
	 * degree one -- rails visibly not lining up with the track. The OBJs shipped all along.
	 * Naming is regular, so derive it rather than keep a 34-entry table.
	 * SUPER_LARGE/VERY_LARGE must be tested before LARGE (both contain "LARGE").
	 */
	private static String ceObjFor(String n) {
		if (n.endsWith("_45DEGREE_TURN") && !n.contains("SWITCH")) {
			String size = null;
			if (n.contains("SUPER_LARGE")) size = "super_large";
			else if (n.contains("VERY_LARGE")) size = "very_large";
			else if (n.contains("LARGE")) size = "large";
			else if (n.contains("MEDIUM")) size = "medium";
			if (size == null) return null;
			return "track_curve_45degree_" + size + (n.contains("LEFT") ? "_left" : "");
		}
		if (n.endsWith("_PARALLEL_CURVE") && !n.contains("SWITCH")) {
			String letter = null;
			if (n.contains("LARGE")) letter = "l";
			else if (n.contains("MEDIUM")) letter = "m";
			else if (n.contains("SMALL")) letter = "s";
			if (letter == null) return null;
			return "track_curve_parallel_" + letter + (n.contains("LEFT") ? "_left" : "");
		}
		// ModelLeft/RightCurvedSlopeTCTrack were dropped too, so the 6 curved-slope items
		// hit the SLOPE branch below and drew a STRAIGHT slope on a curve. Note these OBJs
		// use a "_l" left suffix, not "_left".
		if (n.contains("CURVED_SLOPE")) {
			String size = null;
			if (n.contains("SUPER_LARGE")) size = "super_large";
			else if (n.contains("VERY_LARGE")) size = "very_large";
			else if (n.contains("LARGE")) size = "large";
			if (size == null) return null;
			return "track_slope_curve_" + size + (n.contains("LEFT") ? "_l" : "");
		}
		return null;
	}

	private void renderFallback(String n, TileTCRail railTile, double x, double y, double z) {
		// BATCH PORT: if this new CE type has a real OBJ mapped, draw that instead of approximating.
		String flat = CE_FLAT.get(n);
		if (flat != null) {
			modelCE.renderFlat(flat, x, y, z);
			return;
		}
		String facing = ceObjFor(n);
		if (facing != null) {
			modelCE.renderFacing(facing, n, railTile, x, y, z);
			return;
		}
		String size = n.contains("SUPER_LARGE") ? "super_large" : n.contains("29X29") ? "29x" : n.contains("32X32") ? "32x"
				: n.contains("TURN_1X1") ? "1x" : n.contains("VERY_LARGE") ? "very_large"
				: n.contains("LARGE") ? "large" : "medium";
		boolean left = n.contains("LEFT");
		if (n.contains("CROSSING")) {
			modelTwoWaysCrossing.render(x, y, z);
		} else if (n.contains("SLOPE")) {
			String surface = n.contains("WOOD") ? "wood" : n.contains("GRAVEL") ? "gravel" : "ballast";
			if (n.contains("SUPER_LARGE") || n.contains("VERY_LARGE")) modelVeryLargeSlope.render(surface, railTile, x, y, z);
			else if (n.contains("LARGE")) modelLargeSlope.render(surface, railTile, x, y, z);
			else modelSlope.render(surface, railTile, x, y, z);
		} else if (n.contains("PARALLEL_SWITCH")) {
			if (left) modelLeftSwitchTurn.render("medium_parallel", railTile, x, y, z);
			else modelRightSwitchTurn.render("medium_parallel", railTile, x, y, z);
		} else if (n.contains("SWITCH")) {
			String sw = size.equals("medium") ? "medium" : "large_90";   // (super_large/29x/32x/1x are turns only)
			if (left) modelLeftSwitchTurn.render(sw, railTile, x, y, z);
			else modelRightSwitchTurn.render(sw, railTile, x, y, z);
		} else if (n.contains("TURN") || n.contains("CURVE")) {
			if (left) modelLeftTurn.render(size, railTile, x, y, z);
			else modelRightTurn.render(size, railTile, x, y, z);
		}
		// straights/diagonals fall through: rendered by the rail block itself, like LONG_STRAIGHT
	}
}
