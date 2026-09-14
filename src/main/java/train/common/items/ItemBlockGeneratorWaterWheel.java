package train.common.items;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class ItemBlockGeneratorWaterWheel extends ItemBlock{

	public ItemBlockGeneratorWaterWheel(Block id) {
		super(id);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack par1ItemStack, @javax.annotation.Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("\u00a77" + "RF generator.");
		tooltip.add("\u00a77" + "Max Production: 5 RF/t.");
		tooltip.add("\u00a77" + "Output at the sides.");
		tooltip.add("\u00a77" + "Orients itself automatically");
		tooltip.add("\u00a77" + "according to water flow direction.");
	}
}
