package train.common.items;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import train.common.Traincraft;

import javax.annotation.Nullable;
import java.util.List;

public class ItemATOCard extends Item {

    public ItemATOCard()  {
        setCreativeTab(Traincraft.tcTab);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack par1ItemStack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add("§7" + "Allows you to use ATO on W-MTC equipped trains");
        tooltip.add("§7" + "Put this in the second inventory slot of a locomotive");
    }
}
