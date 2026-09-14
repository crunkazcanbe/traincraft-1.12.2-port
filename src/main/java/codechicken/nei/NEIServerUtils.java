package codechicken.nei;

import net.minecraft.item.ItemStack;

public class NEIServerUtils {
    public static boolean areStacksSameType(ItemStack stack1, ItemStack stack2) {
        if (stack1 == null || stack2 == null) return false;
        return stack1.getItem() == stack2.getItem();
    }
}
