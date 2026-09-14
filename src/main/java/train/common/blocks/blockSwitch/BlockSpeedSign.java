package train.common.blocks.blockSwitch;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
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
import train.common.tile.tileSwitch.TileSpeedSign;

public class BlockSpeedSign extends Block implements ITileEntityProvider {

    private int skinstate = 0;

    public BlockSpeedSign() {
        super(Material.WOOD);
        setCreativeTab(Traincraft.tcTab);
    }

    public void setSkinstate(int skinstate) {
        this.skinstate = skinstate;
    }

    public int getSkinstate() {
        return skinstate;
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
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entityliving, ItemStack stack) {
        super.onBlockPlacedBy(world, pos, state, entityliving, stack);
        TileSpeedSign te = (TileSpeedSign) world.getTileEntity(pos);
        if (te != null) {
            int dir = MathHelper.floor((double) ((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3;
            te.setFacing(EnumFacing.byIndex(dir == 0 ? 2 : dir == 1 ? 5 : dir == 2 ? 3 : 4));
            te.setSkinstate(0);
            world.markBlockRangeForRenderUpdate(pos, pos);
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        TileSpeedSign te = (TileSpeedSign) world.getTileEntity(pos);
        if (te != null) {
            te.increaseSkinState();
            world.markBlockRangeForRenderUpdate(pos, pos);
        }
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileSpeedSign();
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileSpeedSign();
    }
}
