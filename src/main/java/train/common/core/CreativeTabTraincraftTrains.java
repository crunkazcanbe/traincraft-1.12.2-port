package train.common.core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import train.common.library.ItemIDs;

public class CreativeTabTraincraftTrains extends CreativeTabs {

	public CreativeTabTraincraftTrains(int par1, String par2Str) {
		super(par1, par2Str);
	}

	@Override
	public ItemStack createIcon() {
		return new ItemStack(ItemIDs.minecartLocoSD70.item);
	}
}
