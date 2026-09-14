package train.common.items;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import train.common.Traincraft;
import train.common.library.Info;

import java.util.List;

public class ItemChunkLoaderActivator extends Item {

	public ItemChunkLoaderActivator() {
		super();
		maxStackSize = 1;
		setCreativeTab(Traincraft.tcTab);
		setMaxDamage(10);
	}
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack par1ItemStack, @javax.annotation.Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("\u00a77" + "Right click on a Locomotive");
		tooltip.add("\u00a77" + " to start/stop chunk loading.");
		tooltip.add("\u00a77" + "Locomotives will load chunks");
		tooltip.add("\u00a77" + "around attached carts.");
	}
}
