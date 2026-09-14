package net.minecraftforge.common;

import net.minecraft.stats.Achievement;

public class AchievementPage {
    private final String name;
    private final Achievement[] achievements;

    public AchievementPage(String name, Achievement... achievements) {
        this.name = name;
        this.achievements = achievements;
    }

    public static void registerAchievementPage(AchievementPage page) {}

    public String getName() { return name; }
    public Achievement[] getAchievements() { return achievements; }
}
