package train.common.items;

import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import train.common.Traincraft;
import train.common.library.ItemIDs;

import javax.annotation.Nullable;
import java.util.List;

@Optional.Interface(iface = "buildcraft.api.tools.IToolWrench", modid = "buildcraftcore")
public class ItemWrench extends ItemPart implements buildcraft.api.tools.IToolWrench {

	public ItemWrench() {
		super(ItemIDs.composite_wrench.iconName);
		maxStackSize = 1;
		setCreativeTab(Traincraft.tcTab);
	}

	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		Block blockId = world.getBlockState(pos).getBlock();
		if (blockId.rotateBlock(world, pos, facing)) {
			player.swingArm(hand);
			return world.isRemote ? EnumActionResult.PASS : EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack par1ItemStack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("§7" + "Works same as a BuildCraft wrench.");
		tooltip.add("§7" + "Use it to change lantern color.");
		tooltip.add("§7" + "Use it to lock/unlock certain carts (passenger)");
		tooltip.add("§7" + "Use it to remove locked trains (OP only)");
	}

	@Optional.Method(modid = "buildcraftcore")
	@Override
	public boolean canWrench(EntityPlayer player, EnumHand hand, ItemStack wrench, RayTraceResult rayTrace) {
		return true;
	}

	@Optional.Method(modid = "buildcraftcore")
	@Override
	public void wrenchUsed(EntityPlayer player, EnumHand hand, ItemStack wrench, RayTraceResult rayTrace) {}

	@Override
	public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player) {
		return true;
	}
}
