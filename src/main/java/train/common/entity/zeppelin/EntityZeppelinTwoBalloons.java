package train.common.entity.zeppelin;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import train.common.library.ItemIDs;

public class EntityZeppelinTwoBalloons extends AbstractZeppelin{
	
	public EntityZeppelinTwoBalloons(World world) {
		super(world);
	}
	public EntityZeppelinTwoBalloons(World world, double d, double d1, double d2) {
		this(world);
		setPosition(d, d1 + getYOffset(), d2);
		motionX = 0.0D;
		motionY = 0.0D;
		motionZ = 0.0D;
		prevPosX = d;
		prevPosY = d1;
		prevPosZ = d2;
	}
	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float i) {
		if (world.isRemote || isDead) {
			return true;
		}
		boatRockDirection = -boatRockDirection;
		boatTimeSinceHit = 10;
		boatCurrentDamage += i * 10;
		if (damagesource.getTrueSource() instanceof EntityPlayer) {
			if (damagesource.getTrueSource() instanceof EntityPlayer && ((EntityPlayer) damagesource.getTrueSource()).capabilities.isCreativeMode) {
				this.boatCurrentDamage = 1000;
			}
		}
		this.velocityChanged = true;
		if (boatCurrentDamage > 40) {
			if (damagesource.getTrueSource() instanceof EntityPlayer && !(((EntityPlayer) damagesource.getTrueSource()).capabilities.isCreativeMode)) {
				dropItem(ItemIDs.airship.item, 1);
			}
			setDead();
		}
		return true;
	}
	@Override
	public String getName() {
		return "Zeppelin Two Balloons";
	}
}
