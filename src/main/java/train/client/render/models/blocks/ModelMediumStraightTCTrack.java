package train.client.render.models.blocks;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import tmt.Tessellator;
import train.common.library.Info;
import train.common.tile.TileTCRail;

@SideOnly(Side.CLIENT)
public class ModelMediumStraightTCTrack extends ModelBase {

	public ModelMediumStraightTCTrack() {
	}

	public void render() {
		ModelSmallStraightTCTrack.modelSmallStraight.renderAll();
	}

	public void render(TileTCRail tcRail, double x, double y, double z) {
		render(tcRail, x, y, z, "track_normal.png");
	}

	/** Same geometry, caller-chosen texture: the EMBEDDED_* straights are the plain
	 *  straight bed drawn with track_embedded.png (as in Traincraft 1.7.10-CE). */
	public void render(TileTCRail tcRail, double x, double y, double z, String texture) {

		// Bind the texture, so that OpenGL properly textures our block.
		Tessellator.bindTexture(new ResourceLocation(Info.resourceLocation, Info.modelTexPrefix + texture));
		GL11.glColor4f(1, 1, 1, 1);
		//GL11.glScalef(0.5f, 0.5f, 0.5f);

		switch (tcRail.getFacing()){
			case 3:{GL11.glRotatef(-90, 0, 1, 0);break;}
			case 1:{GL11.glRotatef(90, 0, 1, 0);break;}
			case 0:{GL11.glRotatef(180, 0, 1, 0);}
		}
		for (int i = 0; i < 3; i++) {
			render();
			GL11.glTranslatef(0.0f, 0.0f, -1.0f);
		}
	}
}