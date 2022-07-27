package pl.trzcinski.emil.recipeproject.utility.builders;


import pl.trzcinski.emil.recipeproject.model.Component;
import pl.trzcinski.emil.recipeproject.model.Ingredient;

public class IngredientBuilder {

    private Long ingredient_id;

    private String name;

    private Component component;

    public IngredientBuilder withDefaultIngredient() {
        this.ingredient_id = withDefaultIngredient_id().ingredient_id;
        this.name = withDefaultName().name;
        return this;
    }

    public IngredientBuilder withDefaultIngredient_id() {
        this.ingredient_id = 1L;
        return this;
    }

    public IngredientBuilder withIngredient_id(Long ingredient_id) {
        this.ingredient_id = ingredient_id;
        return this;
    }

    public IngredientBuilder withDefaultName() {
        this.name = "Default";
        return this;
    }

    public IngredientBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public IngredientBuilder withComponent(Component component) {
        this.component = component;
        return this;
    }

    public Ingredient build() {
        return new Ingredient(ingredient_id, name, component);
    }

}
