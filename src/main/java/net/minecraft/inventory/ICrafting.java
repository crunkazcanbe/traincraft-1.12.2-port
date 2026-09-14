package net.minecraft.inventory;

import net.minecraft.item.ItemStack;

public interface ICrafting {
    void sendProgressBarUpdate(Container container, int slotId, int value);
    void sendAllWindowProperties(Container container, IInventory inventory);
    void sendSlotContents(Container container, int slotId, ItemStack itemStack);
}
