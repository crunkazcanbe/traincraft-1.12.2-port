package train.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import train.common.api.EntityBogie;
import train.common.library.Info;

public class RenderBogie extends Render<EntityBogie> {

	protected net.minecraft.client.model.ModelBase model = new net.minecraft.client.model.ModelBase() {};
	private static final ResourceLocation TEXTURE = new ResourceLocation(Info.resourceLocation, Info.modelTexPrefix + "robot.png");

	public RenderBogie(RenderManager rm) {
		super(rm);
	}

	@Override
	public void doRender(EntityBogie entity, double x, double y, double z, float f, float f1) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBogie entity) {
		return TEXTURE;
	}
	

}
