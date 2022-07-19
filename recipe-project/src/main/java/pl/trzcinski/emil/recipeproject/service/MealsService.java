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
import static pl.trzcinski.emil.recipeproject.utility.ShoppingList.shoppingList;

@Slf4j
@Service
@Transactional
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
        final Set<Recipe> recipeSet = getSetFromDB(expectedKcal, expectedTotalTimeMinutes, numberOfMeals);
        Meals mealsTemp = new Meals();

        if (recipeSet.isEmpty()) {
            log.info("--------ASKING API---------------");
            mealsTemp = dataBaseMealsService.create(getExpectedMealsFromApi(expectedKcal, expectedTotalTimeMinutes, numberOfMeals));

            return Meals.builder()
                    .id(mealsTemp.getId())
                    .recipeSet(mealsTemp.getRecipeSet())
                    .totalKcalOfMeals(sumOfMealsKcal(mealsTemp))
                    .sumOfCookTotalTime(sumOfCookTimes(mealsTemp))
                    .componentsMap(shoppingList(mealsTemp))
                    .build();

        } else if (recipeSet.size() < numberOfMeals) {
            log.info("--------ASKING API - NOT ENOUGH RECIPE IN DB---------------");

            //pobiera nowe recepty z api
            final Meals apiTempMeals = getExpectedMealsFromApi(expectedKcal, expectedTotalTimeMinutes, numberOfMeals);
            //wyciąga set recept
            final Set<Recipe> apiRecipeTemp = apiTempMeals.getRecipeSet();

            //wyciaga nazwy recept
//            final Set<String> apiRecipeNamesSet = apiTempMeals.getRecipeSet().stream().map(Recipe::getName).collect(Collectors.toSet());
            //sprawdza czy nie ma powtórzeń i tworzy set brakujących recept
//            mealsTemp.setRecipeSet(checkingRecipes(apiRecipeTemp, apiRecipeNamesSet)); // sprawdzić parametry metody

            //zapisuje w bazie danych nowo pobrane recepty - ale nie ma jeszcze przygotowanego pełenego meals - coś tu się dobrze odbierdala

            mealsTemp = dataBaseMealsService.create(getExpectedMealsFromApiAndDB(checkingRecipes(apiRecipeTemp), recipeSet));

            return Meals.builder()
                    .id(mealsTemp.getId())
                    .recipeSet(getSetFromDB(expectedKcal, expectedTotalTimeMinutes, numberOfMeals))
                    .totalKcalOfMeals(sumOfMealsKcal(mealsTemp))
                    .sumOfCookTotalTime(sumOfCookTimes(mealsTemp))
                    .componentsMap(shoppingList(mealsTemp))
                    .build();
        }

        log.info("--------ASKING DB---------------");
        Optional<Meals> mealsFromDB = Optional.ofNullable(dataBaseMealsService.findExpectedMeals(expectedKcal, expectedTotalTimeMinutes, numberOfMeals));

        if (mealsFromDB.isEmpty()) {
            Meals correctMeals = dataBaseMealsService.create(createExpectedMealAtDB(recipeSet));

            return Meals.builder()
                    .id(correctMeals.getId())
                    .recipeSet(correctMeals.getRecipeSet())
                    .totalKcalOfMeals(sumOfMealsKcal(correctMeals))
                    .sumOfCookTotalTime(sumOfCookTimes(correctMeals))
                    .componentsMap(shoppingList(correctMeals))
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

    public Set<Recipe> getSetOfRecipesWithAllParameters(int expectedKcal, int expectedTotalTimeMinutes, int numberOfMeals) {
        Set<Recipe> mealsSet = new HashSet<>();
        final int preparedKcal = calculateKcalPerMeal(expectedKcal, numberOfMeals);
        final int preparedTime = calculateTimePerMeal(expectedTotalTimeMinutes, numberOfMeals);

        switch (numberOfMeals) {
            case 3:
                mealsSet.add(getFromDBSetOfRecipes(preparedKcal, preparedTime, DINNER.getMeal(), mealsSet));
                mealsSet.remove(null);

            case 2:
                mealsSet.add(getFromDBSetOfRecipes(preparedKcal, preparedTime, LUNCH.getMeal(), mealsSet));
                mealsSet.remove(null);

            case 1:
                mealsSet.add(getFromDBSetOfRecipes(preparedKcal, preparedTime, BREAKFAST.getMeal(), mealsSet));
                mealsSet.remove(null);
                break;

            default:
                //nothing to do here
        }

        return mealsSet;
    }

    @Nullable
    private Recipe getFromDBSetOfRecipes(int expectedKcal, int expectedTotalTimeMinutes, String mealTag, Set<Recipe> mealsSet) {
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
            //tu jest problem - przy sprawdzaniu ilości przpisów

            return null;
        }

        // tu wprowadzić zabezpiecznie dla tych samych nazw
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

    private Meals getExpectedMealsFromApi(int expectedKcal, int expectedTotalTimeMinutes, int numberOfMeals) throws Exception {
        Meals mealsApi = new Meals();

        mealsApi.setId(null);
        mealsApi.setRecipeSet(recipeService.getSetOfRecipesWithAllParameters(expectedKcal, expectedTotalTimeMinutes, numberOfMeals));
        mealsApi.setRecipeSetSize(mealsApi.getRecipeSet().size());
        mealsApi.setTotalKcalOfMeals(sumOfMealsKcal(mealsApi));
        mealsApi.setSumOfCookTotalTime(sumOfCookTimes(mealsApi));
        mealsApi.setComponentsMap(shoppingList(mealsApi));
        return mealsApi;

    }

    private Meals getExpectedMealsFromApiAndDB(Set<Recipe> recipeSetTemp, Set<Recipe> recipeSet) {
        Meals mealsToDataBase = new Meals();
        Set<Recipe> concatSet = hasToBigSize(Stream.concat(recipeSetTemp.stream(), recipeSet.stream()).collect(Collectors.toSet()));

        //comcat przekracza maxymalny zakres ilości w secie dodaje ponad 3
        // w tym miejscu set może miec max 3 elemnty


        mealsToDataBase.setId(null);
        mealsToDataBase.setRecipeSet(concatSet);
        mealsToDataBase.setRecipeSetSize(mealsToDataBase.getRecipeSet().size());
        mealsToDataBase.setTotalKcalOfMeals(sumOfMealsKcal(mealsToDataBase));
        mealsToDataBase.setSumOfCookTotalTime(sumOfCookTimes(mealsToDataBase));
        mealsToDataBase.setComponentsMap(shoppingList(mealsToDataBase));
        return mealsToDataBase;
    }

    private Meals getExpectedMealsFromDB(Meals someMeals) {

        someMeals.setId(someMeals.getId());
        someMeals.setRecipeSet(someMeals.getRecipeSet());
        someMeals.setRecipeSetSize(someMeals.getRecipeSet().size());
        someMeals.setTotalKcalOfMeals(sumOfMealsKcal(someMeals));
        someMeals.setSumOfCookTotalTime(sumOfCookTimes(someMeals));
        someMeals.setComponentsMap(shoppingList(someMeals));
        return someMeals;
    }

    private Meals createExpectedMealAtDB(Set<Recipe> recipeSet) {
        Meals mealsToDataBase = new Meals();

        mealsToDataBase.setId(null);
        mealsToDataBase.setRecipeSet(recipeSet);
        mealsToDataBase.setRecipeSetSize(mealsToDataBase.getRecipeSet().size());
        mealsToDataBase.setTotalKcalOfMeals(sumOfMealsKcal(mealsToDataBase));
        mealsToDataBase.setSumOfCookTotalTime(sumOfCookTimes(mealsToDataBase));
        mealsToDataBase.setComponentsMap(shoppingList(mealsToDataBase));
        return mealsToDataBase;
    }

    //sprawdzić dokladnie działanie - coś tu jest zakręcone
    private Set<Recipe> checkingRecipes(Set<Recipe> recipeSet) {
        final Set<Recipe> temp = new HashSet<>();
        final Set<String> apiRecipeNamesSet = recipeSet.stream().map(Recipe::getName).collect(Collectors.toSet());

        for (String recipeName : apiRecipeNamesSet) {
            Optional<Recipe> optionalRecipe = Optional.ofNullable(dataBaseMealsService.findByName(recipeName));

            if (optionalRecipe.isEmpty()) {
                temp.addAll(recipeSet.stream()
                        .filter(recipe -> recipe.getName().equals(recipeName))
                        .collect(Collectors.toSet()));
            }
        }
        return temp;
    }

    public Set<Recipe> hasSameName(Set<Recipe> recipeTemp, @NotNull Set<Recipe> mealsSet) {
        final Set<Recipe> temp = new HashSet<>();

        Set<String> tempOfRecipeNames = mealsSet.stream()
                .filter(Objects::nonNull) //sprawdzić i ewentualnie wywalić
                .map(Recipe::getName)
                .collect(Collectors.toSet());

        for (String recipeName : tempOfRecipeNames) {
            temp.addAll(recipeTemp.stream()
                    .filter(recipe -> !recipe.getName().equals(recipeName))
                    .toList());
        }
        return temp;
    }

    private Set<Recipe> hasToBigSize(Set<Recipe> concatSet) {

        // uwaga tą metode robić na 3 mniejsze np. do sniadania obiadu i kolacji
        // tu ma zostać max 3 recepty
//         zrobic stream zbiejący powtórzenia do oddzielnej koekcji na podstawie dinner, lunch , breakfast
//         jeżlei kolekcja ma tylko rozmair 1 to ją wyczyścić
//         jeżlei kolekcja ma roxzmair wiekszy niż 1 to zrobić stream z reduce do reicpe z najwikeszą kalorycznością
//         jeżlei przpeis ma dwa tagi np: lunch dinnner "spróbuj" i powstaje podowjenie nazw psiłków spróbuje pobrać przepis z miniejszą kalorycznością - tego raczej nie trzeba będzie
//         cascadeTyp robi update przy zapisie, wiec może być set z nazwą recepty pochodząca z bazydanych - trzeba tylko ograniczyć rozmair setu do max 3!
//         uwaga na rozmair 2!! żeby nie zajebać nulla - ewentualnie zrobić filtr ".filter(Objects::nonNull)"

        //  tu pobiera najwięszą kaloryczność z poszeczgólnych list
        // pozostaje pobrać przepisy z list na podstawie największej kalorczyności i dodać do setu

        final Set<Recipe> tempSet = new HashSet<>();

        tempSet.add(getBreakfast(concatSet));
        tempSet.add(getLunch(concatSet));
        tempSet.add(getDinner(concatSet));

        return hasNull(tempSet);
    }

    private Recipe getBreakfast(Set<Recipe> concatSet) {
        final List<Recipe> preparedBreakfastRecipeList;

        final List<Recipe> breakfastRecipeList = concatSet.stream()
                .filter(recipe -> recipe.getTags()
                        .stream()
                        .anyMatch(tag -> tag.getName().equalsIgnoreCase(BREAKFAST.getMeal())))
                .toList();

        if (breakfastRecipeList.isEmpty()) {
            return null;
        }

        Optional<Integer> breakfastOptional = breakfastRecipeList.stream()
                .map(recipe -> recipe.getNutrition().getCalories())
                .reduce(Math::max);

        if (breakfastRecipeList.size() > 1) {
            preparedBreakfastRecipeList = breakfastRecipeList.stream()
                    .filter(recipe -> recipe.getNutrition().getCalories() >= breakfastOptional.get()) //TODO OPTIONAL
                    .toList();

            return preparedBreakfastRecipeList.get(0);
        }

        return breakfastRecipeList.get(0);
    }

    private Recipe getLunch(Set<Recipe> concatSet) {
        final List<Recipe> preparedLunchRecipeList;

        final List<Recipe> lunchRecipeList = concatSet.stream()
                .filter(recipe -> recipe.getTags()
                        .stream()
                        .anyMatch(tag -> tag.getName().equalsIgnoreCase(LUNCH.getMeal()))).toList();

        if (lunchRecipeList.isEmpty()) {
            return null;
        }

        Optional<Integer> lunchOptional = lunchRecipeList.stream()
                .map(recipe -> recipe.getNutrition().getCalories())
                .reduce(Math::max);

        if (lunchRecipeList.size() > 1) {
            preparedLunchRecipeList = lunchRecipeList.stream()
                    .filter(recipe -> recipe.getNutrition().getCalories() >= lunchOptional.get()) //TODO OPTIONAL
                    .toList();

            return preparedLunchRecipeList.get(0);
        }

        return lunchRecipeList.get(0);
    }

    private Recipe getDinner(Set<Recipe> concatSet) {
        final List<Recipe> preparedDinnerRecipeList;

        final List<Recipe> dinnerRecipeList = concatSet.stream()
                .filter(recipe -> recipe.getTags()
                        .stream()
                        .anyMatch(tag -> tag.getName().equalsIgnoreCase(DINNER.getMeal())))
                .toList();

        if (dinnerRecipeList.isEmpty()) {
            return null;
        }

        Optional<Integer> dinnerOptional = dinnerRecipeList.stream()
                .map(recipe -> recipe.getNutrition().getCalories())
                .reduce(Math::max);

        if (dinnerRecipeList.size() > 1) {
            preparedDinnerRecipeList = dinnerRecipeList.stream()
                    .filter(recipe -> recipe.getNutrition().getCalories() >= dinnerOptional.get()) //TODO OPTIONAL
                    .toList();

            return preparedDinnerRecipeList.get(0);
        }

        return dinnerRecipeList.get(0);
    }

    private Set<Recipe> hasNull(Set<Recipe> mealsSet) {
        return mealsSet
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    @NotNull
    private Integer sumOfMealsKcal(Meals meals) {

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

    @NotNull
    private Integer sumOfCookTimes(Meals meals) {

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

    //metoda ogolna z if / else sprawdzająca baze danych lub uruchamiająca recipeservice - jest
    //metoda zwracająca map ze składnikami -jest
    //metoda do uruchaminia recipeservice - jest
    //metoda sumująca Kcal - jest
    //metoda sumująca czasPrzyrządzania - jest
}
