package train.common.entity.rollingStock;

import train.common.api.SeatCfg;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import train.common.Traincraft;
import train.common.api.LiquidManager;
import train.common.api.SteamTrain;
import train.common.core.FakePlayer;
import train.common.core.util.TraincraftUtil;
import train.common.library.EnumTrains;
import train.common.library.GuiIDs;

import java.util.Random;

public class EntityLocoSteamSnowPlow extends SteamTrain {
	public EntityLocoSteamSnowPlow(World world) {
		super(world, EnumTrains.locoSteamSnowPlow.getTankCapacity(), LiquidManager.WATER_FILTER);
		initLocoSteam();
	}

	public void initLocoSteam() {
		fuelTrain = 0;
		locoInvent = new ItemStack[inventorySize];
	}

	public EntityLocoSteamSnowPlow(World world, double d, double d1, double d2) {
		this(world);
		setPosition(d, d1 + yOffset, d2);
		motionX = 0.0D;
		motionY = 0.0D;
		motionZ = 0.0D;
		prevPosX = d;
		prevPosY = d1;
		prevPosZ = d2;
	}

	@Override
	public void setDead() {
		super.setDead();
		isDead = true;
	}

	@Override
	public void pressKey(int i) {
		if (i == 7 && riddenByEntity != null && riddenByEntity instanceof EntityPlayer) {
			((EntityPlayer) riddenByEntity).openGui(Traincraft.instance, GuiIDs.LOCO, world, (int) this.posX, (int) this.posY, (int) this.posZ);
		}
	}

	private static final double[][]	blockpos	= { { 4, 0, 1 }, { 4, 0, -1 }, { 4, 0, 0 }};
	private double[] point1;
	private FakePlayer fakePlayer = null;
	private int rotation =0;

	private static final float radianF = (float) Math.PI / 180.0f;
	@Override
	public void onUpdate() {
		super.onUpdate();
		if (world.isRemote || bogieLoco==null) {
			return;
		}
		checkInvent(locoInvent[0], locoInvent[1], this);
		if (fakePlayer == null){
			 fakePlayer = new FakePlayer(world);
		}
		rotation = MathHelper.floor(TraincraftUtil.atan2degreesf(
				bogieLoco.posZ - posZ,
				bogieLoco.posX - posX));

		point1 = rotateVec3(blockpos[0], getPitch(), rotation);
		point1[0] += posX;point1[1] += posY;point1[2] += posZ;
		mineSnow(world, point1, locoInvent, fakePlayer);
		point1[1]++;
		mineSnow(world, point1, locoInvent, fakePlayer);
		point1[1]++;
		mineSnow(world, point1, locoInvent, fakePlayer);


		point1 = rotateVec3(blockpos[1], getPitch(), rotation);
		point1[0] += posX;point1[1] += posY;point1[2] += posZ;
		mineSnow(world, point1, locoInvent, fakePlayer);
		point1[1]++;
		mineSnow(world, point1, locoInvent, fakePlayer);
		point1[1]++;
		mineSnow(world, point1, locoInvent, fakePlayer);


		point1 = rotateVec3(blockpos[2], getPitch(), rotation);
		point1[0] += posX;point1[1] += posY+1;point1[2] += posZ;
		mineSnow(world, point1, locoInvent, fakePlayer);
		point1[1]++;
		mineSnow(world, point1, locoInvent, fakePlayer);

	}

	private static void mineSnow(World world, double[] point, ItemStack[] locoInvent, FakePlayer fakePlayer){
		BlockPos pos = new BlockPos(MathHelper.floor(point[0]),MathHelper.floor(point[1]),MathHelper.floor(point[2]));
		Block b = world.getBlockState(pos).getBlock();

		if((b == Blocks.SNOW || b == Blocks.SNOW_LAYER) && b.canHarvestBlock(world, pos, fakePlayer)){
			world.setBlockToAir(pos);
			int snowballs = new Random().nextInt(9);
			for(int i=2; i<locoInvent.length && snowballs>0; i++){
				if (locoInvent[i] == null){
					locoInvent[i] = new ItemStack(Items.SNOWBALL, snowballs);
					snowballs--;
				} else if (locoInvent[i].getItem() == Items.SNOWBALL && locoInvent[i].getCount() < Items.SNOWBALL.getItemStackLimit()){
					while (locoInvent[i].getCount() < locoInvent[i].getMaxStackSize() && snowballs >0){
						locoInvent[i].setCount(locoInvent[i].getCount() + 1);
						snowballs--;
					}
				}
				if (snowballs ==0){
					break;
				}
			}
			if (snowballs >0){
				EntityItem entityitem = new EntityItem(world, point[0], point[1] + 1, point[2], new ItemStack(Items.SNOWBALL, snowballs));
				entityitem.setPickupDelay(10);
				world.spawnEntity(entityitem);

			}
		}
	}

	private static double[] rotateVec3(double[] offset, float pitch, float yaw) {
		double[] xyz = new double[]{offset[0],offset[1],offset[2]};
		//rotate pitch
		if (pitch != 0.0F) {
			pitch *= radianF;

			xyz[0] = (offset[0] * Math.cos(pitch));
			xyz[1] = (offset[0] * Math.sin(pitch));
		}
		//rotate yaw
		if (yaw != 0.0F) {
			yaw *= radianF;
			double cos = MathHelper.cos(yaw);
			double sin = MathHelper.sin(yaw);

			xyz[0] = (offset[0] * cos) - (offset[2] * sin);
			xyz[2] = (offset[0] * sin) + (offset[2] * cos);
		}
		return xyz;
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		super.writeEntityToNBT(nbttagcompound);

		nbttagcompound.setShort("fuelTrain", (short) fuelTrain);
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < locoInvent.length; i++) {
			if (locoInvent[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				locoInvent[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbttagcompound.setTag("Items", nbttaglist);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		super.readEntityFromNBT(nbttagcompound);

		fuelTrain = nbttagcompound.getShort("fuelTrain");
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
		locoInvent = new ItemStack[getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound1.getByte("Slot") & 0xff;
			if (j >= 0 && j < locoInvent.length) {
				locoInvent[j] = new ItemStack(nbttagcompound1);
			}
		}
	}

	@Override
	public int getSizeInventory() {
		return inventorySize;
	}

	@Override
	public String getName() {
		return "Steam Snow Plow";
	}

	@Override
	public boolean processInitialInteract(EntityPlayer entityplayer, net.minecraft.util.EnumHand hand) {
		playerEntity = entityplayer;
		if ((super.processInitialInteract(entityplayer, hand))) {
			return false;
		}
		if (!world.isRemote) {
			if (riddenByEntity != null && (riddenByEntity instanceof EntityPlayer) && riddenByEntity != entityplayer) {
				return true;
			}
			entityplayer.startRiding(this);
		}
		return true;
	}

	@Override
	public float getOptimalDistance(EntityMinecart cart) {
		return (0.7F);
	}
	@Override
	public boolean canBeAdjusted(EntityMinecart cart) {
		return canBeAdjusted;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
	}
}