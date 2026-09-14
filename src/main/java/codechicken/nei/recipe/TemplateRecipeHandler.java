package codechicken.nei.recipe;

import codechicken.nei.PositionedStack;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import java.awt.Rectangle;
import java.util.List;
import java.util.ArrayList;

public abstract class TemplateRecipeHandler {
    public List<CachedRecipe> arecipes = new ArrayList<>();
    public List<RecipeTransferRect> transferRects = new ArrayList<>();
    public int cycleticks = 0;

    public abstract String getRecipeName();
    public String getOverlayIdentifier() { return getRecipeName(); }
    public String getGuiTexture() { return ""; }
    public Class<? extends GuiContainer> getGuiClass() { return null; }

    public void loadTransferRects() {}
    public void drawBackground(int recipe) {}
    public void drawForeground(int recipe) {}
    public void drawExtras(int recipe) {}
    public boolean hasOverlay(GuiContainer gui, Container container, int recipe) { return false; }

    public int recipiesPerPage() { return 1; }
    public int numRecipes() { return arecipes.size(); }

    public void loadCraftingRecipes(String outputId, Object... results) {}
    public void loadCraftingRecipes(ItemStack result) {}
    public void loadUsageRecipes(String inputId, Object... ingredients) {}
    public void loadUsageRecipes(ItemStack ingredient) {}

    public TemplateRecipeHandler newInstance() {
        try { return getClass().newInstance(); } catch (Exception e) { return this; }
    }

    protected void randomRenderPermutation(PositionedStack stack, int cycle) {}
    protected void drawProgressBar(int x, int y, int u, int v, int w, int h, int tick, int type) {}

    public List<PositionedStack> getIngredientStacks(int recipeIndex) {
        return arecipes.get(recipeIndex).getIngredients();
    }
    public List<PositionedStack> getResultStack(int recipeIndex) {
        List<PositionedStack> list = new ArrayList<>();
        PositionedStack result = arecipes.get(recipeIndex).getResult();
        if (result != null) list.add(result);
        return list;
    }

    public static class RecipeTransferRect {
        public Rectangle rect;
        public String outputId;
        public RecipeTransferRect(Rectangle rect, String outputId) {
            this.rect = rect;
            this.outputId = outputId;
        }
    }

    public abstract class CachedRecipe {
        public List<PositionedStack> ingredients = new ArrayList<>();
        public PositionedStack result;
        public List<PositionedStack> getIngredients() { return ingredients; }
        public PositionedStack getResult() { return result; }
        public PositionedStack getIngredient() { return ingredients.isEmpty() ? null : ingredients.get(0); }
        public PositionedStack getOtherStack() { return null; }
        public List<PositionedStack> getOtherStacks() { return new ArrayList<>(); }
        public List<PositionedStack> getCycledIngredients(int cycle, List<PositionedStack> ingredients) { return ingredients; }
    }
}
