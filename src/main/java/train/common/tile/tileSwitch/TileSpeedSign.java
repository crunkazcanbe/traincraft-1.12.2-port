package train.common.tile.tileSwitch;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;

public class TileSpeedSign extends TileEntity {

    private int skinstate;
    private EnumFacing facing;

    public EnumFacing getFacing() {
        if (facing != null) {
            return this.facing;
        }
        return EnumFacing.NORTH;
    }

    public void setSkinstate(int skinstate) {
        if (world != null) {
            world.markBlockRangeForRenderUpdate(pos, pos);
        }
        this.skinstate = skinstate;
    }

    public int getSkinstate() {
        return skinstate;
    }

    public void increaseSkinState() {
        if (skinstate >= 4) {
            skinstate = 0;
        } else {
            skinstate++;
        }
        if (world != null) {
            world.markBlockRangeForRenderUpdate(pos, pos);
        }
    }

    public void setFacing(EnumFacing face) {
        if (world != null) {
            world.markBlockRangeForRenderUpdate(pos, pos);
        }
        this.facing = face;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTag) {
        if (nbtTag.hasKey("Orientation")) {
            facing = EnumFacing.byIndex(nbtTag.getByte("Orientation"));
        }
        if (nbtTag.hasKey("skinstate")) {
            skinstate = nbtTag.getInteger("skinstate");
        }
        super.readFromNBT(nbtTag);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbtTag) {
        if (facing != null) {
            nbtTag.setByte("Orientation", (byte) facing.ordinal());
        } else {
            nbtTag.setByte("Orientation", (byte) EnumFacing.NORTH.ordinal());
        }
        nbtTag.setInteger("skinstate", this.skinstate);
        super.writeToNBT(nbtTag);
        return nbtTag;
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        return new SPacketUpdateTileEntity(this.pos, 1, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound());
    }

    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(pos.getX() - 1, pos.getY() - 1, pos.getZ() - 1, pos.getX() + 2, pos.getY() + 2, pos.getZ() + 2);
    }
}
