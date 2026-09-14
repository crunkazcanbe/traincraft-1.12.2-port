package tmt;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

/**
 * basically the same as Vec3D, but a float. Usually used for storing rotations.
 * @author Ferdinand
 */

public class Vec3f {

    public float xCoord;
    public float yCoord;
    public float zCoord;

    public Vec3f(){
        xCoord = 0;
        yCoord = 0;
        zCoord = 0;
    }

    public Vec3f(Vec3d v){
        xCoord = (float)(float)v.x;
        yCoord = (float)(float)v.y;
        zCoord= (float)(float)v.z;
    }

    public Vec3f(float f, float g, float h){
        xCoord = f;
        yCoord = g;
        zCoord = h;
    }

    public Vec3f crossProduct(Vec3f p_72431_1_) {
        return new Vec3f(this.yCoord * p_72431_1_.zCoord - this.zCoord * p_72431_1_.yCoord, this.zCoord * p_72431_1_.xCoord - this.xCoord * p_72431_1_.zCoord, this.xCoord * p_72431_1_.yCoord - this.yCoord * p_72431_1_.xCoord);
    }

    public Vec3f normalize() {
        float d0 = (float)Math.sqrt(this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord);
        return d0 < 1.0E-4D ? new Vec3f(0.0F, 0.0F, 0.0F) : new Vec3f(this.xCoord / d0, this.yCoord / d0, this.zCoord / d0);
    }

    public void add(Vec3f vec){
        xCoord+=vec.xCoord;
        yCoord+=vec.yCoord;
        zCoord+=vec.zCoord;
    }
    public Vec3f addVector(float x, float y, float z){
        return new Vec3f(xCoord + x,yCoord + y,zCoord + z);
    }

    public Vec3f subtract(Vec3f vec){
        return new Vec3f(xCoord-vec.xCoord,
        yCoord-vec.yCoord,
        zCoord-vec.zCoord);
    }
}
