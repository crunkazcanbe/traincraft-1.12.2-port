package train.client.core.handlers;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.item.ItemStack;
import train.client.core.helpers.CapesHelper;
import train.common.Traincraft;
import train.common.core.util.MP3Player;
import train.common.library.Info;

public class ClientTickHandler {
	private static final Minecraft mc = Minecraft.getMinecraft();
	private static boolean isHidden = false;

	@SubscribeEvent
	public void tick(TickEvent event) {
		if(event.side != Side.CLIENT) {
			return;
		}
		switch(event.phase) {
			case START:
				tickStart(event);
				break;
			case END:
				break;
			default:
				break;
		}
	}

	private void tickStart(TickEvent event) {
		if (mc.world == null) { // fixes streaming after exiting a world
			for (MP3Player player : Traincraft.proxy.playerList) if (player != null) player.stop();
			Traincraft.proxy.playerList.clear();
		}
		if(mc.world != null && mc.world.playerEntities != null) {
			for (Object p: mc.world.playerEntities) {
				AbstractClientPlayer player = (AbstractClientPlayer) p;
				CapesHelper user = CapesHelper.users.get(player.getDisplayName().getUnformattedText());
				if(user == null) {
					user = new CapesHelper(player.getDisplayName().getUnformattedText());
					CapesHelper.users.put(player.getDisplayName().getUnformattedText(), user);
					user.setDaemon(true);
					user.setName("Cape for " + player.getDisplayName().getUnformattedText());
					user.start();
				}else if(CapesHelper.isLoaded && user.MCCape) {
					// Cape loading not supported in 1.12.2 API
					// player.func_152121_a(MinecraftProfileTexture.Type.CAPE, user.getCurrentRL());
				}
			}
		}
		if(!isHidden && Loader.isModLoaded("NotEnoughItems")) {
			if(mc.world != null && mc.world.playerEntities != null) {
				Traincraft.proxy.doNEICheck(new ItemStack(Block.getBlockFromName(Info.modID + ":tcRail")));
				Traincraft.proxy.doNEICheck(new ItemStack(Block.getBlockFromName(Info.modID + ":tcRailGag")));
				isHidden = true;
			}
		}
	}

}