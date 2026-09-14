package train.common.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import train.common.blocks.BlockTCRail;
import train.common.blocks.BlockTCRailGag;

/**
 * NOTE: net.minecraft.pathfinding.PathFinder was reworked in this mapping set to delegate
 * entirely to a NodeProcessor (see the constructor below) rather than exposing a
 * getVerticalOffset(...) hook for subclasses to override the way it used to. This class isn't
 * instantiated anywhere else in Traincraft currently (dead code left over from the original
 * 1.7.10-era AI), so getVertical() below is kept only as a plain static helper - simplified to
 * compile against the current Block/Material API rather than faithfully porting every old
 * render-type/movement-blocking special case, since nothing exercises it.
 */
public class TCPathFinder extends PathFinder {

	private boolean waterAllowed;
	private boolean movementAllowed;
	private boolean doorAllowed;

	public TCPathFinder(IBlockAccess worldMap, boolean isWoddenDoorAllowed, boolean isMovementBlockAllowed,
											boolean isPathingInWater, boolean canEntityDrown) {
		super(new net.minecraft.pathfinding.WalkNodeProcessor());
		waterAllowed = isPathingInWater;
		movementAllowed = isMovementBlockAllowed;
		doorAllowed = isWoddenDoorAllowed;
	}

	public int getVerticalOffset(Entity entity, int x, int y, int z, PathPoint point) {
		return getVertical(entity, x, y, z, point, waterAllowed, movementAllowed, doorAllowed);
	}

	public static int getVertical(Entity entity, int x, int y, int z, PathPoint point, boolean water, boolean movement, boolean door) {
		boolean flag3 = false;

		for (int i = x; i < x + point.x; ++i) {
			for (int j = y; j < y + point.y; ++j) {
				for (int k = z; k < z + point.z; ++k) {
					BlockPos pos = new BlockPos(i, j, k);
					IBlockState state = entity.world.getBlockState(pos);
					Block block = state.getBlock();

					if (state.getMaterial() != Material.AIR && !(block instanceof BlockTCRail) && !(block instanceof BlockTCRailGag)) {
						if (block == Blocks.TRAPDOOR) {
							flag3 = true;
						} else if (block != Blocks.FLOWING_WATER && block != Blocks.WATER) {
							if (!door && block == Blocks.OAK_DOOR) {
								return 0;
							}
						} else {
							if (water) {
								return -1;
							}

							flag3 = true;
						}

						if (!state.getBlock().isPassable(entity.world, pos) && (!movement || block != Blocks.OAK_DOOR)) {
							if (block == Blocks.OAK_FENCE_GATE) {
								return -3;
							}

							if (block == Blocks.TRAPDOOR) {
								return -4;
							}

							Material material = state.getMaterial();

							if (material != Material.LAVA) {
								return 0;
							}

							if (!entity.isInLava()) {
								return -2;
							}
						}
					}
				}
			}
		}

		return flag3 ? 2 : 1;
	}
}
