package pl.trzcinski.emil.recipeproject.utility;

import pl.trzcinski.emil.recipeproject.model.Recipe;
import pl.trzcinski.emil.recipeproject.model.RecipeList;

import java.util.List;

public class RecipeListFilters {

    private RecipeListFilters() {
        //defensive move to block creating instance of this class
    }

    public static RecipeList listFiltering(RecipeList recipeList) {

        List<Recipe> temp = recipeList.getResults()
                .stream()
                //.filter(recipe -> recipe.getId() != null)
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
                .filter(recipe -> recipe.getTags() != null)
                .toList();

        RecipeList filteredRecipeList = new RecipeList();
        filteredRecipeList.setResults(temp);
        return filteredRecipeList;
    }

    public static RecipeList getExpectedMeal(RecipeList recipeList, String meal) {

        List<Recipe> temp = recipeList.getResults()
                .stream()
                .filter(recipe -> recipe.getTags()
                        .stream()
                        .anyMatch(mealTag -> mealTag.getName().equalsIgnoreCase(meal)))
                .toList();

        RecipeList recipeListWithExpectedMeals = new RecipeList();
        recipeListWithExpectedMeals.setResults(temp);

        return recipeListWithExpectedMeals;
    }
}
