package train.client.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;
import train.common.library.Info;
import tmt.Tessellator;
import net.minecraft.util.ResourceLocation;
import train.client.render.models.blocks.ModelBlockSignal;
import train.common.tile.TileSignal;

public class RenderSignal extends TileEntitySpecialRenderer {

	private static final ModelBlockSignal modelSignal = new ModelBlockSignal(1.0F / 16.0F);

	public RenderSignal() {
	}

	public void renderAModelAt(TileSignal var1, double d, double d1, double d2, float f) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) d + 0.46F, (float) d1 + 0.0F, (float) d2 + 0.46F);
		// These binds were commented out during the port because bindTextureByName(String) does
		// not exist in 1.12.2 -- but nothing replaced them, so the model drew with whatever
		// texture happened to be bound last (the block atlas) and the signal appeared as a tall
		// column of garbled multicoloured pixels. Only visible once the block itself was
		// actually registered. state: 0 = red, 1 = green.
		Tessellator.bindTexture(new ResourceLocation(Info.resourceLocation,
				Info.trainsPrefix + (var1.state == 1 ? "signal_suisse_green.png" : "signal_suisse_red.png")));
		modelSignal.render(0.0625F, var1.getFacing());
		GL11.glPopMatrix();
	}
	
	@Override
	public void render(net.minecraft.tileentity.TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) { renderTileEntityAt(te, x, y, z, partialTicks); }

	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
		renderAModelAt((TileSignal) tileentity, d, d1, d2, f);
	}
}