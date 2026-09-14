package train.common.items;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import train.common.Traincraft;

public class ItemWaterTankBuckets extends ItemBucket {

	public ItemWaterTankBuckets(Block block, int icon) {
		super(block);
		this.maxStackSize = 16;
		setCreativeTab(Traincraft.tcTab);
	}

	public boolean canBeStoredInToolbox(ItemStack itemstack) {
		return true;
	}

	public ItemStack fillCustomBucket(World w, int i, int j, int k) {
		BlockPos pos = new BlockPos(i, j, k);
		Block b = w.getBlockState(pos).getBlock();
		if (b == Blocks.WATER || b == Blocks.FLOWING_WATER) {
			w.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
			return new ItemStack(this);
		}
		return null;
	}

}
