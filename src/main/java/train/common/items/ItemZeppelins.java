package train.common.items;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import train.common.Traincraft;
import train.common.core.handlers.ConfigHandler;
import train.common.entity.zeppelin.EntityZeppelinOneBalloon;
import train.common.entity.zeppelin.EntityZeppelinTwoBalloons;

import javax.annotation.Nullable;
import java.util.List;

public class ItemZeppelins extends Item {
	private int type;

	public ItemZeppelins(int type) {
		super();
		maxStackSize = 5;
		setCreativeTab(Traincraft.tcTab);
		this.type = type;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer entityplayer, EnumHand hand) {
		ItemStack itemstack = entityplayer.getHeldItem(hand);
		if (!world.isRemote && !ConfigHandler.ENABLE_ZEPPELIN) {
			entityplayer.sendMessage(new TextComponentString("Zeppelin has been deactivated by the OP"));
			return new ActionResult<>(EnumActionResult.PASS, itemstack);
		}
		RayTraceResult movingobjectposition = rayTrace(world, entityplayer, true);
		if (movingobjectposition == null || movingobjectposition.typeOfHit != RayTraceResult.Type.BLOCK) {
			return new ActionResult<>(EnumActionResult.PASS, itemstack);
		}
		int i = movingobjectposition.getBlockPos().getX();
		int j = movingobjectposition.getBlockPos().getY();
		int k = movingobjectposition.getBlockPos().getZ();
		if (!world.isRemote) {
			if (type == 0) world.spawnEntity(new EntityZeppelinTwoBalloons(world, (float) i + 0.5F, (float) j + 1.5F, (float) k + 0.5F));
			if (type == 1) world.spawnEntity(new EntityZeppelinOneBalloon(world, (float) i + 0.5F, (float) j + 1.5F, (float) k + 0.5F));
		}
		itemstack.shrink(1);
		return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack par1ItemStack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("§7" + "More info in the guidebook.");
	}
}
