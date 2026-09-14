package net.minecraft.stats;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Achievement extends StatBase {
    public final int displayColumn;
    public final int displayRow;
    public Achievement parentAchievement;

    public Achievement(String unlocalizedName, String id, int column, int row, Item item, Achievement parent) {
        super(unlocalizedName, new net.minecraft.util.text.TextComponentString(id));
        this.displayColumn = column;
        this.displayRow = row;
        this.parentAchievement = parent;
    }

    public Achievement(String unlocalizedName, String id, int column, int row, ItemStack item, Achievement parent) {
        super(unlocalizedName, new net.minecraft.util.text.TextComponentString(id));
        this.displayColumn = column;
        this.displayRow = row;
        this.parentAchievement = parent;
    }

    public Achievement registerStat() {
        return this;
    }

    public Achievement setSpecial() {
        return this;
    }
}
