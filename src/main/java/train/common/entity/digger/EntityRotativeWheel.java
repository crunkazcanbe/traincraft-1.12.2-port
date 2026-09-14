package train.common.entity.digger;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockTorch;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import train.common.core.FakePlayer;

import java.util.List;

public class EntityRotativeWheel extends Entity {

	public double seatX;
	public double seatY;
	public double seatZ;
	public double riderOffset;
	public int seatID;
	public Entity entity;
	private EntityPlayer fakePlayer;
	public int startWheel;
	private int field_9394_d;

	public EntityRotativeWheel(World world) {
		super(world);
		riderOffset = 0;
		setSize(1F, 1F);
		preventEntitySpawning = false;
		isImmuneToFire = true;
		fakePlayer = new FakePlayer(world);
	}

	public EntityRotativeWheel(World world, Entity entity, int numSeat, double x, double y, double z, double riderOffset) {
		this(world);
		this.entity = entity;
		this.seatID = numSeat;
		this.seatX = x;
		this.seatY = y;
		this.seatZ = z;
		this.riderOffset = riderOffset;
	}

	@Override
	protected void entityInit() {}

	@Override
	public boolean canBeCollidedWith() {
		return !isDead;
	}

	@Override
	public void onUpdate() {
		if (entity != null && entity instanceof EntityRotativeDigger) {
			startWheel = ((EntityRotativeDigger) entity).getStart();
		}
		if (fakePlayer == null && this.world != null)
			fakePlayer = new FakePlayer(this.world);

		List listLiving = this.world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(0.4, 0.4, 0.4));
		if (listLiving != null && listLiving.size() > 0 && entity != null && entity instanceof EntityRotativeDigger && ((EntityRotativeDigger) entity).getFuel() > 0) {
			for (int j1 = 0; j1 < listLiving.size(); j1++) {
				float f3 = 0.05F;
				double X = (float) rand.nextGaussian() * f3;
				double Y = (float) rand.nextGaussian() * f3 + 0.2F;
				double Z = (float) rand.nextGaussian() * f3;

				Entity ent = (Entity) listLiving.get(j1);
				if (ent instanceof EntityRotativeDigger) {
					// skip
				}
				else if (ent instanceof EntityLiving) {
					ent.attackEntityFrom(DamageSource.GENERIC, 4);
					ent.addVelocity(X, Y, Z);
				}
				else {
					ent.addVelocity(X, Y, Z);
				}
			}
		}
		if (entity != null && entity instanceof EntityRotativeDigger && ((EntityRotativeDigger) entity).getFuel() > 0) {
			Vec3d vec = new Vec3d(posX - 0.5, posY, posZ - 0.5);
			this.harvestBlock_do(vec);
		}
		if (this.world.isRemote) {
			if (field_9394_d > 0) {
				double d1 = posX + (field_9393_e - posX) / (double) field_9394_d;
				double d5 = posY + (field_9392_f - posY) / (double) field_9394_d;
				double d9 = posZ + (field_9391_g - posZ) / (double) field_9394_d;
				double d12;
				for (d12 = field_9390_h - (double) rotationYaw; d12 < -180D; d12 += 360D) {}
				for (; d12 >= 180D; d12 -= 360D) {}
				rotationYaw += d12 / (double) field_9394_d;
				rotationPitch += (field_9389_i - (double) rotationPitch) / (double) field_9394_d;
				field_9394_d--;
				setPosition(d1, d5, d9);
				setRotation(rotationYaw, rotationPitch);
			}
			else {
				double d2 = posX + motionX;
				double d6 = posY + motionY;
				double d10 = posZ + motionZ;
				setPosition(d2, d6, d10);
				motionX *= 0.99000000953674316D;
				motionZ *= 0.99000000953674316D;
			}
			return;
		}
	}

	private void harvestBlock_do(Vec3d pos) {
		if (pos == null) return;
		BlockPos bp = new BlockPos((int) pos.x, (int) pos.y, (int) pos.z);
		Block id = this.world.getBlockState(bp).getBlock();
		if (id != null && !shouldIgnoreBlockForHarvesting(pos, id)) {
			this.world.setBlockToAir(bp);
		}
	}

	private boolean shouldIgnoreBlockForHarvesting(Vec3d pos, Block id) {
		if (id == null || id instanceof BlockTorch || id instanceof BlockLiquid) {
			return true;
		}
		Block bedrock = Block.getBlockFromName("bedrock");
		if (id == bedrock) return true;
		return false;
	}

	public int getStartWheel() {
		return this.startWheel;
	}

	private int miningTickCounter = 0;

	@SideOnly(Side.CLIENT)
	private void playMiningEffect(Vec3d pos, Block block_index) {
		miningTickCounter++;
		// visual effect stub
	}

	private int getSideFromYaw() {
		if (rotationYaw == 0) return 5;
		if (rotationYaw == 90) return 3;
		if (rotationYaw == 180) return 4;
		if (rotationYaw == 270) return 2;
		return 1;
	}

	@Override
	public void applyEntityCollision(Entity entity) {}

	@Override
	protected void readEntityFromNBT(NBTTagCompound var1) {}

	@Override
	protected void writeEntityToNBT(NBTTagCompound var1) {}

	public void setPositionAndRotation2(double d, double d1, double d2, float f, float f1, int i, boolean teleport) {
		field_9393_e = d;
		field_9392_f = d1;
		field_9391_g = d2;
		field_9390_h = f;
		field_9389_i = f1;
		field_9394_d = i + 4;
		motionX = field_9388_j;
		motionZ = field_9386_l;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void setVelocity(double d, double d1, double d2) {
		field_9388_j = motionX = d;
		field_9386_l = motionZ = d2;
	}

	private double field_9393_e;
	private double field_9392_f;
	private double field_9391_g;
	private double field_9390_h;
	private double field_9389_i;
	private double field_9388_j;
	private double field_9387_k;
	private double field_9386_l;
}
