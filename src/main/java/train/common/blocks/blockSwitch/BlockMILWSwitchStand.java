package train.common.blocks.blockSwitch;

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
import train.common.tile.tileSwitch.TileMILWSwitchStand;

public class BlockMILWSwitchStand extends BlockContainer {

    public BlockMILWSwitchStand() {
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
        return new TileMILWSwitchStand();
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileMILWSwitchStand();
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entityliving, ItemStack stack) {
        super.onBlockPlacedBy(world, pos, state, entityliving, stack);
        TileMILWSwitchStand te = (TileMILWSwitchStand) world.getTileEntity(pos);
        if (te != null) {
            int dir = MathHelper.floor((double) ((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3;
            te.setFacing(EnumFacing.byIndex(dir == 0 ? 2 : dir == 1 ? 5 : dir == 2 ? 3 : 4));
            world.markBlockRangeForRenderUpdate(pos, pos);
        }
    }

    public boolean canProvidePower() {
        return true;
    }
}
