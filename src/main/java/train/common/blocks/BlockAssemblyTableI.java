package train.common.blocks;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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
import train.common.tile.TileCrafterTierI;

import java.util.Random;

public class BlockAssemblyTableI extends BlockContainer {

	public BlockAssemblyTableI(Material material) {
		super(material);
		setCreativeTab(Traincraft.tcTab);
	}

	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			if (!player.isSneaking()) {
				TileEntity te = world.getTileEntity(pos);
				if (te instanceof TileCrafterTierI) {
					player.openGui(Traincraft.instance, GuiIDs.CRAFTER_TIER_I, world, pos.getX(), pos.getY(), pos.getZ());
				}
			} else {
				return false;
			}
		}
		return true;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		Random distilRand = new Random();
		TileCrafterTierI tileentitytierI = (TileCrafterTierI) world.getTileEntity(pos);
		if (tileentitytierI != null) {
			label0: for (int l = 0; l < tileentitytierI.getSizeInventory(); l++) {
				if ((l > 9 && l < 18)) { continue; }
				ItemStack itemstack = tileentitytierI.getStackInSlot(l);
				if (itemstack == null) { continue; }
				float f = distilRand.nextFloat() * 0.8F + 0.1F;
				float f1 = distilRand.nextFloat() * 0.8F + 0.1F;
				float f2 = distilRand.nextFloat() * 0.8F + 0.1F;
				do {
					if (itemstack.getCount() <= 0) { continue label0; }
					int i1 = distilRand.nextInt(21) + 10;
					if (i1 > itemstack.getCount()) { i1 = itemstack.getCount(); }
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
		super.onBlockPlacedBy(world, pos, state, entityliving, stack);
		TileCrafterTierI te = (TileCrafterTierI) world.getTileEntity(pos);
		if (te != null) {
			int dir = MathHelper.floor((double) ((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3;
			te.setFacing(EnumFacing.byIndex(dir == 0 ? 2 : dir == 1 ? 5 : dir == 2 ? 3 : 4));
		}
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int meta) {
		return new TileCrafterTierI();
	}
	public net.minecraft.util.EnumBlockRenderType getRenderType(net.minecraft.block.state.IBlockState state) {
		return net.minecraft.util.EnumBlockRenderType.MODEL;
	}
}
