package train.common.core.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import train.common.api.AbstractTrains;

import java.util.HashMap;
import java.util.Map;

/** Server -> client: a train's DataWatcherShim values (fuel, water, heat, flags...), so its GUI and HUD are right. */
public class PacketDataWatch implements IMessage {
    private int entityId;
    private Map<Integer, Object> values = new HashMap<>();

    public PacketDataWatch() {}
    public PacketDataWatch(int entityId, Map<Integer, Object> values) { this.entityId = entityId; this.values = values; }

    @Override
    public void toBytes(ByteBuf b) {
        b.writeInt(entityId);
        int n = 0;
        for (Object v : values.values()) if (type(v) >= 0) n++;
        b.writeShort(n);
        for (Map.Entry<Integer, Object> e : values.entrySet()) {
            Object v = e.getValue();
            int t = type(v);
            if (t < 0) continue;
            b.writeShort(e.getKey()); b.writeByte(t);
            switch (t) {
                case 0: b.writeInt((Integer) v); break;
                case 1: b.writeFloat((Float) v); break;
                case 2: ByteBufUtils.writeUTF8String(b, (String) v); break;
                case 3: b.writeByte((Byte) v); break;
                case 4: b.writeBoolean((Boolean) v); break;
                case 5: b.writeDouble((Double) v); break;
                case 6: b.writeLong((Long) v); break;
                default: b.writeShort((Short) v); break;
            }
        }
    }

    private static int type(Object v) {
        return v instanceof Integer ? 0 : v instanceof Float ? 1 : v instanceof String ? 2 : v instanceof Byte ? 3 : v instanceof Boolean ? 4
                : v instanceof Double ? 5 : v instanceof Long ? 6 : v instanceof Short ? 7 : -1;
    }

    @Override
    public void fromBytes(ByteBuf b) {
        entityId = b.readInt();
        int n = b.readShort();
        for (int i = 0; i < n; i++) {
            int k = b.readShort(), t = b.readByte();
            Object v;
            switch (t) {
                case 0: v = b.readInt(); break;
                case 1: v = b.readFloat(); break;
                case 2: v = ByteBufUtils.readUTF8String(b); break;
                case 3: v = b.readByte(); break;
                case 4: v = b.readBoolean(); break;
                case 5: v = b.readDouble(); break;
                case 6: v = b.readLong(); break;
                default: v = b.readShort(); break;
            }
            values.put(k, v);
        }
    }

    public static class Handler implements IMessageHandler<PacketDataWatch, IMessage> {
        @Override
        public IMessage onMessage(PacketDataWatch m, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                if (Minecraft.getMinecraft().world == null) return;
                Entity e = Minecraft.getMinecraft().world.getEntityByID(m.entityId);
                if (e instanceof AbstractTrains) ((AbstractTrains) e).watcher().apply(m.values);
            });
            return null;
        }
    }
}
