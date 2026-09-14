package train.client.gui;


import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(new net.minecraft.util.math.BlockPos(x, y, z));
        if (ID == 1) {
            if (player.getRidingEntity() != null) {
                return new GuiMTCInfo(player.getRidingEntity());

            } else {
                return null;
            }

        } else if (ID == 2) {

            if (player.getRidingEntity() != null) {

                return new GuiSpeedTransmitter(te);

            } else {
                return null;
            }

        }


        return null;
    }
}
