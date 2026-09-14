package train.client.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import tmt.Tessellator;
import train.client.render.models.blocks.ModelMetroMadridOHW;
import train.common.library.Info;
import train.common.tile.TileMetroMadridPole;

public class RenderMetroMadridPole extends TileEntitySpecialRenderer {

	private static final ModelMetroMadridOHW modelMetroMadridOHW = new ModelMetroMadridOHW();
	private static final ResourceLocation texture = new ResourceLocation(Info.resourceLocation, Info.modelTexPrefix + "metromadridpole.png");

	public RenderMetroMadridPole() {
	}

	public void render(TileEntity var1, double x, double y, double z) {
		GL11.glPushMatrix();

		GL11.glTranslated(x, y, z);
		Tessellator.bindTexture(texture);

		GL11.glTranslatef(0.5F, 0.65F, 0.5F);

		switch (((TileMetroMadridPole) var1).getFacing()) {
			case NORTH: {
				GL11.glRotated(180, 0, 0, 1);
				GL11.glRotated(90, 0, 1, 0);
				break;
			}
			case SOUTH: {
				GL11.glRotated(180, 0, 0, 1);
				GL11.glRotated(270, 0, 1, 0);
				break;
			}
			case WEST: {
				GL11.glRotated(180, 0, 0, 1);
				GL11.glRotated(180, 0, 1, 0);
				break;
			}
			case EAST: {
				GL11.glRotated(180, 0, 0, 1);
				GL11.glRotated(0, 0, 1, 0);
				break;
			}
			default: {
				break;
			}
		}

		modelMetroMadridOHW.render();

		GL11.glPopMatrix();
	}

	@Override
	public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		renderTileEntityAt(te, x, y, z, partialTicks);
	}

	public void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float var8) {
		render(var1, var2, var4, var6);
	}
}
