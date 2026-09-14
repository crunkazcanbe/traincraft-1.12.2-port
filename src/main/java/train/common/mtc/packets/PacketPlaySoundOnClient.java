package train.common.mtc.packets;

import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

/*
public class PacketPlaySoundOnClient implements IMessage {
    public int entity;
    public String sound;


    public PacketPlaySoundOnClient() {
    }

    public PacketPlaySoundOnClient(int entity, String sound) {
        // Entity TrainEntity = context.getServerHandler().player.ridingEntity;
        this.entity = entity;
        this.sound = sound;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        entity = buf.readInt();
        sound = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(entity);
        ByteBufUtils.writeUTF8String(buf, sound);
    }

}*/

public class PacketPlaySoundOnClient implements IMessage {
    public int entityID;
    public String sound;

    public PacketPlaySoundOnClient() {}

    public PacketPlaySoundOnClient(Integer entityID, String sound) {
        this.entityID = entityID;
        this.sound = sound;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        this.entityID = buf.readInt();
        sound = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(entityID);
        ByteBufUtils.writeUTF8String(buf, sound);
    }
}