package train.common.core.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import train.common.api.AbstractTrains;

public class PacketPaintbrushColorClient implements IMessage {

	int entityID;
	int color;

	public PacketPaintbrushColorClient() {}

	public PacketPaintbrushColorClient(int entityID, int color) {
		this.entityID = entityID;
		this.color = color;
	}

	@Override
	public void fromBytes(ByteBuf bbuf) {
		this.entityID = bbuf.readInt();
		this.color = bbuf.readInt();
	}

	@Override
	public void toBytes(ByteBuf bbuf) {
		bbuf.writeInt(this.entityID);
		bbuf.writeInt(this.color);
	}

	public static class Handler implements IMessageHandler<PacketPaintbrushColorClient, IMessage> {

		@Override
		public IMessage onMessage(PacketPaintbrushColorClient message, MessageContext context) {
			Entity e = Minecraft.getMinecraft().world.getEntityByID(message.entityID);
			if (e instanceof AbstractTrains) ((AbstractTrains) e).setColor(message.color);
			return null;
		}
	}
}
