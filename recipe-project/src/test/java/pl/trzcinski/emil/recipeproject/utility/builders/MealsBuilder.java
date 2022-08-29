package pl.trzcinski.emil.recipeproject.utility.builders;

import pl.trzcinski.emil.recipeproject.model.Meals;
import pl.trzcinski.emil.recipeproject.model.Recipe;
import pl.trzcinski.emil.recipeproject.model.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MealsBuilder {

    private Long id;

    private Set<Recipe> recipeSet;

    private int recipeSetSize;

    private int totalKcalOfMeals;

    private int sumOfCookTotalTime;

    private Set<User> userSet;

    private Map<String, String> shoppingList;

    public MealsBuilder withDefaultMeal() {

        this.id = 1L;
        this.recipeSet = new HashSet<>(Set.of(new RecipeBuilder().withDefaultRecipe().build(),
                new RecipeBuilder().withDefaultRecipe().build(),
                new RecipeBuilder().withDefaultRecipe().build()));

        this.recipeSetSize = recipeSet.size();
        this.totalKcalOfMeals = 900;
        this.sumOfCookTotalTime = 60;
        this.shoppingList = new HashMap<>();
        shoppingList.put("assam tea leaves", "1");
        shoppingList.put("scallions", "3");
        shoppingList.put("all purpose flour", "185.0");
        shoppingList.put("balsamic vinegar", "1");
        shoppingList.put("whole green cardamom pods", "7");
        shoppingList.put("fresh basil leaf", "20.0");
        shoppingList.put("nonstick cooking spray", "1");
        shoppingList.put("cornstarch", "2");
        shoppingList.put("cookies", "1");
        return this;
    }

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

    public MealsBuilder withUserSet(Set<User> setOfUsers) {
        this.userSet = setOfUsers;
        return this;

    }

    public MealsBuilder withShoppingList(Map<String, String> shoppingList) {
        this.shoppingList = shoppingList;
        return this;

    }

    public Meals build() {
        return new Meals(id, recipeSet, recipeSetSize, totalKcalOfMeals, sumOfCookTotalTime, userSet, shoppingList);
    }
}
