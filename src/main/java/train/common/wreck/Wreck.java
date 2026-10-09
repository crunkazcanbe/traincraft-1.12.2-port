package train.common.wreck;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import train.common.api.*;
import train.common.core.handlers.ConfigHandler;
import train.common.core.handlers.TrainsDamageSource;
import train.common.library.BlockIDs;
import train.common.tile.TileTCRail;
import train.common.tile.TileTCRailGag;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Derailments and train wrecks (her ask 2026-10-08: "make trains derail ... wrecks ... as detailed as possible").
 *
 * What makes a train come off the rails:
 *  - taking a curve too fast: sideways acceleration v^2/r against the car's limit (lower for tall / sloshing tank cars).
 *    Above ~85% the wheels squeal, throw sparks and the driver gets a warning; past 100% stress builds up and it goes.
 *  - running off the end of the track / a gap / a missing rail while moving
 *  - hitting another train that isn't coupled to it (rear-end, head-on, side-swipe)
 *  - slamming into something solid (a buffer stop, a wall)
 *
 * How bad it is, by speed:  DERAILED (wheels off, still upright)  ->  TIPPED (on its side)  ->  TOTALED (a write-off:
 * burning, maybe exploding). A derailed car uncouples; the rest of the train keeps its momentum and piles into it, so a
 * fast wreck really does accordion. A wrecked car slides and ploughs to a stop (tearing up soft blocks, hitting
 * anything in the way), spills its cargo (a tipped tank car empties onto the ground; fuel, oil and the like catch fire),
 * throws its riders off and hurts them, and a steam loco's boiler can burst.
 *
 * Getting it back: Rerailing Frogs put a derailed (upright) car back on the nearest track; a tipped car needs the
 * Breakdown Crane to stand it up first; a totaled one can only be cut up for scrap (the crane, or just break it).
 *
 * Everything is in the "derailments" section of the Traincraft config. Speeds are the km/h the HUD shows.
 */
public final class Wreck {
    private Wreck() {}

    public static final int OK = 0, DERAILED = 1, TIPPED = 2, TOTALED = 3;
    /** watcher slots (synced to the client by DataWatcherShim / PacketDataWatch) */
    public static final int W_STATE = 34, W_ROLL = 35, W_FIRE = 36, W_WORK = 37;

    public static final TrainsDamageSource WRECK = (TrainsDamageSource) new TrainsDamageSource("trainWreck", " was killed in a train wreck").setDamageBypassesArmor();

    // ---------------------------------------------------------------------------------------------- per-car state

    /** all the wreck state a car carries (a field on EntityRollingStock) */
    public static final class State {
        public int state;               // OK / DERAILED / TIPPED / TOTALED
        public float roll;              // target roll, degrees, signed (+ = over to the car's right)
        public int fire;                // ticks left burning
        public float stress;            // curve over-speed stress; derails at 1
        public int offRailTicks;        // ticks in a row with no rail under it
        public int offRailTick = -1;    // the last tick it had no rail under it
        public double lastSpeed;        // blocks/tick, last tick
        public int warnCooldown;
        public int work;                // rerail / crane progress, ticks left (0 = none)
        public int workKind;            // 1 rerail, 2 right it up, 3 scrap
        public java.util.UUID workBy;
        public String why = "";
        // curve seen this tick (set from the track-following code)
        public int curveTick = -1;
        public double curveR, curveCx, curveCz;
        // client: animated roll
        public float rollClient, prevRollClient;

        void save(NBTTagCompound t) {
            NBTTagCompound w = new NBTTagCompound();
            w.setInteger("state", state);
            w.setFloat("roll", roll);
            w.setInteger("fire", fire);
            w.setString("why", why);
            t.setTag("tcWreck", w);
        }

        void load(NBTTagCompound t) {
            if (!t.hasKey("tcWreck")) return;
            NBTTagCompound w = t.getCompoundTag("tcWreck");
            state = w.getInteger("state");
            roll = w.getFloat("roll");
            fire = w.getInteger("fire");
            why = w.getString("why");
            rollClient = prevRollClient = roll;
        }
    }

    public static void save(EntityRollingStock c, NBTTagCompound t) { c.wreck.save(t); }

    public static void load(EntityRollingStock c, NBTTagCompound t) {
        c.wreck.load(t);
        sync(c);
    }

    public static void initWatch(EntityRollingStock c) {
        c.watcher().addObject(W_STATE, 0);
        c.watcher().addObject(W_ROLL, 0f);
        c.watcher().addObject(W_FIRE, 0);
        c.watcher().addObject(W_WORK, 0);
    }

    static void sync(EntityRollingStock c) {
        c.watcher().updateObject(W_STATE, c.wreck.state);
        c.watcher().updateObject(W_ROLL, c.wreck.roll);
        c.watcher().updateObject(W_FIRE, c.wreck.fire > 0 ? 1 : 0);
        c.watcher().updateObject(W_WORK, c.wreck.work > 0 ? c.wreck.workKind : 0);
    }

    public static int state(EntityRollingStock c) {
        return c.world.isRemote ? c.watcher().getWatchableObjectInt(W_STATE) : c.wreck.state;
    }

    public static boolean isWrecked(EntityRollingStock c) { return state(c) != OK; }

    // ---------------------------------------------------------------------------------------------- units

    /** the speed scale the HUD uses (Traincraft shows speeds x3 unless "real train speed" is on) */
    static double scale() { return ConfigHandler.REAL_TRAIN_SPEED ? 1 : 3; }

    /** blocks/tick -> the km/h on the HUD */
    public static double kmh(double bpt) { return bpt * 20 * 3.6 * scale(); }

    static double speed(Entity e) { return Math.sqrt(e.motionX * e.motionX + e.motionZ * e.motionZ); }

    /** how many g sideways this car can take on a curve before it goes (tall tank cars slosh, locos sit low) */
    static double limitG(EntityRollingStock c) {
        double k = 1.0;
        if (c instanceof LiquidTank) k = 0.85;
        else if (c instanceof Locomotive) k = 1.12;
        else if (c instanceof Freight) k = 0.95;
        return ConfigHandler.WRECK_CURVE_LIMIT_G * k;
    }

    /** the speed (HUD km/h) this car can take a curve of radius r (blocks) at */
    public static double safeKmh(EntityRollingStock c, double r) {
        return Math.sqrt(limitG(c) * 9.81 * r * scale()) * 3.6;
    }

    // ---------------------------------------------------------------------------------------------- detection (server)

    /** the track code tells us when the car is on a curve this tick */
    public static void onCurve(EntityRollingStock c, double r, double cx, double cz) {
        State s = c.wreck;
        s.curveTick = c.ticksExisted;
        s.curveR = r;
        s.curveCx = cx;
        s.curveCz = cz;
    }

    /** after the car moved along the track this tick */
    public static void afterTrackMove(EntityRollingStock c) {
        if (!ConfigHandler.WRECK_ENABLED || c.world.isRemote) return;
        State s = c.wreck;
        if (s.offRailTick == c.ticksExisted) return;   // off the rails this tick: offRail() is counting
        double v = speed(c), before = s.lastSpeed;
        s.lastSpeed = v;
        s.offRailTicks = 0;
        if (s.warnCooldown > 0) s.warnCooldown--;

        // --- curves
        if (s.curveTick == c.ticksExisted && s.curveR > 0.5) {
            double r = s.curveR * scale(), vm = v * 20 * scale();
            double g = vm * vm / r / 9.81, ratio = g / limitG(c);
            if (ratio > ConfigHandler.WRECK_WARN_FRACTION) squeal(c, ratio, s.curveR);
            if (ratio > 1) s.stress += (float) ((ratio - 1) * 0.25 + 0.02);
            else s.stress = Math.max(0, s.stress - 0.05f);
            if (s.stress >= 1) {
                // which way is "outside": away from the curve's centre
                double ox = c.posX - s.curveCx, oz = c.posZ - s.curveCz;
                int side = sideOf(c, ox, oz);
                int sev = ratio < 1.35 ? DERAILED : ratio < 1.85 || kmh(v) < 90 ? TIPPED : TOTALED;
                derail(c, sev, side, String.format(Locale.ROOT, "too fast for a %.0f m curve (%.0f km/h, safe about %.0f km/h)",
                        s.curveR, kmh(v), safeKmh(c, s.curveR)));
                return;
            }
        } else {
            s.stress = Math.max(0, s.stress - 0.05f);
        }

        // --- slammed into something solid (a buffer stop, a wall, a mountain)
        if (c.collidedHorizontally && before - v > 0.5 * before && kmh(before) > ConfigHandler.WRECK_IMPACT_KMH) {
            double k = kmh(before);
            int sev = k < ConfigHandler.WRECK_IMPACT_KMH * 2.2 ? DERAILED : k < ConfigHandler.WRECK_IMPACT_KMH * 4 ? TIPPED : TOTALED;
            c.motionX = c.motionX == 0 ? 0 : c.motionX;
            derail(c, sev, c.world.rand.nextBoolean() ? 1 : -1, String.format(Locale.ROOT, "hit an obstruction at %.0f km/h", k));
        }
    }

    /** no rail under the car this tick. Returns true when it has now derailed (the caller stops normal movement). */
    public static boolean offRail(EntityRollingStock c) {
        if (!ConfigHandler.WRECK_ENABLED || c.world.isRemote) return false;
        State s = c.wreck;
        s.offRailTick = c.ticksExisted;
        double v = Math.max(speed(c), s.lastSpeed);   // lastSpeed = the speed it left the rails at
        // a rail gap of one tick happens at some track joins; three in a row is really off the rails
        if (++s.offRailTicks < 3 || kmh(v) < 2) return false;
        double k = kmh(v);
        int sev = k < ConfigHandler.WRECK_OFFTRACK_TIP_KMH ? DERAILED : k < ConfigHandler.WRECK_OFFTRACK_TIP_KMH * 3 ? TIPPED : TOTALED;
        derail(c, sev, c.world.rand.nextBoolean() ? 1 : -1, String.format(Locale.ROOT, "ran off the end of the track at %.0f km/h", k));
        return true;
    }

    /** two uncoupled trains touched. Returns true when that wrecked them (the caller skips its gentle push). */
    public static boolean onCollision(EntityRollingStock a, EntityRollingStock b) {
        if (!ConfigHandler.WRECK_ENABLED || a.world.isRemote) return false;
        if (a.cartLinked1 == b || a.cartLinked2 == b || b.cartLinked1 == a || b.cartLinked2 == a) return false;
        if (a.train != null && a.train == b.train) return false;
        double nx = b.posX - a.posX, nz = b.posZ - a.posZ, d = Math.sqrt(nx * nx + nz * nz);
        if (d < 1e-4) return false;
        nx /= d;
        nz /= d;
        // closing speed along the line between them (+ = coming together)
        double closing = (a.motionX - b.motionX) * nx + (a.motionZ - b.motionZ) * nz;
        double k = kmh(closing);
        boolean wa = isWrecked(a), wb = isWrecked(b);
        if (k < ConfigHandler.WRECK_COLLISION_KMH) {
            if (!wa && !wb) return false;           // two working trains bumping gently: Traincraft's own push
            // a wreck is a dead weight: a train can't shove into it, but nothing holds it from pulling away
            double va = a.motionX * nx + a.motionZ * nz, vb = b.motionX * nx + b.motionZ * nz;
            if (!wa && va > 0) { a.motionX -= va * nx; a.motionZ -= va * nz; }
            if (!wb && vb < 0) { b.motionX -= vb * nx; b.motionZ -= vb * nz; }
            return true;
        }
        int sev = k < ConfigHandler.WRECK_COLLISION_KMH * 2.3 ? DERAILED : k < ConfigHandler.WRECK_COLLISION_KMH * 4.6 ? TIPPED : TOTALED;
        String why = String.format(Locale.ROOT, "collided with %s at %.0f km/h", nameOf(b), k);
        String why2 = String.format(Locale.ROOT, "collided with %s at %.0f km/h", nameOf(a), k);
        // momentum: they end up moving together (perfectly inelastic, equal masses is good enough)
        double mx = (a.motionX + b.motionX) / 2, mz = (a.motionZ + b.motionZ) / 2;
        int sideA = a.world.rand.nextBoolean() ? 1 : -1;
        // (a's fuel going up can't "break" b into an item: onDamage turns the blast into b derailing, then this upgrades it)
        derail(a, sev, sideA, why);
        derail(b, sev, -sideA, why2);
        a.motionX = b.motionX = mx;
        a.motionZ = b.motionZ = mz;
        a.motionY = b.motionY = 0.08 + Math.min(0.25, k / 400);
        return true;
    }

    /** +1 if (ox, oz) is to the car's right, -1 if left */
    static int sideOf(EntityRollingStock c, double ox, double oz) {
        double yaw = Math.toRadians(c.rotationYaw);
        double fx = Math.cos(yaw), fz = Math.sin(yaw);   // rotationYaw = atan2(dz, dx) in Traincraft
        return fx * oz - fz * ox > 0 ? 1 : -1;
    }

    static void squeal(EntityRollingStock c, double ratio, double r) {
        World w = c.world;
        State s = c.wreck;
        if (w.rand.nextFloat() < 0.35 + 0.4 * Math.min(1, ratio - 0.85)) {
            particles(w, EnumParticleTypes.LAVA, c.posX, c.posY + 0.1, c.posZ, 1, 0.6, 0.05, 0.6, 0);
            particles(w, EnumParticleTypes.FLAME, c.posX, c.posY + 0.1, c.posZ, 2, 0.7, 0.05, 0.7, 0.01);
        }
        if (c.ticksExisted % 6 == 0)
            w.playSound(null, c.posX, c.posY, c.posZ, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.NEUTRAL, 0.4f + (float) Math.min(0.6, ratio - 0.85), 1.9f);
        if (s.warnCooldown == 0) {
            s.warnCooldown = 30;
            String msg = String.format(Locale.ROOT, ratio > 1 ? "§c⚠ DERAILMENT DANGER §f- far too fast for this curve! Safe about %.0f km/h"
                    : "§e⚠ Wheels screeching §f- slow down for this curve (safe about %.0f km/h)", safeKmh(c, r));
            for (Entity p : riders(c)) if (p instanceof EntityPlayer) ((EntityPlayer) p).sendStatusMessage(new TextComponentString(msg), true);
        }
    }

    // ---------------------------------------------------------------------------------------------- the wreck

    /** take it off the rails. severity DERAILED / TIPPED / TOTALED; side = which way it falls (+1 right, -1 left) */
    public static void derail(EntityRollingStock c, int severity, int side, String why) {
        World w = c.world;
        if (w.isRemote) return;
        State s = c.wreck;
        if (severity <= s.state) return;
        boolean first = s.state == OK;
        double v = Math.max(speed(c), s.lastSpeed), k = kmh(v);
        s.state = severity;
        s.why = why;
        s.stress = 0;
        s.work = 0;
        // lean / fall over: a derailed car leans a little into the ballast, a tipped one lies on its side
        s.roll = side * (severity == DERAILED ? 6 + w.rand.nextFloat() * 6 : 84 + w.rand.nextFloat() * 10);
        if (first) {
            // it's no longer part of the train: the cars behind keep coming (and pile into it)
            c.unLink();
            c.Link1 = 0;
            c.Link2 = 0;
            if (c instanceof Locomotive) {
                ((Locomotive) c).isLocoTurnedOn = false;
                ((Locomotive) c).parkingBrake = false;
            }
            // a hop as the wheels climb the rail head
            c.motionY = Math.max(c.motionY, 0.06 + Math.min(0.2, k / 500));
        }
        // sideways kick toward where it falls
        double yaw = Math.toRadians(c.rotationYaw), rx = -Math.sin(yaw), rz = Math.cos(yaw);
        double kick = severity == DERAILED ? 0.02 : 0.06;
        c.motionX += rx * side * kick;
        c.motionZ += rz * side * kick;

        effectsBurst(c, severity, k);
        riders(c, severity, k, side);
        if (severity >= TIPPED) spill(c, severity, k);
        if (severity == TOTALED) explode(c, k);
        else if (severity == TIPPED && c instanceof SteamTrain && ((Locomotive) c).isLocoTurnedOn) boilerBurst(c);
        sync(c);
        announce(c, severity, why);
        // the next cars back, coupled to nothing now, run on into it; a fast curve wreck drags its neighbours off too
        if (first && k > 40) for (EntityRollingStock n : neighbours(c)) {
            if (!isWrecked(n) && w.rand.nextFloat() < Math.min(0.9, k / 120))
                derail(n, Math.max(DERAILED, severity - 1), side, "dragged off the rails by " + nameOf(c));
        }
    }

    static List<EntityRollingStock> neighbours(EntityRollingStock c) {
        List<EntityRollingStock> l = new ArrayList<>();
        for (Entity e : c.world.getEntitiesWithinAABBExcludingEntity(c, c.getEntityBoundingBox().grow(3, 1, 3)))
            if (e instanceof EntityRollingStock) l.add((EntityRollingStock) e);
        return l;
    }

    static List<Entity> riders(EntityRollingStock c) {
        List<Entity> l = new ArrayList<>(c.getPassengers());
        if (c.bogieLoco != null) l.addAll(c.bogieLoco.getPassengers());
        return l;
    }

    static void riders(EntityRollingStock c, int severity, double k, int side) {
        if (!ConfigHandler.WRECK_RIDER_DAMAGE) return;
        double yaw = Math.toRadians(c.rotationYaw), fx = Math.cos(yaw), fz = Math.sin(yaw), rx = -fz, rz = fx;
        double fwd = Math.signum(c.motionX * fx + c.motionZ * fz);
        for (Entity p : riders(c)) {
            if (!(p instanceof EntityLivingBase)) continue;
            EntityLivingBase e = (EntityLivingBase) p;
            float dmg = (float) (severity == DERAILED ? k / 22 : severity == TIPPED ? k / 7 : k / 3.5);
            if (severity >= TIPPED) {
                // thrown out of the car, forward and over the side it falls to
                e.dismountRidingEntity();
                double t = Math.min(1.2, k / 90);
                e.motionX = fx * fwd * t + rx * side * 0.35;
                e.motionZ = fz * fwd * t + rz * side * 0.35;
                e.motionY = 0.35 + t * 0.2;
                e.velocityChanged = true;
            }
            // riding gives Resistance (so the old "ran over" bumps didn't hurt); a wreck does
            e.removePotionEffect(MobEffects.RESISTANCE);
            if (dmg >= 1) e.attackEntityFrom(WRECK, Math.min(60, dmg));
        }
    }

    // ---------------------------------------------------------------------------------------------- wrecked: each tick (server)

    /** a wrecked car moves on its own: slides, ploughs, burns. Replaces the track movement. */
    public static void tickWrecked(EntityRollingStock c) {
        World w = c.world;
        State s = c.wreck;
        // the rerail / crane job in progress
        if (s.work > 0) tickWork(c);
        if (s.state == OK) return;

        c.motionY -= 0.06;
        double v = speed(c);
        // plough: tear up what's soft, stop on what isn't
        if (v > 0.03) plough(c, v);
        c.move(net.minecraft.entity.MoverType.SELF, c.motionX, c.motionY, c.motionZ);
        if (c.onGround) {
            c.motionY = 0;
            // friction on the ground: a car on its wheels in the ballast slides further than one on its side
            double f = s.state == DERAILED ? 0.93 : 0.86;
            c.motionX *= f;
            c.motionZ *= f;
        } else {
            c.motionX *= 0.99;
            c.motionZ *= 0.99;
        }
        if (Math.abs(c.motionX) < 0.002) c.motionX = 0;
        if (Math.abs(c.motionZ) < 0.002) c.motionZ = 0;
        v = speed(c);
        s.lastSpeed = v;

        // sparks + dust while it grinds along
        if (v > 0.04) {
            IBlockState ground = w.getBlockState(new BlockPos(c.posX, c.posY - 0.5, c.posZ));
            particles(w, EnumParticleTypes.BLOCK_DUST, c.posX, c.posY + 0.2, c.posZ, 6, 1.2, 0.2, 1.2, 0.1, Block.getStateId(ground));
            particles(w, EnumParticleTypes.LAVA, c.posX, c.posY + 0.2, c.posZ, 1, 1.0, 0.1, 1.0, 0);
            if (c.ticksExisted % 4 == 0)
                w.playSound(null, c.posX, c.posY, c.posZ, SoundEvents.BLOCK_GRAVEL_BREAK, SoundCategory.NEUTRAL, 1.0f, 0.5f);
            if (c.ticksExisted % 7 == 0)
                w.playSound(null, c.posX, c.posY, c.posZ, SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.NEUTRAL, 0.35f, 0.5f + w.rand.nextFloat() * 0.3f);
            hitThings(c, v);
        }
        // hiss and creak once it's down
        if (v <= 0.04 && c.ticksExisted % 80 == 0 && w.rand.nextInt(3) == 0)
            w.playSound(null, c.posX, c.posY, c.posZ, SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE, SoundCategory.NEUTRAL, 0.5f, 0.4f);
        if (s.state == TOTALED && c.ticksExisted % 5 == 0)
            particles(w, EnumParticleTypes.SMOKE_LARGE, c.posX, c.posY + 1.2, c.posZ, 2, 0.8, 0.3, 0.8, 0.02);
        // fire
        if (s.fire > 0) {
            s.fire--;
            particles(w, EnumParticleTypes.FLAME, c.posX, c.posY + 0.8, c.posZ, 4, 1.0, 0.5, 1.0, 0.02);
            if (c.ticksExisted % 3 == 0) particles(w, EnumParticleTypes.SMOKE_LARGE, c.posX, c.posY + 1.5, c.posZ, 2, 0.6, 0.3, 0.6, 0.05);
            if (c.ticksExisted % 30 == 0) w.playSound(null, c.posX, c.posY, c.posZ, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.5f, 0.8f);
            if (ConfigHandler.WRECK_FIRE && c.ticksExisted % 40 == 0) igniteAround(c, 2);
            for (Entity e : w.getEntitiesWithinAABBExcludingEntity(c, c.getEntityBoundingBox().grow(1, 0.5, 1)))
                if (e instanceof EntityLivingBase) e.setFire(4);
            if (s.fire == 0) sync(c);
        }
        if (c instanceof Locomotive) ((Locomotive) c).isLocoTurnedOn = false;
        // the front bogie (an invisible helper entity) stays with the body
        if (c.bogieLoco != null) {
            double yaw = Math.toRadians(c.serverRealRotation + 90);
            c.bogieLoco.setPosition(c.posX + Math.cos(yaw) * Math.abs(c.bogieShift), c.posY, c.posZ + Math.sin(yaw) * Math.abs(c.bogieShift));
            c.bogieLoco.motionX = c.bogieLoco.motionZ = 0;
        }
        if (c.ticksExisted % 20 == 0) sync(c);
    }

    static void plough(EntityRollingStock c, double v) {
        if (!ConfigHandler.WRECK_BLOCK_DAMAGE) return;
        World w = c.world;
        double dx = c.motionX / v, dz = c.motionZ / v;
        // a strip a car wide just ahead of it
        for (int a = -1; a <= 1; a++) {
            double px = c.posX + dx * 1.6 - dz * a * 0.7, pz = c.posZ + dz * 1.6 + dx * a * 0.7;
            for (int y = 0; y <= 1; y++) {
                BlockPos p = new BlockPos(px, c.posY + y, pz);
                IBlockState st = w.getBlockState(p);
                Block b = st.getBlock();
                if (st.getMaterial() == Material.AIR) continue;
                float hard = st.getBlockHardness(w, p);
                boolean soft = hard >= 0 && (hard <= 0.8f || st.getMaterial() == Material.GLASS || st.getMaterial() == Material.LEAVES
                        || st.getMaterial() == Material.PLANTS || st.getMaterial() == Material.VINE || b instanceof BlockRailBase
                        || b == BlockIDs.tcRail.block || b == BlockIDs.tcRailGag.block || st.getMaterial() == Material.WOOD && hard <= 2.5f && v > 0.3);
                if (soft) {
                    if (b == BlockIDs.tcRail.block || b == BlockIDs.tcRailGag.block) continue; // leave the TC track (its pieces are multi-block)
                    w.destroyBlock(p, true);
                }
            }
        }
    }

    static void hitThings(EntityRollingStock c, double v) {
        double k = kmh(v);
        for (Entity e : c.world.getEntitiesWithinAABBExcludingEntity(c, c.getEntityBoundingBox().grow(0.6, 0.2, 0.6))) {
            if (e instanceof EntityRollingStock && !isWrecked((EntityRollingStock) e)) {
                // a sliding wreck hitting a train still on the rails knocks it off too
                if (k > ConfigHandler.WRECK_COLLISION_KMH) derail((EntityRollingStock) e, k > ConfigHandler.WRECK_COLLISION_KMH * 2.3 ? TIPPED : DERAILED,
                        sideOf((EntityRollingStock) e, c.posX - e.posX, c.posZ - e.posZ) * -1, "hit by the wreck of " + nameOf(c));
            } else if (e instanceof EntityLivingBase && !riders(c).contains(e) && e != c.bogieLoco) {
                if (k > 6) e.attackEntityFrom(WRECK, (float) Math.min(40, k / 4));
                e.motionX += c.motionX * 1.5;
                e.motionZ += c.motionZ * 1.5;
                e.motionY += 0.25;
                e.velocityChanged = true;
            }
        }
    }

    // ---------------------------------------------------------------------------------------------- effects

    static void effectsBurst(EntityRollingStock c, int severity, double k) {
        World w = c.world;
        float vol = severity == DERAILED ? 1.0f : 2.0f;
        w.playSound(null, c.posX, c.posY, c.posZ, SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.NEUTRAL, vol, 0.5f);
        w.playSound(null, c.posX, c.posY, c.posZ, SoundEvents.ENTITY_IRONGOLEM_HURT, SoundCategory.NEUTRAL, vol, 0.4f);
        if (severity >= TIPPED) {
            w.playSound(null, c.posX, c.posY, c.posZ, SoundEvents.ENTITY_ZOMBIE_BREAK_DOOR_WOOD, SoundCategory.NEUTRAL, 2.0f, 0.5f);
            w.playSound(null, c.posX, c.posY, c.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 0.7f, 1.6f);
        }
        IBlockState ground = w.getBlockState(new BlockPos(c.posX, c.posY - 0.5, c.posZ));
        int n = severity == DERAILED ? 25 : 70;
        particles(w, EnumParticleTypes.BLOCK_DUST, c.posX, c.posY + 0.3, c.posZ, n, 1.5, 0.4, 1.5, 0.15, Block.getStateId(ground));
        particles(w, EnumParticleTypes.LAVA, c.posX, c.posY + 0.3, c.posZ, severity * 5, 1.2, 0.2, 1.2, 0);
        particles(w, EnumParticleTypes.CLOUD, c.posX, c.posY + 0.5, c.posZ, severity * 10, 1.5, 0.5, 1.5, 0.05);
    }

    static boolean flammable(FluidStack f) {
        if (f == null || f.amount <= 0 || f.getFluid() == null) return false;
        Fluid fl = f.getFluid();
        if (fl.getTemperature(f) >= 600) return true;
        String n = fl.getName().toLowerCase(Locale.ROOT);
        for (String x : new String[]{"oil", "diesel", "fuel", "gasoline", "petrol", "kerosene", "ethanol", "creosote", "naphtha", "hydrogen",
                "methane", "propane", "butane", "lpg", "gas", "benzene", "alcohol", "biodiesel", "lava", "napalm", "nitro", "tnt", "plantoil", "seed_oil"})
            if (n.contains(x)) return true;
        return false;
    }

    /** whatever liquid the car carries (tank cars, diesel fuel), or null */
    static FluidStack liquid(EntityRollingStock c) {
        if (c instanceof LiquidTank) {
            FluidTank t = ((LiquidTank) c).getTank();
            return t == null ? null : t.getFluid();
        }
        if (c instanceof DieselTrain && ((DieselTrain) c).getDiesel() > 0) {
            Fluid f = net.minecraftforge.fluids.FluidRegistry.getFluid("diesel");
            if (f == null) f = net.minecraftforge.fluids.FluidRegistry.getFluid("oil");
            return f == null ? null : new FluidStack(f, ((DieselTrain) c).getDiesel());
        }
        return null;
    }

    /** a tipped or wrecked car loses its load */
    static void spill(EntityRollingStock c, int severity, double k) {
        if (!ConfigHandler.WRECK_CARGO_SPILL) return;
        World w = c.world;
        // freight: crates burst open
        ItemStack[] inv = c instanceof Freight ? ((Freight) c).cargoItems : null;
        if (inv != null) {
            for (int i = 0; i < inv.length; i++) {
                ItemStack st = inv[i];
                if (st == null || st.isEmpty()) continue;
                float chance = severity == TOTALED ? 0.9f : 0.5f;
                if (w.rand.nextFloat() > chance) continue;
                // some of it is ruined in a total wreck
                if (severity == TOTALED && w.rand.nextFloat() < 0.3f) {
                    inv[i] = ItemStack.EMPTY;
                    continue;
                }
                net.minecraft.entity.item.EntityItem ei = new net.minecraft.entity.item.EntityItem(w, c.posX + (w.rand.nextDouble() - 0.5) * 2,
                        c.posY + 0.8, c.posZ + (w.rand.nextDouble() - 0.5) * 2, st.copy());
                ei.motionX = (w.rand.nextDouble() - 0.5) * 0.4 + c.motionX;
                ei.motionY = 0.2 + w.rand.nextDouble() * 0.2;
                ei.motionZ = (w.rand.nextDouble() - 0.5) * 0.4 + c.motionZ;
                ei.setDefaultPickupDelay();
                w.spawnEntity(ei);
                inv[i] = ItemStack.EMPTY;
            }
        }
        // tank cars: the shell splits and the load runs out onto the ground
        if (c instanceof LiquidTank) {
            FluidTank t = ((LiquidTank) c).getTank();
            FluidStack f = t == null ? null : t.getFluid();
            if (f != null && f.amount > 0) {
                int lose = severity == TOTALED ? f.amount : (int) (f.amount * (0.4 + w.rand.nextFloat() * 0.4));
                t.drain(lose, true);
                Block fb = f.getFluid().getBlock();
                if (fb != null) {
                    int pools = Math.min(9, Math.max(1, lose / 3000));
                    for (int i = 0; i < pools; i++) {
                        BlockPos p = new BlockPos(c.posX + (w.rand.nextDouble() - 0.5) * 4, c.posY + 0.5, c.posZ + (w.rand.nextDouble() - 0.5) * 4);
                        while (p.getY() > 1 && w.isAirBlock(p.down())) p = p.down();
                        if (w.isAirBlock(p)) w.setBlockState(p, fb.getDefaultState());
                    }
                }
                particles(w, EnumParticleTypes.WATER_SPLASH, c.posX, c.posY + 0.8, c.posZ, 40, 1.5, 0.3, 1.5, 0.2);
                // a ruptured tank of something that burns, with sparks everywhere, catches
                if (flammable(f) && ConfigHandler.WRECK_FIRE && w.rand.nextFloat() < (severity == TOTALED ? 1f : 0.45f)) {
                    c.wreck.fire = Math.max(c.wreck.fire, 20 * 90);
                    igniteAround(c, 3);
                }
            }
        }
    }

    /** a total write-off: fuel and flammable loads go up, steam boilers burst, electrics arc */
    static void explode(EntityRollingStock c, double k) {
        World w = c.world;
        FluidStack f = liquid(c);
        boolean burns = flammable(f);
        float power = 0;
        if (burns) power = (float) Math.min(5, 2.0 + f.amount / 10000.0);
        else if (c instanceof SteamTrain && ((Locomotive) c).isLocoTurnedOn) { boilerBurst(c); return; }
        else if (c instanceof ElectricTrain) {
            particles(w, EnumParticleTypes.FIREWORKS_SPARK, c.posX, c.posY + 1.5, c.posZ, 60, 1, 1, 1, 0.3);
            power = 1.5f;
        } else if (k > 100) power = 1.5f;
        if (ConfigHandler.WRECK_FIRE && (burns || c instanceof Locomotive)) c.wreck.fire = 20 * 120;
        if (power > 0 && ConfigHandler.WRECK_EXPLOSIONS)
            w.newExplosion(c, c.posX, c.posY + 0.8, c.posZ, power, burns && ConfigHandler.WRECK_FIRE, ConfigHandler.WRECK_EXPLOSIONS_BREAK_BLOCKS);
        if (c instanceof LiquidTank && f != null) ((LiquidTank) c).getTank().drain(f.amount, true);
        if (burns && ConfigHandler.WRECK_FIRE) igniteAround(c, 4);
    }

    /** a steam loco on its side with the fire in: the boiler lets go */
    static void boilerBurst(EntityRollingStock c) {
        World w = c.world;
        particles(w, EnumParticleTypes.EXPLOSION_HUGE, c.posX, c.posY + 1, c.posZ, 1, 0, 0, 0, 0);
        particles(w, EnumParticleTypes.CLOUD, c.posX, c.posY + 1.5, c.posZ, 120, 2.5, 2, 2.5, 0.25);
        w.playSound(null, c.posX, c.posY, c.posZ, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.NEUTRAL, 3f, 0.5f);
        if (ConfigHandler.WRECK_EXPLOSIONS)
            w.newExplosion(c, c.posX, c.posY + 1, c.posZ, 2.5f, false, ConfigHandler.WRECK_EXPLOSIONS_BREAK_BLOCKS);
        // scalding steam
        for (Entity e : w.getEntitiesWithinAABBExcludingEntity(c, c.getEntityBoundingBox().grow(4, 2, 4)))
            if (e instanceof EntityLivingBase) e.attackEntityFrom(WRECK, 6);
    }

    static void igniteAround(EntityRollingStock c, int r) {
        if (!ConfigHandler.WRECK_FIRE) return;
        World w = c.world;
        for (int i = 0; i < r * 3; i++) {
            BlockPos p = new BlockPos(c.posX + w.rand.nextInt(r * 2 + 1) - r, c.posY + w.rand.nextInt(2), c.posZ + w.rand.nextInt(r * 2 + 1) - r);
            if (w.isAirBlock(p) && w.getBlockState(p.down()).isSideSolid(w, p.down(), net.minecraft.util.EnumFacing.UP))
                w.setBlockState(p, Blocks.FIRE.getDefaultState());
        }
    }

    static void particles(World w, EnumParticleTypes t, double x, double y, double z, int n, double dx, double dy, double dz, double sp, int... args) {
        if (w instanceof WorldServer) ((WorldServer) w).spawnParticle(t, x, y, z, n, dx, dy, dz, sp, args);
    }

    static String nameOf(EntityRollingStock c) {
        String n = c.getTrainName();
        return n == null || n.isEmpty() ? c.getName() : n;
    }

    static void announce(EntityRollingStock c, int severity, String why) {
        String head = severity == DERAILED ? "§6⚠ DERAILMENT" : severity == TIPPED ? "§c⚠ TRAIN WRECK" : "§4☢ TRAIN WRECK - TOTAL LOSS";
        String what = severity == DERAILED ? "came off the rails" : severity == TIPPED ? "derailed and turned over" : "was destroyed";
        String msg = String.format(Locale.ROOT, "%s§f: %s %s - %s (%d, %d, %d)", head, nameOf(c), what, why,
                MathHelper.floor(c.posX), MathHelper.floor(c.posY), MathHelper.floor(c.posZ));
        for (EntityPlayer p : c.world.playerEntities)
            if (p.getDistanceSq(c) < 160 * 160) p.sendMessage(new TextComponentString(msg));
        train.common.Traincraft.tcLog.info("[wreck] " + msg.replaceAll("§.", ""));
    }

    /** damage to a car: explosions and fire never make a train vanish into an item - a big blast knocks it off the rails /
     *  onto its side instead - and only a player can break up a wreck. Returns true when the damage is handled here. */
    public static boolean onDamage(EntityRollingStock c, net.minecraft.util.DamageSource src, float amount) {
        if (c.world.isRemote || !ConfigHandler.WRECK_ENABLED) return false;
        boolean player = src.getTrueSource() instanceof EntityPlayer && !src.isExplosion();
        if (src.isExplosion()) {
            if (amount > 6 && c.wreck.state < TIPPED) derail(c, amount > 16 ? TIPPED : DERAILED, c.world.rand.nextBoolean() ? 1 : -1, "caught in an explosion");
            return true;
        }
        if (src.isFireDamage() || src == WRECK || src == net.minecraft.util.DamageSource.IN_WALL || src == net.minecraft.util.DamageSource.CRAMMING) return true;
        return c.wreck.state != OK && !player;
    }

    // ---------------------------------------------------------------------------------------------- recovery

    /** right-clicked with a recovery tool. Returns true when the click was used. */
    public static boolean useTool(EntityRollingStock c, EntityPlayer p, ItemStack held) {
        if (c.world.isRemote) return true;
        State s = c.wreck;
        boolean frogs = held.getItem() instanceof ItemRerailer, crane = held.getItem() instanceof ItemBreakdownCrane;
        if (!frogs && !crane) return false;
        if (s.work > 0) {
            p.sendStatusMessage(new TextComponentString("§eThe crew is already working on it..."), true);
            return true;
        }
        if (s.state == OK) {
            p.sendStatusMessage(new TextComponentString("§aThis car is on the rails."), true);
            return true;
        }
        if (speed(c) > 0.02) {
            p.sendStatusMessage(new TextComponentString("§cWait until it stops moving!"), true);
            return true;
        }
        if (frogs) {
            if (s.state != DERAILED) {
                p.sendStatusMessage(new TextComponentString(s.state == TIPPED ? "§cIt's on its side - stand it up with a Breakdown Crane first."
                        : "§cIt's a write-off. Cut it up with a Breakdown Crane (or break it) for scrap."), true);
                return true;
            }
            if (rerailSpot(c) == null) {
                p.sendStatusMessage(new TextComponentString("§cNo track within 4 blocks to put it back on."), true);
                return true;
            }
            start(c, p, 1, 80);
        } else {
            if (s.state == DERAILED) {
                p.sendStatusMessage(new TextComponentString("§eIt's still upright - Rerailing Frogs will do."), true);
                return true;
            }
            start(c, p, s.state == TIPPED ? 2 : 3, s.state == TIPPED ? 160 : 120);
        }
        if (!p.capabilities.isCreativeMode) held.damageItem(1, p);
        return true;
    }

    static void start(EntityRollingStock c, EntityPlayer p, int kind, int ticks) {
        State s = c.wreck;
        s.work = ticks;
        s.workKind = kind;
        s.workBy = p.getUniqueID();
        String what = kind == 1 ? "Setting the rerailing frogs and jacking it back onto the track" : kind == 2 ? "Rigging the crane to stand it back up" : "Cutting the wreck up for scrap";
        p.sendStatusMessage(new TextComponentString("§b" + what + "... stay close (" + ticks / 20 + " s)"), true);
        sync(c);
    }

    static void tickWork(EntityRollingStock c) {
        World w = c.world;
        State s = c.wreck;
        EntityPlayer p = s.workBy == null ? null : w.getPlayerEntityByUUID(s.workBy);
        if (p == null || p.getDistanceSq(c) > 10 * 10) {
            if (p != null) p.sendStatusMessage(new TextComponentString("§cYou walked away - the job stopped."), true);
            s.work = 0;
            sync(c);
            return;
        }
        s.work--;
        if (c.ticksExisted % 10 == 0) {
            SoundEvent snd = s.workKind == 3 ? SoundEvents.BLOCK_ANVIL_USE : s.workKind == 2 ? SoundEvents.BLOCK_CHEST_LOCKED : SoundEvents.BLOCK_PISTON_EXTEND;
            w.playSound(null, c.posX, c.posY, c.posZ, snd, SoundCategory.NEUTRAL, 0.8f, 0.6f + w.rand.nextFloat() * 0.3f);
            particles(w, EnumParticleTypes.CRIT, c.posX, c.posY + 1, c.posZ, 6, 0.8, 0.5, 0.8, 0.1);
        }
        if (s.workKind == 2) s.roll *= 0.97f;   // the crane slowly stands it up (synced so it animates)
        if (c.ticksExisted % 10 == 0) sync(c);
        if (s.work > 0) return;
        if (s.workKind == 1) {
            if (rerail(c)) p.sendStatusMessage(new TextComponentString("§aBack on the rails! Couple it up and go (gently)."), true);
            else p.sendStatusMessage(new TextComponentString("§cCouldn't find track to set it on."), true);
        } else if (s.workKind == 2) {
            s.state = DERAILED;
            s.roll = (s.roll >= 0 ? 1 : -1) * 6;
            s.fire = 0;
            p.sendStatusMessage(new TextComponentString("§aStood back up. Now put it on the track with Rerailing Frogs."), true);
        } else {
            for (ItemStack st : scrap(c)) c.entityDropItem(st, 0.5f);
            c.setDead();
            p.sendStatusMessage(new TextComponentString("§aCut up for scrap."), true);
        }
        sync(c);
    }

    static boolean railAt(World w, double x, double y, double z) {
        for (int dy = -1; dy <= 1; dy++) {
            Block b = w.getBlockState(new BlockPos(x, y + dy, z)).getBlock();
            if (b instanceof BlockRailBase || b == BlockIDs.tcRail.block || b == BlockIDs.tcRailGag.block) return true;
        }
        return false;
    }

    /** the way the track runs at p (degrees, Traincraft's serverRealRotation convention), or NaN if unknown */
    static float trackYaw(World w, BlockPos p) {
        net.minecraft.tileentity.TileEntity te = w.getTileEntity(p);
        if (te instanceof TileTCRailGag) {
            TileTCRailGag g = (TileTCRailGag) te;
            te = w.getTileEntity(new BlockPos(g.originX, g.originY, g.originZ));
        }
        if (te instanceof TileTCRail) return (((TileTCRail) te).getFacing() % 2 == 0) ? 0 : 90;
        IBlockState st = w.getBlockState(p);
        if (st.getBlock() instanceof BlockRailBase) {
            BlockRailBase.EnumRailDirection d = ((BlockRailBase) st.getBlock()).getRailDirection(w, p, st, null);
            if (d == BlockRailBase.EnumRailDirection.NORTH_SOUTH || d == BlockRailBase.EnumRailDirection.ASCENDING_NORTH || d == BlockRailBase.EnumRailDirection.ASCENDING_SOUTH) return 0;
            if (d == BlockRailBase.EnumRailDirection.EAST_WEST || d == BlockRailBase.EnumRailDirection.ASCENDING_EAST || d == BlockRailBase.EnumRailDirection.ASCENDING_WEST) return 90;
        }
        return Float.NaN;
    }

    /** where to set it down: a rail within 4 blocks, facing a way that puts its front bogie on rail too.
     *  Returns {x, y, z, yaw} or null. */
    static double[] rerailSpot(EntityRollingStock c) {
        World w = c.world;
        BlockPos o = new BlockPos(c.posX, c.posY, c.posZ);
        List<BlockPos> rails = new ArrayList<>();
        for (BlockPos p : BlockPos.getAllInBox(o.add(-4, -2, -4), o.add(4, 2, 4))) {
            Block b = w.getBlockState(p).getBlock();
            if (b instanceof BlockRailBase || b == BlockIDs.tcRail.block || b == BlockIDs.tcRailGag.block) rails.add(p.toImmutable());
        }
        rails.sort((a, b) -> Double.compare(a.distanceSqToCenter(c.posX, c.posY, c.posZ), b.distanceSqToCenter(c.posX, c.posY, c.posZ)));
        double shift = Math.abs(c.bogieShift);
        for (BlockPos p : rails) {
            float ty = trackYaw(w, p);
            float base = Float.isNaN(ty) ? Math.round(c.serverRealRotation / 90f) * 90f : ty;
            // keep the way it was pointing if that works, else turn it round
            float a = Math.abs(MathHelper.wrapDegrees(c.serverRealRotation - base)) <= 90 ? base : base + 180;
            for (float yaw : new float[]{a, a + 180}) {
                double x = p.getX() + 0.5, z = p.getZ() + 0.5;
                if (c.bogieLoco == null || shift < 0.1) return new double[]{x, p.getY(), z, MathHelper.wrapDegrees(yaw)};
                double r = Math.toRadians(yaw + 90);
                if (railAt(w, x + Math.cos(r) * shift, p.getY(), z + Math.sin(r) * shift)) return new double[]{x, p.getY(), z, MathHelper.wrapDegrees(yaw)};
            }
        }
        return null;
    }

    static boolean rerail(EntityRollingStock c) {
        double[] spot = rerailSpot(c);
        if (spot == null) return false;
        State s = c.wreck;
        c.motionX = c.motionY = c.motionZ = 0;
        c.serverRealRotation = (float) spot[3];
        c.setPosition(spot[0], spot[1] + 0.2, spot[2]);
        if (c.bogieLoco != null) {
            double r = Math.toRadians(spot[3] + 90), sh = Math.abs(c.bogieShift);
            c.bogieLoco.setPosition(spot[0] + Math.cos(r) * sh, spot[1] + 0.2, spot[2] + Math.sin(r) * sh);
        }
        c.needsBogieUpdate = true;   // and let Traincraft seat it the way it does on load
        s.state = OK;
        s.roll = 0;
        s.fire = 0;
        s.stress = 0;
        s.offRailTicks = 0;
        s.lastSpeed = 0;
        c.world.playSound(null, c.posX, c.posY, c.posZ, SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.NEUTRAL, 0.6f, 1.4f);
        sync(c);
        return true;
    }

    /** what a totaled car leaves when cut up (or broken) */
    public static List<ItemStack> scrap(EntityRollingStock c) {
        List<ItemStack> l = new ArrayList<>();
        java.util.Random r = c.world.rand;
        int iron = (c instanceof Locomotive ? 18 : 10) + r.nextInt(8);
        l.add(new ItemStack(Items.IRON_INGOT, iron));
        l.add(new ItemStack(Items.IRON_NUGGET, 10 + r.nextInt(30)));
        if (c instanceof Locomotive) l.add(new ItemStack(Items.REDSTONE, 2 + r.nextInt(6)));
        if (r.nextBoolean()) l.add(new ItemStack(Blocks.PLANKS, 2 + r.nextInt(8)));
        return l;
    }

    /** breaking a car: a totaled one gives scrap instead of the train (unless the config says keep it) */
    public static boolean dropScrapInstead(EntityRollingStock c) {
        if (c.wreck.state != TOTALED || ConfigHandler.WRECK_TOTALED_KEEPS_CART) return false;
        for (ItemStack st : scrap(c)) c.entityDropItem(st, 0.5f);
        return true;
    }

    // ---------------------------------------------------------------------------------------------- client

    /** client tick: ease the roll toward where the server says it lies (it topples fast, a crane lifts slowly) */
    public static void clientTick(EntityRollingStock c) {
        State s = c.wreck;
        s.prevRollClient = s.rollClient;
        float target = c.watcher().getWatchableObjectFloat(W_ROLL);
        if (state(c) == OK) target = 0;
        float d = target - s.rollClient, step = Math.abs(target) > Math.abs(s.rollClient) ? 7f : 2.5f;
        s.rollClient += Math.max(-step, Math.min(step, d));
        // flames on a burning wreck, sparks while the crew works
        World w = c.world;
        if (c.watcher().getWatchableObjectInt(W_FIRE) == 1 && w.rand.nextInt(2) == 0)
            w.spawnParticle(EnumParticleTypes.FLAME, c.posX + (w.rand.nextDouble() - 0.5) * 2, c.posY + 0.5 + w.rand.nextDouble(), c.posZ + (w.rand.nextDouble() - 0.5) * 2, 0, 0.04, 0);
    }

    public static float renderRoll(EntityRollingStock c, float pt) {
        return c.wreck.prevRollClient + (c.wreck.rollClient - c.wreck.prevRollClient) * pt;
    }

    /** a list of the wreck tool items' damage etc. lives in their own classes */
    public static AxisAlignedBB box(EntityRollingStock c) { return c.getEntityBoundingBox(); }
}
