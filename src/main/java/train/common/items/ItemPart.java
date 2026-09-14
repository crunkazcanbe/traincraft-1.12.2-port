package train.common.items;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;
import train.common.Traincraft;
import train.common.library.Info;

/**
 * @author canitzp
 */
public class ItemPart extends Item{

    protected String iconName = "";
    protected String folder = "parts";

    public ItemPart(String iconName){
        this.iconName = iconName;
        this.setMaxStackSize(64);
        this.setCreativeTab(Traincraft.tcTab);
    }

    public ItemPart overridePath(String newFolder){
        this.folder = newFolder;
        return this;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(TextureMap iconRegister) {
        // texture registration handled by ModelLoader in 1.12.2
    }

}
