package pl.trzcinski.emil.recipeproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.trzcinski.emil.recipeproject.model.Meals;
import pl.trzcinski.emil.recipeproject.model.Recipe;

import java.util.*;
import java.util.stream.Collectors;

import static pl.trzcinski.emil.recipeproject.service.MealTagEnum.*;
import static pl.trzcinski.emil.recipeproject.utility.MealPreparedAttributes.calculateKcalPerMeal;
import static pl.trzcinski.emil.recipeproject.utility.MealPreparedAttributes.calculateTimePerMeal;
import static pl.trzcinski.emil.recipeproject.utility.ShoppingList.shoppingList;

@Slf4j
@Service
public class MealsService implements RecipeSetService {

    private final Meals meals;
    private final DataBaseMealsService dataBaseMealsService;
    private final RecipeService recipeService;

    public MealsService(Meals meals, DataBaseMealsService dataBaseMealsService, RecipeService recipeService) {
        this.meals = meals;
        this.dataBaseMealsService = dataBaseMealsService;
        this.recipeService = recipeService;
    }

    public Meals getMeals(int expectedKcal, int expectedTotalTimeMinutes, int numberOfMeals) throws Exception {
        final Set<Recipe> recipeSet = getFromDB(expectedKcal, expectedTotalTimeMinutes, numberOfMeals);
        final Meals mealsTemp = new Meals();

        if (recipeSet.isEmpty()) {
            log.info("--------PYTAMY API---------------");
            dataBaseMealsService.create(getExpectedMealsFromApi(expectedKcal, expectedTotalTimeMinutes, numberOfMeals));

        } else if (recipeSet.size() < numberOfMeals) {
            log.info("--------PYTAMY API - ZA MAŁO PRZEPISÓW---------------");

            final Meals tempMeals = getExpectedMealsFromApi(expectedKcal, expectedTotalTimeMinutes, numberOfMeals);
            final Set<Recipe> recipeTemp = tempMeals.getRecipeSet();
            final Set<String> recipeNamesSet = tempMeals.getRecipeSet().stream().map(Recipe::getName).collect(Collectors.toSet());

            mealsTemp.setRecipeSet(checkingRecipies(recipeTemp, recipeNamesSet));
            dataBaseMealsService.create(mealsTemp);

            final Set<Recipe> recipeSetTemp = getFromDB(expectedKcal, expectedTotalTimeMinutes, numberOfMeals);
            mealsTemp.setRecipeSet(recipeSetTemp);
            return getExpectedMealsFromDB(recipeSetTemp);

        } else {
            log.info("--------PYTAMY BAZE DANYCH---------------");
            meals.setRecipeSet(recipeSet);
            return getExpectedMealsFromDB(recipeSet);
        }

        return meals;
    }

    public Set<Recipe> checkingRecipies(Set<Recipe> recipeSet, Set<String> recipeNamesSet) {
        final Set<Recipe> temp = new HashSet<>();

        for (String recipeName : recipeNamesSet) {
            Optional<Recipe> optionalRecipe = Optional.ofNullable(dataBaseMealsService.findByName(recipeName));

            if (optionalRecipe.isEmpty()) {
                temp.addAll(recipeSet.stream()
                        .filter(recipe -> recipe.getName().equals(recipeName))
                        .collect(Collectors.toSet()));
            }
        }
        return temp;
    }

    public Set<Recipe> getFromDB(int expectedKcal, int expectedTotalTimeMinutes, int numberOfMeals) {
        if (dataBaseMealsService.isNotEmpty()) {
            return getSetOfRecipesWithAllParameters(expectedKcal, expectedTotalTimeMinutes, numberOfMeals);
        }

        return Collections.emptySet();
    }

    @Override
    public Set<Recipe> getSetOfRecipesWithAllParameters(int expectedKcal, int expectedTotalTimeMinutes, int numberOfMeals) {
        final Set<Recipe> mealsSet = new HashSet<>();
        final int preparedKcal = calculateKcalPerMeal(expectedKcal, numberOfMeals);
        final int preparedTime = calculateTimePerMeal(expectedTotalTimeMinutes, numberOfMeals);

        switch (numberOfMeals) {
            case 3:
                mealsSet.add(getFromDBSetOfRecipes(preparedKcal, preparedTime, DINNER.getMeal()));

            case 2:
                mealsSet.add(getFromDBSetOfRecipes(preparedKcal, preparedTime,  LUNCH.getMeal()));

            case 1:
                mealsSet.add(getFromDBSetOfRecipes(preparedKcal, preparedTime,  BREAKFAST.getMeal()));
                break;

            default:
                //nothing to do here
        }

        return mealsSet
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    private Recipe getFromDBSetOfRecipes(int expectedKcal, int expectedTotalTimeMinutes, String mealTag) {
        final Set<Recipe> recipeTemp = dataBaseMealsService.findExpectedMeals(expectedKcal, expectedTotalTimeMinutes);

        List<Recipe> readySet = recipeTemp
                .stream()
                .filter(recipe -> recipe.getTags()
                        .stream()
                        .anyMatch(tag -> tag.getName().equals(mealTag)))
                .toList();

        if (readySet.isEmpty()) {
            return null;
        }

        return readySet.get(0);
    }

    public Meals getExpectedMealsFromApi(int expectedKcal, int expectedTotalTimeMinutes, int numberOfMeals) throws Exception {

        meals.setRecipeSet(recipeService.getSetOfRecipesWithAllParameters(expectedKcal, expectedTotalTimeMinutes, numberOfMeals));
        meals.setTotalKcalOfMeals(sumOfMealsKcal());
        meals.setSumOfCookTotalTime(sumOfCookTimes());
        meals.setComponentsMap(shoppingList(meals)); // zmienć na mape

        return meals;
    }

    public Meals getExpectedMealsFromDB(Set<Recipe> recipeSetTemp) {

        meals.setRecipeSet(recipeSetTemp);
        meals.setTotalKcalOfMeals(sumOfMealsKcal());
        meals.setSumOfCookTotalTime(sumOfCookTimes());
        meals.setComponentsMap(shoppingList(meals)); // zmienć na mape

        return meals;
    }

    public Integer sumOfMealsKcal() {

        OptionalInt sumOfKcal = meals.getRecipeSet()
                .stream()
                .mapToInt(recipe -> recipe.getNutrition().getCalories())
                .reduce(Integer::sum);

        if(sumOfKcal.isEmpty()) {
            log.debug("--- empty calories numbers ---");
            throw new RuntimeException("--- empty calories numbers ---");
        }

        return sumOfKcal.getAsInt();
    }

    public Integer sumOfCookTimes() {

        OptionalInt sumOfTime = meals.getRecipeSet()
                .stream()
                .mapToInt(Recipe::getTotalTimeMinutes)
                .reduce(Integer::sum);

        if(sumOfTime.isEmpty()) {
            log.debug("--- empty numbers of cook time ---");
            throw new RuntimeException("--- empty numbers of cook time ---");
        }

        return sumOfTime.getAsInt();
    }


        //metoda ogolna z if / else sprawdzająca baze danych lub uruchamiająca recipeservice - jest
        //metoda zwracająca map ze składnikami -jest
        //metoda do uruchaminia recipeservice - jest
        //metoda sumująca Kcal - jest
        //metoda sumująca czasPrzyrządzania - jest

}
