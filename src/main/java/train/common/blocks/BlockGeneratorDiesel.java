/*******************************************************************************
 * Copyright (c) 2013 Spitfire4466. All rights reserved.
 *
 * @name TrainCraft
 * @author Spitfire4466
 ******************************************************************************/

package train.common.blocks;

import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import train.common.Traincraft;
import train.common.library.GuiIDs;
import train.common.tile.TileGeneratorDiesel;

import java.util.Random;

public class BlockGeneratorDiesel extends BlockContainer {

	/** Packs the same "facing (low 2 bits) | extra state (upper 2 bits)" data the old int-metadata code used. */
	public static final PropertyInteger DATA = PropertyInteger.create("data", 0, 15);

	public BlockGeneratorDiesel() {
		super(Material.IRON);
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
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntity te = world.getTileEntity(pos);
		if (player.isSneaking()) {
			return false;
		}
		if (!world.isRemote) {
			if (te instanceof TileGeneratorDiesel) {
				player.openGui(Traincraft.instance, GuiIDs.GENERATOR_DIESEL, world, pos.getX(), pos.getY(), pos.getZ());
			}
		}
		return true;
	}

	/**
	 * Lets the block know when one of its neighbor changes.
	 */
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos fromPos) {
		boolean flag = world.isBlockPowered(pos);
		TileEntity tile = world.getTileEntity(pos);

		if (tile instanceof TileGeneratorDiesel) {
			((TileGeneratorDiesel) tile).powered = flag;
		}
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase living, ItemStack stack) {
		TileEntity teRaw = world.getTileEntity(pos);
		TileGeneratorDiesel te = teRaw instanceof TileGeneratorDiesel ? (TileGeneratorDiesel) teRaw : null;
		int var6 = MathHelper.floor((double) (living.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		int var7 = state.getValue(DATA) >> 2;
		++var6;
		var6 %= 4;

		int facing;
		if (var6 == 0) facing = 2;
		else if (var6 == 1) facing = 3;
		else if (var6 == 2) facing = 0;
		else facing = 1;

		int data = facing | (var7 << 2);
		if (te != null) {
			te.setFacing(data);
		}
		world.setBlockState(pos, state.withProperty(DATA, data), 2);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		int l = state.getValue(DATA) & 3;
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileGeneratorDiesel && ((TileGeneratorDiesel) tile).currentBurnTime > 0) {
			double d0 = pos.getX() + 0.5D;
			double d2 = pos.getZ() + 0.5D;
			double d3 = 1.67D;
			double py = pos.getY() + d3;
			switch (l) {
				case 0:
					for (int i = 0; i < 40; i++) {
						world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + 0.2, py, d2 - 0.42, 0.0D, 0.0D, 0.0D);
					}
					break;
				case 1:
					for (int i = 0; i < 40; i++) {
						world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + 0.42, py, d2 + 0.2, 0.0D, 0.0D, 0.0D);
					}
					break;
				case 2:
					for (int i = 0; i < 40; i++) {
						world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - 0.2, py, d2 + 0.42, 0.0D, 0.0D, 0.0D);
					}
					break;
				case 3:
					for (int i = 0; i < 40; i++) {
						world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - 0.42, py, d2 - 0.2, 0.0D, 0.0D, 0.0D);
					}
					break;
				default:
					break;
			}
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileGeneratorDiesel();
	}
}
