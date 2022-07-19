package pl.trzcinski.emil.recipeproject.utility.builders;

import pl.trzcinski.emil.recipeproject.model.Recipe;
import pl.trzcinski.emil.recipeproject.model.RecipeList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeListBuilder {

    private List<Recipe> results;

    Recipe recipe = new RecipeBuilder().withDefaultRecipe().build();
    Recipe secRecipe = new RecipeBuilder().withDefaultRecipe().withDefaultName().build();

    public RecipeListBuilder withDefaultRecipeList() {
        this.results = new ArrayList<>(Arrays.asList(recipe, secRecipe));
        return this;
    }

    public RecipeList build() {
        RecipeList recipeList = new RecipeList(results);
        return recipeList;
    }

}
