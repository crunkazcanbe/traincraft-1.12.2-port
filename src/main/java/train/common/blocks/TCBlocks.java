/*******************************************************************************
 * Copyright (c) 2012 Mrbrutal. All rights reserved.
 *
 * @name TrainCraft
 * @author Mrbrutal
 ******************************************************************************/

package train.common.blocks;

import net.minecraftforge.fml.common.Loader;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import train.common.Traincraft;
import train.common.library.BlockIDs;
import train.common.library.Info;
import train.common.blocks.blockSwitch.BlockMILWSwitchStand;
import train.common.blocks.blockSwitch.BlockcircleSwitchStand;
import train.common.blocks.blockSwitch.BlockowoSwitchStand;
import train.common.blocks.blockSwitch.BlockowoYardSwitchStand;
import train.common.blocks.blockSwitch.BlockSpeedSign;
import train.common.mtc.*;

public class TCBlocks {

	public static void init() {
		loadBlocks();
		registerBlocks();
		setHarvestLevels();
	}

	public static void loadBlocks() {
		BlockIDs.distilIdle.block = new BlockDistil(2, false).setHardness(3.5F);
		BlockIDs.distilActive.block = new BlockDistil(2, true).setHardness(3.5F).setLightLevel(0.8F);

		BlockIDs.assemblyTableI.block = new BlockAssemblyTableI(Material.WOOD).setHardness(3.5F);
		BlockIDs.assemblyTableII.block = new BlockAssemblyTableII(Material.ROCK).setHardness(3.5F);
		BlockIDs.assemblyTableIII.block = new BlockAssemblyTableIII(Material.ROCK).setHardness(3.5F);

		BlockIDs.trainWorkbench.block = new BlockTrainWorkbench(16).setHardness(1.7F);
		BlockIDs.stopper.block = new BlockStopper().setHardness(1.7F);

		BlockIDs.openFurnaceIdle.block = new BlockOpenHearthFurnace(false).setHardness(3.5F);
		BlockIDs.openFurnaceActive.block = new BlockOpenHearthFurnace(true).setHardness(3.5F);
		BlockIDs.oreTC.block = new BlockOreTC().setHardness(3.0F).setResistance(5F);

		BlockIDs.lantern.block = new BlockLantern().setHardness(1.7F).setLightLevel(0.98F);
		BlockIDs.switchStand.block = new BlockSwitchStand().setHardness(1.7F);
		BlockIDs.waterWheel.block = new BlockWaterWheel().setHardness(1.7F);
		BlockIDs.windMill.block = new BlockWindMill().setHardness(1.7F);
		BlockIDs.generatorDiesel.block = new BlockGeneratorDiesel().setHardness(1.7F);

		BlockIDs.tcRail.block = new BlockTCRail().setHardness(1.0F).setCreativeTab(null);
		BlockIDs.tcRailGag.block = new BlockTCRailGag().setHardness(1.0F).setCreativeTab(null);

		BlockIDs.bridgePillar.block = new BlockBridgePillar().setHardness(3.5F);

		BlockIDs.traincraftTrack.block = new BlockTraincraftTrack().setHardness(0.7F);

		BlockIDs.asphalt.block = new Block(Material.GROUND).setHardness(2F).setResistance(10F).setCreativeTab(Traincraft.tcTab);
		BlockIDs.highSpeedBallast.block = new Block(Material.GROUND).setHardness(1F).setResistance(10F).setCreativeTab(Traincraft.tcTab);
		BlockIDs.dirtyBallast.block = new Block(Material.GROUND).setHardness(1F).setResistance(1F).setCreativeTab(Traincraft.tcTab);
		BlockIDs.dirtierBallast.block = new Block(Material.GROUND).setHardness(1F).setResistance(1F).setCreativeTab(Traincraft.tcTab);
		BlockIDs.snowGravel.block = new Block(Material.GROUND).setHardness(1F).setCreativeTab(Traincraft.tcTab);
		BlockIDs.poweredGravel.block = new BlockpoweredGravel(Material.ROCK).setHardness(0F).setCreativeTab(Traincraft.tcTab);
		BlockIDs.MFPBWigWag.block = new BlockMFPBWigWag().setHardness(2.5F).setCreativeTab(Traincraft.tcTab);
		BlockIDs.MILWSwitchStand.block = new BlockMILWSwitchStand().setHardness(1F).setCreativeTab(Traincraft.tcTab);
		BlockIDs.americanstopper.block = new BlockAmericanStopper().setHardness(1.7F).setCreativeTab(Traincraft.tcTab);
		BlockIDs.circleSwitchStand.block = new BlockcircleSwitchStand().setHardness(2F).setCreativeTab(Traincraft.tcTab);
		BlockIDs.embeddedStopper.block = new BlockEmbeddedStopper().setHardness(1.7F).setCreativeTab(Traincraft.tcTab);
		BlockIDs.owoSwitchStand.block = new BlockowoSwitchStand().setHardness(2F).setCreativeTab(Traincraft.tcTab);
		BlockIDs.owoYardSwitchStand.block = new BlockowoYardSwitchStand().setHardness(4F).setCreativeTab(Traincraft.tcTab);
		BlockIDs.metroMadridPole.block = new BlockMetroMadridPole(Material.IRON).setHardness(2F).setCreativeTab(Traincraft.tcTab);
		BlockIDs.speedSign.block = new BlockSpeedSign().setCreativeTab(Traincraft.tcTab);
		BlockIDs.asphaltSlab.block = new BlockAsphaltSlab(false);
		BlockIDs.asphaltDoubleSlab.block = new BlockAsphaltSlab(true);
		BlockIDs.asphaltStairs.block = new BlockAsphaltStairs(BlockIDs.asphalt.block.getDefaultState()).setHardness(2.0F).setLightOpacity(0);
		train.common.tracks.TrackSpec.blockTrack = BlockIDs.traincraftTrack.block;

		if (Loader.isModLoaded("ComputerCraft") || Loader.isModLoaded("OpenComputers")) {
			BlockIDs.mtcTransmitterSpeed.block = new BlockInfoTransmitterSpeed(Material.ROCK).setHardness(3.5F).setCreativeTab(Traincraft.tcTab);
			BlockIDs.mtcTransmitterMTC.block = new BlockInfoTransmitterMTC(Material.ROCK).setHardness(3.5F).setCreativeTab(Traincraft.tcTab);
			BlockIDs.mtcATOStopTransmitter.block = new BlockATOTransmitterStopPoint(Material.ROCK).setHardness(3.5F).setCreativeTab(Traincraft.tcTab);
			BlockIDs.mtcReceiverMTC.block = new BlockInfoGrabberMTC(Material.ROCK).setHardness(3.5F).setCreativeTab(Traincraft.tcTab);
			BlockIDs.mtcReceiverDestination.block = new BlockInfoGrabberDestination(Material.ROCK).setHardness(3.5F).setCreativeTab(Traincraft.tcTab);
			BlockIDs.pdmInstructionBlock.block = new BlockPDMInstructionRadio(Material.ROCK).setHardness(3.5F).setCreativeTab(Traincraft.tcTab);
		}
	}

	public static void registerBlocks() {
		for (BlockIDs blocks : BlockIDs.values()) {
			if (blocks.block != null) {
				if (blocks == BlockIDs.asphaltDoubleSlab) {
					net.minecraft.util.ResourceLocation rn = new net.minecraft.util.ResourceLocation(Info.modID, blocks.name());
					blocks.block.setRegistryName(rn);
					blocks.block.setTranslationKey(Info.modID + ":" + blocks.name());
					net.minecraftforge.fml.common.registry.ForgeRegistries.BLOCKS.register(blocks.block);
					continue;
				}
				if (blocks == BlockIDs.asphaltSlab) {
					net.minecraft.util.ResourceLocation rn = new net.minecraft.util.ResourceLocation(Info.modID, blocks.name());
					blocks.block.setRegistryName(rn);
					blocks.block.setTranslationKey(Info.modID + ":" + blocks.name());
					net.minecraftforge.fml.common.registry.ForgeRegistries.BLOCKS.register(blocks.block);
					net.minecraft.item.ItemSlab slabItem = new net.minecraft.item.ItemSlab(blocks.block,
							(net.minecraft.block.BlockSlab) blocks.block,
							(net.minecraft.block.BlockSlab) BlockIDs.asphaltDoubleSlab.block);
					slabItem.setRegistryName(rn);
					net.minecraftforge.fml.common.registry.ForgeRegistries.ITEMS.register(slabItem);
					continue;
				}
				net.minecraft.util.ResourceLocation regName = new net.minecraft.util.ResourceLocation(Info.modID, blocks.name());
				blocks.block.setRegistryName(regName);
				// NOTE: uses ":" (not ".") to match the existing en_us.lang keys, e.g. "tile.tc:windMill.name" —
				// same convention TCItems.java already uses for items ("tc:" + name), see ItemIDs registration.
				blocks.block.setTranslationKey(Info.modID + ":" + blocks.name());
				net.minecraftforge.fml.common.registry.ForgeRegistries.BLOCKS.register(blocks.block);

				ItemBlock ib;
				if (blocks.hasItemBlock && blocks.itemBlockClass != null) {
					try {
						ib = (ItemBlock) blocks.itemBlockClass.getConstructor(Block.class).newInstance(blocks.block);
					} catch (Exception e) {
						ib = new ItemBlock(blocks.block);
					}
				} else {
					ib = new ItemBlock(blocks.block);
				}
				ib.setRegistryName(regName);
				net.minecraftforge.fml.common.registry.ForgeRegistries.ITEMS.register(ib);
			}
		}
	}

	public static void setHarvestLevels() {
		BlockIDs.trainWorkbench.block.setHarvestLevel("axe", 0);
		BlockIDs.assemblyTableI.block.setHarvestLevel("axe", 0);
		BlockIDs.assemblyTableII.block.setHarvestLevel("axe", 0);
		BlockIDs.assemblyTableIII.block.setHarvestLevel("axe", 0);
		BlockIDs.waterWheel.block.setHarvestLevel("axe", 0);
		BlockIDs.windMill.block.setHarvestLevel("axe", 0);
		BlockIDs.bridgePillar.block.setHarvestLevel("axe", 0);
		BlockIDs.oreTC.block.setHarvestLevel("pickaxe", 1);

		BlockIDs.asphalt.block.setHarvestLevel("pickaxe", 0);
		BlockIDs.asphaltSlab.block.setHarvestLevel("pickaxe", 0);
		BlockIDs.asphaltDoubleSlab.block.setHarvestLevel("pickaxe", 0);
		BlockIDs.asphaltStairs.block.setHarvestLevel("pickaxe", 0);
		BlockIDs.highSpeedBallast.block.setHarvestLevel("shovel", 0);
		BlockIDs.dirtyBallast.block.setHarvestLevel("shovel", 0);
		BlockIDs.dirtierBallast.block.setHarvestLevel("shovel", 0);
		BlockIDs.snowGravel.block.setHarvestLevel("shovel", 0);
		BlockIDs.poweredGravel.block.setHarvestLevel("shovel", 0);

		Blocks.RAIL.setHarvestLevel("ItemStacked", 0);
		Blocks.DETECTOR_RAIL.setHarvestLevel("ItemStacked", 0);
		Blocks.GOLDEN_RAIL.setHarvestLevel("ItemStacked", 0);
	}
}
