package train.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import train.common.Traincraft;
import train.common.tile.TileMetroMadridPole;

public class BlockMetroMadridPole extends Block implements ITileEntityProvider {

    public BlockMetroMadridPole(Material material) {
        super(material);
        setCreativeTab(Traincraft.tcTab);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase living, ItemStack stack) {
        TileMetroMadridPole te = (TileMetroMadridPole) world.getTileEntity(pos);
        int dir = MathHelper.floor((double) ((living.rotationYaw * 4F) / 360F) + 0.5D) & 3;
        if (te != null) {
            te.setFacing(EnumFacing.byIndex(dir == 0 ? 2 : dir == 1 ? 5 : dir == 2 ? 3 : 4));
        }
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileMetroMadridPole();
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileMetroMadridPole();
    }

    @Override
    public net.minecraft.util.EnumBlockRenderType getRenderType(IBlockState state) {
        return net.minecraft.util.EnumBlockRenderType.INVISIBLE;
    }
}
