package train.common.core.handlers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import train.common.api.AbstractTrains;
import train.common.api.EntityBogie;

/**
 * Issue #14: riders lost health "inconsistently" in locomotives - mostly crawling at 0-10 km/h or above ~98 km/h,
 * with nothing beside the track. A seated rider is moved by the train, not by its own movement, so the
 * environmental checks that run for a walking player misfire: the lowered seat lets the head clip the rail/ground
 * (suffocation), the body bobbing on curves/slopes adds up fall distance, and the bogie entities around the seat
 * count as cramming. While riding a Traincraft train those can't hurt you; anything else that does is logged
 * once per cause so the next report names it.
 */
public class RiderSafetyHandler {
    private static final java.util.Set<String> LOGGED = new java.util.HashSet<>();

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onAttack(LivingAttackEvent event) {
        if (!(event.getEntityLiving() instanceof EntityPlayer)) return;
        Entity ride = event.getEntityLiving().getRidingEntity();
        if (!(ride instanceof AbstractTrains || ride instanceof EntityBogie)) return;
        DamageSource s = event.getSource();
        if (s == DamageSource.IN_WALL || s == DamageSource.FALL || s == DamageSource.CRAMMING || s == DamageSource.FLY_INTO_WALL) {
            event.getEntityLiving().fallDistance = 0;
            event.setCanceled(true);
            return;
        }
        if (LOGGED.add(s.getDamageType()))
            System.out.println("[Traincraft] rider " + event.getEntityLiving().getName() + " hurt while riding "
                    + ride.getClass().getSimpleName() + " by '" + s.getDamageType() + "' (" + event.getAmount() + ")");
    }
}
