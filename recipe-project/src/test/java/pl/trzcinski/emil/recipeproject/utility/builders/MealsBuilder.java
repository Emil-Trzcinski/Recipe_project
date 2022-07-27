package pl.trzcinski.emil.recipeproject.utility.builders;

import pl.trzcinski.emil.recipeproject.model.Meals;
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

    public MealsBuilder defaultID() {
        this.id = 1L;
        return this;
    }

    public MealsBuilder withMealsId(Long someId) {
        this.id = someId;
        return this;
    }

    public MealsBuilder withRecipeSet(Set<Recipe> setOfRecipe) {
        this.recipeSet = setOfRecipe;
        return this;
    }

    public MealsBuilder withDefaultRecipeSetSize() {
        this.recipeSetSize = 3;
        return this;
    }

    public MealsBuilder withRecipeSetSize(Integer recipeSetSize) {
        this.recipeSetSize = recipeSetSize;
        return this;
    }

    public MealsBuilder withDefaultTotalKcalOfMeals() {
        this.totalKcalOfMeals = 2400;
        return this;
    }

    public MealsBuilder withTotalKcalOfMeals(Integer kcal) {
        this.totalKcalOfMeals = kcal;
        return this;
    }

    public MealsBuilder withDefaultSumOfCookTotalTime() {
        this.sumOfCookTotalTime = 90;
        return this;
    }

    public MealsBuilder withSumOfCookTotalTime(Integer time) {
        this.sumOfCookTotalTime = time;
        return this;
    }

    public MealsBuilder withComponentsMap(Map<String, String> mapOfComponents) {
        this.componentsMap = mapOfComponents;
        return this;

    }

    public Meals build() {
        return new Meals(id, recipeSet, recipeSetSize, totalKcalOfMeals, sumOfCookTotalTime, componentsMap);
    }

}
