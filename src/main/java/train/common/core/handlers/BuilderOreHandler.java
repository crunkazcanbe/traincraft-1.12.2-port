package train.common.core.handlers;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class BuilderOreHandler {
	public static boolean isOre(int id) {
		List<ItemStack> oreList = OreDictionary.getOres(OreDictionary.getOreName(id));
		if (oreList != null && oreList.size() > 0) {
			return true;
		}
		return false;

	}
}