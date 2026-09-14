package train.common.blocks;

import net.minecraft.block.BlockContainer;
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
import train.common.tile.TileEmbeddedStopper;

public class BlockEmbeddedStopper extends BlockContainer {

    public BlockEmbeddedStopper() {
        super(Material.IRON);
        setCreativeTab(Traincraft.tcTab);
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
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return world.isSideSolid(pos.down(), EnumFacing.UP);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase living, ItemStack stack) {
        TileEmbeddedStopper te = (TileEmbeddedStopper) world.getTileEntity(pos);
        int var6 = MathHelper.floor((double) (living.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        int var7 = world.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos)) >> 2;
        ++var6;
        var6 %= 4;
        if (var6 == 0) { if (te != null) te.setFacing(2 | var7 << 2); }
        if (var6 == 1) { if (te != null) te.setFacing(3 | var7 << 2); }
        if (var6 == 2) { if (te != null) te.setFacing(0 | var7 << 2); }
        if (var6 == 3) { if (te != null) te.setFacing(1 | var7 << 2); }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEmbeddedStopper(meta);
    }
}
