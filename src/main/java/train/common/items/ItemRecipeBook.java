package train.common.items;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import train.common.Traincraft;
import train.common.library.GuiIDs;

import javax.annotation.Nullable;
import java.util.List;

public class ItemRecipeBook extends Item {

	public static int page   = 0;
	public static int recipe = 0;

	public ItemRecipeBook() {
		super();
		maxStackSize = 1;
		setCreativeTab(Traincraft.tcTab);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		NBTTagCompound var3 = stack.getTagCompound();
		if (var3 == null) {
			var3 = new NBTTagCompound();
			stack.setTagCompound(var3);
		}
		stack.getTagCompound().setInteger("currPage", ItemRecipeBook.page);
		stack.getTagCompound().setInteger("currRecipe", ItemRecipeBook.recipe);
		player.openGui(Traincraft.instance, GuiIDs.RECIPE_BOOK, world, (int) player.posX, (int) player.posY, (int) player.posZ);
		return new ActionResult<>(EnumActionResult.SUCCESS, stack);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack par1ItemStack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("§7" + "Contains everything");
		tooltip.add("§7" + "you should know");
		tooltip.add("§7" + "about Traincraft.");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean hasEffect(ItemStack par1ItemStack) {
		return true;
	}

	@Override
	public boolean getShareTag() {
		return true;
	}
}
