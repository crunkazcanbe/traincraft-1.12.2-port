package train.common.api;

import com.mojang.authlib.GameProfile;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import mods.railcraft.api.carts.IMinecart;
import mods.railcraft.api.carts.IRoutableCart;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import train.common.blocks.BlockTCRail;
import train.common.blocks.BlockTCRailGag;
import train.common.core.util.TraincraftUtil;
import train.common.items.ItemTCRail;
import train.common.items.ItemTCRail.TrackTypes;
import train.common.library.BlockIDs;
import train.common.tile.TileTCRail;
import train.common.tile.TileTCRailGag;

import java.util.List;

public class EntityBogie extends EntityMinecart {

	public boolean isOnRail;
	public float prevDpdx;
	public float prevDpdz;
	protected float yOffset = 0.0f;
	protected float ySize = 0.0f;
	public int meta;
	public EntityRollingStock entityMainTrain;

	/**
	 * See {@link train.common.api.AbstractTrains#setPosition}: restores 1.7.10 bounding-box
	 * placement ({@code minY = posY - yOffset + ySize}) so the bogie rail-follow Y math resolves
	 * to the original ride height under 1.12.2.
	 */
	@Override
	public void setPosition(double x, double y, double z) {
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		float halfWidth = this.width / 2.0F;
		float h = this.height;
		double boxMinY = y - (double) this.yOffset + (double) this.ySize;
		this.setEntityBoundingBox(new AxisAlignedBB(
				x - halfWidth, boxMinY, z - halfWidth,
				x + halfWidth, boxMinY + (double) h, z + halfWidth));
		if (this.isAddedToWorld() && !this.world.isRemote) {
			this.world.updateEntityWithOptionalForce(this, false);
		}
	}

	/**
	 * Inverse of the {@link #setPosition} shim above -- the half the port was missing.
	 *
	 * 1.7.10's {@code Entity.moveEntity} finished with
	 * {@code posY = boundingBox.minY + yOffset - ySize}. 1.12.2's {@code Entity.move}
	 * finishes with {@code resetPositionToBB()}, whose vanilla body is simply
	 * {@code posY = bb.minY} -- the yOffset term is gone. Without this override the
	 * entity loses {@code yOffset} (0.65) of height on EVERY move() call and sinks into
	 * BlockTCRail's 0.125-high collision slab, where the horizontal sweep (stepHeight is
	 * 0 on EntityMinecart) clamps X/Z displacement to zero. That is why trains held
	 * correct non-zero motion yet never changed position. Added 2026-09-12.
	 */
	@Override
	public void resetPositionToBB() {
		net.minecraft.util.math.AxisAlignedBB bb = this.getEntityBoundingBox();
		this.posX = (bb.minX + bb.maxX) / 2.0D;
		this.posY = bb.minY + (double) this.yOffset - (double) this.ySize;
		this.posZ = (bb.minZ + bb.maxZ) / 2.0D;
		if (this.isAddedToWorld() && !this.world.isRemote) {
			this.world.updateEntityWithOptionalForce(this, false);
		}
	}
	public int entityMainTrainID;
	protected int bogieIndex;
	public double bogieShift;
	protected Side side;


	private int turnProgress;
    private double minecartX;
    private double minecartY;
    private double minecartZ;
    private double minecartYaw;
    private double minecartPitch;
    private int i,j,k;//used for pathfinding, reduces GC overhead



	public EntityBogie(World world) {

		super(world);

		this.isOnRail = false;
		this.prevDpdx = 0F;
		this.prevDpdz = 0F;
		this.world = world;

		if (entityMainTrain != null) {

			setSize(entityMainTrain.width, entityMainTrain.height);
		}

		//this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0, 0.5, 0));
		setCollisionHandler(null);
		this.yOffset = 0.65f;
		//this.setSize(0.1F, 1.98F);
		this.side = FMLCommonHandler.instance().getEffectiveSide();
	}

	public EntityBogie(World world, double d, double d1, double d2, EntityRollingStock mainTrain, int id, int index, double bogieShift) {

		this(world);

		this.entityMainTrain = mainTrain;
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		this.prevPosX = d;
		this.prevPosY = d1;
		this.prevPosZ = d2;
		this.entityMainTrainID = id;
		this.bogieIndex = index;
		this.bogieShift = bogieShift;
		this.setPosition(d, d1 + this.yOffset, d2);
	}

	@Override
	public boolean canBePushed() {

		return false;
	}

	@Override
	public net.minecraft.entity.item.EntityMinecart.Type getType() {
		return net.minecraft.entity.item.EntityMinecart.Type.RIDEABLE;
	}

	/**
	 * Returns a boundingBox used to collide the entity with other entities and blocks. This enables the entity to be pushable on contact, like boats or minecarts.
	 */
	@Override
	public AxisAlignedBB getCollisionBox(Entity par1Entity) {

		return null;
	}

	@Override
	public boolean attackEntityFrom(DamageSource damageSource, float f) {
		return (this.entityMainTrain != null && entityMainTrain.attackEntityFrom(damageSource, f));
	}

	@Override
	public void applyEntityCollision(Entity entity) {

		if (this.entityMainTrain != null && entity != this.entityMainTrain) {

			this.entityMainTrain.applyEntityCollision(entity);
		}
	}

	public void updateDistance() {
		float angle = TraincraftUtil.atan2f((this.posZ - entityMainTrain.posZ), (this.posX - entityMainTrain.posX));
		angle %= 6.28319F;

		if (angle >= 3.14159F) {
			angle -= 6.28319F;
		}

		if (angle < -3.14159F) {
			angle += 6.28319F;
		}

		//System.out.println("distance "+Math.sqrt(dx*dx+dz*dz)+" "+this.entityMainTrain);
		//
		//		double rads = serverRealRotation * Math.PI / 180.0D;
		//		double pitchRads = entityMainTrain.serverRealPitch * Math.PI / 180.0D;
		//		double cos = Math.cos(rads);
		//		double sin = Math.sin(rads);
		//this.setPosition((entityMainTrain.posX - Math.cos(rads) * this.bogieShift), entityMainTrain.posY + ((Math.tan(pitchRads) * -this.bogieShift)+ entityMainTrain.getMountedYOffset()), (entityMainTrain.posZ - Math.sin(rads) * this.bogieShift));
		//this.bogieLoco[i] = new EntityBogie(world, (posX - Math.cos(rads) * this.bogieShift), posY + ((Math.tan(pitchRads) * -this.bogieShift) + getMountedYOffset()), (posZ - Math.sin(rads) * this.bogieShift), this, this.ID, i, this.bogieShift[i]);
		//System.out.println("sin "+ sin);
		//System.out.println("cos "+ cos);
		//if (cos==-1)cos=0;
		//System.out.println("shift "+bogieShift);
		//System.out.println(this.posZ +" Z "+  (posZ - (sin * this.bogieShift)));
		//System.out.println(this.posX +" X "+  (posX - (cos * this.bogieShift)));
		//float anglePitchClient = serverRealPitch*60;
		/*System.out.println("rotation "+serverRealRotation);
		System.out.println(this.posZ +" Z "+  bogieZ1);
		System.out.println(this.posX +" X "+  bogieX1);
		/*System.out.println(this.posX +" X "+  bogieX1);*/
		this.motionX = ((entityMainTrain.posX + (Math.cos(angle) * Math.abs(this.bogieShift))) - this.posX);
		this.motionZ = ((entityMainTrain.posZ + (Math.sin((angle)) * Math.abs(this.bogieShift))) - this.posZ);
		//this.setPosition(bogieX1, this.posY, bogieZ1);


		//		double d = entityMainTrain.posX - this.posX;
		//		double d1 = entityMainTrain.posZ - this.posZ;
		//		double d2 = (float) Math.sqrt((d * d) + (d1 * d1));
		//
		//		double vecX = entityMainTrain.posX - this.posX;
		//		double vecZ = entityMainTrain.posZ - this.posZ;
		//
		//		double vecNorm = (float) Math.sqrt(vecX * vecX + vecZ * vecZ);
		//
		//		double unitX = vecX / vecNorm;
		//		double unitZ = vecZ / vecNorm;
		//
		//		float optDist = (float) -bogieShift;
		//		double stretch = d2 - optDist;
		//
		//		double div = spring();
		//		if (Math.sqrt(entityMainTrain.motionX * entityMainTrain.motionX + entityMainTrain.motionZ * entityMainTrain.motionZ) < 0.17) {
		//			div = 0.049;
		//		}
		//		double springX = div * stretch * vecX * -1;
		//		double springZ = div * stretch * vecZ * -1;
		//
		//		springX = limitForce(springX);
		//		springZ = limitForce(springZ);
		//
		//		/* if (adj1) { ((AbstractTrains) cart1).motionX += springX; ((AbstractTrains) cart1).motionZ += springZ; }
		//		if (adj2) {
		//		System.out.println(entityMainTrain.motionX + " " + entityMainTrain.motionZ);
		//		System.out.println(Math.sqrt(entityMainTrain.motionX*entityMainTrain.motionX + entityMainTrain.motionZ*entityMainTrain.motionZ));
		//
		//		if (Math.abs(entityMainTrain.motionX) > 0.003 || Math.abs(entityMainTrain.motionZ) > 0.003) {
		//			this.motionX -= springX;
		//			this.motionZ -= springZ;
		//		}
		//		else {
		//			this.motionX = 0;
		//			this.motionZ = 0;
		//			entityMainTrain.motionX = 0;
		//			entityMainTrain.motionZ = 0;
		//		}*/
		//		this.motionX -= springX;
		//		this.motionZ -= springZ;
		//		double speedVecX = entityMainTrain.motionX - this.motionX;
		//		double speedVecZ = entityMainTrain.motionZ - this.motionZ;
		//
		//		double dot = speedVecX * unitX + speedVecZ * unitZ;
		//
		//		double divider = damp();
		//		if (Math.sqrt(entityMainTrain.motionX * entityMainTrain.motionX + entityMainTrain.motionZ * entityMainTrain.motionZ) < 0.017) {
		//			divider = 0.2;
		//		}
		//		double dampX = divider * dot * unitX * -1;// 0.4
		//		double dampZ = divider * dot * unitZ * -1;
		//
		//		dampX = limitForce(dampX);
		//		dampZ = limitForce(dampZ);
		//		this.motionX -= dampX;
		//		this.motionZ -= dampZ;
		/*
		 * if (adj1) { ((AbstractTrains) cart1).motionX += dampX; ((AbstractTrains) cart1).motionZ += dampZ; }
		if (adj2) {
		if (Math.abs(entityMainTrain.motionX) > 0.003 || Math.abs(entityMainTrain.motionZ) > 0.003) {
			this.motionX -= dampX;
			this.motionZ -= dampZ;
		}
		else {
			this.motionX = 0;
			this.motionZ = 0;
			entityMainTrain.motionX = 0;
			entityMainTrain.motionZ = 0;
		}
		}*/
	}

	public boolean isOnRail(){
		int bx = MathHelper.floor(this.posX);
		int by = MathHelper.floor(this.posY);
		int bz = MathHelper.floor(this.posZ);
		Block block = this.world.getBlockState(new net.minecraft.util.math.BlockPos(bx, by, bz)).getBlock();
		if(block == net.minecraft.init.Blocks.AIR) {
			block = this.world.getBlockState(new net.minecraft.util.math.BlockPos(bx, by-1, bz)).getBlock();
			if(!(block instanceof BlockRailBase || block == BlockIDs.tcRail.block || block == BlockIDs.tcRailGag.block)) {
				block = this.world.getBlockState(new net.minecraft.util.math.BlockPos(bx, by+1, bz)).getBlock();
			}
		}
		return (block instanceof BlockRailBase || block == BlockIDs.tcRail.block || block == BlockIDs.tcRailGag.block);
	}

	@SideOnly(Side.CLIENT)
	public float getShadowSize() {

		return this.height / 2.0F;
	}

	public boolean processInitialInteract(EntityPlayer entityplayer, net.minecraft.util.EnumHand hand) {

		if (this.entityMainTrain != null) {

			this.entityMainTrain.processInitialInteract(entityplayer, hand);
		}

		return true;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {

		this.entityMainTrainID = nbttagcompound.getInteger("trainID");
		this.bogieIndex = nbttagcompound.getInteger("bogieIndex");
		this.bogieShift = nbttagcompound.getDouble("bogieShift");

		super.readEntityFromNBT(nbttagcompound);
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {

		nbttagcompound.setInteger("trainID", entityMainTrainID);
		nbttagcompound.setInteger("bogieIndex", bogieIndex);
		nbttagcompound.setDouble("bogieShift", bogieShift);

		super.writeEntityToNBT(nbttagcompound);
	}

	public int getMinecartType() {

		return -1;
	}

	public String getDestination() {

		if (this.entityMainTrain != null) {

			return this.entityMainTrain.getDestination();
		}

		return null;
	}

	public boolean setDestination(ItemStack ticket) {

		return (this.entityMainTrain != null && this.entityMainTrain.setDestination(ticket));
	}

	public boolean doesCartMatchFilter(ItemStack stack, EntityMinecart cart) {

		return false;
	}

	/**
	 * Return false if this cart should not call onMinecartPass() and should ignore Powered Rails.
	 *
	 * @return True if this cart should call onMinecartPass().
	 */
	@Override
	public boolean shouldDoRailFunctions() {

		return false;
	}

	@Override
	public double getSlopeAdjustment() {

		return 0;
	}

	/**
	 * Returns the carts max speed when traveling on rails. Carts going faster than 1.1 cause issues
	 * with chunk loading. This value is compared with the rails max speed and the carts current
	 * speed cap to determine the carts current max speed. A normal rail's max speed is 0.4.
	 *
	 * @return Carts max speed.
	 */
	@Override
	public float getMaxCartSpeedOnRail() {

		return 1.8f;
	}

	protected void func_145821_a(int x, int y, int z, double maxSpeed, double slopeAdjustment, Block block, int railMeta) {
		// removed in 1.12.2 - method signature changed
	}
	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate(){
		//super.onUpdate(); // XXX I'll just assume that this is not supposed to be there. Why would you run Vanilla update code, only to run your own code afterwards to do basically the same..?
		
		this.setCurrentCartSpeedCapOnRail(1.8F);
		this.setMaxSpeedAirLateral(1.8F);

		if (!this.world.isRemote) {

			if(this.entityMainTrain == null) {
				this.setDead();
				world.removeEntity(this);
			}
			
			this.prevPosX = this.posX;
			this.prevPosY = this.posY;
			this.prevPosZ = this.posZ;

			i = MathHelper.floor((double)this.posX);
			j = MathHelper.floor((double)this.posY);
			k = MathHelper.floor((double)this.posZ);
			Block block = this.world.getBlockState(new net.minecraft.util.math.BlockPos(i, j - 1, k)).getBlock();

			if (block instanceof BlockRailBase || block == BlockIDs.tcRail.block || block == BlockIDs.tcRailGag.block) {
				j--;
			} else {
				block = this.world.getBlockState(new net.minecraft.util.math.BlockPos(i, j + 1, k)).getBlock();
				if(block instanceof BlockRailBase || block == BlockIDs.tcRail.block || block == BlockIDs.tcRailGag.block){
					j++;
				} else {
					block = this.world.getBlockState(new net.minecraft.util.math.BlockPos(i, j, k)).getBlock();
				}
			}

			if (block instanceof BlockRailBase) {
				super.onUpdate();
				this.setPosition(this.posX, this.posY + yOffset - 0.3d, this.posZ);
				// System.out.println("Server Y: " + this.posY);
			} else {
		        	TileEntity tileEntity = this.world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k));
		        	TileTCRail tileRail;

					if (tileEntity instanceof TileTCRailGag) {
						TileTCRailGag tileGag = (TileTCRailGag) tileEntity;
						tileEntity = this.world.getTileEntity(new net.minecraft.util.math.BlockPos(tileGag.originX, tileGag.originY, tileGag.originZ));
					}

					if (tileEntity instanceof TileTCRail) {

						tileRail = (TileTCRail) tileEntity;
					}
					else {
						super.onUpdate();
						return;
					}

					//applyDragAndPushForces();
					limitSpeedOnTCRail();

					if (ItemTCRail.isTCTurnTrack(tileRail)) {

						int meta = tileRail.getFacing();

					if (shouldIgnoreSwitch(tileRail, i, j, k, meta)) {
						moveOnTCStraight(j, tileRail.getPos().getX(), tileRail.getPos().getZ(), tileRail.getFacing());
					} else {
						if (ItemTCRail.isTCTurnTrack(tileRail))
							moveOnTC90TurnRail(j, tileRail.r, tileRail.cx, tileRail.cz);
					}
					
					// shouldIgnoreSwitch(tileRail, i, j, k, meta);
					// if (ItemTCRail.isTCTurnTrack(tileRail)) moveOnTC90TurnRail(i, j, k,
					// tileRail.r, tileRail.cx, tileRail.cy, tileRail.cz, tileRail.getType(), meta);
					}

					if (ItemTCRail.isTCStraightTrack(tileRail)) {

						moveOnTCStraight(j, tileRail.getPos().getX(), tileRail.getPos().getZ(), tileRail.getFacing());
					}

					else if (ItemTCRail.isTCTwoWaysCrossingTrack(tileRail)) {

					moveOnTCTwoWaysCrossing();
					}

					else if (ItemTCRail.isTCSlopeTrack(tileRail)) {

						moveOnTCSlope(j, tileRail.getPos().getX(), tileRail.getPos().getZ(), tileRail.slopeAngle, tileRail.slopeHeight, tileRail.getFacing());
					}
			}
			// this.func_145775_I(); // removed in 1.12.2
			this.rotationPitch = 0.0F;


			@SuppressWarnings("rawtypes")
			List list = this.world.getEntitiesWithinAABBExcludingEntity(this, getCollisionHandler()!=null?
					getCollisionHandler().getMinecartCollisionBox(this):
					this.getEntityBoundingBox().expand(0.2D, 0.0D, 0.2D));

			if (list != null && !list.isEmpty()) {
				for(i = 0; i < list.size(); ++i) {
					this.applyEntityCollision((Entity)list.get(i));
				}
			}

		} else if (this.turnProgress > 0) {
			this.rotationYaw = (float)(this.rotationYaw + MathHelper.wrapDegrees((double)this.minecartYaw - this.rotationYaw) / this.turnProgress);
			this.rotationPitch = (float)(this.rotationPitch + (this.minecartPitch - this.rotationPitch) / this.turnProgress);
			--this.turnProgress;
			this.setPosition(this.posX + (this.minecartX - this.posX) / this.turnProgress
					, this.posY + (this.minecartY - this.posY) / this.turnProgress,
					this.posZ + (this.minecartZ - this.posZ) / this.turnProgress);
			this.setRotation(this.rotationYaw, this.rotationPitch);
		} else {
			this.setPosition(this.posX, this.posY, this.posZ);
			this.setRotation(this.rotationYaw, this.rotationPitch);
		}

		if (posX == 0 && posZ == 0) {
			setDead();
			world.removeEntity(this);
		}
	}

	private void moveOnTCStraight(int j, double cx, double cz, int meta) {
		posY=j;
		if (meta == 2 || meta == 0) {
			double norm = Math.sqrt(motionX * motionX + motionZ * motionZ);

			setPosition(cx + 0.5, posY + yOffset+this.ySize+0.5, posZ);
			//setPosition(posX, posY + yOffset, posZ);

			motionX = 0;
			motionZ = Math.copySign(norm, motionZ);
			this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0, 0 , Math.copySign(norm, this.motionZ)));

			net.minecraft.util.math.AxisAlignedBB bb1 = this.getEntityBoundingBox();
			setPosition((bb1.minX + bb1.maxX) *0.5,
					bb1.minY + this.yOffset - this.ySize-0.5,
					(bb1.minZ + bb1.maxZ)*0.5
			);

			//System.out.println("straight z "+Math.copySign(norm, motionZ));
		}
		if (meta == 1 || meta == 3) {

			setPosition(posX, posY + yOffset+this.ySize+0.5, cz + 0.5);
			//setPosition(posX, posY + yOffset, posZ);

			motionX = Math.copySign(Math.sqrt(motionX * motionX + motionZ * motionZ), motionX);
			motionZ = 0;
			this.setEntityBoundingBox(this.getEntityBoundingBox().offset(motionX, 0 , 0));

			net.minecraft.util.math.AxisAlignedBB bb2 = this.getEntityBoundingBox();
			setPosition((bb2.minX + bb2.maxX) *0.5,
					bb2.minY + this.yOffset - this.ySize-0.5,
					(bb2.minZ + bb2.maxZ)*0.5
			);

			//System.out.println("straight x "+Math.copySign(norm, motionX));
		}
	}

	private void moveOnTCTwoWaysCrossing() {
		/*
		 * Nitro-Note: Do we need all those shitty motionX and Z? We don't even
		 * need something to parse to this function. setPosition is superflous since you can't place
		 * trains down on 2 way crossings.
		 */
		 //this.posY = j;// + 0.2D;
		//System.out.println(l);
		//if(l==2||l==0)this.move(net.minecraft.entity.MoverType.SELF, motionX, 0.0D, 0.0D);
		//if(l==1||l==3)this.move(net.minecraft.entity.MoverType.SELF, 0.0D, 0.0D, motionZ);
		//if(Math.abs(motionX)>Math.abs(motionZ))System.out.println("X");
		//if(Math.abs(motionZ)>Math.abs(motionX))System.out.println("Z");
		
		double norm = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
		
		if (Math.abs(motionZ) > Math.abs(motionX)) {

			// this.setPosition(this.posX, this.posY + this.yOffset, cz + 0.5D);
			this.move(net.minecraft.entity.MoverType.SELF, 0.0D, 0.0D, Math.copySign(norm, this.motionZ));

			// this.motionX = 0.0D;
			// this.motionZ = Math.copySign(norm, this.motionZ);
		}
		else {
			
			// double norm = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
			
			// this.setPosition(cx + 0.5D, this.posY + this.yOffset, this.posZ);
			this.move(net.minecraft.entity.MoverType.SELF, Math.copySign(norm, this.motionX), 0.0D, 0.0D);

			// this.motionX = Math.copySign(norm, this.motionX);
			// this.motionZ = 0.0D;
		}

	}
	private void moveOnTCSlope(int j, double cx, double cz, double slopeAngle, double slopeHeight, int meta) {

		posY = j + 0.5;
		if (meta == 2 || meta == 0) {

			if (meta == 2) {
				cz += 1;
			}

			double norm = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
			double newPosY = Math.abs(j + (Math.tan(slopeAngle * Math.abs(cz - this.posZ))) + this.yOffset + 0.3);
			this.setPosition(cx + 0.5D, newPosY, this.posZ);

			this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0, 0 , Math.copySign(norm, this.motionZ)));
			this.posX = (this.getEntityBoundingBox().minX + this.getEntityBoundingBox().maxX) / 2.0D;
			this.posY = this.getEntityBoundingBox().minY + (double)this.yOffset - (double)this.ySize;
			this.posZ = (this.getEntityBoundingBox().minZ + this.getEntityBoundingBox().maxZ) / 2.0D;

			this.motionX = 0.0D;
			this.motionY = 0.0D;
			this.motionZ = Math.copySign(norm, this.motionZ);
		} else if (meta == 1 || meta == 3) {
			if (meta == 1) {
				cx += 1;
			}

			double norm = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
			double newPosY = (j + (Math.tan(slopeAngle * Math.abs(cx - this.posX))) + this.yOffset + 0.3);
			this.setPosition(this.posX, newPosY, cz + 0.5D);

			this.setEntityBoundingBox(this.getEntityBoundingBox().offset(Math.copySign(norm, this.motionX), 0 ,0));
			this.posX = (this.getEntityBoundingBox().minX + this.getEntityBoundingBox().maxX) / 2.0D;
			this.posY = this.getEntityBoundingBox().minY + (double)this.yOffset - (double)this.ySize;
			this.posZ = (this.getEntityBoundingBox().minZ + this.getEntityBoundingBox().maxZ) / 2.0D;

			this.motionX = Math.copySign(norm, this.motionX);
			this.motionY = 0.0D;
			this.motionZ = 0.0D;
		}
	}

	private void moveOnTC90TurnRail(int j,double r, double cx, double cz){
		posY = j;// + 0.2;
		double cpx = posX - cx;
		double cpz = posZ - cz;
		double cp_norm = Math.sqrt(cpx * cpx + cpz * cpz);

		double vnorm = Math.sqrt(motionX * motionX + motionZ * motionZ);

		double vx2 = -(cpz/cp_norm) * vnorm;//-v
		double vz2 = (cpx/cp_norm) * vnorm;//u

		double px2_cx = (posX + motionX) - cx;
		double pz2_cz = (posZ + motionZ) - cz;

		double p2_c_norm = Math.sqrt((px2_cx * px2_cx) + (pz2_cz * pz2_cz));

		double p_corr_x = cx + ((cpx / cp_norm) * r);
		double p_corr_z = cz + ((cpz / cp_norm) * r);

		setPosition(p_corr_x, posY + yOffset, p_corr_z);

		this.move(net.minecraft.entity.MoverType.SELF, motionX= Math.copySign(vx2, cx + ((px2_cx/p2_c_norm) * r) - posX),
				0.0D,
				motionZ=Math.copySign(vz2, cz + ((pz2_cz/p2_c_norm) * r) - posZ));
	}
	private boolean shouldIgnoreSwitch(TileTCRail tile, int i, int j, int k, int meta) {
		if (tile != null
				&& (tile.getType().equals(TrackTypes.MEDIUM_RIGHT_TURN.getLabel())
						|| tile.getType().equals(TrackTypes.MEDIUM_LEFT_TURN.getLabel())
						|| tile.getType().equals(TrackTypes.LARGE_LEFT_TURN.getLabel())
						|| tile.getType().equals(TrackTypes.LARGE_RIGHT_TURN.getLabel()))
				&& tile.canTypeBeModifiedBySwitch) {
			if (meta == 2) {
				if (motionZ > 0 && Math.abs(motionX) < 0.01) {
					TileEntity tile2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k + 1));
					if (tile2 instanceof TileTCRail) {
						((TileTCRail) tile2).setSwitchState(false, true);
					}
					return true;
				}
			}
			if (meta == 0) {
				if (motionZ < 0 && Math.abs(motionX) < 0.01) {
					TileEntity tile2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i, j, k - 1));
					if (tile2 instanceof TileTCRail) {
						((TileTCRail) tile2).setSwitchState(false, true);
					}
					return true;
				}
			}
			if (meta == 1) {
				if (Math.abs(motionZ) < 0.01 && motionX > 0) {
					TileEntity tile2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i + 1, j, k));
					if (tile2 instanceof TileTCRail) {
						((TileTCRail) tile2).setSwitchState(false, true);
					}
					return true;
				}
			}
			if (meta == 3) {
				if (Math.abs(motionZ) < 0.01 && motionX < 0) {
					TileEntity tile2 = world.getTileEntity(new net.minecraft.util.math.BlockPos(i - 1, j, k));
					if (tile2 instanceof TileTCRail) {
						((TileTCRail) tile2).setSwitchState(false, true);
					}
					return true;
				}
			}
		}
		return false;
	}
	private void limitSpeedOnTCRail() {

		/*
		Block id = world.getBlockState(new net.minecraft.util.math.BlockPos(x, y, z)).getBlock();

		if (!BlockRailBase.isRailBlock(id)) {

			return;
		}

		railMaxSpeed = ((BlockRailBase) Block.blocksList[id]).getRailMaxSpeed(world, this, x, y, z);
		 */

		//double railMaxSpeed = 3; // XXX Really? Define a field for THAT? Come on..
		double maxSpeed = Math.min(3.0D, getMaxCartSpeedOnRail());

		if (this.motionX < -maxSpeed) {

			this.motionX = -maxSpeed;
		}
		else if (this.motionX > maxSpeed) {

			this.motionX = maxSpeed;
		}

		if (this.motionZ < -maxSpeed) {

			this.motionZ = -maxSpeed;
		}
		else if (this.motionZ > maxSpeed) {

			this.motionZ = maxSpeed;
		}
	}

	public GameProfile getOwner() {
		return  this.entityMainTrain.getOwner();
	}
	
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotation2(double p_70056_1_, double p_70056_3_, double p_70056_5_, float p_70056_7_,
			float p_70056_8_, int p_70056_9_) {
		// super.setPositionAndRotation2 removed in 1.12.2
		this.minecartX = p_70056_1_;
		this.minecartY = p_70056_3_;
		this.minecartZ = p_70056_5_;
		this.minecartYaw = p_70056_7_;
		this.minecartPitch = p_70056_8_;
		this.turnProgress = p_70056_9_ + 2;
	}

	/**
	 * 1.12 delivers the server's position updates through setPositionAndRotationDirect. This mod
	 * still carried the 1.7.10 name (setPositionAndRotation2), which 1.12 never calls, so the
	 * client copy never moved: the real train drove off and a frozen "ghost" stayed behind.
	 */
	@Override
	@net.minecraftforge.fml.relauncher.SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
	public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int increments, boolean teleport) {
		setPositionAndRotation2(x, y, z, yaw, pitch, increments);
	}
}