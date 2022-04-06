package pl.trzcinski.emil.recipeproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.trzcinski.emil.recipeproject.model.Meals;
import pl.trzcinski.emil.recipeproject.model.Recipe;
import pl.trzcinski.emil.recipeproject.repository.RecipeRepository;

import java.util.OptionalInt;

@Slf4j
@Service
public class MealsService {

    private final Recipe recipe;
    private final Meals meals;
    private final RecipeRepository recipeRepository;
    private final RecipeService recipeService;

    public MealsService(Recipe recipe, Meals meals, RecipeRepository recipeRepository, RecipeService recipeService) {
        this.recipe = recipe;
        this.meals = meals;
        this.recipeRepository = recipeRepository;
        this.recipeService = recipeService;
    }

    public Meals getExceptedMeals(int expectedKcal, int expectedTotalTimeMinutes, int numberOfMeals) throws Exception {

        meals.setRecipeSet(recipeService
                .getSetOfRecipesWithAllParameters(expectedKcal, expectedTotalTimeMinutes, numberOfMeals));

        meals.setTotalKcalOfMeals(sumOfMealsKcal());
        meals.setSumOfCookTotalTime(sumOfCookTimes());

        return meals;
    }

    public Integer sumOfMealsKcal() {
        OptionalInt sumOfKcal = meals.getRecipeSet()
                .stream().mapToInt(recipe -> recipe.getNutrition().getCalories())
                .reduce(Integer::sum);

        if(sumOfKcal.isEmpty()) {
            log.debug("--- empty numbers---");
            throw new RuntimeException("--- empty numbers---");
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




        //metoda ogolan z if / else sprawdzająca baze danych lub uruchamiająca recipeservice

        //metoda do sprawdzania bazy danych
        //metoda zwracająca set ze składnikami

        //metoda do uruchaminia recipeservice - jest
        //metoda sumująca Kcal - jest
        //metoda sumująca czasPrzyrządzania - jest

}
