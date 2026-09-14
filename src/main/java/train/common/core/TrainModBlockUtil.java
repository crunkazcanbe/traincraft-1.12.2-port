package train.common.core;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class TrainModBlockUtil {
	public static ArrayList<ItemStack> getItemStackFromBlock(World world, int i, int j, int k) {
		BlockPos pos = new BlockPos(i, j, k);
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();

		if (block == null) {
			return null;
		}

		List<ItemStack> drops = block.getDrops(world, pos, state, 0);
		return new ArrayList<>(drops);
	}
}
