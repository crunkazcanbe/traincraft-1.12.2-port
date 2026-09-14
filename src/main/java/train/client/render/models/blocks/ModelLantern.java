package train.client.render.models.blocks;

import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import train.common.library.Info;
import train.common.tile.TileLantern;

@SideOnly(Side.CLIENT)
public class ModelLantern extends ModelBase {
	private IModelCustom modelLantern;

	public ModelLantern() {
		modelLantern = AdvancedModelLoader.loadModel(new ResourceLocation(Info.modelPrefix + "lantern.obj"));
	}

	public void render() {
		modelLantern.renderAll();
	}

	public void render(TileLantern lantern, double x, double y, double z) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5f, (float) y + 0.5f, (float) z + 0.5f);
		GL11.glScalef(0.5f, 0.5f, 0.5f);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(new ResourceLocation(Info.resourceLocation, Info.modelTexPrefix + "lantern_uv_draw_2.png"));
		int j = lantern.getRandomColor();
		float f1 = 1.0F;
		float f2 = (float) (j >> 16 & 255) / 255.0F;
		float f3 = (float) (j >> 8 & 255) / 255.0F;
		float f4 = (float) (j & 255) / 255.0F;
		GL11.glColor4f(f1 * f2, f1 * f3, f1 * f4, 1);
		this.render();
		GL11.glColor4f(1, 1, 1, 1);
		GL11.glPopMatrix();
	}
}
