package codechicken.nei;

import net.minecraft.item.ItemStack;

public class NEIClientUtils {
    public static boolean areStacksSameTypeCrafting(ItemStack stack1, ItemStack stack2) {
        if (stack1 == null || stack2 == null) return false;
        return stack1.getItem() == stack2.getItem();
    }
}
