package train.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import train.common.Traincraft;
import train.common.library.BlockIDs;
import train.common.tile.TileTCRailGag;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockTCRailGag extends Block {

	public BlockTCRailGag() {
		super(Material.IRON);
		setCreativeTab(Traincraft.tcTab);
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		return false;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileTCRailGag) {
			TileTCRailGag tileEntity = (TileTCRailGag) tile;
			BlockPos origin = new BlockPos(tileEntity.originX, tileEntity.originY, tileEntity.originZ);
			world.destroyBlock(origin, false);
			world.removeTileEntity(origin);
		}
		world.removeTileEntity(pos);
	}

	@Override
	public int quantityDropped(Random random) {
		return 0;
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return ItemStack.EMPTY;
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos fromPos) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileTCRailGag) {
			TileTCRailGag tileEntity = (TileTCRailGag) tile;
			BlockPos origin = new BlockPos(tileEntity.originX, tileEntity.originY, tileEntity.originZ);
			if (world.isAirBlock(origin)) {
				world.destroyBlock(pos, false);
				world.removeTileEntity(pos);
				return;
			}
			if (!world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) && world.getBlockState(pos.down()).getBlock() != BlockIDs.bridgePillar.block) {
				world.destroyBlock(pos, false);
				world.removeTileEntity(pos);
			}
		}
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileTCRailGag) {
			float bbHeight = ((TileTCRailGag) tile).bbHeight;
			return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, bbHeight, 1.0D);
		}
		return FULL_BLOCK_AABB;
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileTCRailGag();
	}

	@Override
	public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return false;
	}

	@Nullable
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileTCRailGag && !((TileTCRailGag) tile).type.equals("null")) {
			float bbHeight = ((TileTCRailGag) tile).bbHeight;
			return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, bbHeight, 1.0D);
		}
		return NULL_AABB;
	}
}
