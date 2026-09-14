package train.common.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import train.common.Traincraft;
import train.common.tile.TileBridgePillar;

public class BlockBridgePillar extends BlockContainer {

	public BlockBridgePillar() {
		super(Material.WOOD);
		setCreativeTab(Traincraft.tcTab);
	}

	@Override
	public boolean isFullBlock(IBlockState state) { return false; }

	@Override
	public boolean isOpaqueCube(IBlockState state) { return false; }

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileBridgePillar();
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entityliving, ItemStack stack) {
		int l = MathHelper.floor((double)(entityliving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		int i1 = world.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos)) >> 2;
		++l; l %= 4;
		int meta = (l == 0 ? 2 : l == 1 ? 3 : l == 2 ? 0 : 1) | i1 << 2;
		world.setBlockState(pos, this.getStateFromMeta(meta), 2);
	}
}
