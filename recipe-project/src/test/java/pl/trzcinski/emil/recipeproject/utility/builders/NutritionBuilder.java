package pl.trzcinski.emil.recipeproject.utility.builders;

import pl.trzcinski.emil.recipeproject.model.Nutrition;
import pl.trzcinski.emil.recipeproject.model.Recipe;

public class NutritionBuilder {

    private Long id;

    private Integer calories;

    private Integer carbohydrates;

    private Integer fat;

    private Integer protein;

    private Integer sugar;

    private Integer fiber;

    private Recipe recipe;

    public NutritionBuilder withDefaultNutrition() {
        this.id = withDefaultId().id;
        this.calories = withDefaultCalories().calories;
        this.carbohydrates = withDefaultCarbohydrates().carbohydrates;
        this.fat = withDefaultFat().fat;
        this.protein = withDefaultProtein().protein;
        this.sugar = withDefaultSugar().sugar;
        this.fiber = withDefaultFiber().fiber;

        return this;
    }

    public NutritionBuilder withDefaultId() {
        this.id = 1L;
        return this;
    }

    public NutritionBuilder withIdTag(Long id) {
        this.id = id;
        return this;
    }

    public NutritionBuilder withDefaultCalories() {
        this.calories = 300;
        return this;
    }

    public NutritionBuilder withCalories(Integer calories) {
        this.calories = calories;
        return this;
    }

    public NutritionBuilder withDefaultCarbohydrates() {
        this.carbohydrates = 301;
        return this;
    }

    public NutritionBuilder withCarbohydrates(Integer carbohydrates) {
        this.carbohydrates = carbohydrates;
        return this;
    }

    public NutritionBuilder withDefaultFat() {
        this.fat = 302;
        return this;
    }

    public NutritionBuilder withFat(Integer fat) {
        this.fat = fat;
        return this;
    }

    public NutritionBuilder withDefaultProtein() {
        this.protein = 303;
        return this;
    }

    public NutritionBuilder withProtein(Integer protein) {
        this.protein = protein;
        return this;
    }

    public NutritionBuilder withDefaultSugar() {
        this.sugar = 304;
        return this;
    }

    public NutritionBuilder withSugar(Integer sugar) {
        this.sugar = sugar;
        return this;
    }

    public NutritionBuilder withDefaultFiber() {
        this.fiber = 305;
        return this;
    }

    public NutritionBuilder withFiber(Integer fiber) {
        this.fiber = fiber;
        return this;
    }

    public NutritionBuilder withRecipe(Recipe recipe) {
        this.recipe = recipe;
        return this;
    }

    public Nutrition build() {
        return new Nutrition(id, calories, carbohydrates, fat, protein, sugar, fiber, recipe);
    }

}
