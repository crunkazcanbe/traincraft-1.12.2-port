package train.common.blocks;

import net.minecraft.util.math.BlockPos;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import train.common.Traincraft;
import train.common.tile.TileWindMill;

import java.util.Random;

public class BlockWindMill extends Block {
	/** Packs the same "facing (low 2 bits) | extra state (upper 2 bits)" data the old int-metadata code used. */
	public static final PropertyInteger DATA = PropertyInteger.create("data", 0, 15);

	public BlockWindMill() {
		super(Material.WOOD);
		setCreativeTab(Traincraft.tcTab);
		this.setTickRandomly(true);
		this.setDefaultState(this.blockState.getBaseState().withProperty(DATA, 0));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, DATA);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(DATA, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(DATA);
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
		return new TileWindMill();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random random) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileWindMill && ((TileWindMill) tile).windClient > 0) {
			if (random.nextInt(20) == 0) {
				world.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.ENTITY_MINECART_INSIDE, SoundCategory.BLOCKS,
						random.nextFloat() * 0.25F + 0.1F, random.nextFloat() * 1F - 0.6F, true);
			}
		}
	}

	/**
	 * Called when the block is placed in the world.
	 */
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		int l = MathHelper.floor((double) (placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		int i1 = state.getValue(DATA) >> 2;
		++l;
		l %= 4;

		int facing;
		if (l == 0) facing = 2;
		else if (l == 1) facing = 3;
		else if (l == 2) facing = 0;
		else facing = 1;

		world.setBlockState(pos, state.withProperty(DATA, facing | (i1 << 2)), 2);
	}

	/**
	 * ejects contained items into the world, and notifies neighbours of an update, as appropriate
	 */
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileWindMill) {
			tile.invalidate();
		}
		super.breakBlock(world, pos, state);
	}
}
