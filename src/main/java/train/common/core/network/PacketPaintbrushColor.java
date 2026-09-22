package train.common.core.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import train.common.Traincraft;
import train.common.api.AbstractTrains;

public class PacketPaintbrushColor implements IMessage {

	int entityID;
	int color;

	public PacketPaintbrushColor() {}

	public PacketPaintbrushColor(int entityID, int color) {
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

	public static class Handler implements IMessageHandler<PacketPaintbrushColor, IMessage> {

		@Override
		public IMessage onMessage(PacketPaintbrushColor message, MessageContext context) {
			Entity e = context.getServerHandler().player.world.getEntityByID(message.entityID);
			if (e instanceof AbstractTrains) {
				AbstractTrains t = (AbstractTrains) e;
				t.setColor(message.color);
				Traincraft.modChannel.sendToAllAround(
					new PacketPaintbrushColorClient(message.entityID, message.color),
					new net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint(e.world.provider.getDimension(), e.posX, e.posY, e.posZ, 150.0D));
			}
			return null;
		}
	}
}

