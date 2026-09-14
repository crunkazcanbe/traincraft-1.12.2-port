package li.cil.oc.api.network;

import net.minecraft.nbt.NBTTagCompound;

public interface Node {
    // NOTE: real OpenComputers puts load/save on a separate Persistable interface; folded
    // directly into this vendored stub since that's the only place Traincraft calls them from.
    void load(NBTTagCompound nbt);
    void save(NBTTagCompound nbt);

    String address();
    Environment host();
    Visibility reachability();
    boolean canBeReachedFrom(Node other);
    boolean isNeighborOf(Node other);
    void connect(Node node);
    void disconnect(Node node);
    void remove();
    void sendToAddress(String address, String name, Object... data);
    void sendToNeighbors(String name, Object... data);
    void sendToReachable(String name, Object... data);
    void sendToVisible(String name, Object... data);
}
