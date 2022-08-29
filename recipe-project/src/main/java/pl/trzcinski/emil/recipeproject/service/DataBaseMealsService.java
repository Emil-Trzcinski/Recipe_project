package pl.trzcinski.emil.recipeproject.service;

import org.springframework.stereotype.Service;
import pl.trzcinski.emil.recipeproject.model.Meals;
import pl.trzcinski.emil.recipeproject.model.Recipe;
import pl.trzcinski.emil.recipeproject.repository.MealsRepository;
import pl.trzcinski.emil.recipeproject.repository.RecipeRepository;

import java.util.Set;

import static pl.trzcinski.emil.recipeproject.service.Conversion.CONVERTER;

/**
 * DataBaseMealsService deals with communication with the database of prepared meals and recipes
 */
@Service
public class DataBaseMealsService {

    private final MealsRepository mealsRepository;
    private final RecipeRepository recipeRepository;

    public DataBaseMealsService(MealsRepository mealsRepository, RecipeRepository recipeRepository) {

        this.mealsRepository = mealsRepository;
        this.recipeRepository = recipeRepository;
    }

    /**
     * saves and returns the meal
     * @param meals meal
     * @return meal
     */
    public Meals create(Meals meals) {
        return mealsRepository.save(meals);
    }

    /**
     * finds a recipe with the given name
     * @param name meal name
     * @return meal
     */
    public Recipe findByName(String name) {
        return recipeRepository.findByName(name);
    }

    /**
     * finds the expected meal with values closest to the expected one
     * @param expectedKcal number of calories
     * @param expectedTotalTimeMinutes estimated cooking time
     * @param size number of meals
     * @return meal
     */
    public Meals findExpectedMeals(int expectedKcal, int expectedTotalTimeMinutes, int size) {
        return mealsRepository.findTopByTotalKcalOfMealsBetweenAndSumOfCookTotalTimeLessThanEqualAndRecipeSetSizeEquals
                (calculateMinimumOfKcal(expectedKcal), expectedKcal, expectedTotalTimeMinutes, size);
    }

    /**
     * finds a set of unique recipes between minimum and expected values
     * @param expectedKcal number of calories
     * @param expectedTotalTimeMinutes estimated cooking time
     * @return set of unique recipes
     */
    public Set<Recipe> findExpectedRecipeSet(int expectedKcal, int expectedTotalTimeMinutes) {
        return recipeRepository.
                findByNutrition_CaloriesBetweenAndCookTimeMinutesLessThanEqual
                        (calculateMinimumOfKcal(expectedKcal), expectedKcal, expectedTotalTimeMinutes);
    }

    /**
     * checks if the database is not empty
     * @return true if there are meals in the database
     */
    public boolean isNotEmpty() {
        return mealsRepository.count() > 0;
    }

    /**
     * calculate the lower end of a database query
     * @param expectedKcal expected amount of kcal
     * @return lower value of kcal
     */
    private Integer calculateMinimumOfKcal(int expectedKcal) {
        return (int) (expectedKcal * CONVERTER);
    }
}
