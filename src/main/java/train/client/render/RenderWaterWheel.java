package train.client.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import train.client.render.models.blocks.ModelWaterWheel;
import train.common.tile.TileWaterWheel;

public class RenderWaterWheel extends TileEntitySpecialRenderer {
	private static final ModelWaterWheel modelWaterWheel = new ModelWaterWheel();

	@Override
	public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		renderTileEntityAt(te, x, y, z, partialTicks);
	}

	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick) {
		modelWaterWheel.render((TileWaterWheel) tileEntity, x, y, z);
	}
}
