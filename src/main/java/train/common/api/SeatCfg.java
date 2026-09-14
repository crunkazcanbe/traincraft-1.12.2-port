package train.common.api;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Every one of Traincraft's ~393 rolling-stock classes hardcodes its own rider offset as
 * "double distance = N;", and the sign convention is not obvious -- the loco models carry a yaw
 * offset, so the math's "forward" is not reliably the train's front. Rather than guess, every one of
 * those literals now goes through d(), so the whole fleet is tunable from one text file with no
 * rebuild and no code edit.
 *
 * ~/.config/dogpound/train-seat.conf
 *   mode=keep     use each train's original value (stock behaviour)
 *   mode=front    +|N|  -- rider toward one end
 *   mode=rear     -|N|  -- rider toward the other end
 *   mode=center   0     -- rider dead centre of the train
 *   mode=value    use "value=" for every train
 *   value=3.5
 *
 * Per-train overrides beat the global mode: a line like "EntityLocoElectricICE1=-4.375" pins that
 * one train's seat distance exactly, regardless of mode. Needed because each model's sign
 * convention differs -- "front" for one model is "rear" for another.
 *
 * Re-read at most once a second, so editing the file moves the seat in a live game.
 */
public final class SeatCfg {
    private SeatCfg() {}

    private static final File FILE = new File(
            System.getProperty("user.home", "."), ".config/dogpound/train-seat.conf");

    private static volatile String mode = "keep";
    private static volatile double value = 0.0D;
    private static volatile long lastRead = 0L;
    private static volatile Properties props = new Properties();

    /** Transform a train's hardcoded seat distance according to the config. */
    public static double d(Object train, double original) {
        reloadIfStale();
        String override = props.getProperty(train.getClass().getSimpleName());
        if (override != null) {
            try { return Double.parseDouble(override.trim()); }
            catch (NumberFormatException ignored) {}
        }
        String m = mode;
        if ("front".equals(m))  return Math.abs(original);
        if ("rear".equals(m))   return -Math.abs(original);
        if ("center".equals(m)) return 0.0D;
        if ("value".equals(m))  return value;
        return original;   // keep
    }

    private static void reloadIfStale() {
        long now = System.currentTimeMillis();
        if (now - lastRead < 1000L) return;
        lastRead = now;
        if (!FILE.isFile()) { mode = "keep"; props = new Properties(); return; }
        FileInputStream in = null;
        try {
            Properties p = new Properties();
            in = new FileInputStream(FILE);
            p.load(in);
            mode = p.getProperty("mode", "keep").trim().toLowerCase();
            try { value = Double.parseDouble(p.getProperty("value", "0").trim()); }
            catch (NumberFormatException ignored) { value = 0.0D; }
            props = p;
        } catch (Throwable ignored) {
            mode = "keep";
        } finally {
            if (in != null) try { in.close(); } catch (Throwable ignored) {}
        }
    }
}
