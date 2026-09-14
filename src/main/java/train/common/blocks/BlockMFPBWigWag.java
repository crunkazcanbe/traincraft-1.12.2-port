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
import train.common.tile.TileMFPBWigWag;

public class BlockMFPBWigWag extends Block implements ITileEntityProvider {

    public BlockMFPBWigWag() {
        super(Material.ROCK);
        setCreativeTab(Traincraft.tcTab);
        this.setTickRandomly(true);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public net.minecraft.util.EnumBlockRenderType getRenderType(IBlockState state) {
        return net.minecraft.util.EnumBlockRenderType.INVISIBLE;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileMFPBWigWag();
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileMFPBWigWag();
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entityliving, ItemStack stack) {
        super.onBlockPlacedBy(world, pos, state, entityliving, stack);
        TileMFPBWigWag te = (TileMFPBWigWag) world.getTileEntity(pos);
        if (te != null) {
            int dir = MathHelper.floor((double) ((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3;
            te.setFacing(EnumFacing.byIndex(dir == 0 ? 2 : dir == 1 ? 5 : dir == 2 ? 3 : 4));
            world.markBlockRangeForRenderUpdate(pos, pos);
        }
    }
}
