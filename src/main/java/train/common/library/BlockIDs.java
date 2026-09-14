/*******************************************************************************
 * Copyright (c) 2012 Mrbrutal. All rights reserved.
 * 
 * @name TrainCraft
 * @author Mrbrutal
 ******************************************************************************/

package train.common.library;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import train.common.items.*;

public enum BlockIDs {

	assemblyTableI(false, null),
	assemblyTableII(false, null),
	assemblyTableIII(false, null),

	distilIdle(false, null),
	distilActive(false, null),
	signal(false, null),
	
	//book(true, ItemBlockBook.class),

	trainWorkbench(false, null),

	stopper(false, null),

	openFurnaceIdle(false, null),
	openFurnaceActive(false, null),
	oreTC(true, ItemBlockOreTC.class),
	lantern(false, null),
	switchStand(false, null),
	waterWheel(true, ItemBlockGeneratorWaterWheel.class),
	windMill(true, ItemBlockGeneratorWindMill.class),
	generatorDiesel(true, ItemBlockGeneratorDiesel.class),
	mtcTransmitterSpeed(false, null),
	mtcTransmitterMTC(false, null),
	mtcATOStopTransmitter(false, null),
	mtcReceiverMTC(false, null),
	mtcReceiverDestination(false, null),
	pdmInstructionBlock(false, null),
	//Liquids
	diesel(false, ItemBlockFluid.class),
	refinedFuel(false, ItemBlockFluid.class),
	
	tcRailGag(false,null),
	tcRail(false,null),
	bridgePillar(false,null),
	traincraftTrack(false,null),

	asphalt(false, null),
	asphaltSlab(false, null),
	asphaltDoubleSlab(false, null),
	asphaltStairs(false, null),
	highSpeedBallast(false, null),
	dirtyBallast(false, null),
	dirtierBallast(false, null),
	snowGravel(false, null),
	poweredGravel(false, null),
	MFPBWigWag(false, null),
	MILWSwitchStand(false, null),
	americanstopper(false, null),
	circleSwitchStand(false, null),
	embeddedStopper(false, null),
	owoSwitchStand(false, null),
	owoYardSwitchStand(false, null),
	metroMadridPole(false, null),
	speedSign(false, null),
	autoSwitchStand(false, null),
	kSignal(false, null),
	signalSpanish(false, null),
	overheadWire(false, null),
	overheadWireDouble(false, null),
	FortyFootContainer(false, null),
	FiftyThreeFootContainer(false, null);


	public Block block;
	public boolean hasItemBlock;
	public Class itemBlockClass;

	BlockIDs(boolean hasItemBlock, Class<? extends ItemBlock> itemBlockClass) {
		this.hasItemBlock = hasItemBlock;
		this.itemBlockClass = itemBlockClass;
	}
}
