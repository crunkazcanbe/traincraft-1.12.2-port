package train.common.blocks;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import train.common.Traincraft;

import java.util.Random;

public class BlockOreTC extends BlockFalling {

	/** 0=copperOre, 1=oilSands, 2=petroleum, 3=ballast - matches ItemBlockOreTC's subNames order. */
	public static final PropertyInteger VARIANT = PropertyInteger.create("variant", 0, 3);

	public BlockOreTC() {
		super(Material.ROCK);
		setCreativeTab(Traincraft.tcTab);
		setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, 0));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, VARIANT);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(VARIANT, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(VARIANT);
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
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		if (getMetaFromState(state) == 1) world.scheduleUpdate(pos, this, this.tickRate(world));
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
		if (getMetaFromState(state) == 1) world.scheduleUpdate(pos, this, this.tickRate(world));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> subItems) {
		for (int i = 0; i < 4; i++) {
			subItems.add(new ItemStack(this, 1, i));
		}
	}
}
