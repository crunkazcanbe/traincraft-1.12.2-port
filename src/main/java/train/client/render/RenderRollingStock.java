package train.client.render;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.BlockRailBase;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import tmt.Tessellator;
import train.common.api.EntityRollingStock;
import train.common.api.Freight;
import train.common.api.Locomotive;
import train.common.core.util.TraincraftUtil;
import train.common.entity.rollingStock.EntityTracksBuilder;
import train.common.library.Info;

import java.util.ArrayList;
import java.util.Random;

@SideOnly(Side.CLIENT)
public class RenderRollingStock extends Render<EntityRollingStock> {
	private static Random random = new Random();
	private static final java.util.Set<Integer> LOG_ONCE = new java.util.HashSet<Integer>();
	private static final java.util.Map<Integer, Float> LAST_ROT = new java.util.HashMap<Integer, Float>();
	// ponytail: vertical lift for bogie locos so their body clears the rail (was ~1/4 buried).
	private static final float BOGIE_LIFT = 0.85F;
	// ponytail: TUNE KNOB. Freight/cargo wagons sit buried in the ground (their model
	// origin is low and, unlike bogie locos, they got no lift). Raise the body to sit on
	// the rail. Increase to raise, decrease to lower.
	private static final float FREIGHT_LIFT = 0.6F;

	// LIVE TUNING: reads ~/.config/dogpound/train-render.conf every ~1s (key=value floats)
	// so rotation + lift can be dialed in without rebuilding. Keys: locoA locoB wagA wagB
	// bogieLift freightLift. Rotation = A + B*yaw (yaw = track tangent). Defaults below.
	private static long confLoadTime = 0L;
	private static final java.util.Map<String, Float> CONF = new java.util.HashMap<String, Float>();
	private static float conf(String key, float def) {
		long now = System.currentTimeMillis();
		if (now - confLoadTime > 1000L) {
			confLoadTime = now;
			try {
				java.io.File f = new java.io.File(System.getProperty("user.home"), ".config/dogpound/train-render.conf");
				if (f.exists()) {
					CONF.clear();
					for (String line : java.nio.file.Files.readAllLines(f.toPath())) {
						line = line.trim();
						if (line.isEmpty() || line.startsWith("#") || !line.contains("=")) continue;
						String[] kv = line.split("=", 2);
						try { CONF.put(kv[0].trim(), Float.parseFloat(kv[1].trim())); } catch (NumberFormatException ignored) {}
					}
				}
			} catch (Exception ignored) {}
		}
		Float v = CONF.get(key);
		return v != null ? v : def;
	}

	private static boolean isDerailed(EntityRollingStock cart) {
		int x = MathHelper.floor(cart.posX), y = MathHelper.floor(cart.posY), z = MathHelper.floor(cart.posZ);
		for (int dy = -1; dy <= 0; dy++) {
			net.minecraft.block.Block b = cart.world.getBlockState(new net.minecraft.util.math.BlockPos(x, y + dy, z)).getBlock();
			if (b instanceof BlockRailBase || b == train.common.library.BlockIDs.tcRail.block || b == train.common.library.BlockIDs.tcRailGag.block) return false;
		}
		return true;
	}

	public RenderRollingStock(net.minecraft.client.renderer.entity.RenderManager rm) {
		super(rm);
		this.shadowSize = 0.5F;
	}

	/**
	 * Renders the Minecart.
	 */
	public static void renderTheMinecart(EntityRollingStock cart, double x, double y, double z, float yaw, float time) {
		GL11.glPushMatrix();
		long var10 = cart.getEntityId() * 493286711L;
		var10 = var10 * var10 * 4392167121L + var10 * 98761L;
		float var12 = (((var10 >> 16 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
		float var13 = (((var10 >> 20 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
		float var14 = (((var10 >> 24 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
		GL11.glTranslatef(var12, var13, var14);
		double var15 = cart.lastTickPosX + (cart.posX - cart.lastTickPosX) * time;
		double var17 = cart.lastTickPosY + (cart.posY - cart.lastTickPosY) * time;
		double var19 = cart.lastTickPosZ + (cart.posZ - cart.lastTickPosZ) * time;
		float pitch = cart.prevRotationPitch + (cart.rotationPitch - cart.prevRotationPitch) * time;
		Vec3d renderYVect = cart.yVector(var15, var17, var19);//only on TC rails
		// PORT FIX: this was stubbed to null, so a train sitting on a rail never took its facing
		// from the track and rendered sideways. renderY() gives the rail position at an offset
		// along the track; sampling +/-0.3 gives the rail's direction -> the train's yaw.
		Vec3d var23 = cart.renderY(var15, var17, var19, 0.0D);
		if (var23 != null) {
			Vec3d var25 = cart.renderY(var15, var17, var19, 0.3D);
			Vec3d var26 = cart.renderY(var15, var17, var19, -0.3D);

			if (var25 == null) {
				var25 = var23;
			}

			if (var26 == null) {
				var26 = var23;
			}

			x += var23.x - var15;
			y += (var25.y + var26.y) / 2.0D - var17;
			z += var23.z - var19;
			Vec3d var27 = var26.add(-var25.x, -var25.y, -var25.z);

			if (var27.length() != 0.0D) {
				var27 = var27.normalize();
				yaw = TraincraftUtil.atan2degreesf(var27.z, var27.x);
				pitch = (float) (Math.atan(var27.y) * 73.0D);
			}

		}else if (renderYVect != null) {//only on TC rails
			Vec3d var25 = cart.renderY(var15, var17, var19, 0.30000001192092896D);
			Vec3d var26 = cart.renderY(var15, var17, var19, -0.30000001192092896D);

			if (var25 == null) {
				var25 = renderYVect;
			}

			if (var26 == null) {
				var26 = renderYVect;
			}
			y += (var25.y + var26.y) / 2.0D - var17;
		}

		yaw %= 360.0F;
		if (yaw < 0.0F) {
			yaw += 360.0F;
		}
		yaw += 360.0F;

		float serverYaw = (cart.rotationYaw + 180) % 360;
		if (serverYaw < 0.0F) {
			serverYaw += 360.0F;
		}
		serverYaw += 360.0F;
		// This decides "is the train running backwards along the track" and flips it if so.
		// A raw subtraction is wrap-blind: yaw=361 vs serverYaw=719 is 2 degrees apart, but reads
		// as 358 and spuriously flips the train nose-for-tail. wrapDegrees folds it to [-180,180).
		if (Math.abs(MathHelper.wrapDegrees(yaw - serverYaw)) > 90.0F) {
			yaw += 180.0F;
			pitch = -pitch;
		}
		/*if (var23 == null && Math.abs(yaw - serverYaw) < 90.0D) {
			pitch = -pitch;
		}*/
		//System.out.println(Math.abs(yaw - serverYaw));
		//System.out.println("yaw after "+yaw+" server yaw after "+serverYaw);

		GL11.glTranslatef((float) x, (float) y, (float) z);
		int i = MathHelper.floor(cart.posX);
		int j = MathHelper.floor(cart.posY);
		int k = MathHelper.floor(cart.posZ);

		// NOTE: func_150049_b_ = isRailBlockAt
		if (cart.world != null && (BlockRailBase.isRailBlock(cart.world.getBlockState(new net.minecraft.util.math.BlockPos(i, j, k)))
				|| BlockRailBase.isRailBlock(cart.world.getBlockState(new net.minecraft.util.math.BlockPos(i, j - 1, k))))) {
			cart.setMountedYOffset(-0.55);
		} else if (cart.posYFromServer != 0) {
			cart.setMountedYOffset(-0.5);
			GL11.glTranslatef(0f, -0.30f, 0f);
		}
		if(cart.world != null && cart.world.getBlockState(new net.minecraft.util.math.BlockPos(i,j,k)).getBlock().getClass().getName().equals("ebf.tim.blocks.rails.BlockRailCore")){
			GL11.glTranslatef(0f, 0.15f, 0f);
		}
		// RAIL-DIRECTION source: Traincraft's BlockTCRail (NOT a vanilla rail) stores its
		// placement facing (0-3) in its TileTCRail. facing*90 = the track's yaw, which finally
		// differs between N-S (0/180) and E-W (90/270). Client-safe; falls back to rotationYaw.
		float railYaw = cart.rotationYaw;
		String railDbg = "norail";
		try {
			net.minecraft.tileentity.TileEntity rte = cart.world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k));
			if (!(rte instanceof train.common.tile.TileTCRail || rte instanceof train.common.tile.TileTCRailGag)) rte = cart.world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j - 1, k));
			// Long track pieces are one TileTCRail plus filler "gag" blocks that don't carry the
			// facing. Resolve a gag to its origin rail, or the train fell back to rotationYaw (which
			// follows the direction of MOTION) every time its centre crossed a filler block --
			// that is what spun the train nose-for-tail when it started, stopped or reversed.
			if (rte instanceof train.common.tile.TileTCRailGag) {
				train.common.tile.TileTCRailGag gag = (train.common.tile.TileTCRailGag) rte;
				rte = cart.world.getTileEntity(new net.minecraft.util.math.BlockPos(gag.originX, gag.originY, gag.originZ));
			}
			if (rte instanceof train.common.tile.TileTCRail) {
				int f = ((train.common.tile.TileTCRail) rte).getFacing();
				railYaw = f * 90.0F;
				// A straight piece placed from the other end reports the opposite facing (0 vs 2),
				// which spun the train nose-for-tail on every such piece. Only the track's AXIS
				// matters here: keep whichever of railYaw / railYaw+180 is nearer the heading the
				// train already had.
				if (!Float.isNaN(cart.lastRailYaw) && Math.abs(MathHelper.wrapDegrees(railYaw - cart.lastRailYaw)) > 90.0F) {
					railYaw = MathHelper.wrapDegrees(railYaw + 180.0F);
				}
				railDbg = "tcFacing=" + f;
				cart.lastRailYaw = railYaw;
			} else if (!Float.isNaN(cart.lastRailYaw)) {
				// Off the rails (derailed) or a rail with no tile: keep the last track heading
				// rather than snapping to the motion-dependent rotationYaw.
				railYaw = cart.lastRailYaw;
				railDbg = "lastRail";
			}
		} catch (Throwable t) { railDbg = "ERR:" + t; }
		if (cart.bogieLoco != null) {// || cart.bogieUtility[0]!=null){
			//GL11.glRotatef((float)(90-cart.rotationYawClientReal), 0.0F, 1.0F, 0.0F);
			if (cart.oldClientYaw == 0) cart.oldClientYaw = cart.rotationYawClientReal;

			float tempYaw = (cart.rotationYawClientReal - cart.oldClientYaw);
			float newYaw = 0;
			//System.out.println("rotationYawBogie "+rotationYawBogie+" oldYaw "+cart.oldClientYaw+" tempYaw "+(Math.abs(tempYaw)/10));
			//System.out.println(Math.abs(cart.oldClientYaw-rotationYawBogie));
			if(Math.abs(cart.oldClientYaw-cart.rotationYawClientReal)>170){
				cart.oldClientYaw = cart.rotationYawClientReal;
			}
			if (cart.oldClientYaw != cart.rotationYawClientReal && Math.abs(cart.oldClientYaw-cart.rotationYawClientReal)>(Math.abs(tempYaw)/10)) {
				newYaw = cart.oldClientYaw + Math.copySign((Math.abs(tempYaw)/10), tempYaw);
				cart.oldClientYaw += Math.copySign((Math.abs(tempYaw)/10), tempYaw);
			}
			else {
				newYaw = cart.rotationYawClientReal;
				cart.oldClientYaw = cart.rotationYawClientReal;
			}
			// Bogie locos orient off their OWN smoothed rotation, exactly like upstream
			// Traincraft-5 (RenderRollingStock line 163): newYaw <- rotationYawClientReal
			// <- serverRealRotation (= atan2(bogieDz,bogieDx) - 90). A prior port swapped
			// this for the WAGON formula (180 - track-tangent yaw), which laid bogie locos
			// 90 degrees off the rail. LIVE-TUNABLE now: orient off the stable TRACK TANGENT
			// (yaw), not the drifting bogie-derived newYaw. Formula = locoA + locoB*yaw.
			// DIAGNOSTIC: print every candidate direction-value ONCE per loco so we can see
			// which one actually differs by 90 between perpendicular (N-S vs E-W) tracks.
			if (LOG_ONCE.add(cart.getEntityId())) {
				System.out.println("[TC-ROT] " + cart.getClass().getSimpleName()
					+ " id=" + cart.getEntityId()
					+ " rotationYaw=" + cart.rotationYaw
					+ " serverRealRotation=" + cart.serverRealRotation
					+ " rotationYawClientReal=" + cart.rotationYawClientReal
					+ " newYaw=" + newYaw
						+ " railYaw=" + railYaw
						+ " bogieAngle=" + (cart.bogieLoco != null ? (float) Math.toDegrees(Math.atan2(cart.bogieLoco.posZ - cart.posZ, cart.bogieLoco.posX - cart.posX)) : -999.0F)
						+ " railDbg=[" + railDbg + "]");
			}
			// LIVE-SWITCHABLE rotation source (no rebuild needed to try each):
			// locoSrc 0=rotationYaw 1=newYaw 2=serverRealRotation 3=rotationYawClientReal
			float locoSrc;
			switch ((int) conf("locoSrc", 4.0F)) {
				case 0:  locoSrc = cart.rotationYaw;            break;
				case 2:  locoSrc = cart.serverRealRotation;     break;
				case 3:  locoSrc = cart.rotationYawClientReal;  break;
				case 4:  locoSrc = railYaw;                     break;
				case 5:  locoSrc = cart.bogieLoco != null ? (float) Math.toDegrees(Math.atan2(cart.bogieLoco.posZ - cart.posZ, cart.bogieLoco.posX - cart.posX)) : cart.rotationYaw; break;
				default: locoSrc = newYaw;                      break;
			}
			// PER-MODEL correction: different loco models were authored facing different
			// axes, so two locos with IDENTICAL direction data can render 90 apart. Add a
			// live-tunable offset keyed by class name, e.g. off_EntityLocoDieselBapCF7round=90.
			// CF7round's model is authored 90 off from the CF7 model, so bake that as its
			// default correction (still overridable live via off_<class> in the conf file).
			float modelDefault = cart.getClass().getSimpleName().equals("EntityLocoDieselBapCF7round") ? 90.0F : 0.0F;
			float modelOff = conf("off_" + cart.getClass().getSimpleName(), modelDefault);
			float locoRot = conf("locoA", 90.0F) + conf("locoB", -1.0F) * locoSrc + modelOff;
			// [TC-FLIP] log any sudden 90+ degree turn of a loco body so a remaining flip shows its cause.
			Float prevRot = LAST_ROT.put(cart.getEntityId(), locoRot);
			if (prevRot != null && Math.abs(MathHelper.wrapDegrees(locoRot - prevRot)) > 60.0F) {
				System.out.println("[TC-FLIP] " + cart.getClass().getSimpleName() + " " + prevRot + " -> " + locoRot
					+ " railDbg=[" + railDbg + "] rotationYaw=" + cart.rotationYaw + " motion=" + cart.motionX + "," + cart.motionZ);
			}
			GL11.glRotatef(locoRot, 0.0F, 1.0F, 0.0F);
			cart.setRenderYaw(newYaw);
			cart.setRenderPitch(pitch);
		}
		else {
			// NOTE: func_150049_b_ = isRailBlockAt
			if (cart.world!=null && (BlockRailBase.isRailBlock(cart.world.getBlockState(new net.minecraft.util.math.BlockPos(i, j, k))) || BlockRailBase.isRailBlock(cart.world.getBlockState(new net.minecraft.util.math.BlockPos(i, j-1, k))))){
				if(cart.isClientInReverse){
					yaw+=180;
					pitch = -pitch;
				}
				if (LOG_ONCE.add(cart.getEntityId())) {
						System.out.println("[TC-TRAIN-DEBUG] " + cart.getClass().getSimpleName()
							+ " ON-RAIL branch  yaw=" + yaw + "  rotationYaw=" + cart.rotationYaw
							+ "  var23null=" + (var23 == null) + "  glRotate(180-yaw)=" + (180.0F - yaw)
							+ "  bogieLoco=" + (cart.bogieLoco != null));
					}
					GL11.glRotatef(conf("wagA", 90.0F) + conf("wagB", -1.0F) * ((int) conf("wagSrc", 4.0F) == 0 ? cart.rotationYaw : railYaw) + conf("off_" + cart.getClass().getSimpleName(), 0.0F), 0.0F, 1.0F, 0.0F);
				cart.setRenderYaw(yaw);
				cart.setRenderPitch(pitch);
			}else{
				if (cart.oldClientYaw == 0) cart.oldClientYaw = cart.rotationYawClientReal;

				float tempYaw = (cart.rotationYawClientReal - cart.oldClientYaw);
				float newYaw = 0;
				//System.out.println("rotationYawBogie "+rotationYawBogie+" oldYaw "+cart.oldClientYaw+" tempYaw "+(Math.abs(tempYaw)/10));
				//System.out.println(Math.abs(cart.oldClientYaw-rotationYawBogie));
				if(Math.abs(cart.oldClientYaw-cart.rotationYawClientReal)>170){
					cart.oldClientYaw = cart.rotationYawClientReal;
				}
				if (cart.oldClientYaw != cart.rotationYawClientReal && Math.abs(cart.oldClientYaw-cart.rotationYawClientReal)>(Math.abs(tempYaw)/10)) {
					newYaw = cart.oldClientYaw + Math.copySign((Math.abs(tempYaw)/10), tempYaw);
					cart.oldClientYaw += Math.copySign((Math.abs(tempYaw)/10), tempYaw);
				}
				else {
					newYaw = cart.rotationYawClientReal;
					cart.oldClientYaw = cart.rotationYawClientReal;
				}
				GL11.glRotatef(conf("wagA", 90.0F) + conf("wagB", -1.0F) * ((int) conf("wagSrc", 4.0F) == 0 ? cart.rotationYaw : railYaw) + conf("off_" + cart.getClass().getSimpleName(), 0.0F), 0.0F, 1.0F, 0.0F);
				cart.setRenderYaw(yaw);
				cart.setRenderPitch(pitch);
			}
		}

		//if(cart.bogie!=null)cart.world.spawnParticle("reddust", cart.bogie.posX, cart.bogie.posY, cart.bogie.posZ, 0.1, 0.4, 0.1);

		//GL11.glRotatef(conf("wagA", 90.0F) + conf("wagB", -1.0F) * ((int) conf("wagSrc", 4.0F) == 0 ? cart.rotationYaw : railYaw) + conf("off_" + cart.getClass().getSimpleName(), 0.0F), 0.0F, 1.0F, 0.0F);
		if (cart.bogieLoco != null) {// || cart.bogieUtility[0]!=null){
			// ponytail: bogie locos buried nose-first because pitch was applied RAW here;
			// the working wagon branch (below) scales anglePitchClient by /60f. Match it.
			GL11.glRotatef(-cart.anglePitchClient/60f, 0.0F, 0.0F, 1.0F);
			// ponytail: TUNE KNOB. Bogie loco models sit ~1/4 buried in the rail because
			// their origin is low. Lift the body up. Increase to raise, decrease to lower.
			GL11.glTranslatef(0.0F, conf("bogieLift", BOGIE_LIFT), 0.0F);
		}
		else {
			// ponytail: lift freight/cargo wagons out of the ground (they had no lift; bogie
			// locos above get BOGIE_LIFT). Tune FREIGHT_LIFT.
			if (cart instanceof Freight) {
					GL11.glTranslatef(0.0F, conf("freightLift", FREIGHT_LIFT) + conf("lift_" + cart.getClass().getSimpleName(), 0.0F), 0.0F);
				} else {
					// Passenger/stock/caboose cars (IPassenger, extend EntityRollingStock, NOT
					// Freight) had NO lift and sank into the rail. Live-tunable via wagonLift
					// plus optional per-model lift_<ClassName>.
					GL11.glTranslatef(0.0F, conf("wagonLift", 0.6F) + conf("lift_" + cart.getClass().getSimpleName(), 0.0F), 0.0F);
				}
			if(renderYVect != null){
				pitch = cart.anglePitchClient/60f;
				if(cart.rotationYawClientReal>-5 && cart.rotationYawClientReal<5){
					pitch=-pitch;
				}
				if(!cart.isClientInReverse && (cart.rotationYawClientReal>85 && cart.rotationYawClientReal<95 )){
					pitch=-pitch;
				}
				if(cart.isClientInReverse && (cart.rotationYawClientReal<-265 && cart.rotationYawClientReal>-275 )){
					pitch=-pitch;
				}
				GL11.glRotatef(pitch, 0.0F, 0.0F, 1.0F);
			}
			else{
				GL11.glRotatef(-pitch, 0.0F, 0.0F, 1.0F);
			}
		}
		// Derailed (no track under the train): drop the body onto the ground and tip it over, so a
		// train that ran off the end of the line reads as derailed instead of hovering at rail height.
		// Live-tunable: derailDrop (blocks down), derailTilt (degrees of lean).
		if (isDerailed(cart)) {
			GL11.glTranslatef(0.0F, -conf("derailDrop", 1.0F), 0.0F);
			GL11.glRotatef(conf("derailTilt", 8.0F), 1.0F, 0.0F, 0.0F);
		}
		float var28 = cart.getRollingAmplitude() - time;
		float var30 = cart.getDamage() - time;

		if (var30 < 0.0F) {
			var30 = 0.0F;
		}

		if (var28 > 0.0F) {
			float angle = MathHelper.sin(var28) * var28 * var30 / 10.0F;
			angle = Math.min(angle, 0.8F);
			angle = Math.copySign(angle, cart.getRollingDirection());
			GL11.glRotatef(angle, 1.0F, 0.0F, 0.0F);
		}
		for (RenderEnum renders : RenderEnum.values()) {
			if (renders.getEntityClass() != null && renders.getEntityClass().equals(cart.getClass())) {
				//loadTexture(getTextureFile(renders.getTexture(), renders.getIsMultiTextured(), cart));
				if (renders.getTrans() != null) {
					GL11.glTranslatef(renders.getTrans()[0], renders.getTrans()[1], renders.getTrans()[2]);
				}
				if (renders.getRotate() != null) {
					GL11.glRotatef(renders.getRotate()[0], 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(renders.getRotate()[1], 0.0F, 1.0F, 0.0F);
					GL11.glRotatef(renders.getRotate()[2], 0.0F, 0.0F, 1.0F);
				}
				if (renders.getScale() != null) {
					GL11.glScalef(renders.getScale()[0], renders.getScale()[1], renders.getScale()[2]);
				}
				Tessellator.bindTexture(getTexture(cart));

				GL11.glEnable(GL11.GL_LIGHTING);
				int skyLight = cart.world.getCombinedLight(new net.minecraft.util.math.BlockPos(i, j, k), 0);
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit,  skyLight % 65536,
						skyLight / 65536f);


				renders.getModel().render(cart, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);

				//GL11.glEnable(GL11.GL_LIGHTING);

				if (renders.hasSmoke()) {
					if (cart.bogieLoco != null) {// || cart.bogieUtility[0]!=null){
						renderSmokeFX(cart, 90 + cart.rotationYawClientReal, (float) cart.anglePitchClient, renders.getSmokeType(), renders.getSmokeFX(), renders.getSmokeIterations(), time, renders.hasSmokeOnSlopes());
					}
					else {
						renderSmokeFX(cart, (yaw), pitch, renders.getSmokeType(), renders.getSmokeFX(), renders.getSmokeIterations(), time, renders.hasSmokeOnSlopes());
					}
				}
				if (renders.hasExplosion()) {
					if (cart.bogieLoco != null) {// || cart.bogieUtility[0]!=null){
						renderExplosionFX(cart, 90 + cart.rotationYawClientReal, (float) cart.anglePitchClient, renders.getExplosionType(), renders.getExplosionFX(), renders.getExplosionFXIterations(), renders.hasSmokeOnSlopes());
					}
					else {
						renderExplosionFX(cart, yaw, pitch, renders.getExplosionType(), renders.getExplosionFX(), renders.getExplosionFXIterations(), renders.hasSmokeOnSlopes());
					}
				}

				break;
			}
		}

		GL11.glPopMatrix();
	}

	private static final java.util.Map<String, ResourceLocation> TEX_CACHE = new java.util.HashMap<String, ResourceLocation>();

	private static ResourceLocation loc(String path) {
		// Texture files on disk are all lowercase; render bases are MixedCase, which breaks binding
		// on case-sensitive filesystems (every train went magenta). Normalise to lowercase.
		return new ResourceLocation(Info.resourceLocation, path.toLowerCase(java.util.Locale.ROOT));
	}

	private static boolean exists(ResourceLocation rl) {
		try { net.minecraft.client.Minecraft.getMinecraft().getResourceManager().getResource(rl); return true; }
		catch (Exception e) { return false; }
	}

	private static ResourceLocation getResourceFile(String texture, boolean multiTexture, EntityRollingStock cart) {
		if (!multiTexture) {
			ResourceLocation single = loc(Info.trainsPrefix + texture + ".png");
			return exists(single) ? single : net.minecraft.client.renderer.texture.TextureMap.LOCATION_MISSING_TEXTURE;
		}
		int c = cart.getColor();
		String key = texture + "#" + c;
		ResourceLocation cached = TEX_CACHE.get(key);
		if (cached != null) return cached;

		ResourceLocation result = null;
		if (c >= 0 && c <= 15) {
			// colored variant, e.g. "ice1_engine_" + "Blue" -> "ice1_engine_blue.png".
			// getColorAsString has holes (9-12 return null) and not every train ships every
			// colour, so this MUST be existence-checked or Cleanroom's texture loader NPE-crashes
			// the whole client on a nonexistent path (FM H24-66 crash, 2026-07-09).
			String colName = train.common.api.AbstractTrains.getColorAsString(c);
			if (colName != null) {
				ResourceLocation cl = loc(Info.trainsPrefix + texture + colName + ".png");
				if (exists(cl)) result = cl;
			}
		}
		if (result == null) {
			// uncolored/default (color == -1) or the requested colour has no file:
			// prefer the base texture (drop trailing '_').
			String base = texture.endsWith("_") ? texture.substring(0, texture.length() - 1) : texture;
			ResourceLocation baseLoc = loc(Info.trainsPrefix + base + ".png");
			if (exists(baseLoc)) {
				result = baseLoc;
			} else {
				// Some trains (e.g. Class 390) ship ONLY colored variants and no base file.
				// Fall back to the first available colour so it isn't magenta.
				for (int col : new int[]{15, 4, 1, 0, 8, 2}) { // white, blue, red, black, grey, green
					ResourceLocation cl = loc(Info.trainsPrefix + texture + train.common.api.AbstractTrains.getColorAsString(col) + ".png");
					if (exists(cl)) { result = cl; break; }
				}
			}
		}
		if (result == null) {
			// Nothing on disk at all: bind the vanilla missing texture (pink/black) instead of a
			// nonexistent path — a magenta train beats a dead game.
			result = net.minecraft.client.renderer.texture.TextureMap.LOCATION_MISSING_TEXTURE;
		}
		TEX_CACHE.put(key, result);
		return result;
	}

	private static void renderSmokeFX(EntityRollingStock cart, float yaw, float pitch, String smokeType, ArrayList<double[]> smokeFX, int smokeIterations, float time, boolean hasSmokeOnSlopes) {
		if(cart instanceof Locomotive && !((Locomotive)cart).isLocoTurnedOn()){return;}
		if(Math.abs(pitch)>30)return;
		//if (pitch != 0 && !hasSmokeOnSlopes) { return; }
		if ((cart instanceof Locomotive && ((Locomotive) cart).getFuel() > 0) || (cart instanceof EntityTracksBuilder && ((EntityTracksBuilder) cart).getFuel() > 0)) {
			int r = random.nextInt(10 * smokeIterations);
			double speed = 0;
			if (cart instanceof Locomotive) speed = ((Locomotive) cart).getSpeed();
			if (r < ((smokeIterations * 4) + (speed * 5))) {
				double rotatedvec[];
				for (int j = 0; j < smokeIterations; j++) {


					for (double[] explosion : smokeFX) {
						rotatedvec = rotatePointF(explosion[0], explosion[1], explosion[2], pitch, yaw);
						cart.world.spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL,
								cart.posX + rotatedvec[0], cart.posY + rotatedvec[1], cart.posZ +rotatedvec[2],
								0.0,0.0,0.0);
					}
				}
			}
		}
	}
	public static final float radianF = (float) Math.PI / 180.0f;
	public static double[] rotatePointF(double x, double y, double z, float pitch, float yaw) {
		double[] xyz = new double[]{x,y,z};
		float sin, cos;
		//rotate pitch
		if (pitch != 0.0F) {
			pitch *= radianF;
			cos = MathHelper.cos(pitch);
			sin = MathHelper.sin(pitch);

			xyz[0] = (y * sin) + (x * cos);
			xyz[1] = (y * cos) - (x * sin);
		}
		//rotate yaw
		if (yaw != 0.0F) {
			yaw *= radianF;
			cos = MathHelper.cos(yaw);
			sin = MathHelper.sin(yaw);

			xyz[0] = (x * cos) - (z * sin);
			xyz[2] = (x * sin) + (z * cos);
		}

		return xyz;
	}

	private static void renderExplosionFX(EntityRollingStock cart, float yaw, float pitch, String explosionType, ArrayList<double[]> explosionFX, int explosionFXIterations, boolean hasSmokeOnSlopes) {
		if(cart instanceof Locomotive && !((Locomotive)cart).isLocoTurnedOn())return;
		float yawMod = yaw % 360;
		double pitchRads = Math.toDegrees(pitch);
		//if (pitch != 0 && !hasSmokeOnSlopes) { return; }
		if(Math.abs(pitch)>30)return;
		if (cart instanceof Locomotive && ((Locomotive) cart).getFuel() > 0) {
			if (random.nextInt(300) < (explosionFXIterations * 10)) {
				for (int j = 0; j < explosionFXIterations; j++) {
					if (yawMod == 180) {
						for (double[] explosion : explosionFX) {
							cart.world.spawnParticle(net.minecraft.util.EnumParticleTypes.EXPLOSION_NORMAL,cart.posX - explosion[0], cart.posY + explosion[1] + ((Math.tan(pitchRads)* 4  * -explosion[1])), cart.posZ + explosion[2], 0.0D, 0.0D, 0.0D);
							cart.world.spawnParticle(net.minecraft.util.EnumParticleTypes.EXPLOSION_NORMAL,cart.posX - explosion[0], cart.posY + explosion[1] + ((Math.tan(pitchRads)* 4  * -explosion[1])), cart.posZ - explosion[2], 0.0D, 0.0D, 0.0D);
						}
					}
					else if (yawMod == 90) {
						for (double[] explosion : explosionFX) {
							cart.world.spawnParticle(net.minecraft.util.EnumParticleTypes.EXPLOSION_NORMAL,cart.posX + explosion[2], cart.posY + explosion[1] + ((Math.tan(pitchRads)*4 * -explosion[1])), cart.posZ + explosion[0], 0.0D, 0.0D, 0.0D);
							cart.world.spawnParticle(net.minecraft.util.EnumParticleTypes.EXPLOSION_NORMAL,cart.posX - explosion[2], cart.posY + explosion[1] + ((Math.tan(pitchRads)*4 * -explosion[1])), cart.posZ + explosion[0], 0.0D, 0.0D, 0.0D);
						}
					}
					else if (yawMod == 0) {
						for (double[] explosion : explosionFX) {
							cart.world.spawnParticle(net.minecraft.util.EnumParticleTypes.EXPLOSION_NORMAL,cart.posX + explosion[0], cart.posY + explosion[1] + ((Math.tan(pitchRads)*4 * -explosion[1])), cart.posZ + explosion[2], 0.0D, 0.0D, 0.0D);
							cart.world.spawnParticle(net.minecraft.util.EnumParticleTypes.EXPLOSION_NORMAL,cart.posX + explosion[0], cart.posY + explosion[1] + ((Math.tan(pitchRads)*4 * -explosion[1])), cart.posZ - explosion[2], 0.0D, 0.0D, 0.0D);
						}
					}
					else if (yawMod == -90) {
						for (double[] explosion : explosionFX) {
							cart.world.spawnParticle(net.minecraft.util.EnumParticleTypes.EXPLOSION_NORMAL,cart.posX + explosion[2], cart.posY + explosion[1] + ((Math.tan(pitchRads)*4 * -explosion[1])), cart.posZ - explosion[0], 0.0D, 0.0D, 0.0D);
							cart.world.spawnParticle(net.minecraft.util.EnumParticleTypes.EXPLOSION_NORMAL,cart.posX - explosion[2], cart.posY + explosion[1] + ((Math.tan(pitchRads)*4 * -explosion[1])), cart.posZ - explosion[0], 0.0D, 0.0D, 0.0D);
						}
					}
				}
			}
		}
	}

	@Override
	public void doRender(EntityRollingStock par1Entity, double x, double y, double d2, float yaw, float time) {
		renderTheMinecart(par1Entity, x, y, d2, yaw, time);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityRollingStock entity) {
		return getTexture(entity);
	}

	public static ResourceLocation getTexture(Entity entity) {
		for (RenderEnum renders : RenderEnum.values()) {
			if (renders.getEntityClass() != null && renders.getEntityClass().equals(entity.getClass())) { return getResourceFile(renders.getTexture(), renders.getIsMultiTextured(), (EntityRollingStock) entity); }
		}
		return null;
	}
}