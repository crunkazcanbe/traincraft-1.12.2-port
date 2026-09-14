package net.minecraftforge.fluids;

import net.minecraft.item.ItemStack;

/** Legacy stub - FluidContainerRegistry was removed in Forge 1.12 */
public class FluidContainerRegistry {
    public static boolean isContainer(ItemStack stack) { return false; }
    public static boolean isFilledContainer(ItemStack stack) { return stack != null && getFluidForFilledItem(stack) != null; }
    public static boolean isEmptyContainer(ItemStack stack) { return false; }
    public static boolean containsFluid(ItemStack stack, FluidStack fluid) { return false; }
    public static FluidStack getFluidForFilledItem(ItemStack stack) { return null; }
    public static ItemStack fillFluidContainer(FluidStack fluid, ItemStack empty) { return null; }
    public static void registerFluidContainer(Fluid fluid, ItemStack full, ItemStack empty) {}
    public static void registerFluidContainer(FluidStack fluid, ItemStack full, ItemStack empty) {}
}
