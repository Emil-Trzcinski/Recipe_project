package pl.trzcinski.emil.recipeproject.utility;

import pl.trzcinski.emil.recipeproject.domain.Recipe;
import pl.trzcinski.emil.recipeproject.domain.RecipeList;

import java.util.List;

public class RecipeListFilter {

    public static RecipeList listFiltering(RecipeList recipeList) {
        List<Recipe> temp = recipeList.getResults()
                .stream()
                .filter(recipe -> recipe.getId() != null)
                .filter(recipe -> recipe.getName() != null)
                .filter(recipe -> recipe.getNutrition() != null)
                .filter(recipe -> recipe.getNutrition().getCalories() != null)
                .filter(recipe -> recipe.getNutrition().getCarbohydrates() != null)
                .filter(recipe -> recipe.getNutrition().getFat() != null)
                .filter(recipe -> recipe.getNutrition().getFiber() != null)
                .filter(recipe -> recipe.getNutrition().getProtein() != null)
                .filter(recipe -> recipe.getNutrition().getSugar() != null)
                .filter(recipe -> recipe.getTotalTimeMinutes() != null)
                .filter(recipe -> recipe.getInstructions() != null)
                .filter(recipe -> recipe.getSections() != null)
                .filter(recipe -> recipe.getNumServings() != null)
                .filter(recipe -> recipe.getThumbnailUrl() != null)
                .toList();

        RecipeList filteredRecipeList = new RecipeList();
        filteredRecipeList.setResults(temp);
        return filteredRecipeList;
    }
}
