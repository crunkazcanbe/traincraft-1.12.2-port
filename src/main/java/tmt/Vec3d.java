package tmt;

/**
 * TMT Vec3d helper wrapper around MC's Vec3d
 */
public class Vec3d extends net.minecraft.util.math.Vec3d {

    public Vec3d(double x, double y, double z) {
        super(x, y, z);
    }

    public Vec3d(net.minecraft.util.math.Vec3d vec) {
        super(vec.x, vec.y, vec.z);
    }

    public net.minecraft.util.math.Vec3d add(net.minecraft.util.math.Vec3d vec2) {
        return new Vec3d(x + vec2.x, y + vec2.y, z + vec2.z);
    }

    public Vec3d subtract(net.minecraft.util.math.Vec3d vec2) {
        return new Vec3d(x - vec2.x, y - vec2.y, z - vec2.z);
    }
}
