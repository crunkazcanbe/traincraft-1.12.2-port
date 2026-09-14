package train.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import train.common.Traincraft;
import train.common.library.GuiIDs;
import train.common.tile.TileTrainWbench;

import java.util.Random;

public class BlockTrainWorkbench extends BlockContainer {

	public BlockTrainWorkbench(int j) {
		super(Material.WOOD);
		setCreativeTab(Traincraft.tcTab);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntity te = world.getTileEntity(pos);
		if (player.isSneaking()) {
			return false;
		}
		if (!world.isRemote) {
			if (te != null && te instanceof TileTrainWbench) {
				player.openGui(Traincraft.instance, GuiIDs.TRAIN_WORKBENCH, world, pos.getX(), pos.getY(), pos.getZ());
			}
		}
		return true;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		Random distilRand = new Random();
		TileTrainWbench tilewb = (TileTrainWbench) world.getTileEntity(pos);
		if (tilewb != null) {
			label0: for (int l = 0; l < tilewb.getSizeInventory(); l++) {
				ItemStack itemstack = tilewb.getStackInSlot(l);
				if (itemstack == null) {
					continue;
				}
				float f = distilRand.nextFloat() * 0.8F + 0.1F;
				float f1 = distilRand.nextFloat() * 0.8F + 0.1F;
				float f2 = distilRand.nextFloat() * 0.8F + 0.1F;
				do {
					if (itemstack.getCount() <= 0) {
						continue label0;
					}
					int i1 = distilRand.nextInt(21) + 10;
					if (i1 > itemstack.getCount()) {
						i1 = itemstack.getCount();
					}
					itemstack.shrink(i1);
					EntityItem entityitem = new EntityItem(world, pos.getX() + f, pos.getY() + f1, pos.getZ() + f2, itemstack.splitStack(i1));
					float f3 = 0.05F;
					entityitem.motionX = (float) distilRand.nextGaussian() * f3;
					entityitem.motionY = (float) distilRand.nextGaussian() * f3 + 0.2F;
					entityitem.motionZ = (float) distilRand.nextGaussian() * f3;
					world.spawnEntity(entityitem);
				} while (true);
			}
		}
		super.breakBlock(world, pos, state);
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		super.onBlockAdded(world, pos, state);
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entityliving, ItemStack stack) {
		TileTrainWbench te = (TileTrainWbench) world.getTileEntity(pos);
		if (te != null) {
			int dir = MathHelper.floor((double) ((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3;
			// dir 0=South, 1=West, 2=North, 3=East
			EnumFacing f = dir == 0 ? EnumFacing.SOUTH : dir == 1 ? EnumFacing.WEST : dir == 2 ? EnumFacing.NORTH : EnumFacing.EAST;
			te.setFacing(f);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileTrainWbench();
	}
	public net.minecraft.util.EnumBlockRenderType getRenderType(net.minecraft.block.state.IBlockState state) {
		return net.minecraft.util.EnumBlockRenderType.MODEL;
	}
}
