package net.minecraftforge.client;

import net.minecraft.item.ItemStack;

public interface IItemRenderer {
    enum ItemRenderType {
        ENTITY, EQUIPPED, EQUIPPED_FIRST_PERSON, INVENTORY, FIRST_PERSON_MAP
    }
    enum ItemRendererHelper {
        BLOCK_3D, EQUIPPED_BLOCK, BLOCK_ROTATE, INVENTORY_BLOCK,
        ENTITY_ROTATION, ENTITY_BOBBING
    }

    boolean handleRenderType(ItemStack item, ItemRenderType type);
    boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper);
    void renderItem(ItemRenderType type, ItemStack item, Object... data);
}
