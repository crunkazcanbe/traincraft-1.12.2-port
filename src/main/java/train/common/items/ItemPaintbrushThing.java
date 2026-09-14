package train.common.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import train.common.Traincraft;

import javax.annotation.Nullable;
import java.util.List;

public class ItemPaintbrushThing extends Item {

    public ItemPaintbrushThing() {
        super();
        maxStackSize = 64;
        setCreativeTab(Traincraft.tcTab);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add("§7" + TextFormatting.GREEN + "Shift-right-click" + TextFormatting.GRAY + " on a train to open the texture menu.");
    }
}
