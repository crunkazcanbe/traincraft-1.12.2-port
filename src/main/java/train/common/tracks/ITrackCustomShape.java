package train.common.tracks;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

/**
 * Used by rails that modify the bounding boxes.
 */
public interface ITrackCustomShape extends ITrackInstance {

	AxisAlignedBB getCollisionBoundingBoxFromPool();

	AxisAlignedBB getSelectedBoundingBoxFromPool();

	RayTraceResult collisionRayTrace(Vec3d vec3d, Vec3d vec3d1);
}
