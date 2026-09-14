package train.common.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import train.common.library.BlockIDs;
import train.common.tile.TileTCRail;
import train.common.tile.TileTCRailGag;

import javax.annotation.Nullable;
import java.util.List;

public class ItemTrackDebugger extends Item {
    public ItemTrackDebugger() {
        super();
        maxStackSize = 1;
        setCreativeTab(null);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            if (!(player.canUseCommand(2, "") && player.capabilities.isCreativeMode)) {
                player.sendMessage(new TextComponentString("You are not allowed to do that!"));
                return EnumActionResult.FAIL;
            }
            Block block = world.getBlockState(pos).getBlock();
            if (block == BlockIDs.tcRail.block) {
                TileTCRail tile = (TileTCRail) world.getTileEntity(pos);
                if (tile != null) {
                    player.sendMessage(new TextComponentString(TextFormatting.RED + "TileTCRail"));
                    player.sendMessage(new TextComponentString(TextFormatting.GOLD + "Name: " + TextFormatting.WHITE + tile.getType()));
                    player.sendMessage(new TextComponentString(TextFormatting.GOLD + "x: " + TextFormatting.WHITE + tile.getPos().getX() + TextFormatting.GOLD + " y: " + TextFormatting.WHITE + tile.getPos().getY() + TextFormatting.GOLD + " z: " + TextFormatting.WHITE + tile.getPos().getZ()));
                    player.sendMessage(new TextComponentString(TextFormatting.GOLD + "Meta: " + TextFormatting.WHITE + tile.getBlockMetadata()));
                    player.sendMessage(new TextComponentString(TextFormatting.GOLD + "cx: " + TextFormatting.WHITE + tile.cx + TextFormatting.GOLD + " cy: " + TextFormatting.WHITE + tile.cy + TextFormatting.GOLD + " cz: " + TextFormatting.WHITE + tile.cz));
                    player.sendMessage(new TextComponentString(TextFormatting.GOLD + "r: " + TextFormatting.WHITE + tile.r));
                    player.sendMessage(new TextComponentString(TextFormatting.GOLD + "SwitchState: " + TextFormatting.WHITE + tile.getSwitchState()));
                    player.sendMessage(new TextComponentString(TextFormatting.GOLD + "LinkedX: " + TextFormatting.WHITE + tile.linkedX + TextFormatting.GOLD + " LinkedY: " + TextFormatting.WHITE + tile.linkedY + TextFormatting.GOLD + " LinkedZ: " + TextFormatting.WHITE + tile.linkedZ));
                    player.sendMessage(new TextComponentString(TextFormatting.GOLD + "SlopeLength: " + TextFormatting.WHITE + tile.slopeLength + TextFormatting.GOLD + " SlopeHeight: " + TextFormatting.WHITE + tile.slopeHeight + TextFormatting.GOLD + " SlopeAngle: " + TextFormatting.WHITE + tile.slopeAngle));
                    player.sendMessage(new TextComponentString(TextFormatting.GOLD + "SlopeLength2: " + TextFormatting.WHITE + tile.slopeLength));
                    player.sendMessage(new TextComponentString(" "));
                }
            } else if (block == BlockIDs.tcRailGag.block) {
                TileTCRailGag tile = (TileTCRailGag) world.getTileEntity(pos);
                if (tile != null) {
                    player.sendMessage(new TextComponentString(TextFormatting.GREEN + "TileTCGag"));
                    player.sendMessage(new TextComponentString(TextFormatting.GOLD + "Name: " + TextFormatting.WHITE + tile.type));
                    player.sendMessage(new TextComponentString(TextFormatting.GOLD + "x: " + TextFormatting.WHITE + tile.getPos().getX() + TextFormatting.GOLD + " y: " + TextFormatting.WHITE + tile.getPos().getY() + TextFormatting.GOLD + " z: " + TextFormatting.WHITE + tile.getPos().getZ()));
                    player.sendMessage(new TextComponentString(TextFormatting.GOLD + "OriginX: " + TextFormatting.WHITE + tile.originX + TextFormatting.GOLD + " OriginY: " + TextFormatting.WHITE + tile.originY + TextFormatting.GOLD + " OriginZ: " + TextFormatting.WHITE + tile.originZ));
                    player.sendMessage(new TextComponentString(TextFormatting.GOLD + "BBHeight: " + TextFormatting.WHITE + tile.bbHeight));
                    player.sendMessage(new TextComponentString(" "));
                }
            } else if (block instanceof BlockRailBase) {
                player.sendMessage(new TextComponentString(TextFormatting.BLUE + "BlockRailBase"));
            } else {
                player.sendMessage(new TextComponentString("Not a rail"));
                return EnumActionResult.FAIL;
            }
        }
        return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add("§7Gets TileEntityData for current track");
    }
}
