package models;

import java.util.ArrayList;
import java.util.List;

public class Ingredient {
    private List<String> ingredients = new ArrayList<>();


    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredient(String ingredient) {
        this.ingredients.add(ingredient);
    }
}
