package codechicken.nei.api;

import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.item.ItemStack;

public class API {
    public static void registerRecipeHandler(TemplateRecipeHandler handler) {}
    public static void registerUsageHandler(TemplateRecipeHandler handler) {}
    public static void hideItem(ItemStack stack) {}
    public static void addItemToCreativeInventory(ItemStack stack) {}
    public static void setOverrideName(ItemStack stack, String name) {}
}
