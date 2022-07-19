package pl.trzcinski.emil.recipeproject.service;

import pl.trzcinski.emil.recipeproject.model.Recipe;

import java.util.Set;

public interface SetService {

    Set<Recipe> getSetOfRecipesWithAllParameters
            (int expectedKcal, int expectedTotalTimeMinutes, int numberOfMeals) throws Exception;

    Set<Recipe> hasSameName(Set<Recipe> recipeTemp, Set<Recipe> mealsSet);
}
