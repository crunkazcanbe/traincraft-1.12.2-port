package train.common.blocks.blockSwitch;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import train.common.Traincraft;
import train.common.tile.tileSwitch.TilekSignal;

public class BlockkSignal extends BlockContainer {

    public BlockkSignal() {
        super(Material.ROCK);
        setCreativeTab(Traincraft.tcTab);
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
        return new TilekSignal();
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TilekSignal();
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        super.onBlockAdded(world, pos, state);
        TilekSignal te = (TilekSignal) world.getTileEntity(pos);
        if (te != null && world.isBlockPowered(pos)) {
            te.state = 1;
        }
        updateTick(world, pos);
    }

    public int tickRate() {
        return 4;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entityliving, ItemStack stack) {
        super.onBlockPlacedBy(world, pos, state, entityliving, stack);
        TilekSignal te = (TilekSignal) world.getTileEntity(pos);
        if (te != null) {
            int dir = MathHelper.floor((double) ((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3;
            te.setFacing(EnumFacing.byIndex(dir == 0 ? 2 : dir == 1 ? 5 : dir == 2 ? 3 : 4));
            world.markBlockRangeForRenderUpdate(pos, pos);
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        updateTick(world, pos);
        return true;
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, net.minecraft.block.Block block, BlockPos fromPos) {
        TilekSignal te = (TilekSignal) world.getTileEntity(pos);
        if (te == null) return;
        if (te.state == 1 && !world.isBlockPowered(pos)) {
            world.scheduleUpdate(pos, this, 4);
        } else if (te.state == 0 && world.isBlockPowered(pos)) {
            te.state = 1;
        }
        updateTick(world, pos);
    }

    public void updateTick(World world, BlockPos pos) {
        TilekSignal te = (TilekSignal) world.getTileEntity(pos);
        if (te == null) return;
        if (te.state == 1 && !world.isBlockPowered(pos)) { te.state = 0; }
        if (te.state == 0 && world.isBlockPowered(pos)) { te.state = 1; }
    }
}
