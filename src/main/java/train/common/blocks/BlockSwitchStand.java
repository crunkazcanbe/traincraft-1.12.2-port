package train.common.blocks;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import train.common.Traincraft;
import train.common.tile.TileSwitchStand;

import java.util.Random;

public class BlockSwitchStand extends Block {

	public BlockSwitchStand() {
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
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileSwitchStand();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World par1World, BlockPos pos, Random par5Random) {
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entityliving, ItemStack stack) {
		super.onBlockPlacedBy(world, pos, state, entityliving, stack);
		TileSwitchStand te = (TileSwitchStand) world.getTileEntity(pos);
		if (te != null) {
			int dir = MathHelper.floor((double) ((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3;
			te.setFacing(EnumFacing.byIndex(dir == 0 ? 2 : dir == 1 ? 5 : dir == 2 ? 3 : 4));
		}
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return true;
		} else {
			int i1 = world.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos));
			int j1 = i1 & 7;
			int k1 = 8 - (i1 & 8);
			world.setBlockState(pos, this.getStateFromMeta(j1 + k1), 3);
			world.playSound((EntityPlayer)null, pos, SoundEvents.UI_BUTTON_CLICK, SoundCategory.BLOCKS, 0.3F, k1 > 0 ? 0.6F : 0.5F);
			world.notifyNeighborsRespectDebug(pos, this, true);

			if (j1 == 1) {
				world.notifyNeighborsRespectDebug(pos.west(), this, true);
			} else if (j1 == 2) {
				world.notifyNeighborsRespectDebug(pos.east(), this, true);
			} else if (j1 == 3) {
				world.notifyNeighborsRespectDebug(pos.north(), this, true);
			} else if (j1 == 4) {
				world.notifyNeighborsRespectDebug(pos.south(), this, true);
			} else if (j1 != 5 && j1 != 6) {
				if (j1 == 0 || j1 == 7) {
					world.notifyNeighborsRespectDebug(pos.up(), this, true);
				}
			} else {
				world.notifyNeighborsRespectDebug(pos.down(), this, true);
			}

			return true;
		}
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		int meta = state.getBlock().getMetaFromState(state);
		if ((meta & 8) > 0) {
			world.notifyNeighborsRespectDebug(pos, this, true);
			int i1 = meta & 7;
			if (i1 == 1) {
				world.notifyNeighborsRespectDebug(pos.west(), this, true);
			} else if (i1 == 2) {
				world.notifyNeighborsRespectDebug(pos.east(), this, true);
			} else if (i1 == 3) {
				world.notifyNeighborsRespectDebug(pos.north(), this, true);
			} else if (i1 == 4) {
				world.notifyNeighborsRespectDebug(pos.south(), this, true);
			} else if (i1 != 5 && i1 != 6) {
				if (i1 == 0 || i1 == 7) {
					world.notifyNeighborsRespectDebug(pos.up(), this, true);
				}
			} else {
				world.notifyNeighborsRespectDebug(pos.down(), this, true);
			}
		}
		super.breakBlock(world, pos, state);
	}

	@Override
	public int getWeakPower(IBlockState state, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return (state.getBlock().getMetaFromState(state) & 8) > 0 ? 15 : 0;
	}

	@Override
	public int getStrongPower(IBlockState state, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		int i1 = state.getBlock().getMetaFromState(state);
		if ((i1 & 8) == 0) return 0;
		int j1 = i1 & 7;
		int sideIdx = side.getIndex();
		return j1 == 0 && sideIdx == 0 ? 15 : (j1 == 7 && sideIdx == 0 ? 15 : (j1 == 6 && sideIdx == 1 ? 15 : (j1 == 5 && sideIdx == 1 ? 15 : (j1 == 4 && sideIdx == 2 ? 15 : (j1 == 3 && sideIdx == 3 ? 15 : (j1 == 2 && sideIdx == 4 ? 15 : (j1 == 1 && sideIdx == 5 ? 15 : 0)))))));
	}

	@Override
	public boolean canProvidePower(IBlockState state) {
		return true;
	}
}
