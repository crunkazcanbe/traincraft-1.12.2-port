package train.client.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import train.client.render.models.blocks.ModelLantern;
import train.common.tile.TileLantern;

public class RenderLantern extends TileEntitySpecialRenderer {
	private static final ModelLantern modelLantern = new ModelLantern();

	@Override
	public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		renderTileEntityAt(te, x, y, z, partialTicks);
	}

	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick) {
		modelLantern.render((TileLantern) tileEntity, x, y, z);
	}
}
