package train.common.items;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import train.common.Traincraft;

public class ItemMetroMadridPole extends Item {
    private Block spawnID;

    public ItemMetroMadridPole(Block block) {
        super();
        spawnID = block;
        setCreativeTab(Traincraft.tcTab);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer entityplayer, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemstack = entityplayer.getHeldItem(hand);
        int i = pos.getX(), j = pos.getY(), k = pos.getZ();
        Block i1 = world.getBlockState(pos).getBlock();
        if (i1 == Blocks.SNOW_LAYER) {
            facing = EnumFacing.UP;
        } else if (i1 != Blocks.VINE) {
            i += facing.getXOffset();
            j += facing.getYOffset();
            k += facing.getZOffset();
        }
        if (itemstack.isEmpty()) {
            return EnumActionResult.PASS;
        }
        BlockPos targetPos = new BlockPos(i, j, k);
        if (world.setBlockState(targetPos, spawnID.getDefaultState(), 3)) {
            IBlockState placed = world.getBlockState(targetPos);
            if (placed.getBlock() == spawnID) {
                spawnID.onBlockPlacedBy(world, targetPos, placed, entityplayer, itemstack);
                SoundType sound = spawnID.getSoundType(placed, world, targetPos, entityplayer);
                world.playSound(null, targetPos, sound.getPlaceSound(), SoundCategory.BLOCKS, (sound.getVolume() + 1.0F) / 2.0F, sound.getPitch() * 0.8F);
            }
            itemstack.shrink(1);
        }
        return EnumActionResult.SUCCESS;
    }
}
