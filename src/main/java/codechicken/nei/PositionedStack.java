package codechicken.nei;

import net.minecraft.item.ItemStack;
import java.util.List;
import java.util.ArrayList;

public class PositionedStack {
    public ItemStack item;
    public int relx, rely;
    public List<ItemStack> items = new ArrayList<>();

    public PositionedStack(Object ingredient, int x, int y) {
        this.relx = x;
        this.rely = y;
        if (ingredient instanceof ItemStack) {
            this.item = (ItemStack) ingredient;
            this.items.add(this.item);
        }
    }

    public PositionedStack(Object ingredient, int x, int y, boolean cycle) {
        this(ingredient, x, y);
    }

    public void setMaxSize(int size) {}
    public void generatePermutations() {}
    public ItemStack[] getItems() { return items.toArray(new ItemStack[0]); }

    public PositionedStack copy() {
        PositionedStack copy = new PositionedStack(item != null ? item.copy() : null, relx, rely);
        return copy;
    }
}
