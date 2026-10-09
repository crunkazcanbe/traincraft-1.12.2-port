package train.common.wreck;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import train.common.Traincraft;

import javax.annotation.Nullable;
import java.util.List;

/** Breakdown Crane: wreck recovery (see Wreck). Used by right-clicking the car; the job takes a few seconds and the crew needs you nearby. */
public class ItemBreakdownCrane extends Item {
    public ItemBreakdownCrane() {
        setMaxStackSize(1);
        setMaxDamage(24);
        setCreativeTab(Traincraft.tcTab);
    }

    @Override
    @net.minecraftforge.fml.relauncher.SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tip, ITooltipFlag flag) {
        tip.add(TextFormatting.GRAY + "Right-click a car on its side to stand it back up, or a destroyed one to cut it up for scrap.");
        tip.add(TextFormatting.DARK_GRAY + "The train has to be stopped. Stay within 10 blocks while the crew works.");
    }
}
