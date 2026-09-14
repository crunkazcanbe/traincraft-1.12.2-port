package train.common.core;

import com.mojang.authlib.GameProfile;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.FoodStats;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.UUID;

/**
 * Fake player entity, which is used in calls requiring player instance. All methods are modified to do nothing, to prevent modification of player stats.
 * 
 * @author MightyPork
 * @copy 2012
 */
public class FakePlayer extends EntityPlayer {
	/**
	 * Create fake player in world
	 * 
	 * @param world the world
	 */
	public FakePlayer(World world) {
		super(world, new GameProfile(new UUID(0,0),""));
		inventory = new InventoryPlayer(this);
		inventory.currentItem = 0;
		inventory.setInventorySlotContents(0, new ItemStack(Items.DIAMOND_PICKAXE, 1, 0));
		flyToggleTimer = 0;
		// score = 0;
		boolean isSwinging = false; // Maybe spawnForced ??
		xpCooldown = 0;
		timeUntilPortal = 20;
		inPortal = false;
		capabilities = new PlayerCapabilities();
		speedInAir = 0.02F;
		fishEntity = null;
		this.inventoryContainer = new ContainerPlayer(this.inventory, !world.isRemote, this);
		this.openContainer = this.inventoryContainer;
	}

	@Override
	public boolean isCreative() {
		return false;
	}

	@Override
	public boolean isSpectator() {
		return false;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
	}

	@Override
	public void onUpdate() {}

	@Override
	public void preparePlayerToSpawn() {}

	@Override
	protected void updateEntityActionState() {}

	@Override
	public void onLivingUpdate() {}

	@Override
	public void onDeath(DamageSource damagesource) {
		super.onDeath(damagesource);
	}


	@Override
	public boolean canHarvestBlock(IBlockState state) {
		return true;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound) {}

	@Override
	public int getTotalArmorValue() {
		return 0;
	}

	@Override
	public double getYOffset() {
		return 0;
	}

	@Override
	public void swingArm(EnumHand hand) {}

	@Override
	public void attackTargetEntityWithCurrentItem(Entity entity) {}

	@Override
	public void onCriticalHit(Entity entity) {}

	@Override
	public void onEnchantmentCritical(Entity entity) {}

	@Override
	public void respawnPlayer() {}

	@Override
	public void setDead() {
		super.setDead();
	}

	@Override
	public boolean isEntityInsideOpaqueBlock() {
		return false;
	}

	@Override
	public void sendMessage(ITextComponent chatComponent) {}

	@Override
	public void addStat(StatBase statbase, int i) {}

	@Override
	public void jump() {}

	@Override
	public void addMovementStat(double d, double d1, double d2) {}

	@Override
	public void fall(float f, float damageMultiplier) {}

	@Override
	public int xpBarCap() {
		return 1000;
	}

	@Override
	public void addExhaustion(float f) {}

	@Override
	public FoodStats getFoodStats() {
		return foodStats;
	}

	@Override
	public boolean canEat(boolean flag) {
		return false;
	}

	@Override
	public boolean shouldHeal() {
		return false;
	}

	@Override
	protected int getExperiencePoints(EntityPlayer entityplayer) {
		return 0;
	}

	@Override
	protected boolean isPlayer() {
		return true;
	}

	@Override
	public boolean canUseCommand(int var1, String var2) {
		return false;
	}

	@Override
	public BlockPos getPosition() {
		return null;
	}

}
