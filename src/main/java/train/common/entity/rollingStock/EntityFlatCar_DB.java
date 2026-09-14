package train.common.entity.rollingStock;

import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import train.common.api.EntityRollingStock;
import train.common.api.IPassenger;

public class EntityFlatCar_DB extends EntityRollingStock implements IPassenger {

	public EntityFlatCar_DB(World world) {
		super(world);
	}

	public EntityFlatCar_DB(World world, double d, double d1, double d2) {
		this(world);
		setPosition(d, d1 + (double) yOffset, d2);
		motionX = 0.0D;
		motionY = 0.0D;
		motionZ = 0.0D;
		prevPosX = d;
		prevPosY = d1;
		prevPosZ = d2;
	}

	@Override
	public void onUpdate(){
		super.onUpdate();
		net.minecraft.util.math.AxisAlignedBB bb = getEntityBoundingBox();
		setEntityBoundingBox(new net.minecraft.util.math.AxisAlignedBB(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY - 1, bb.maxZ));
	}

	@Override
	public void updatePassenger(net.minecraft.entity.Entity passenger) {
		if(riddenByEntity==null){return;}
		riddenByEntity.setPosition(posX, posY + getMountedYOffset() + riddenByEntity.getYOffset() + 0.4, posZ);
	}

	@Override
	public void setDead() {
		super.setDead();
		isDead = true;
	}

	@Override
	public boolean processInitialInteract(EntityPlayer entityplayer, net.minecraft.util.EnumHand hand) {
		playerEntity = entityplayer;
		if ((super.processInitialInteract(entityplayer, hand))) {
			return false;
		}
		if (entityplayer.getRidingEntity() == this){
			return false;
		}
		if (!world.isRemote) {
			ItemStack itemstack = entityplayer.inventory.getCurrentItem();
			if(lockThisCart(itemstack, entityplayer))return true;
			if (riddenByEntity != null && (riddenByEntity instanceof EntityPlayer) && riddenByEntity != entityplayer) {
				return true;
			}
			if (!world.isRemote) {
				entityplayer.startRiding(this);
			}
		}
		return true;
	}

	@Override
	public boolean canBeRidden() {
		return true;
	}

	@Override
	public boolean isStorageCart() {
		return false;
	}

	@Override
	public boolean isPoweredCart() {
		return false;
	}

	@Override
	public float getOptimalDistance(EntityMinecart cart) {
		return 1.84F;
	}
}