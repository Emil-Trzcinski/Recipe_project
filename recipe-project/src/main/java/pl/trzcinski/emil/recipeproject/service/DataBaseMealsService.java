package pl.trzcinski.emil.recipeproject.service;

import org.springframework.stereotype.Service;
import pl.trzcinski.emil.recipeproject.model.Meals;
import pl.trzcinski.emil.recipeproject.model.Recipe;
import pl.trzcinski.emil.recipeproject.repository.MealsRepository;
import pl.trzcinski.emil.recipeproject.repository.RecipeRepository;

import java.util.Set;

@Service
public class DataBaseMealsService {

    private final MealsRepository mealsRepository;
    private final RecipeRepository recipeRepository;

    public DataBaseMealsService(MealsRepository mealsRepository, RecipeRepository recipeRepository) {

        this.mealsRepository = mealsRepository;
        this.recipeRepository = recipeRepository;
    }

    public Meals create(Meals meals) {
        mealsRepository.save(meals);
        return meals;
        //hmmm zwracac np: ID
    }

    public Recipe findByName(String name) {
        return recipeRepository.findByName(name);
    }

    public Set<Recipe> findExpectedMeals(int expectedKcal, int expectedTotalTimeMinutes) {
        return recipeRepository.
                findByNutrition_CaloriesLessThanEqualAndCookTimeMinutesLessThanEqual
                        (expectedKcal, expectedTotalTimeMinutes);
    }

    public boolean isNotEmpty() {
        return mealsRepository.count() > 0;
    }

    //create
    //find/findAll
    //opcjonalnie delete

}
