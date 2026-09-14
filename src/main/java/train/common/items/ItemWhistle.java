package train.common.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import train.common.Traincraft;
import train.common.library.Info;

import javax.annotation.Nullable;
import java.util.List;

public class ItemWhistle extends Item {

    public ItemWhistle() {
        super();
        maxStackSize = 64;
        setCreativeTab(Traincraft.tcTab);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add("§7" + TextFormatting.GREEN + "Right click to whistle!");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        world.playSound(null, player.posX, player.posY, player.posZ,
                new SoundEvent(new ResourceLocation(Info.resourceLocation, "whistle")), SoundCategory.PLAYERS, 1F, 1.0F);
        return super.onItemRightClick(world, player, hand);
    }
}
