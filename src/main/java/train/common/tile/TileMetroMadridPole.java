package train.common.tile;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class TileMetroMadridPole extends TileEntity {

    private EnumFacing facing;

    public EnumFacing getFacing() {
        if (facing != null) {
            return this.facing;
        }
        return EnumFacing.NORTH;
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
        super.readFromNBT(nbtTag);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbtTag) {
        if (facing != null) {
            nbtTag.setByte("Orientation", (byte) facing.ordinal());
        } else {
            nbtTag.setByte("Orientation", (byte) EnumFacing.NORTH.ordinal());
        }
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
}
