package pl.trzcinski.emil.recipeproject.service;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import pl.trzcinski.emil.recipeproject.api.request.ExternalApiRequest;
import pl.trzcinski.emil.recipeproject.model.Recipe;
import pl.trzcinski.emil.recipeproject.model.RecipeList;

import java.util.*;
import java.util.stream.Collectors;

import static pl.trzcinski.emil.recipeproject.utility.MealPreparedAttributes.calculateKcalPerMeal;
import static pl.trzcinski.emil.recipeproject.utility.MealPreparedAttributes.calculateTimePerMeal;
import static pl.trzcinski.emil.recipeproject.service.MealTagEnum.*;

@Slf4j
@Service
public class RecipeService implements RecipeSetService {

    private final RecipeListFilterService recipeListFilterService;

    private final ExternalApiRequest externalApiRequest;
    private final RecipeListMapperService recipeListMapperService;
    private RecipeList recipeList;

    public RecipeService(RecipeListFilterService recipeListFilterService, RecipeList recipeList, ExternalApiRequest externalApiRequest,
                         RecipeListMapperService recipeListMapperService) {

        this.recipeListFilterService = recipeListFilterService;
        this.recipeList = recipeList;
        this.externalApiRequest = externalApiRequest;
        this.recipeListMapperService = recipeListMapperService;
    }

    public Set<Recipe> getSetOfRecipesWithAllParameters
            (int expectedKcal, int expectedTotalTimeMinutes, int numberOfMeals) throws Exception {

        final Set<Recipe> mealsSet = new HashSet<>();
        final int preparedKcal = calculateKcalPerMeal(expectedKcal, numberOfMeals);
        final int preparedTime = calculateTimePerMeal(expectedTotalTimeMinutes, numberOfMeals);

        switch (numberOfMeals) {
            case 3:
                mealsSet.add(getRecipeFromListOfRecipes(preparedKcal, preparedTime, DINNER.getMeal(), mealsSet));

            case 2:
                mealsSet.add(getRecipeFromListOfRecipes(preparedKcal, preparedTime, LUNCH.getMeal(), mealsSet));

            case 1:
                mealsSet.add(getRecipeFromListOfRecipes(preparedKcal, preparedTime, BREAKFAST.getMeal(), mealsSet));
                break;

            default:
                //nothing to do here
        }

        return mealsSet;
    }

    private Recipe getRecipeFromListOfRecipes
            (int expectedKcal, int expectedTotalTimeMinutes, String mealTag, Set<Recipe> mealsSet) throws Exception {

        int requestStartingPoint = 0;
        Set<Recipe> recipeTemp;
        Set<Recipe> preparedSet;

        do {
            recipeList = recipeListMapperService.getListFromResponseBody(externalApiRequest.getResponse(mealTag, requestStartingPoint));

            recipeList = recipeListFilterService.listFiltering(recipeList);

            recipeTemp = recipeList.getResults().stream()
                    .filter(recipe ->
                            (recipe.getNutrition().getCalories() > 0 &&
                                    recipe.getNutrition().getCalories() <= expectedKcal &&
                                    recipe.getTotalTimeMinutes() > 0 &&
                                    recipe.getTotalTimeMinutes() <= expectedTotalTimeMinutes))
                    .collect(Collectors.toSet());

            requestStartingPoint += 40;

        } while (recipeTemp.isEmpty());

        if (!mealsSet.isEmpty()) {
            preparedSet = hasSameName(recipeTemp, mealsSet);
        } else {
            return getRecipeWithTopParameters(recipeTemp);
        }

        return getRecipeWithTopParameters(preparedSet);
    }

    private Recipe getRecipeWithTopParameters(Set<Recipe> recipeTemp) {

        Optional<Recipe> optionalRecipe;
        optionalRecipe = recipeTemp.stream()
                .reduce((recipe, secRecipe) -> {
                    if (secRecipe != null) {
                        return recipe.getNutrition().getCalories() > secRecipe.getNutrition().getCalories() ? recipe : secRecipe;
                    }
                    return recipe;
                });

        if (optionalRecipe.isEmpty()) {
            log.info("-----Recipe is empty-----");
            throw new RuntimeException("-----Recipe is empty-----");
        }

        return optionalRecipe.get();
    }

    public Set<Recipe> hasSameName(Set<Recipe> recipeTemp, @NotNull Set<Recipe> mealsSet) {
        final Set<Recipe> temp = new HashSet<>();
        Set<String> tempOfRecipeNames = mealsSet.stream().map(Recipe::getName).collect(Collectors.toSet());

        for (String recipeName : tempOfRecipeNames) {
            temp.addAll(recipeTemp.stream()
                    .filter(recipe -> !recipe.getName().equals(recipeName))
                    .collect(Collectors.toSet()));
        }
        return temp;
    }
}
