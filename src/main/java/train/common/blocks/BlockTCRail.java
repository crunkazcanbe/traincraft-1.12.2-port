package train.common.blocks;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import train.common.Traincraft;
import train.common.items.ItemTCRail;
import train.common.items.ItemWrench;
import train.common.library.BlockIDs;
import train.common.tile.TileTCRail;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockTCRail extends Block {

	public BlockTCRail() {
		super(Material.IRON);
		setCreativeTab(Traincraft.tcTab);
	}

	@Override
	public boolean canPlaceBlockAt(World par1World, BlockPos pos) {
		return false;
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		TileTCRail tileEntity = (TileTCRail) world.getTileEntity(pos);
		if (tileEntity != null && tileEntity.idDrop != null) {
			return new ItemStack(tileEntity.idDrop);
		}
		return ItemStack.EMPTY;
	}

	@Override
	public int quantityDropped(Random random) {
		return 0;
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileTCRail tileEntity = (TileTCRail) world.getTileEntity(pos);
		if (tileEntity != null && tileEntity.isLinkedToRail) {
			BlockPos linked = new BlockPos(tileEntity.linkedX, tileEntity.linkedY, tileEntity.linkedZ);
			world.destroyBlock(linked, false);
			world.removeTileEntity(linked);
		}
		if (tileEntity != null && (tileEntity.idDrop != null) && !world.isRemote) {
			EntityPlayer player = Traincraft.proxy.getPlayer();
			if (!(player != null && player.capabilities.isCreativeMode)) {
				Block.spawnAsEntity(world, pos, new ItemStack(tileEntity.idDrop, 1, 0));
			}
		}
		world.removeTileEntity(pos);
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos fromPos) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile == null || !(tile instanceof TileTCRail)) return;

		TileTCRail tileEntity = (TileTCRail) tile;
		if (tileEntity.isLinkedToRail) {
			BlockPos linked = new BlockPos(tileEntity.linkedX, tileEntity.linkedY, tileEntity.linkedZ);
			if (world.isAirBlock(linked)) {
				world.removeTileEntity(pos);
				world.destroyBlock(pos, false);
			}
		}
		if (!world.getBlockState(pos.down()).isSideSolid(world, pos.down(), net.minecraft.util.EnumFacing.UP) && world.getBlockState(pos.down()).getBlock() != BlockIDs.bridgePillar.block) {
			world.destroyBlock(pos, false);
			world.removeTileEntity(pos);
		}
		if (tileEntity != null && !world.isRemote) {
			boolean flag = world.isBlockPowered(pos);
			if (tileEntity.previousRedstoneState != flag) {
				tileEntity.changeSwitchState(world, tileEntity, pos.getX(), pos.getY(), pos.getZ());
				tileEntity.previousRedstoneState = flag;
			}
		}
	}

	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	/** Flat outline box so the rail selects like a rail, not a full cube. */
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		return TC_RAIL_AABB;
	}

	/**
	 * Flat collision box (1/8 block). Without this the origin BlockTCRail fell back to the default
	 * full-block collision, which made the player stand a whole block above the track.
	 */
	@Nullable
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		return TC_RAIL_AABB;
	}

	private static final AxisAlignedBB TC_RAIL_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D);

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileTCRail();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		TileEntity te = world.getTileEntity(pos);
		int l = state.getBlock().getMetaFromState(state);
		if (!world.isRemote && te != null && (te instanceof TileTCRail)) {
			if (player != null && player.inventory != null && player.inventory.getCurrentItem() != null && (player.inventory.getCurrentItem().getItem() instanceof ItemWrench) && ((TileTCRail) te).getType() != null && ((TileTCRail) te).getType().equals(ItemTCRail.TrackTypes.SMALL_STRAIGHT.getLabel())) {
				// The rail block has no direction in its metadata on 1.12 (it is always 0); the real
				// direction lives on the tile. Rotate that, or the wrench does nothing the trains see.
				l = (((TileTCRail) te).getFacing() + 1) % 4;
				((TileTCRail) te).setFacing(l);
				((TileTCRail) te).markDirty();
				world.notifyBlockUpdate(pos, state, state, 3);
				((TileTCRail) te).hasRotated = true;
				return true;
			}
		}
		return false;
	}
}
