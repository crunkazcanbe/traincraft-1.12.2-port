package train.client.render.models.blocks;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import tmt.Tessellator;
import train.common.library.Info;
import train.common.tile.TileTCRail;

@SideOnly(Side.CLIENT)
public class ModelRightTurnTCTrack extends ModelBase {
	private static IModelCustom modelMediumRightTurn = AdvancedModelLoader.loadModel(new ResourceLocation(Info.modelPrefix + "track_curve_medium.obj"));
	private static IModelCustom modelLargeRightTurn = AdvancedModelLoader.loadModel(new ResourceLocation(Info.modelPrefix + "track_curve_big.obj"));
	private static IModelCustom modelVeryLargeRightTurn = AdvancedModelLoader.loadModel(new ResourceLocation(Info.modelPrefix + "track_curve_very_big.obj"));;

	// GitHub issue #1: these four turns had no model and borrowed the very-large one. Offsets from the 1.7.10 CE
	// ModelRightTurnTCTrack; left = right minus (size-1) on both axes, the same rule our existing sizes follow.
	private static IModelCustom model_super_large = AdvancedModelLoader.loadModel(new ResourceLocation(Info.modelPrefix + "track_curve_super_big.obj"));
	private static IModelCustom model_29x = AdvancedModelLoader.loadModel(new ResourceLocation(Info.modelPrefix + "track_curve_29x.obj"));
	private static IModelCustom model_32x = AdvancedModelLoader.loadModel(new ResourceLocation(Info.modelPrefix + "track_curve_32x.obj"));
	private static IModelCustom model_1x = AdvancedModelLoader.loadModel(new ResourceLocation(Info.modelPrefix + "track_curve_1x.obj"));

	private static int GLID=-1;

	public ModelRightTurnTCTrack() {
	}

	public void renderMedium() {
		modelMediumRightTurn.renderAll();
	}

	public void renderLarge() {
		modelLargeRightTurn.renderAll();
	}
	public void renderVeryLarge() {
		modelVeryLargeRightTurn.renderAll();
	}

	public void render(String type, TileTCRail tcRail, double x, double y, double z) {

		// Bind the texture, so that OpenGL properly textures our block.
		Tessellator.bindTexture(new ResourceLocation(Info.resourceLocation, Info.modelTexPrefix + "track_normal.png"));
		GL11.glColor4f(1, 1, 1, 1);
		//GL11.glScalef(0.5f, 0.5f, 0.5f);

		switch (tcRail.getFacing()){
			case 0:{GL11.glRotatef(-90, 0, 1, 0);break;}
			case 1:{GL11.glRotatef(180, 0, 1, 0);break;}
			case 2:{GL11.glRotatef(90, 0, 1, 0);break;}
		}
		if (type.equals("medium")) {
			GL11.glTranslatef(-1.0f, 0.0f, 3.0f);
			this.renderMedium();
		} else if (type.equals("large")) {
			GL11.glTranslatef(-1.0f, 0.0f, 5.0f);
			this.renderLarge();
		} else if (type.equals("very_large")){
			GL11.glTranslatef(8.5f, 0.0f, 9.54f);
			this.renderVeryLarge();
		}
		else if (type.equals("super_large")) {
			GL11.glTranslatef(14.5f, 0.0f, 15.5f);
			model_super_large.renderAll();
		}
		else if (type.equals("29x")) {
			GL11.glTranslatef(27.5f, 0.0f, 28.5f);
			model_29x.renderAll();
		}
		else if (type.equals("32x")) {
			GL11.glTranslatef(30.5f, 0.0f, 31.5f);
			model_32x.renderAll();
		}
		else if (type.equals("1x")) {
			GL11.glTranslatef(-0.5f, 0.0f, 0.5f);
			model_1x.renderAll();
		}
	}
}