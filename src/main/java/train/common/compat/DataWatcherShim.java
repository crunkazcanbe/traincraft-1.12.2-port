package train.common.compat;

import java.util.HashMap;
import java.util.Map;

/**
 * Compatibility shim for 1.7.10 DataWatcher API → 1.12.2 EntityDataManager.
 * Stores values in a HashMap since we don't need real network sync for this port.
 */
public class DataWatcherShim {
    private final Map<Integer, Object> data = new HashMap<>();

    public void addObject(int slot, Object value) {
        data.put(slot, value);
    }

    public void updateObject(int slot, Object value) {
        data.put(slot, value);
    }

    public int getWatchableObjectInt(int slot) {
        Object o = data.get(slot);
        if (o instanceof Integer) return (Integer) o;
        if (o instanceof Number) return ((Number) o).intValue();
        return 0;
    }

    public float getWatchableObjectFloat(int slot) {
        Object o = data.get(slot);
        if (o instanceof Float) return (Float) o;
        if (o instanceof Number) return ((Number) o).floatValue();
        return 0.0f;
    }

    public String getWatchableObjectString(int slot) {
        Object o = data.get(slot);
        return o instanceof String ? (String) o : "";
    }

    public byte getWatchableObjectByte(int slot) {
        Object o = data.get(slot);
        if (o instanceof Byte) return (Byte) o;
        if (o instanceof Number) return ((Number) o).byteValue();
        return 0;
    }

    public boolean getWatchableObjectBoolean(int slot) {
        Object o = data.get(slot);
        return o instanceof Boolean && (Boolean) o;
    }
}
