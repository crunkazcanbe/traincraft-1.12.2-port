package train.common.compat;

import java.util.HashMap;
import java.util.Map;

/**
 * Compatibility shim for 1.7.10 DataWatcher API → 1.12.2.
 * The values live in a map; the server sends changed ones (and a full refresh every couple of seconds) to the
 * players tracking the train with PacketDataWatch, so the client GUIs / HUDs see real fuel, water, heat, lock state...
 * (GitHub issue #14: "would be able to be more precise with speed if the GUIs worked" - they all read zeros before).
 */
public class DataWatcherShim {
    private final Map<Integer, Object> data = new HashMap<>();
    private final java.util.Set<Integer> dirty = new java.util.HashSet<>();

    public void addObject(int slot, Object value) {
        data.put(slot, value);
        dirty.add(slot);
    }

    public void updateObject(int slot, Object value) {
        Object old = data.put(slot, value);
        if (!java.util.Objects.equals(old, value)) dirty.add(slot);
    }

    /** the values changed since the last call (server -> clients) */
    public Map<Integer, Object> takeDirty() {
        Map<Integer, Object> m = new HashMap<>();
        for (int k : dirty) m.put(k, data.get(k));
        dirty.clear();
        return m;
    }

    /** everything (the periodic full refresh) */
    public Map<Integer, Object> all() { dirty.clear(); return new HashMap<>(data); }

    /** client: what the server sent */
    public void apply(Map<Integer, Object> m) { data.putAll(m); }

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
