package train.common.blocks;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.init.SoundEvents;
import train.common.Traincraft;
import train.common.tile.TileWaterWheel;

import java.util.Random;

public class BlockWaterWheel extends Block {

	public BlockWaterWheel() {
		super(Material.WOOD);
		setCreativeTab(Traincraft.tcTab);
		this.setTickRandomly(true);
	}

	@Override
	public boolean hasTileEntity(IBlockState state) { return true; }

	@Override
	public boolean isFullBlock(IBlockState state) { return false; }

	@Override
	public boolean isOpaqueCube(IBlockState state) { return false; }

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) { return new TileWaterWheel(); }

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random random) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile != null && tile instanceof TileWaterWheel && ((TileWaterWheel) tile).getWaterDir() > -1001) {
			double d0 = (double)pos.getX() + 0.5;
			double d2 = (double)pos.getZ() + 0.5;
			world.spawnParticle(EnumParticleTypes.WATER_SPLASH, d0, pos.getY() + 1, d2, 0.0D, 0.0D, 0.0D);
			world.spawnParticle(EnumParticleTypes.WATER_SPLASH, d0, pos.getY(), d2, 0.0D, 0.0D, 0.0D);
			if (random.nextInt(20) == 0) {
				world.playSound((net.minecraft.entity.player.EntityPlayer)null, pos, SoundEvents.BLOCK_WATER_AMBIENT, SoundCategory.BLOCKS, random.nextFloat() * 0.25F + 0.75F, random.nextFloat() + 0.1F);
			}
		}
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entityliving, ItemStack stack) {
		int l = MathHelper.floor((double)(entityliving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		int i1 = world.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos)) >> 2;
		++l; l %= 4;
		int meta = (l == 0 ? 2 : l == 1 ? 3 : l == 2 ? 0 : 1) | i1 << 2;
		world.setBlockState(pos, this.getStateFromMeta(meta), 2);
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileWaterWheel) { tile.onChunkUnload(); }
		super.breakBlock(world, pos, state);
	}
}
