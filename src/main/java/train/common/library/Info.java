/*******************************************************************************
 * Copyright (c) 2012 Mrbrutal. All rights reserved.
 * 
 * @name TrainCraft
 * @author Mrbrutal
 ******************************************************************************/

package train.common.library;

public class Info {

	/* Mod relevant information */
	public static final String modID = "tc";
	public static final String modName = "Traincraft";
	public static final String		modVersion				= "4.4.1-1.12.2";
	public static final String channel = "Traincraft";
	public static final String keyChannel = "TraincraftKey";
	public static final String rotationChannel = "TraincraftRotation";

	/* Localization keys for versioning */

	/* All the resources for the mod */
	public static final String resourceLocation = "tc";
	public static final String guiPrefix = "textures/gui/";
	public static final String bookPrefix = "textures/gui/book/";
	public static final String trainsPrefix = "textures/trains/";

	/**
	 * Movement/fuel tracing. Left in because it is genuinely useful while finishing the
	 * 1.12.2 port, but it used to print EVERY TICK a cart moved: a short test session put
	 * 993 of 1586 log lines (63%) into TC-RAIL/TC-FUEL spam, which buries real errors and
	 * costs performance. Must stay false in a release build.
	 */
	public static final boolean DEBUG_MOVEMENT = false;
	public static final String zeppelinTexturePrefix = "textures/zeppelin/";
	public static final String modelPrefix = "tc:models/";//"/src/train/Resources/Models/";
	//public static final String modelPrefix2 = "models/";
	public static final String modelTexPrefix = "textures/models/";
	public static final String armorPrefix = "textures/armor/";
	public static final String villagerPrefix = "textures/villager/";

	public static final String TEX_TIER_I = guiPrefix + "gui_tierI_ironAge.png";
	public static final String TEX_TIER_II = guiPrefix + "gui_tierII_steelAge.png";
	public static final String TEX_TIER_III = guiPrefix + "gui_tierIII_advancedAge.png";

	/* Other variables */
	public static final String[] tooltipsTierI = new String[] { "Planks", "Chimney", "Cab", "Dye", "Component", "Boiler", "Firebox", "Wheels", "Frame", "Coupler" };
	public static final String[] tooltipsTierII = new String[] { "Component", "Chimney", "Cab", "Dye", "Component", "Power", "Engine", "Wheels", "Frame", "Coupler" };
}
