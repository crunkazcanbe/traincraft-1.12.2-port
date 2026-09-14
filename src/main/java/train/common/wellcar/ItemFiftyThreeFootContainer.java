package train.common.wellcar;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemFiftyThreeFootContainer extends ItemBlock {
    public ItemFiftyThreeFootContainer(Block block) {
        super(block);
        setMaxStackSize(1);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        tooltip.add(TextFormatting.GRAY + "A container that you can put items in.");
        tooltip.add(TextFormatting.GRAY + "Can be put on Wellcars.");
        tooltip.add(TextFormatting.GRAY + "Color can be changed with a paintbrush.");
        tooltip.add(TextFormatting.RED + "Warning, a work in progress! Things may break!");
    }
}
