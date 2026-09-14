package train.client.core;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.util.SoundCategory;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.Level;
import train.client.core.handlers.ClientTickHandler;
import train.client.core.handlers.RecipeBookHandler;
import train.client.core.handlers.TCKeyHandler;
import train.client.core.helpers.JLayerHook;
import train.client.gui.*;
import train.client.render.*;
import train.client.render.renderSwitch.*;
import train.common.Traincraft;
import train.common.adminbook.GUIAdminBook;
import train.common.api.EntityBogie;
import train.common.api.EntityRollingStock;
import train.common.core.CommonProxy;
import train.common.core.Traincraft_EventSounds;
import train.common.entity.digger.EntityRotativeDigger;
import train.common.entity.digger.EntityRotativeWheel;
import train.common.entity.rollingStock.EntityJukeBoxCart;
import train.common.entity.zeppelin.EntityZeppelinOneBalloon;
import train.common.entity.zeppelin.EntityZeppelinTwoBalloons;
import train.common.library.BlockIDs;
import train.common.library.GuiIDs;
import train.common.library.Info;
import train.common.library.ItemIDs;
import train.common.tile.*;
import train.common.tile.tileSwitch.*;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

import javazoom.jl.decoder.JavaLayerUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;

public class ClientProxy extends CommonProxy {

	public static boolean isHoliday() {
		Calendar cal = Calendar.getInstance();
		return(cal.get(Calendar.MONTH) == Calendar.DECEMBER || (cal.get(Calendar.MONTH) == Calendar.JANUARY) && cal.get(Calendar.DATE) < 7);
	}
	
	@Override
	public void throwAlphaException() {
		throw new AlphaExpiredException();
	}

	@Override
	public void registerEvents(FMLPreInitializationEvent event) {
		super.registerEvents(event);
		ClientTickHandler tickHandler = new ClientTickHandler();
		HUDloco huDloco = new HUDloco();
		if (Loader.isModLoaded("ComputerCraft") || Loader.isModLoaded("OpenComputers")){
			HUDMTC hudMTC = new HUDMTC();
			registerEvent(hudMTC);
		}

		registerEvent(tickHandler);
		registerEvent(huDloco);
	}

	@Override
	public void registerRenderInformation() {
		FMLCommonHandler.instance().bus().register(new ClientTickHandler());

		RenderingRegistry.registerEntityRenderingHandler(EntityRollingStock.class, rm -> new RenderRollingStock(rm));
		RenderingRegistry.registerEntityRenderingHandler(EntityZeppelinTwoBalloons.class, rm -> new RenderZeppelins(rm));
		RenderingRegistry.registerEntityRenderingHandler(EntityZeppelinOneBalloon.class, rm -> new RenderZeppelins(rm));
		RenderingRegistry.registerEntityRenderingHandler(EntityRotativeDigger.class, rm -> new RenderRotativeDigger(rm));
		RenderingRegistry.registerEntityRenderingHandler(EntityRotativeWheel.class, rm -> new RenderRotativeWheel(rm));
		//bogies
		RenderingRegistry.registerEntityRenderingHandler(EntityBogie.class, rm -> new RenderBogie(rm));


		ClientRegistry.bindTileEntitySpecialRenderer(TileStopper.class, new RenderStopper());
		// IItemRenderer removed in 1.12.2: MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockIDs.stopper.block), new ItemRenderStopper());
		
		//ClientRegistry.bindTileEntitySpecialRenderer(TileBook.class, new RenderTCBook());
		// IItemRenderer removed in 1.12.2: //MinecraftForgeClient.registerItemRenderer(BlockIDs.book.blockID, new ItemRenderBook());

		ClientRegistry.bindTileEntitySpecialRenderer(TileSignal.class, new RenderSignal());
		// IItemRenderer removed in 1.12.2: MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockIDs.signal.block), new ItemRenderSignal());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileLantern.class, new RenderLantern());
		// IItemRenderer removed in 1.12.2: MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockIDs.lantern.block), new ItemRenderLantern());

		ClientRegistry.bindTileEntitySpecialRenderer(TileSwitchStand.class, new RenderSwitchStand());
		// IItemRenderer removed in 1.12.2: MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockIDs.switchStand.block), new ItemRenderSwitchStand());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileWaterWheel.class, new RenderWaterWheel());
		// IItemRenderer removed in 1.12.2: MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockIDs.waterWheel.block), new ItemRenderWaterWheel());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileWindMill.class, new RenderWindMill());
		// IItemRenderer removed in 1.12.2: MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockIDs.windMill.block), new ItemRenderWindMill());

		ClientRegistry.bindTileEntitySpecialRenderer(TileGeneratorDiesel.class, new RenderGeneratorDiesel());
		// IItemRenderer removed in 1.12.2: MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockIDs.generatorDiesel.block), new ItemRenderGeneratorDiesel());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileTCRail.class, new RenderTCRail());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileBridgePillar.class, new RenderBridgePillar());
		// IItemRenderer removed in 1.12.2: MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockIDs.bridgePillar.block), new ItemRenderBridgePillar());

		// ponytail: TESRs for the CE rail-accessory blocks that placed invisible because their
		// TileEntitySpecialRenderer was never ported/bound. Models ported to tmt.* under
		// train.client.render.renderSwitch.models and train.client.render.models.blocks.
		// TileMFPBWigWag: model ported from the FMT/TiM export to tmt.* (static geometry,
		// arm-swing animation omitted) under train.client.render.renderSwitch.models.ModelMFPBWigWag.
		ClientRegistry.bindTileEntitySpecialRenderer(TileMFPBWigWag.class, new RenderMFPBWigWag());
		ClientRegistry.bindTileEntitySpecialRenderer(TilecircleSwitchStand.class, new RendercircleSwitchStand());
		ClientRegistry.bindTileEntitySpecialRenderer(TileowoSwitchStand.class, new RenderowoSwitchStand());
		ClientRegistry.bindTileEntitySpecialRenderer(TileowoYardSwitchStand.class, new RenderowoYardSwtichStand());
		ClientRegistry.bindTileEntitySpecialRenderer(TileMILWSwitchStand.class, new RenderMILWSwitchStand());
		ClientRegistry.bindTileEntitySpecialRenderer(TileautoSwitchStand.class, new RenderautoSwitchStand());
		ClientRegistry.bindTileEntitySpecialRenderer(TileSpeedSign.class, new RenderSpeedSign());
		ClientRegistry.bindTileEntitySpecialRenderer(TileoverheadWire.class, new RenderoverheadWire());
		ClientRegistry.bindTileEntitySpecialRenderer(TileoverheadWireDouble.class, new RenderoverheadWireDouble());
		ClientRegistry.bindTileEntitySpecialRenderer(TileMetroMadridPole.class, new RenderMetroMadridPole());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEmbeddedStopper.class, new RenderEmbeddedStopper());
		ClientRegistry.bindTileEntitySpecialRenderer(TileAmericanStopper.class, new RenderAmericanStopper());
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(new net.minecraft.util.math.BlockPos(x, y, z));
		EntityPlayer riddenByEntity = null;
		Entity entity = player.getRidingEntity();
		if (player.getRidingEntity() != null) {
			riddenByEntity = player;
		}

		Entity entity1 = null;
		if (y == -1) {
			for (Object ent : world.loadedEntityList) {
				if (((Entity) ent).getEntityId() == x)
					entity1 = (Entity) ent;
			}
		}
		switch (ID) {
		case (GuiIDs.CRAFTER_TIER_I):
			return te != null && te instanceof TileCrafterTierI ? new GuiCrafterTier(player.inventory, (TileCrafterTierI) te) : null;
		case (GuiIDs.CRAFTER_TIER_II):
			return te != null && te instanceof TileCrafterTierII ? new GuiCrafterTier(player.inventory, (TileCrafterTierII) te) : null;
		case (GuiIDs.CRAFTER_TIER_III):
			return te != null && te instanceof TileCrafterTierIII ? new GuiCrafterTier(player.inventory, (TileCrafterTierIII) te) : null;
		case (GuiIDs.DISTIL):
			return te != null && te instanceof TileEntityDistil ? new GuiDistil(player.inventory, (TileEntityDistil) te) : null;
		case (GuiIDs.GENERATOR_DIESEL):
			return te != null && te instanceof TileGeneratorDiesel ? new GuiGeneratorDiesel(player.inventory, (TileGeneratorDiesel) te) : null;
		case (GuiIDs.OPEN_HEARTH_FURNACE):
			return te != null && te instanceof TileEntityOpenHearthFurnace ? new GuiOpenHearthFurnace(player.inventory, (TileEntityOpenHearthFurnace) te) : null;
		case GuiIDs.TRAIN_WORKBENCH:
			return te != null && te instanceof TileTrainWbench ? new GuiTrainCraftingBlock(player.inventory, player.world, (TileTrainWbench) te) : null;
		case (GuiIDs.LOCO):
			return riddenByEntity != null ? new GuiLoco2(riddenByEntity.inventory, entity) : null;
		case (GuiIDs.FORNEY):
			return riddenByEntity != null ? new GuiForney(riddenByEntity.inventory, entity) : null;
		case (GuiIDs.CRAFTING_CART):
			return riddenByEntity != null ? new GuiCraftingCart(riddenByEntity.inventory, world) : null;
		case (GuiIDs.FURNACE_CART):
			return riddenByEntity != null ? new GuiFurnaceCart(riddenByEntity.inventory, entity) : null;
		case (GuiIDs.ZEPPELIN):
			return riddenByEntity != null ? new GuiZepp(riddenByEntity.inventory, entity) : null;
		case (GuiIDs.DIGGER):
			return riddenByEntity != null ? new GuiBuilder(player, riddenByEntity.inventory, entity) : null;
		case (GuiIDs.MTC_INFO):
			return riddenByEntity != null && Loader.isModLoaded("ComputerCraft")  || Loader.isModLoaded("OpenComputers") ? new GuiMTCInfo(player) : null;

			//Stationary entities while player is not riding. 
		case (GuiIDs.FREIGHT):
			return entity1 != null ? new GuiFreight(player,player.inventory, entity1) : null;
		case (GuiIDs.TENDER):
			return entity1 != null ? new GuiTender(player,player.inventory, entity1) : null;
		case (GuiIDs.BUILDER):
			return entity1 != null ? new GuiBuilder(player,player.inventory, entity1) : null;
		case (GuiIDs.LIQUID):
			return entity1 != null ? new GuiLiquid(player,player.inventory, entity1) : null;
		case (GuiIDs.RECIPE_BOOK):
			return new GuiRecipeBook(player, player.getHeldItemMainhand());
		/*case (GuiIDs.RECIPE_BOOK2):
			return te != null && te instanceof TileBook ? new GuiRecipeBook2(player, player.getCurrentEquippedItem()) : new GuiRecipeBook2(player, player.getCurrentEquippedItem());*/
		case (GuiIDs.LANTERN):
			return new GuiLantern(player, (TileLantern)te);
		case (GuiIDs.JUKEBOX):
			return entity1 != null ? new GuiJukebox(player,(EntityJukeBoxCart)entity1) : null;
		default:
			return null;
		}
	}

	@Override
	public int addArmor(String armor) {
		return 0; // addNewArmourRendererPrefix removed in 1.12.2
	}

	@Override
	public GuiScreen getCurrentScreen() {
		return Minecraft.getMinecraft().currentScreen;
	}
	@Override
	public void registerVillagerSkin(int villagerId, String textureName) {
		// VillagerRegistry API changed in 1.12.2 - skin registration not supported
	}
	@Override
	public void registerSounds() {
		MinecraftForge.EVENT_BUS.register(new Traincraft_EventSounds());
		MinecraftForge.EVENT_BUS.register(train.common.blocks.tracks.TrackTextureLoader.INSTANCE);
	}
	
	@Override
	public void registerBookHandler() {
		RecipeBookHandler recipeBookHandler = new RecipeBookHandler();
	}

	@Override
	public Minecraft getMinecraft() {
		return Minecraft.getMinecraft();
	}
	
	@Override
	public EntityPlayer getPlayer() {
		return Minecraft.getMinecraft().player;
	}

	@Optional.Method(modid = "NotEnoughItems")
	@Override
	public void doNEICheck(ItemStack stack) {
		if (Minecraft.getMinecraft().player != null) {
			if(Loader.isModLoaded("Not Enough Items")) {
				try {
					Class neiApi = Class.forName("codechicken.nei.api.API");
					Method hideItem = neiApi.getDeclaredMethod("hideItem", stack.getClass());
					hideItem.invoke(null, stack);
				} catch (ClassNotFoundException e) {
					Traincraft.tcLog.log(Level.WARN, "Chicken core didn't have required class: Wrong version of the library or something is horribly wrong", e);
				} catch (NoSuchMethodException e) {
					Traincraft.tcLog.log(Level.WARN, "Chicken core didn't have required method: Wrong version of the library or something is horribly wrong", e);
				} catch (SecurityException e) {
					Traincraft.tcLog.log(Level.FATAL, "Something is horribly wrong", e);
				} catch (IllegalAccessException e) {
					Traincraft.tcLog.log(Level.FATAL, "Something is horribly wrong", e);
				} catch (IllegalArgumentException e) {
					Traincraft.tcLog.log(Level.WARN, "Chicken core had the method but it's signature was wrong: Wrong version of the library or something is horribly wrong", e);
				} catch (InvocationTargetException e) {
					Traincraft.tcLog.log(Level.WARN, "The method we called from Chicken core threw an exception", e);
				}
			}
        }
	}
	
	@Override
	public float getJukeboxVolume() {
		return Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.RECORDS) * Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.MASTER);
	}

	@Override
	public void openadmingui(String data){
		Minecraft.getMinecraft().displayGuiScreen(new GUIAdminBook(data));
	}

	@Override
	public void registerKeyBindingHandler() {
		FMLCommonHandler.instance().bus().register(new TCKeyHandler());
	}
	
	@Override
	public void setHook() {
		try { JavaLayerUtils.setHook(new JLayerHook(Minecraft.getMinecraft())); } catch (Throwable t) {}
	}

	/**
	 * Registers the flat inventory-icon model for every item this mod registers via {@link ItemIDs}.
	 * Must run in preInit (client side), before items are done being registered but after TCItems.init()
	 * has created and registered them, and before model baking happens.
	 */
	@Override
	public void registerItemModels() {
		for (ItemIDs itemId : ItemIDs.values()) {
			Item item = itemId.item;
			if (item == null || item.getRegistryName() == null) {
				continue;
			}
			// ponytail: the filled fuel-canister items share their name with a diesel/refined-fuel FLUID
			// block whose blockstates/<name>.json (forge:fluid) wins the "<name>#inventory" model lookup,
			// so the item rendered as a solid fluid sprite instead of the can. Point each at its dedicated
			// item model (no blockstate by that name) to break the clash.
			String path = item.getRegistryName().getPath();
			if ("diesel".equals(path)) {
				ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation("tc:item_diesel_filled", "inventory"));
				continue;
			}
			if ("refinedfuel".equals(path.toLowerCase())) {
				ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation("tc:item_fuel_filled", "inventory"));
				continue;
			}
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}
	}

	/**
	 * Registers the inventory-icon model for the {@code TCBlocks} machine blocks (assembly tables,
	 * distillation tower, hearth furnace, workbench, lantern, water wheel, wind mill, diesel generator,
	 * stopper, bridge pillar, switch stand, and the MTC signalling blocks). Must run in preInit, after
	 * {@link train.common.blocks.TCBlocks#init()} has created and registered the blocks/ItemBlocks.
	 *
	 * NOTE: {@code tcRail}, {@code tcRailGag} and {@code traincraftTrack} are intentionally skipped here.
	 * Their in-world look is fully TileEntity-driven (dozens of track textures picked dynamically per
	 * placed track type via ITrackInstance / TileEntityTraincraftTrack), so a static blockstate/model
	 * would misrepresent them rather than fix them.
	 */
	@Override
	public void registerBlockModels() {
		registerBlockItemModel(BlockIDs.assemblyTableI.block);
		registerBlockItemModel(BlockIDs.assemblyTableII.block);
		registerBlockItemModel(BlockIDs.assemblyTableIII.block);
		registerBlockItemModel(BlockIDs.distilIdle.block);
		registerBlockItemModel(BlockIDs.distilActive.block);
		registerBlockItemModel(BlockIDs.trainWorkbench.block);
		registerBlockItemModel(BlockIDs.openFurnaceIdle.block);
		registerBlockItemModel(BlockIDs.openFurnaceActive.block);
		registerBlockItemModel(BlockIDs.lantern.block);
		registerBlockItemModel(BlockIDs.waterWheel.block);
		registerBlockItemModel(BlockIDs.windMill.block);
		registerBlockItemModel(BlockIDs.generatorDiesel.block);
		registerBlockItemModel(BlockIDs.stopper.block);
		registerBlockItemModel(BlockIDs.bridgePillar.block);
		registerBlockItemModel(BlockIDs.switchStand.block);
		// ponytail: these 4 had valid models+textures but were never bound here, so they
		// rendered magenta in the creative tab. Missing from the original registration pass.
		registerBlockItemModel(BlockIDs.MILWSwitchStand.block);
		registerBlockItemModel(BlockIDs.MFPBWigWag.block);
		registerBlockItemModel(BlockIDs.embeddedStopper.block);
		registerBlockItemModel(BlockIDs.americanstopper.block);

		// New CE rail-accessory blocks GLM added without any model/blockstate (rendered magenta when placed).
		// Each now has a flat-panel (signs/signals/poles/switch-stands) or box (containers) placeholder model
		// using its real UV texture. ponytail: placeholder model, port their real OBJ/TESR later.
		registerBlockItemModel(BlockIDs.autoSwitchStand.block);
		registerBlockItemModel(BlockIDs.circleSwitchStand.block);
		registerBlockItemModel(BlockIDs.owoSwitchStand.block);
		registerBlockItemModel(BlockIDs.owoYardSwitchStand.block);
		registerBlockItemModel(BlockIDs.metroMadridPole.block);
		registerBlockItemModel(BlockIDs.kSignal.block);
		registerBlockItemModel(BlockIDs.signalSpanish.block);
		registerBlockItemModel(BlockIDs.speedSign.block);
		registerBlockItemModel(BlockIDs.overheadWire.block);
		registerBlockItemModel(BlockIDs.overheadWireDouble.block);
		registerBlockItemModel(BlockIDs.FortyFootContainer.block);
		registerBlockItemModel(BlockIDs.FiftyThreeFootContainer.block);

		registerBlockItemModel(BlockIDs.asphalt.block);
		registerBlockItemModel(BlockIDs.asphaltSlab.block);
		registerBlockItemModel(BlockIDs.asphaltStairs.block);
		registerBlockItemModel(BlockIDs.highSpeedBallast.block);
		registerBlockItemModel(BlockIDs.dirtyBallast.block);
		registerBlockItemModel(BlockIDs.dirtierBallast.block);
		registerBlockItemModel(BlockIDs.snowGravel.block);
		registerBlockItemModel(BlockIDs.poweredGravel.block);

		// Only instantiated when ComputerCraft/OpenComputers is loaded - null otherwise, so guarded by
		// registerBlockItemModel's own null check.
		registerBlockItemModel(BlockIDs.mtcTransmitterSpeed.block);
		registerBlockItemModel(BlockIDs.mtcTransmitterMTC.block);
		registerBlockItemModel(BlockIDs.mtcATOStopTransmitter.block);
		registerBlockItemModel(BlockIDs.mtcReceiverMTC.block);
		registerBlockItemModel(BlockIDs.mtcReceiverDestination.block);
		registerBlockItemModel(BlockIDs.pdmInstructionBlock.block);

		// oreTC has 4 metadata subtypes (copperOre/oilSands/petroleum/ballast); each damage value gets its
		// own standalone item model instead of sharing one "inventory" model. See
		// assets/tc/models/item/oreTC_{copperOre,oilSands,petroleum,ballast}.json and
		// assets/tc/blockstates/oreTC.json (variant=0..3), matching BlockOreTC's VARIANT property.
		if (BlockIDs.oreTC.block != null) {
			Item oreItem = Item.getItemFromBlock(BlockIDs.oreTC.block);
			String[] oreModelNames = { "oretc_copperore", "oretc_oilsands", "oretc_petroleum", "oretc_ballast" };
			for (int meta = 0; meta < oreModelNames.length; meta++) {
				ModelLoader.setCustomModelResourceLocation(oreItem, meta,
						new ModelResourceLocation(new ResourceLocation(Info.modID, oreModelNames[meta]), "inventory"));
			}
		}
	}

	private void registerBlockItemModel(net.minecraft.block.Block block) {
		if (block == null) {
			return;
		}
		Item item = Item.getItemFromBlock(block);
		if (item == null || item.getRegistryName() == null) {
			return;
		}
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}