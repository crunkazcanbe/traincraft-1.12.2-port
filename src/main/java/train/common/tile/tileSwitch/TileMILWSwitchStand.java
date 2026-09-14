package train.common.tile.tileSwitch;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import train.common.library.BlockIDs;
import train.common.tile.TileTraincraft;

import java.util.Random;

public class TileMILWSwitchStand extends TileTraincraft implements ITickable {

    private int updateTicks = 0;
    private static Random rand = new Random();
    private EnumFacing facing;

    @Override
    public void readFromNBT(NBTTagCompound nbtTag, boolean forSyncing) {
        facing = EnumFacing.byIndex(nbtTag.getByte("Orientation"));
    }

    @Override
    public void update() {
        updateTicks++;
        if (!world.isRemote) {
            if (updateTicks % 20 == 0) {
                BlockPos above = pos.up();
                if (!world.isAirBlock(above)) {
                    Block block = world.getBlockState(above).getBlock();
                    if (block != null) {
                        EntityItem entityitem = new EntityItem(world, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(Item.getItemFromBlock(BlockIDs.MILWSwitchStand.block), 1));
                        float f3 = 0.05F;
                        entityitem.motionX = (float) rand.nextGaussian() * f3;
                        entityitem.motionY = (float) rand.nextGaussian() * f3 + 0.2F;
                        entityitem.motionZ = (float) rand.nextGaussian() * f3;
                        world.spawnEntity(entityitem);
                    }
                    world.setBlockToAir(pos);
                }
                syncTileEntity();
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbtTag, boolean forSyncing) {
        if (facing != null) {
            nbtTag.setByte("Orientation", (byte) facing.ordinal());
        } else {
            nbtTag.setByte("Orientation", (byte) EnumFacing.NORTH.ordinal());
        }
        return nbtTag;
    }

    public EnumFacing getFacing() {
        if (facing != null) {
            return this.facing;
        }
        return EnumFacing.NORTH;
    }

    public void setFacing(EnumFacing face) {
        if (facing != face)
            this.facing = face;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(pos.getX() - 1, pos.getY() - 1, pos.getZ() - 1, pos.getX() + 2, pos.getY() + 2, pos.getZ() + 2);
    }
}
