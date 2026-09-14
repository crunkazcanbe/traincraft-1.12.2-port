package train.common.blocks;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import train.common.Traincraft;
import train.common.items.ItemWrench;
import train.common.library.GuiIDs;
import train.common.tile.TileLantern;

import java.util.Random;

public class BlockLantern extends Block {

	public BlockLantern() {
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
		return new TileLantern();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		double d0 = pos.getX() + 0.5;
		double d1 = pos.getY() + 0.22;
		double d2 = pos.getZ() + 0.5;
		world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D);
		world.spawnParticle(EnumParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (player.isSneaking()) {
			return false;
		}
		TileEntity te = world.getTileEntity(pos);
		if (player.getHeldItem(hand).getItem() instanceof ItemWrench) {
			if (te != null && te instanceof TileLantern) {
				player.openGui(Traincraft.instance, GuiIDs.LANTERN, world, pos.getX(), pos.getY(), pos.getZ());
			}
		}
		return true;
	}
}
