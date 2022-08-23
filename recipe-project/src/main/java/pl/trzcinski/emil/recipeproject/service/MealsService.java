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
 * MealsService przygotowywuje zestaw unikalnych posiłków
 */
@Slf4j
@Service
@Transactional
public class MealsService implements RecipeSetService {

    private final Meals meals;
    private final DataBaseMealsService dataBaseMealsService;
    private final RecipeService recipeService;
    private final ShoppingListService shoppingListService;

    public MealsService(Meals meals, DataBaseMealsService dataBaseMealsService, RecipeService recipeService, ShoppingListService shoppingListService) {
        this.meals = meals;
        this.dataBaseMealsService = dataBaseMealsService;
        this.recipeService = recipeService;
        this.shoppingListService = shoppingListService;
    }

    /**
     * pobiera unikalny zestaw posiłków
     * <p>
     * wpierw odpytuje bazę danych i weryfikuje odpowiedź zgodnie z oczekiwaniami
     * <p>
     * w przypadku braków posiłków uzupełnia je z zew. api
     * <p>
     * jeżlei w bazie danych nie występuję odpowiednie posiłki odpytuje zew. api.
     * @param expectedKcal
     * @param expectedTotalTimeMinutes
     * @param numberOfMeals
     * @return unikalny zestaw posiłków
     */
    public Meals getMeals(int expectedKcal, int expectedTotalTimeMinutes, int numberOfMeals) {
        final Set<Recipe> recipeSetFromDB = getSetFromDB(expectedKcal, expectedTotalTimeMinutes, numberOfMeals);
        Meals mealsTemp;

        if (recipeSetFromDB.isEmpty()) {
            log.info("--------ASKING API FOR RECIPES---------------");
            mealsTemp = dataBaseMealsService.create(getExpectedMealsFromApi(expectedKcal, expectedTotalTimeMinutes, numberOfMeals));

//            Meals mealsResult = new Meals(mealsTemp.getId(), mealsTemp.getRecipeSet(), calculateSumOfMealsKcal(mealsTemp), calculateSumOfCookTimes(mealsTemp), createShoppingList(mealsTemp));
//            return mealsResult;

            return Meals.builder()      //todo refactor wywalic buildery - return new Meals(argmenty) -> zwraca nulle - przeanalizować
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

//            return new Meals(mealsTemp.getId(),
//                    getSetFromDB(expectedKcal, expectedTotalTimeMinutes, numberOfMeals),
//                    sumOfMealsKcal(mealsTemp),
//                    sumOfCookTimes(mealsTemp),
//                    createShoppingList(mealsTemp));

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

//            return new Meals(correctMeals.getId(), correctMeals.getRecipeSet(), sumOfMealsKcal(correctMeals), sumOfCookTimes(correctMeals), createShoppingList(correctMeals));

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
     * tworzy zestaw uniklanych posiłków
     * @param expectedKcal
     * @param expectedTotalTimeMinutes
     * @param numberOfMeals
     * @return unikalny zestaw posiłków
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
     * pobiera przepisy i sprawdza czy nie wystepują te same nazyw
     * <p>
     * jeżlie nie zjadzie posiłku w bazie danych zwraca null
     * @param expectedKcal oczekiwana kalorycznosc
     * @param expectedTotalTimeMinutes oczekiwany całkowity czsa przygotowania
     * @param mealTag nazwa posiłku
     * @param mealsSet zestaw posiadanych przepisów
     * @return przepis
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
     * pobiera unikalne przepisy z zew. api
     * @param expectedKcal oczekiwana kalorycznosc
     * @param expectedTotalTimeMinutes oczekiwany całkowity czas przygotownaia
     * @param numberOfMeals liczba posiłków
     * @return unikalne posiłki
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
     * pobiera unikalne posiłki z bazy danych i zew. api
     * @param recipeSetFromApi zestaw pobrany z api
     * @param recipeSetFromDB zestaw pobrany z bazy danych
     * @return unikalne posiłki
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
     * pobiera unikalne posiłki z bazy danych
     * @param someMeals posiłki
     * @return uniklane posiłki
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
     * tworzy i dodaje przygotowane posiłki do bazydanych
     * @param recipeSet  zestaw przepisów
     * @return unikalne posiłki
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
     * sprawdza czy przepisy z podanego zestawu nie występują już w bazie danych
     * @param recipeSet zestaw przepisów
     * @return unikalny zestaw przepisów nie wystepujące w bazie danych
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
     * zajmue się uzyskaniem unikalnego zestawu nazw przepisów z przepisów i posiłków
     * @param recipeTemp zestaw przepisów
     * @param mealsSet zestaw posiłków
     * @return unikalny zestaw z nawami przepisów
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
     * zajmuje się usuwaniem nadliczbowych posiłków
     * @param concatSet połączony zestaw
     * @return unikalny zestaw
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
     * pobiera przepis z najwyższą możliwą kalorycznością
     * <p>
     * jeżeli lista jest pusta zwraca null
     * @param RecipeList lista przpisow
     * @return przepis
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
     * pobiera przepis na śniadanie
     * @param concatSet połączony zestaw
     * @return przepis na śniadanie
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
     * pobiera przepis na obiad
     * <p>
     * jeżeli przepis posiada tagi obiad i kolacja to zwraca posiłek tylko z tagiem obiad
     * @param concatSet połączony zestaw
     * @return przepis na obiad
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
     * pobiera przepis na kolację
     * <p>
     * jeżeli przepis posiada tagi obiad i kolacja to zwraca posiłek tylko z tagiem kolacja
     * @param concatSet połączony zestaw
     * @return przepis na kolację
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
     * usuówa puste przepisy z zestawu
     * @param mealsSet zestaw przepisow
     * @return unikalny zestaw
     */
    private Set<Recipe> hasNull(Set<Recipe> mealsSet) {
        return mealsSet
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    /**
     * oblicza całkowitą kalorycznosc posiłków
     * @param meals  posiłki
     * @return kalorycznosc
     * @throws RuntimeException kiedy nie wystepuja wartości kalorycznosci posiłku
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
     * oblicza całkowity czas na przygotowanie posiłków
     * @param meals posiłki
     * @return czas
     * @throws RuntimeException kiedy nie wystepuja wartości czasu przygotowania posiłku
     */
    @NotNull
    private Integer calculateSumOfCookTimes(Meals meals) {

        OptionalInt sumOfTime = meals.getRecipeSet()
                .stream()
                .mapToInt(Recipe::getTotalTimeMinutes)
                .reduce(Integer::sum);

        if (sumOfTime.isEmpty()) {
            log.debug("--- empty numbers of cook time ---");
            throw new RuntimeException("--- empty numbers of cook time ---");
        }

        return sumOfTime.getAsInt();
    }
}