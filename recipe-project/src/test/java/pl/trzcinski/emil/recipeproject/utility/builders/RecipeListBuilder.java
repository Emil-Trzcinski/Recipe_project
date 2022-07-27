package pl.trzcinski.emil.recipeproject.utility.builders;

import pl.trzcinski.emil.recipeproject.model.Recipe;
import pl.trzcinski.emil.recipeproject.model.RecipeList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeListBuilder {

    private List<Recipe> results;

    Recipe recipe = new RecipeBuilder().withDefaultRecipe().build();

    Recipe secRecipe = new RecipeBuilder()
            .withName("Some Name")
            .withNutrition(new NutritionBuilder().withDefaultNutrition().build())
            .withTotalTimeMinutes(60)
            .withInstruction(new ArrayList<>(Arrays.asList(new InstructionBuilder().withDefaultInstruction().build(),
                    new InstructionBuilder().withDefaultInstruction().build())))
            .withSection(new ArrayList<>(Arrays.asList(new SectionBuilder().withDefaultSection().build(),
                    new SectionBuilder().withDefaultSection().build())))
            .withNumberServings(3)
            .withDefaultUrl()
            .withTags(new ArrayList<>(Arrays.asList(new TagBuilder().withDefaultTag().build(), new TagBuilder().withDefaultTag().build())))
            .build();

    Recipe thirdRecipe = new RecipeBuilder().withName(null)
            .withNutrition(new NutritionBuilder().withDefaultNutrition().build())
            .withTotalTimeMinutes(null)
            .withInstruction(new ArrayList<>(Arrays.asList(new InstructionBuilder().withDefaultInstruction().build(),
                    new InstructionBuilder().withDefaultInstruction().build())))
            .withSection(new ArrayList<>(Arrays.asList(new SectionBuilder().withDefaultSection().build(),
                    new SectionBuilder().withDefaultSection().build())))
            .withNumberServings(null)
            .withDefaultUrl()
            .withTags(new ArrayList<>(Arrays.asList(new TagBuilder().withDefaultTag().build(), new TagBuilder().withDefaultTag().build())))
            .build();

    public RecipeListBuilder withDefaultRecipeList() {
        this.results = new ArrayList<>(Arrays.asList(recipe, secRecipe, thirdRecipe));
        return this;
    }

    public RecipeList build() {
        RecipeList recipeList = new RecipeList(results);
        return recipeList;
    }
}
