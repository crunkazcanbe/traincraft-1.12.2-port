package train.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import train.common.core.util.TraincraftUtil;

public class EntityLasersLines extends Entity {
	public double x1, y1, z1, x2, y2, z2;
	public boolean hidden = false;

	public String texture;

	public EntityLasersLines(World world) {
		super(world);

		preventEntitySpawning = false;
		noClip = true;
		isImmuneToFire = true;

		setSize(10, 10);
		this.ignoreFrustumCheck = true;

	}

	public EntityLasersLines(World world, double d, double d1, double d2) {
		super(world);

		preventEntitySpawning = false;
		noClip = true;
		isImmuneToFire = true;

		setSize(10, 10);
		this.ignoreFrustumCheck = true;
	}

	public void setPositions(double x1, double y1, double z1, double x2, double y2, double z2) {
		this.x1 = x1;
		this.y1 = y1;
		this.z1 = z1;

		this.x2 = x2;
		this.y2 = y2;
		this.z2 = z2;

		setPosition2(x1, y1, z1);

	}

	public void setTexture(String texture) {
		this.texture = texture;
	}

	public void setPosition2(double d, double d1, double d2) {

		posX = d;
		posY = d1;
		posZ = d2;

		double bbMinX = (x1 <= x2 ? x1 : x2) - 1;
		double bbMinY = (y1 <= y2 ? y1 : y2) - 1;
		double bbMinZ = (z1 <= z2 ? z1 : z2) - 1;
		double bbMaxX = (x1 <= x2 ? x2 : x1) + 1;
		double bbMaxY = (y1 <= y2 ? y2 : y1) + 1;
		double bbMaxZ = (z1 <= z2 ? z2 : z1) + 1;
		this.setEntityBoundingBox(new net.minecraft.util.math.AxisAlignedBB(bbMinX, bbMinY, bbMinZ, bbMaxX, bbMaxY, bbMaxZ));

		updateGraphicData();
	}

	public double renderSize = 1;
	public double angleY = 0;
	public double angleZ = 0;

	public void updateGraphicData() {
		double dx = x1 - x2;
		double dy = y1 - y2;
		double dz = z1 - z2;

		renderSize = Math.sqrt(dx * dx + dy * dy + dz * dz);

		angleZ = 360 - TraincraftUtil.atan2degreesf(dz,dx)+180;

		dx = Math.sqrt(renderSize * renderSize - dy * dy);

		angleY = -TraincraftUtil.atan2degreesf(dy, dx);
	}

	@Override
	public void onUpdate() {

		super.onUpdate();
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {

	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {

	}

	@Override
	protected void entityInit() {

	}
	
	@Override
	protected boolean canTriggerWalking() {
		return false;
	}
	
	@Override
	public boolean canBePushed() {
		return false;
	}

}