package train.client.render.renderSwitch;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import tmt.Tessellator;
import train.client.render.renderSwitch.models.ModelkSignal;
import train.common.library.Info;
import train.common.tile.tileSwitch.TilekSignal;

public class RenderkSignal extends TileEntitySpecialRenderer {
	private static final ModelkSignal model = new ModelkSignal();
	private static final ResourceLocation texture = new ResourceLocation(Info.resourceLocation, Info.modelTexPrefix + "ksignalred.png");
	private static final ResourceLocation texture2 = new ResourceLocation(Info.resourceLocation, Info.modelTexPrefix + "ksignalgreen.png");

	@Override
	public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		renderTileEntityAt(te, x, y, z, partialTicks);
	}

	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick) {
		boolean green = ((TilekSignal) tileEntity).state == 0;
		Tessellator.bindTexture(green ? texture2 : texture);

		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y + 0.6, z + 0.5);
		GL11.glRotated(180, 0, 1, 0);
		boolean skipRender = false;

		switch (((TilekSignal) tileEntity).getFacing()) {
			case NORTH: {
				GL11.glRotated(180, 0, 0, 1);
				GL11.glRotated(90, 0, 1, 0);
				GL11.glTranslated(0.1875, 0, 0.125);
				break;
			}
			case SOUTH: {
				GL11.glRotated(180, 0, 0, 1);
				GL11.glRotated(270, 0, 1, 0);
				GL11.glTranslated(0.1875, 0, 0.125);
				break;
			}
			case EAST: {
				GL11.glRotated(180, 0, 0, 1);
				GL11.glRotated(0, 0, 1, 0);
				GL11.glTranslated(0.1875, 0, 0.125);
				break;
			}
			case WEST: {
				GL11.glRotated(180, 0, 0, 1);
				GL11.glRotated(180, 0, 1, 0);
				GL11.glTranslated(0.1875, 0, 0.125);
				break;
			}
			default: {
				skipRender = true;
			}
		}

		if (!skipRender) {
			model.render(null, 0, 0, 0, 0, 0, 0.0625f);
		}
		GL11.glPopMatrix();
	}
}

