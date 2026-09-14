package train.client.render.renderSwitch;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import tmt.Tessellator;
import train.client.render.renderSwitch.models.ModelspeedSign;
import train.common.library.Info;
import train.common.tile.tileSwitch.TileSpeedSign;

public class RenderSpeedSign extends TileEntitySpecialRenderer {
	static final ModelspeedSign modelspeedSign = new ModelspeedSign();

	private ResourceLocation[] textures = {
			new ResourceLocation(Info.resourceLocation, Info.modelTexPrefix + "speedsign10.png"),
			new ResourceLocation(Info.resourceLocation, Info.modelTexPrefix + "speedsign50.png"),
			new ResourceLocation(Info.resourceLocation, Info.modelTexPrefix + "speedsign90.png"),
			new ResourceLocation(Info.resourceLocation, Info.modelTexPrefix + "speedsign125.png"),
			new ResourceLocation(Info.resourceLocation, Info.modelTexPrefix + "speedsign160.png")};

	@Override
	public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		renderTileEntityAt(te, x, y, z, partialTicks);
	}

	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick) {
		Tessellator.bindTexture(textures[(((TileSpeedSign) tileEntity).getSkinstate())]);
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y + 0.625, z + 0.125);
		GL11.glRotated(180, 0, 1, 0);
		boolean skipRender = false;

		switch (((TileSpeedSign) tileEntity).getFacing()) {
			case NORTH: {
				GL11.glRotated(180, 0, 0, 1);
				GL11.glRotated(90, 0, 1, 0);
				break;
			}
			case SOUTH: {
				GL11.glRotated(180, 0, 0, 1);
				GL11.glRotated(270, 0, 1, 0);
				GL11.glTranslated(-0.75, 0f, 0);
				break;
			}
			case EAST: {
				GL11.glRotated(180, 0, 0, 1);
				GL11.glRotated(180, 0, 1, 0);
				GL11.glTranslated(-0.5, 0f, 0.25);
				break;
			}
			case WEST: {
				GL11.glRotated(180, 0, 0, 1);
				GL11.glRotated(0, 0, 1, 0);
				GL11.glTranslatef(-0.375f, 0f, -0.5f);
				break;
			}
			default: {
				skipRender = true;
			}
		}

		if (!skipRender) {
			modelspeedSign.render(null, 0, 0, 0, 0, 0, 0.0625f);
		}
		GL11.glPopMatrix();
	}
}
