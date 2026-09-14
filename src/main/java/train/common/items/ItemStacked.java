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
import train.common.library.ItemIDs;

import java.util.List;

public class ItemStacked extends Item {

	public ItemStacked(int maxUse) {
		super();
		maxStackSize = 1;
		this.setMaxDamage(maxUse);
		setCreativeTab(Traincraft.tcTab);
	}

	@Override
	public boolean isFull3D() {
		return true;
	}

	@Override
	public boolean shouldRotateAroundWhenRendering() {
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack par1ItemStack, @javax.annotation.Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("\u00a77" + "Right click on a rolling stock");
		tooltip.add("\u00a77" + " to enter attaching mode.");
		tooltip.add("\u00a77" + "Click a few time to reset links.");
		tooltip.add("\u00a77" + "Sneak+Right click on a locomotive");
		tooltip.add("\u00a77" + " to set mode: 'Can pull/Can be pulled'");
	}
}