package pl.trzcinski.emil.recipeproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.trzcinski.emil.recipeproject.api.request.ExternalApiRequest;
import pl.trzcinski.emil.recipeproject.model.Recipe;
import pl.trzcinski.emil.recipeproject.model.RecipeList;
import pl.trzcinski.emil.recipeproject.repository.RecipeRepository;
import pl.trzcinski.emil.recipeproject.utility.RecipeListMapperUtility;

import java.util.*;
import java.util.stream.Collectors;

import static pl.trzcinski.emil.recipeproject.utility.MealPreparedAttributes.calculateKcalPerMeal;
import static pl.trzcinski.emil.recipeproject.utility.MealPreparedAttributes.calculateTimePerMeal;
import static pl.trzcinski.emil.recipeproject.utility.RecipeListFilters.listFiltering;
import static pl.trzcinski.emil.recipeproject.service.MealTagEnum.*;

@Slf4j
@Service
public class RecipeService {

    private final ExternalApiRequest externalApiRequest;
    private final RecipeListMapperUtility recipeListMapperUtility;
    private final RecipeRepository recipeRepository;
    private RecipeList recipeList;


    public RecipeService(RecipeList recipeList, ExternalApiRequest externalApiRequest,
                         RecipeListMapperUtility recipeListMapperUtility, RecipeRepository recipeRepository) {

        this.recipeList = recipeList;
        this.externalApiRequest = externalApiRequest;
        this.recipeListMapperUtility = recipeListMapperUtility;
        this.recipeRepository = recipeRepository;
    }

    //przenieść do MealSrevice????
    public Set<Recipe> getSetOfRecipesWithAllParameters
            (int expectedKcal, int expectedTotalTimeMinutes, int numberOfMeals) throws Exception {

        final Set<Recipe> mealsSet = new HashSet<>();

        final int preparedKcal = calculateKcalPerMeal(expectedKcal, numberOfMeals);
        final int preparedTime = calculateTimePerMeal(expectedTotalTimeMinutes, numberOfMeals);

        switch (numberOfMeals) {
            case 3:
                mealsSet.add(getListOfRecipesWithParameters(preparedKcal, preparedTime, DINNER.getMeal()));

            case 2:
                mealsSet.add(getListOfRecipesWithParameters(preparedKcal, preparedTime,  LUNCH.getMeal()));

            case 1:
                mealsSet.add(getListOfRecipesWithParameters(preparedKcal, preparedTime,  BREAKFAST.getMeal()));
                break;

            default:
                //nothing to do here
        }

        return mealsSet;
    }

    private Recipe getListOfRecipesWithParameters
            (int expectedKcal, int expectedTotalTimeMinutes, String mealTag) throws Exception {

        int requestStartingPoint = 0;
        List<Recipe> recipeTemp;

        do {
        recipeList = recipeListMapperUtility.getListFromResponseBody(externalApiRequest.getResponse(mealTag, requestStartingPoint));
        recipeList = listFiltering(recipeList);
//        recipeList = getExpectedMeal(recipeList, mealTag);

            recipeTemp = recipeList.getResults().stream()
                    .filter(recipe ->
                            (recipe.getNutrition().getCalories() > 0 &&
                                    recipe.getNutrition().getCalories() <= expectedKcal &&
                                    recipe.getTotalTimeMinutes() > 0 &&
                                    recipe.getTotalTimeMinutes() <= expectedTotalTimeMinutes))
                    .collect(Collectors.toList());
            requestStartingPoint += 40;

        } while (recipeTemp.isEmpty());


        //just for test DB
//        Recipe recipeToSave = getRecipeFromListOfRecipes(recipeTemp);
//        recipeRepository.save(recipeToSave);

        return getRecipeFromListOfRecipes(recipeTemp);
    }

    private Recipe getRecipeFromListOfRecipes(List<Recipe> recipeTemp) {

        Optional<Recipe> optionalRecipe;
        optionalRecipe = recipeTemp.stream()
                .reduce((recipe, secRecipe) -> {
                    if (secRecipe != null) {
                        return recipe.getNutrition().getCalories() > secRecipe.getNutrition().getCalories() ?
                                recipe : secRecipe;
                    }
                    return recipe;
                });

        if (optionalRecipe.isEmpty()) {
            log.info("-----Recipe is empty-----");
            throw new RuntimeException("-----Recipe is empty-----");
        }

        return optionalRecipe.get();
    }



//    public void logNameFromRecipeList(RecipeList recipeList) {
//        log.info("--------RecipeList----------");
//
//        recipeList.getResults().stream()
//                .map(recipe ->
//                        "\n Recipe: " + recipe.getName()
//                                + "\n---- Time: " + recipe.getCookTimeMinutes()
//                                + "\n---- PrepTime: " + recipe.getPrepTimeMinutes()
//                                + "\n---- TotalTime: " + recipe.getTotalTimeMinutes()
//                                + "\n---- Kcal: " + recipe.getNutrition().getCalories())
//                                //+ "\n---- Id: " + recipe.getId())
//                .forEach(log::info);
//    }
//
//    public void logNameFromRecipe(Recipe recipe) {
//        log.info("--------Recipe----------");
//        log.info(recipe.toString());
//    }
}
