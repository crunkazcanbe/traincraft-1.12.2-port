package train.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import train.common.Traincraft;
import train.common.entity.digger.EntityRotativeDigger;

public class ItemRotativeDigger extends Item {

	public ItemRotativeDigger() {
		super();
		maxStackSize = 5;
		setCreativeTab(Traincraft.tcTab);
	}

	public boolean canBeStoredInToolbox(ItemStack itemstack) {
		return true;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer entityplayer, EnumHand hand) {
		ItemStack itemstack = entityplayer.getHeldItem(hand);
		RayTraceResult movingobjectposition = rayTrace(world, entityplayer, true);
		if (movingobjectposition == null || movingobjectposition.typeOfHit != RayTraceResult.Type.BLOCK) {
			return new ActionResult<>(EnumActionResult.PASS, itemstack);
		}
		int i = movingobjectposition.getBlockPos().getX();
		int j = movingobjectposition.getBlockPos().getY();
		int k = movingobjectposition.getBlockPos().getZ();
		if (!world.isRemote) {
			world.spawnEntity(new EntityRotativeDigger(world, (float) i + 0.5F, (float) j + 1.5F, (float) k + 0.5F));
		}
		itemstack.shrink(1);
		return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
	}
}
