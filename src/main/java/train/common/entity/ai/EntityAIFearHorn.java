package train.common.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import train.common.api.Locomotive;

public class EntityAIFearHorn extends EntityAIBase {

    private EntityAnimal entity;
    private double randPosX;
    private double randPosY;
    private double randPosZ;

    public EntityAIFearHorn(EntityAnimal e) {
        entity = e;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        Entity target = entity.getAttackTarget();
        if (target instanceof Locomotive) {
            Entity loco = target;
            Vec3d posLoco = new Vec3d(loco.posX, loco.posY, loco.posZ);
            entity.detachHome();
            Vec3d vec3 = RandomPositionGenerator.findRandomTargetBlockAwayFrom(entity, 10, 8, posLoco);
            if (vec3 == null) {
                return false;
            } else {
                this.randPosX = vec3.x;
                this.randPosY = vec3.y;
                this.randPosZ = vec3.z;
                entity.setAttackTarget(null);
                return true;
            }
        }
        return false;
    }

    @Override
    public void startExecuting() {
        entity.getNavigator().tryMoveToXYZ(this.randPosX, this.randPosY, this.randPosZ, 2.0D);
    }

    @Override
    public boolean shouldContinueExecuting() {
        return !this.entity.getNavigator().noPath();
    }
}
