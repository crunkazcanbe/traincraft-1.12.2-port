package train.common.core.handlers;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.ReportedException;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.world.ChunkEvent;
import train.common.api.AbstractTrains;
import train.common.api.Locomotive;
import train.common.entity.ai.EntityAIFearHorn;
import train.common.entity.rollingStock.EntityJukeBoxCart;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class WorldEvents{
	private int windTicker = 0;
	private static Random rand = new Random();
	public static int windStrength = 10 + rand.nextInt(10);

	@SubscribeEvent
	public void onWorldTick(TickEvent.WorldTickEvent handler){
		if(handler.world.isRemote){
			if(windTicker % 128 == 0){
				updateWind();
				windTicker=0;
			}
			windTicker++;
		}
	}

	private static void updateWind() {
		int upChance = 10;
		int downChance = 10;
		if (windStrength > 20) {
			upChance -= windStrength - 20;
		}
		else if (windStrength < 10) {
			downChance -= 10 - windStrength;
		}
		if (rand.nextInt(100) <= upChance) {
			windStrength += 1;
		}
		if (rand.nextInt(100) <= downChance) {
			windStrength -= 1;
		}
	}
	
	@SubscribeEvent
	public void entitySpawn(EntityJoinWorldEvent event) {
		if(event.getEntity() instanceof EntityAnimal) {
			((EntityAnimal) event.getEntity()).tasks.addTask(0, new EntityAIFearHorn(((EntityAnimal) event.getEntity())));
		}
	}

	@SubscribeEvent
	@SuppressWarnings("unused")
	public void playerQuitEvent(PlayerEvent.PlayerLoggedOutEvent event){
		if (event.player.getRidingEntity() instanceof AbstractTrains){
			if (event.player.getRidingEntity() instanceof Locomotive) {
				((Locomotive) event.player.getRidingEntity()).isBraking=true;
				((Locomotive) event.player.getRidingEntity()).parkingBrake=true;
			}
			event.player.dismountRidingEntity();
		}
	}

	@SubscribeEvent
	public void chunkUnloadEvent(ChunkEvent.Unload event){
		net.minecraft.util.math.ChunkPos pos = event.getChunk().getPos();
		net.minecraft.util.math.AxisAlignedBB box = new net.minecraft.util.math.AxisAlignedBB(
				pos.getXStart(), 0, pos.getZStart(), pos.getXEnd() + 1, 256, pos.getZEnd() + 1);
		for (EntityJukeBoxCart cart : event.getWorld().getEntitiesWithinAABB(EntityJukeBoxCart.class, box)) {
			cart.player.setVolume(0);
		}
	}

	@SubscribeEvent
	@SuppressWarnings("unused")
	public void EntityStruckByLightningEvent(EntityStruckByLightningEvent event) {
		if (event.getEntity() instanceof AbstractTrains){
			event.setCanceled(true);
		}
	}
}
