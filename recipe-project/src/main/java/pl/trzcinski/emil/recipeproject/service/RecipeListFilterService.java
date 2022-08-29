package pl.trzcinski.emil.recipeproject.service;

import org.springframework.stereotype.Service;
import pl.trzcinski.emil.recipeproject.model.Recipe;
import pl.trzcinski.emil.recipeproject.model.RecipeList;

import java.util.List;
import java.util.Optional;

/**
 * RecipeListFilterService filtering recipes with missing data
 */
@Service
public class RecipeListFilterService {

    public RecipeListFilterService() {
    }

    /**
     * filtering list of recipes with missing data
     * <p>
     * if the received list is empty, it returns an exception with information about a problem with external api or an exhausted limit of queries
     *
     * @param recipeList recipe list
     * @return filtered recipe list
     * @throws RuntimeException returns an exception when list is empty
     */
    public RecipeList listFiltering(RecipeList recipeList) {

        Optional<List<Recipe>> optionalRecipeList = Optional.ofNullable(recipeList.getResults());

        if (optionalRecipeList.isPresent()) {
            List<Recipe> temp = recipeList.getResults()
                    .stream()
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

        } else {
            throw new RuntimeException("Empty recipe list - " +
                    "probably external api doesn't work correctly or your limit is exhaust - please check response status code");
        }
    }
}
