package train.common.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import train.common.Traincraft;
import train.common.library.ItemIDs;
import train.common.tile.TileSignal;

import java.util.Random;

public class BlockSignal extends BlockContainer {

	public BlockSignal() {
		super(Material.CIRCUITS);
		this.setLightLevel(1.0F);
		setCreativeTab(Traincraft.tcTab);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random random, int fortune) {
		return ItemIDs.signal.item;
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}

	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	public int tickRate() {
		return 4;
	}

	public void onBlockPlacedByOld(World world, BlockPos pos, IBlockState state, EntityLivingBase entityliving, ItemStack stack) {
		TileSignal te = (TileSignal) world.getTileEntity(pos);
		int var6 = MathHelper.floor((double) (entityliving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		int var7 = world.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos)) >> 2;
		++var6;
		var6 %= 4;
		if (var6 == 0) { if (te != null) te.setFacing(2 | var7 << 2); }
		if (var6 == 1) { if (te != null) te.setFacing(3 | var7 << 2); }
		if (var6 == 2) { if (te != null) te.setFacing(0 | var7 << 2); }
		if (var6 == 3) { if (te != null) te.setFacing(1 | var7 << 2); }
		world.scheduleUpdate(pos, this, 4);
		updateTick(world, pos);
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		super.onBlockAdded(world, pos, state);
		TileSignal te = (TileSignal) world.getTileEntity(pos);
		if (te != null && world.isBlockPowered(pos)) {
			te.state = 1;
		}
		updateTick(world, pos);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		updateTick(world, pos);
		return true;
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, net.minecraft.block.Block block, BlockPos fromPos) {
		TileSignal te = (TileSignal) world.getTileEntity(pos);
		if (te == null) return;
		if (te.state == 1 && !world.isBlockPowered(pos)) {
			world.scheduleUpdate(pos, this, 4);
		} else if (te.state == 0 && world.isBlockPowered(pos)) {
			te.state = 1;
		}
		updateTick(world, pos);
	}

	public void updateTick(World world, BlockPos pos) {
		TileSignal te = (TileSignal) world.getTileEntity(pos);
		if (te == null) return;
		if (te.state == 1 && !world.isBlockPowered(pos)) { te.state = 0; }
		if (te.state == 0 && world.isBlockPowered(pos)) { te.state = 1; }
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileSignal();
	}
}
