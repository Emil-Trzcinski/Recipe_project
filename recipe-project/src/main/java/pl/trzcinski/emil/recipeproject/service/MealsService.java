package pl.trzcinski.emil.recipeproject.service;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.trzcinski.emil.recipeproject.model.Meals;
import pl.trzcinski.emil.recipeproject.model.Recipe;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static pl.trzcinski.emil.recipeproject.service.MealTagEnum.*;
import static pl.trzcinski.emil.recipeproject.utility.MealPreparedAttributes.calculateKcalPerMeal;
import static pl.trzcinski.emil.recipeproject.utility.MealPreparedAttributes.calculateTimePerMeal;

/**
 * MealsService prepares unique meals
 */
@Slf4j
@Service
@Transactional
public class MealsService implements RecipeSetService {

    private final DataBaseMealsService dataBaseMealsService;
    private final RecipeService recipeService;
    private final ShoppingListService shoppingListService;

    public MealsService(DataBaseMealsService dataBaseMealsService, RecipeService recipeService, ShoppingListService shoppingListService) {
        this.dataBaseMealsService = dataBaseMealsService;
        this.recipeService = recipeService;
        this.shoppingListService = shoppingListService;
    }

    /**
     * takes a unique set of meals
     * <p>
     * first it questions the database and verifies the answer as expected
     * <p>
     * in case of missing meals, complements them with an external api
     * <p>
     * if there are no appropriate meals in the database, it asks the external API.
     * @param expectedKcal number of calories
     * @param expectedTotalTimeMinutes estimated cooking time
     * @param numberOfMeals number of meals
     * @return meals with unique set of recipes
     */
    public Meals getMeals(int expectedKcal, int expectedTotalTimeMinutes, int numberOfMeals) {
        final Set<Recipe> recipeSetFromDB = getSetFromDB(expectedKcal, expectedTotalTimeMinutes, numberOfMeals);
        Meals mealsTemp;

        if (recipeSetFromDB.isEmpty()) {
            log.info("--------ASKING API FOR RECIPES---------------");
            mealsTemp = dataBaseMealsService.create(getExpectedMealsFromApi(expectedKcal, expectedTotalTimeMinutes, numberOfMeals));

            return Meals.builder()
                    .mealsId(mealsTemp.getMealsId())
                    .recipeSet(mealsTemp.getRecipeSet())
                    .totalKcalOfMeals(calculateSumOfMealsKcal(mealsTemp))
                    .sumOfCookTotalTime(calculateSumOfCookTimes(mealsTemp))
                    .shoppingList(shoppingListService.createShoppingList(mealsTemp))
                    .build();

        } else if (recipeSetFromDB.size() < numberOfMeals) {
            log.info("--------ASKING API - NOT ENOUGH EXPECTED RECIPES IN DB--------");

            final Meals apiTempMeals = getExpectedMealsFromApi(expectedKcal, expectedTotalTimeMinutes, numberOfMeals);
            final Set<Recipe> apiRecipeTemp = apiTempMeals.getRecipeSet();

            mealsTemp = dataBaseMealsService.create(getExpectedMealsFromApiAndDB(checkingDuplicatedRecipes(apiRecipeTemp), recipeSetFromDB));

            return Meals.builder()
                    .mealsId(mealsTemp.getMealsId())
                    .recipeSet(getSetFromDB(expectedKcal, expectedTotalTimeMinutes, numberOfMeals))
                    .totalKcalOfMeals(calculateSumOfMealsKcal(mealsTemp))
                    .sumOfCookTotalTime(calculateSumOfCookTimes(mealsTemp))
                    .shoppingList(shoppingListService.createShoppingList(mealsTemp))
                    .build();
        }

        log.info("--------ASKING DB FOR RECIPES---------------");
        Optional<Meals> mealsFromDB = Optional.ofNullable(dataBaseMealsService.findExpectedMeals(expectedKcal, expectedTotalTimeMinutes, numberOfMeals));

        if (mealsFromDB.isEmpty()) {
            Meals correctMeals = dataBaseMealsService.create(createExpectedMealAtDB(recipeSetFromDB));

            return Meals.builder()
                    .mealsId(correctMeals.getMealsId())
                    .recipeSet(correctMeals.getRecipeSet())
                    .totalKcalOfMeals(calculateSumOfMealsKcal(correctMeals))
                    .sumOfCookTotalTime(calculateSumOfCookTimes(correctMeals))
                    .shoppingList(shoppingListService.createShoppingList(correctMeals))
                    .build();

        } else {

            return getExpectedMealsFromDB(dataBaseMealsService.findExpectedMeals(expectedKcal, expectedTotalTimeMinutes, numberOfMeals));
        }
    }

    private Set<Recipe> getSetFromDB(int expectedKcal, int expectedTotalTimeMinutes, int numberOfMeals) {
        if (dataBaseMealsService.isNotEmpty()) {
            return getSetOfRecipesWithAllParameters(expectedKcal, expectedTotalTimeMinutes, numberOfMeals);
        }

        return Collections.emptySet();
    }

    /**
     * creates a set of unique meals
     * @param expectedKcal number of calories
     * @param expectedTotalTimeMinutes estimated cooking time
     * @param numberOfMeals number of meals
     * @return unique set of recipes
     */
    public Set<Recipe> getSetOfRecipesWithAllParameters(int expectedKcal, int expectedTotalTimeMinutes, int numberOfMeals) {
        Set<Recipe> mealsSet = new HashSet<>();
        final int preparedKcal = calculateKcalPerMeal(expectedKcal, numberOfMeals);
        final int preparedTime = calculateTimePerMeal(expectedTotalTimeMinutes, numberOfMeals);

        switch (numberOfMeals) {
            case 3:
                mealsSet.add(getFromDBSingleRecipe(preparedKcal, preparedTime, DINNER.getMeal(), mealsSet));
                mealsSet.remove(null);

            case 2:
                mealsSet.add(getFromDBSingleRecipe(preparedKcal, preparedTime, LUNCH.getMeal(), mealsSet));
                mealsSet.remove(null);

            case 1:
                mealsSet.add(getFromDBSingleRecipe(preparedKcal, preparedTime, BREAKFAST.getMeal(), mealsSet));
                mealsSet.remove(null);
                break;

            default:
                //nothing to do here
        }

        return mealsSet;
    }

    /**
     * downloads recipes and checks for the same names
     * <p>
     * returns null if it does not find a meal in the database
     * @param expectedKcal number of calories
     * @param expectedTotalTimeMinutes estimated cooking time
     * @param mealTag meals tag
     * @param mealsSet stored set of recipes
     * @return recipe
     */
    @Nullable
    private Recipe getFromDBSingleRecipe(int expectedKcal, int expectedTotalTimeMinutes, String mealTag, Set<Recipe> mealsSet) {
        final Set<Recipe> recipeTemp = dataBaseMealsService.findExpectedRecipeSet(expectedKcal, expectedTotalTimeMinutes);
        Set<Recipe> recipeListTemp;
        List<Recipe> preparedListOfRecipe;

        Set<Recipe> recipeSet = recipeTemp
                .stream()
                .filter(recipe -> recipe.getTags()
                        .stream()
                        .anyMatch(tag -> tag.getName().equals(mealTag)))
                .collect(Collectors.toSet());

        if (recipeSet.isEmpty()) {
            return null;
        }

        if (!mealsSet.isEmpty()) {
            recipeListTemp = hasSameName(recipeSet, mealsSet);
        } else {
            List<Recipe> preparedListFromSet = recipeSet.stream().toList();
            return preparedListFromSet.get(0);
        }

        preparedListOfRecipe = recipeListTemp.stream().toList();

        if (preparedListOfRecipe.isEmpty()) {
            return null;
        }
        return preparedListOfRecipe.get(0);
    }

    /**
     * gets unique recipes from external api
     * @param expectedKcal number of calories
     * @param expectedTotalTimeMinutes estimated cooking time
     * @param numberOfMeals number of meals
     * @return unique meals
     */
    private Meals getExpectedMealsFromApi(int expectedKcal, int expectedTotalTimeMinutes, int numberOfMeals) {
        Meals mealsApi = new Meals();

        mealsApi.setMealsId(null);
        mealsApi.setRecipeSet(recipeService.getSetOfRecipesWithAllParameters(expectedKcal, expectedTotalTimeMinutes, numberOfMeals));
        mealsApi.setRecipeSetSize(mealsApi.getRecipeSet().size());
        mealsApi.setTotalKcalOfMeals(calculateSumOfMealsKcal(mealsApi));
        mealsApi.setSumOfCookTotalTime(calculateSumOfCookTimes(mealsApi));
        mealsApi.setShoppingList(shoppingListService.createShoppingList(mealsApi));
        return mealsApi;
    }

    /**
     * gets unique meals from database and external api
     * @param recipeSetFromApi set downloaded from api
     * @param recipeSetFromDB set retrieved from the database
     * @return unique meals
     */
    private Meals getExpectedMealsFromApiAndDB(Set<Recipe> recipeSetFromApi, Set<Recipe> recipeSetFromDB) {
        Meals mealsToDataBase = new Meals();
        Set<Recipe> concatSet = hasTooBigSize(Stream.concat(recipeSetFromApi.stream(), recipeSetFromDB.stream()).collect(Collectors.toSet()));

        mealsToDataBase.setMealsId(null);
        mealsToDataBase.setRecipeSet(concatSet);
        mealsToDataBase.setRecipeSetSize(mealsToDataBase.getRecipeSet().size());
        mealsToDataBase.setTotalKcalOfMeals(calculateSumOfMealsKcal(mealsToDataBase));
        mealsToDataBase.setSumOfCookTotalTime(calculateSumOfCookTimes(mealsToDataBase));
        mealsToDataBase.setShoppingList(shoppingListService.createShoppingList(mealsToDataBase));
        return mealsToDataBase;
    }

    /**
     * gets unique meals from the database
     * @param someMeals meals
     * @return unique meals
     */
    private Meals getExpectedMealsFromDB(Meals someMeals) {
        Meals mealsFromDB = new Meals();

        mealsFromDB.setMealsId(someMeals.getMealsId());
        mealsFromDB.setRecipeSet(someMeals.getRecipeSet());
        mealsFromDB.setRecipeSetSize(someMeals.getRecipeSet().size());
        mealsFromDB.setTotalKcalOfMeals(calculateSumOfMealsKcal(someMeals));
        mealsFromDB.setSumOfCookTotalTime(calculateSumOfCookTimes(someMeals));
        mealsFromDB.setShoppingList(shoppingListService.createShoppingList(someMeals));
        return mealsFromDB;
    }

    /**
     * creates and adds prepared meals to the database
     * @param recipeSet  set of recipes
     * @return unique meals
     */
    private Meals createExpectedMealAtDB(Set<Recipe> recipeSet) {
        Meals mealsToDataBase = new Meals();

        mealsToDataBase.setMealsId(null);
        mealsToDataBase.setRecipeSet(recipeSet);
        mealsToDataBase.setRecipeSetSize(mealsToDataBase.getRecipeSet().size());
        mealsToDataBase.setTotalKcalOfMeals(calculateSumOfMealsKcal(mealsToDataBase));
        mealsToDataBase.setSumOfCookTotalTime(calculateSumOfCookTimes(mealsToDataBase));
        mealsToDataBase.setShoppingList(shoppingListService.createShoppingList(mealsToDataBase));
        return mealsToDataBase;
    }

    /**
     * checks if recipes from the given set are no longer present in the database
     * @param recipeSet set of recipes
     * @return unique set of recipes not present in the database
     */
    private Set<Recipe> checkingDuplicatedRecipes(Set<Recipe> recipeSet) {
        final Set<Recipe> recipesSet = new HashSet<>();
        final Set<String> apiRecipeNamesSet = recipeSet.stream().map(Recipe::getName).collect(Collectors.toSet());

        for (String recipeName : apiRecipeNamesSet) {
            Optional<Recipe> optionalRecipe = Optional.ofNullable(dataBaseMealsService.findByName(recipeName));

            if (optionalRecipe.isEmpty()) {
                recipesSet.addAll(recipeSet.stream()
                        .filter(recipe -> recipe.getName().equals(recipeName))
                        .collect(Collectors.toSet()));
            }
        }
        return recipesSet;
    }

    /**
     * gets unique set of recipe names from recipes and meals
     * @param recipeTemp set of recipes
     * @param mealsSet set of meals
     * @return a unique set of recipes names
     */
    public Set<Recipe> hasSameName(Set<Recipe> recipeTemp, @NotNull Set<Recipe> mealsSet) {
        final Set<Recipe> singleNames = new HashSet<>();

        Set<String> tempOfRecipeNames = mealsSet.stream()
                .filter(Objects::nonNull)
                .map(Recipe::getName)
                .collect(Collectors.toSet());

        for (String recipeName : tempOfRecipeNames) {
            singleNames.addAll(recipeTemp.stream()
                    .filter(recipe -> !recipe.getName().equals(recipeName))
                    .toList());
        }
        return singleNames;
    }

    /**
     * remove excess meals
     * @param concatSet combined set
     * @return unique set of recipes
     */
    private Set<Recipe> hasTooBigSize(Set<Recipe> concatSet) {

        final Set<Recipe> resultSet = new HashSet<>();

        resultSet.add(getBreakfast(concatSet));
        concatSet.removeAll(resultSet);

        resultSet.add(getLunch(concatSet));
        concatSet.removeAll(resultSet);

        resultSet.add(getDinner(concatSet));
        concatSet.removeAll(resultSet);

        return hasNull(resultSet);
    }

    /**
     * gets the recipe with the highest possible calories
     * <p>
     * if the list is empty, returns null
     * @param RecipeList recipe list
     * @return recipe
     */
    @Nullable
    private Recipe getRecipe(List<Recipe> RecipeList) {
        final List<Recipe> preparedRecipeList;
        if (RecipeList.isEmpty()) {
            return null;
        }

        Optional<Integer> recipeOptional = RecipeList.stream()
                .map(recipe -> recipe.getNutrition().getCalories())
                .reduce(Math::max);

        if (RecipeList.size() > 1) {
            preparedRecipeList = RecipeList.stream()
                    .filter(recipe -> recipe.getNutrition().getCalories() >= recipeOptional.get())
                    .toList();

            return preparedRecipeList.get(0);
        }

        return RecipeList.get(0);
    }

    /**
     * gets a recipe for breakfast
     * @param concatSet combined set
     * @return breakfast recipe
     */
    private Recipe getBreakfast(Set<Recipe> concatSet) {
        final List<Recipe> breakfastRecipeList = concatSet.stream()
                .filter(recipe -> recipe.getTags()
                        .stream()
                        .anyMatch(tag -> tag.getName().equalsIgnoreCase(BREAKFAST.getMeal())))
                .toList();

        return getRecipe(breakfastRecipeList);
    }

    /**
     * gets a recipe for dinner
     * <p>
     * if the recipe has the tags lunch and dinner, it returns the meal only with the tag lunch
     * @param concatSet combined set
     * @return recipe for dinner
     */
    private Recipe getLunch(Set<Recipe> concatSet) {
        final List<Recipe> lunchRecipeList = concatSet.stream()
                .filter(recipe -> recipe.getTags()
                        .stream()
                        .anyMatch(tag -> tag.getName().equalsIgnoreCase(LUNCH.getMeal())))
                .toList();

        final List<Recipe> recipeSingleTagList = lunchRecipeList
                .stream()
                .filter(recipe -> recipe.getTags()
                        .stream()
                        .noneMatch(tag -> tag.getName().equalsIgnoreCase(LUNCH.getMeal())
                                && tag.getName().equalsIgnoreCase(DINNER.getMeal())))
                .toList();

        if (recipeSingleTagList.size() > 1) {
            List<Recipe> singleElements = recipeSingleTagList.stream().filter(recipe -> recipe.getTags()
                            .stream()
                            .anyMatch(tag -> tag.getName().equalsIgnoreCase(LUNCH.getMeal())))
                    .toList();

            return singleElements.get(0);
        }

        return getRecipe(lunchRecipeList);
    }

    /**
     * gets a recipe for dinner
     * <p>
     * if the recipe has the tags lunch and dinner, it returns the meal only with the tag dinner
     * @param concatSet combined set
     * @return recipe for dinner
     */
    private Recipe getDinner(Set<Recipe> concatSet) {
        final List<Recipe> dinnerRecipeList = concatSet.stream()
                .filter(recipe -> recipe.getTags()
                        .stream()
                        .anyMatch(tag -> tag.getName().equalsIgnoreCase(DINNER.getMeal())))
                .toList();

        final List<Recipe> recipeSingleTagList = dinnerRecipeList
                .stream()
                .filter(recipe -> recipe.getTags().stream()
                        .noneMatch(tag -> tag.getName().equalsIgnoreCase(LUNCH.getMeal())
                                && tag.getName().equalsIgnoreCase(DINNER.getMeal())))
                .toList();

        if (recipeSingleTagList.size() > 1) {
            List<Recipe> singleElements = recipeSingleTagList.stream().filter(recipe -> recipe.getTags()
                            .stream()
                            .anyMatch(tag -> tag.getName().equalsIgnoreCase(DINNER.getMeal())))
                    .toList();

            return singleElements.get(0);
        }

        return getRecipe(dinnerRecipeList);
    }

    /**
     * removes empty recipes from the set
     * @param mealsSet set of recipes
     * @return unique recipe set
     */
    private Set<Recipe> hasNull(Set<Recipe> mealsSet) {
        return mealsSet
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    /**
     * calculates the total caloric value of your meals
     * @param meals meals
     * @return calorific value
     * @throws RuntimeException when the caloric value of the meal is not present
     */
    @NotNull
    private Integer calculateSumOfMealsKcal(Meals meals) {

        OptionalInt sumOfKcal = meals.getRecipeSet()
                .stream()
                .mapToInt(recipe -> recipe.getNutrition().getCalories())
                .reduce(Integer::sum);

        if (sumOfKcal.isEmpty()) {
            log.debug("--- empty calories numbers ---");
            throw new RuntimeException("--- empty calories numbers ---");
        }

        return sumOfKcal.getAsInt();
    }

    /**
     * calculates the total time to prepare meals
     * @param meals meals
     * @return cooking time
     * @throws RuntimeException when there is no cooking time value of the meal
     */
    @NotNull
    private Integer calculateSumOfCookTimes(Meals meals) {

        OptionalInt sumOfTime = meals.getRecipeSet()
                .stream()
                .mapToInt(Recipe::getTotalTimeMinutes)
                .reduce(Integer::sum);

        if (sumOfTime.isEmpty()) {
            log.debug("--- empty numbers of cooking time ---");
            throw new RuntimeException("--- empty numbers of cooking time ---");
        }

        return sumOfTime.getAsInt();
    }
}