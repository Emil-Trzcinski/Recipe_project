package pl.trzcinski.emil.recipeproject.utility.builders;

import pl.trzcinski.emil.recipeproject.model.Recipe;

import java.util.Map;
import java.util.Set;

public class MealsBuilder {

    private Long id;

    private Set<Recipe> recipeSet;

    private int recipeSetSize;

    private int totalKcalOfMeals;

    private int sumOfCookTotalTime;

    private Map<String, String> componentsMap;
}
