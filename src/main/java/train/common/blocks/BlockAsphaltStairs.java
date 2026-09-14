package train.common.blocks;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import train.common.Traincraft;

public class BlockAsphaltStairs extends BlockStairs {
    public BlockAsphaltStairs(IBlockState modelState) {
        super(modelState);
        this.setCreativeTab(Traincraft.tcTab);
        this.useNeighborBrightness = true;
    }
}
