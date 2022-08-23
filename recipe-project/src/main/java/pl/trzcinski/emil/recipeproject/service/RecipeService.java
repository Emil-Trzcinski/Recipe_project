package pl.trzcinski.emil.recipeproject.service;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import pl.trzcinski.emil.recipeproject.api.request.ExternalApiRequest;
import pl.trzcinski.emil.recipeproject.model.Recipe;
import pl.trzcinski.emil.recipeproject.model.RecipeList;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static pl.trzcinski.emil.recipeproject.service.Conversion.CONVERTER;
import static pl.trzcinski.emil.recipeproject.service.MealTagEnum.*;
import static pl.trzcinski.emil.recipeproject.utility.MealPreparedAttributes.calculateKcalPerMeal;
import static pl.trzcinski.emil.recipeproject.utility.MealPreparedAttributes.calculateTimePerMeal;

/**
 * RecipeService zajmuje się przetworzeniem pozyskanych przepisów
 */
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

    /**
     * tworzy unikalny zestaw przepisow
     *
     * @param expectedKcal  oczekiwana kaloryczność
     * @param expectedTotalTimeMinutes oczekiwany czas przygotowania
     * @param numberOfMeals liczba posiłków
     * @return zestaw unikalnych przepisow
     */
    public Set<Recipe> getSetOfRecipesWithAllParameters
            (int expectedKcal, int expectedTotalTimeMinutes, int numberOfMeals) {

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

    /**
     * pobiera przepis z najlepszym dopasowaniem
     * <p>
     * uruchamia zapytanie do zewnetrzbnego, api. odfiltrowywyuje braki w danych i sprawdza czy przepis o danej nazwie nie jest już w serwisie
     * <p>
     * w przypadku kiedy nie uzyska oczkiwanego wyniku zwiększa punkt startowy zapytania w api i ponownie przprowadza powyższy proces.
     *
     * @param expectedKcal oczekiwana kaloryczność
     * @param expectedTotalTimeMinutes oczekiwany czas przygotowania
     * @param mealTag tag posiłku
     * @param mealsSet unikany zestaw przepisów
     * @return przepis
     */
    private Recipe getRecipeFromListOfRecipes
            (int expectedKcal, int expectedTotalTimeMinutes, String mealTag, Set<Recipe> mealsSet) {

        int requestStartingPoint = 0;
        Set<Recipe> recipeSetTemp;
        Set<Recipe> preparedSet = new HashSet<>();

        while (preparedSet.isEmpty()) {
            log.info("Request Starting Point is " + requestStartingPoint);
            try {
                recipeList = recipeListMapperService.getListFromResponseBody(externalApiRequest.getResponse(mealTag, requestStartingPoint));

            } catch (Exception e) {
                log.error(e.getMessage());
            }
            recipeList = recipeListFilterService.listFiltering(recipeList);

            recipeSetTemp = getRecipeSetFromFilteredList(expectedKcal, expectedTotalTimeMinutes);

            if (!mealsSet.isEmpty()) {
                preparedSet = hasSameName(recipeSetTemp, mealsSet);

            } else if (!recipeSetTemp.isEmpty()) {
                return getRecipeWithTopParameters(recipeSetTemp);
            }

            requestStartingPoint += 40;
        }
        return getRecipeWithTopParameters(preparedSet);
    }

    /**
     * tworzy unikalny zestaw przepisów z przefiltrowanej już listy
     * @param expectedKcal oczekiwana kaloczycność
     * @param expectedTotalTimeMinutes oczekiwany czas na przygotwanie
     * @return unikalny zestaw przepisów
     */
    @NotNull
    private Set<Recipe> getRecipeSetFromFilteredList(int expectedKcal, int expectedTotalTimeMinutes) {
        Set<Recipe> recipeSetTemp;
        recipeSetTemp = recipeList.getResults().stream()
                .filter(recipe ->
                        (recipe.getNutrition().getCalories() > 0 &&
                                recipe.getNutrition().getCalories() > expectedKcal * CONVERTER &&
                                recipe.getNutrition().getCalories() <= expectedKcal &&
                                recipe.getTotalTimeMinutes() > 0 &&
                                recipe.getTotalTimeMinutes() <= expectedTotalTimeMinutes))
                .collect(Collectors.toSet());
        return recipeSetTemp;
    }

    /**
     * zwraca przepis z najwyższą wartością Kcal
     * @param preparedSet zestaw przepisów
     * @return przepis
     * @throws RuntimeException występuje w przypadku pustego zestawu przepisów
     */
    private Recipe getRecipeWithTopParameters(Set<Recipe> preparedSet) {

        Optional<Recipe> optionalRecipe;
        optionalRecipe = preparedSet.stream()
                .reduce((recipe, secRecipe) -> {
                    if (secRecipe != null) {
                        return recipe.getNutrition().getCalories() > secRecipe.getNutrition().getCalories() ? recipe : secRecipe;
                    }
                    return recipe;
                });

        if (optionalRecipe.isEmpty()) {
            log.error("-----Prepared Set is empty-----");
            throw new RuntimeException("-----Prepared Set is empty-----");
        }

        return optionalRecipe.get();
    }

    /**
     * zwraca przepisy które nie znajdzują się w "Set<Recipe> mealsSet", elimnuje w ten sposob powtarzające się przepisy
     *
     * @param recipeTemp
     * @param mealsSet
     * @return unikalny zestaw przepisow
     */
    public Set<Recipe> hasSameName(Set<Recipe> recipeTemp, @NotNull Set<Recipe> mealsSet) {
        final Set<Recipe> resultSet = new HashSet<>();

        try {
            Set<String> tempOfRecipeNames = mealsSet.stream().map(Recipe::getName).collect(Collectors.toSet());

            for (String recipeName : tempOfRecipeNames) {
                resultSet.addAll(recipeTemp.stream()
                        .filter(recipe -> !recipe.getName().equals(recipeName))
                        .collect(Collectors.toSet()));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return resultSet;
    }
}
