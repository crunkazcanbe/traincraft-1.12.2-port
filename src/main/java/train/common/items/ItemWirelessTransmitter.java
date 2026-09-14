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

public class ItemWirelessTransmitter extends Item {

    public ItemWirelessTransmitter()  {
        setCreativeTab(Traincraft.tcTab);

    }
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack par1ItemStack, @javax.annotation.Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add("\u00a77" + "Allows non W-MTC trains use W-MTC");
        tooltip.add("\u00a77" + "Put this in the first inventory slot of a locomotive");
    }
}
