package pl.trzcinski.emil.recipeproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.trzcinski.emil.recipeproject.api.request.ExternalApiRequest;
import pl.trzcinski.emil.recipeproject.model.Recipe;
import pl.trzcinski.emil.recipeproject.model.RecipeList;
import pl.trzcinski.emil.recipeproject.repository.RecipeRepository;
import pl.trzcinski.emil.recipeproject.utility.RecipeListMapperUtility;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static pl.trzcinski.emil.recipeproject.utility.RecipeListFilters.getExpectedMeal;
import static pl.trzcinski.emil.recipeproject.utility.RecipeListFilters.listFiltering;
import static pl.trzcinski.emil.recipeproject.service.MealTagEnum.*;



@Slf4j
@Service
public class RecipeService {

    private RecipeList recipeList;
    private final ExternalApiRequest externalApiRequest;
    private final RecipeListMapperUtility recipeListMapperUtility;
    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeList recipeList, ExternalApiRequest externalApiRequest,
                         RecipeListMapperUtility recipeListMapperUtility, RecipeRepository recipeRepository) {

        this.recipeList = recipeList;
        this.externalApiRequest = externalApiRequest;
        this.recipeListMapperUtility = recipeListMapperUtility;
        this.recipeRepository = recipeRepository;
    }

    public Set<Recipe> getListOfRecipesWithAllParameters(int kcal, int prepareTotalTimeMinutes, int meals) throws Exception {
        final Set<Recipe> mealSet = new HashSet<>();

        switch (meals) {
            case 1:
                mealSet.add(getListOfRecipesWithParameters(kcal, prepareTotalTimeMinutes, BREAKFAST.getMeal()));
                break;

            case 2:
                mealSet.add(getListOfRecipesWithParameters(kcal, prepareTotalTimeMinutes, BREAKFAST.getMeal()));
                mealSet.add(getListOfRecipesWithParameters(kcal, prepareTotalTimeMinutes, LUNCH.getMeal()));
                break;

            case 3:
                mealSet.add(getListOfRecipesWithParameters(kcal, prepareTotalTimeMinutes, BREAKFAST.getMeal()));
                mealSet.add(getListOfRecipesWithParameters(kcal, prepareTotalTimeMinutes, LUNCH.getMeal()));
                mealSet.add(getListOfRecipesWithParameters(kcal, prepareTotalTimeMinutes, DINNER.getMeal()));
                break;

            default:
                //nothing to do here

        }

        return mealSet;
    }

    public Recipe getListOfRecipesWithParameters(int kcal, int prepareTotalTimeMinutes, String meal) throws Exception {
        recipeList = recipeListMapperUtility.getListFromResponseBody(externalApiRequest.getResponse(meal));
        recipeList = listFiltering(recipeList);
        recipeList = getExpectedMeal(recipeList, meal);

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

        if (optionalRecipe.isEmpty()) {
            log.info("-----Recipe is empty-----");
            //externalApiRequest.setRequestStartingPoint(externalApiRequest.requestStartingPoint += 40);

            throw new RuntimeException("-----Recipe is empty-----");
        }
        return optionalRecipe.get();
    }

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
