package pl.trzcinski.emil.recipeproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.trzcinski.emil.recipeproject.api.request.ExternalApiRequest;
import pl.trzcinski.emil.recipeproject.model.Recipe;
import pl.trzcinski.emil.recipeproject.model.RecipeList;
import pl.trzcinski.emil.recipeproject.repository.RecipeRepository;
import pl.trzcinski.emil.recipeproject.utility.RecipeListMapperUtility;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static pl.trzcinski.emil.recipeproject.utility.RecipeListFilter.listFiltering;

@Slf4j
@Service
public class RecipeService {

    private RecipeList recipeList;
    private final ExternalApiRequest externalApiRequest;
    private final RecipeListMapperUtility recipeListMapperUtility;
    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeList recipeList, ExternalApiRequest externalApiRequest, RecipeListMapperUtility recipeListMapperUtility, RecipeRepository recipeRepository) {
        this.recipeList = recipeList;
        this.externalApiRequest = externalApiRequest;
        this.recipeListMapperUtility = recipeListMapperUtility;
        this.recipeRepository = recipeRepository;
    }

    public Recipe getListOfRecipesWithParameters(int kcal, int prepareTotalTimeMinutes) throws Exception {
        recipeList = recipeListMapperUtility.getListFromResponseBody(externalApiRequest.getResponse());
        recipeList = listFiltering(recipeList);

        List<Recipe> recipeTemp;

        recipeTemp = recipeList.getResults().stream()
                .filter(recipe ->
                        (recipe.getNutrition().getCalories() > 0 &&
                                recipe.getNutrition().getCalories() <= kcal &&
                                recipe.getTotalTimeMinutes() > 0  &&
                                recipe.getTotalTimeMinutes() <= prepareTotalTimeMinutes))
                .collect(Collectors.toList());

        //just for test DB
        Recipe recipeToSave = getRecipeFromListOfRecipes(recipeTemp);
        recipeRepository.save(recipeToSave);

        return getRecipeFromListOfRecipes(recipeTemp);
    }

    private Recipe getRecipeFromListOfRecipes(List<Recipe> recipeTemp) {
        Optional<Recipe> optionalRecipe;
        optionalRecipe = recipeTemp.stream()
                .reduce((recipe, recipe2) -> {
                    if (recipe2 != null) {
                        return recipe.getNutrition().getCalories() > recipe2.getNutrition().getCalories() ?
                                recipe : recipe2;
                    }
                    return recipe;
                });

        return optionalRecipe.get();
    }

    // jakaś medtoda kótra kontaktuje się z funkcja skierowana do externalapi


    public void logNameFromRecipeList(RecipeList recipeList) {
        log.info("--------RecipeList----------");

        recipeList.getResults().stream()
                .map(recipe ->
                        "\n Recipe: " + recipe.getName()
                                + "\n---- Time: " + recipe.getCookTimeMinutes()
                                + "\n---- PrepTime: " + recipe.getPrepTimeMinutes()
                                + "\n---- TotalTime: " + recipe.getTotalTimeMinutes()
                                + "\n---- Kcal: " + recipe.getNutrition().getCalories())
                                //+ "\n---- Id: " + recipe.getId())
                .forEach(log::info);
    }

    public void logNameFromRecipe(Recipe recipe) {
        log.info("--------Recipe----------");
        log.info(recipe.toString());
    }
}
