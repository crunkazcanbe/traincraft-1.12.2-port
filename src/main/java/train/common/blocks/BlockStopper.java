/*******************************************************************************
 * Copyright (c) 2012 Mrbrutal. All rights reserved.
 * 
 * @name TrainCraft
 * @author Mrbrutal
 ******************************************************************************/

package train.common.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import train.common.Traincraft;
import train.common.tile.TileStopper;

public class BlockStopper extends BlockContainer {

	public BlockStopper() {
		super(Material.IRON);
		setCreativeTab(Traincraft.tcTab);
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
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		return world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP);
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase living, ItemStack stack) {
		TileStopper te = (TileStopper) world.getTileEntity(pos);
		int meta = state.getBlock().getMetaFromState(state);
		int var6 = MathHelper.floor(living.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		int var7 = meta >> 2;
		++var6;
		var6 %= 4;

		if (te != null) {
			if (var6 == 0) te.setFacing(2 | var7 << 2);
			else if (var6 == 1) te.setFacing(3 | var7 << 2);
			else if (var6 == 2) te.setFacing(0 | var7 << 2);
			else te.setFacing(1 | var7 << 2);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileStopper(meta);
	}
}
