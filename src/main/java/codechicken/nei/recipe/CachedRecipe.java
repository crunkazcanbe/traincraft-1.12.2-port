package codechicken.nei.recipe;

import codechicken.nei.PositionedStack;
import java.util.List;
import java.util.ArrayList;

public abstract class CachedRecipe {
    public List<PositionedStack> ingredients = new ArrayList<>();
    public PositionedStack result;
    public List<PositionedStack> getIngredients() { return ingredients; }
    public PositionedStack getResult() { return result; }
}
