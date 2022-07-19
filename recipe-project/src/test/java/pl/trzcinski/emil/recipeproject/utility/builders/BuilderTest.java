package pl.trzcinski.emil.recipeproject.utility.builders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.trzcinski.emil.recipeproject.model.Recipe;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BuilderTest {
    Recipe defaultRecipe;

    @BeforeEach
    void defaultSetUp() {
        defaultRecipe = new RecipeBuilder().withDefaultRecipe().build();
    }

    @DisplayName("Recipe is not null")
    @Test
    void should_not_return_null() {
        assertNotNull(defaultRecipe);
        assertNotNull(defaultRecipe.getInstructions());
        assertNotNull(defaultRecipe.getSections());
        assertNotNull(defaultRecipe.getCookTimeMinutes());
        assertNotNull(defaultRecipe.getName());
        assertNotNull(defaultRecipe.getNumServings());
        assertNotNull(defaultRecipe.getNutrition());
        assertNotNull(defaultRecipe.getPrepTimeMinutes());
        assertNotNull(defaultRecipe.getRecipeId());
        assertNotNull(defaultRecipe.getThumbnailUrl());
        assertNotNull(defaultRecipe.getTotalTimeMinutes());
        assertNotNull(defaultRecipe.getTags());
    }

    @DisplayName("Recipe have default properties")
    @Test
    void should_return_default_properties() {
        assertThat(defaultRecipe.getRecipeId()).isEqualTo(1L);
        assertThat(defaultRecipe.getName()).isEqualTo("Default Recipe Name");
        assertThat(defaultRecipe.getNutrition().getId()).isEqualTo(1L);
        assertThat(defaultRecipe.getNutrition().getCalories()).isEqualTo(300);
        assertThat(defaultRecipe.getNutrition().getCarbohydrates()).isEqualTo(301);
        assertThat(defaultRecipe.getNutrition().getFat()).isEqualTo(302);
        assertThat(defaultRecipe.getNutrition().getProtein()).isEqualTo(303);
        assertThat(defaultRecipe.getNutrition().getSugar()).isEqualTo(304);
        assertThat(defaultRecipe.getNutrition().getFiber()).isEqualTo(305);
        assertThat(defaultRecipe.getInstructions().size()).isEqualTo(3);
        assertThat(defaultRecipe.getInstructions().get(0).getDisplayText()).isEqualTo("default instruction");
        assertThat(defaultRecipe.getInstructions().get(1).getDisplayText()).isEqualTo("Some instruction");
        assertThat(defaultRecipe.getInstructions().get(2).getDisplayText()).isEqualTo("Another instruction");
        assertThat(defaultRecipe.getTotalTimeMinutes()).isEqualTo(21);
        assertThat(defaultRecipe.getPrepTimeMinutes()).isEqualTo(5);
        assertThat(defaultRecipe.getCookTimeMinutes()).isEqualTo(13);
        assertThat(defaultRecipe.getSections().size()).isEqualTo(3);
        assertThat(defaultRecipe.getSections().get(0).getComponents().get(0).getRawText()).isEqualTo("default");
        assertThat(defaultRecipe.getSections().get(1).getComponents().get(1).getComponent_id()).isEqualTo(5);
        assertThat(defaultRecipe.getSections().get(1).getComponents().get(1).getRawText()).isEqualTo("Text");
        assertThat(defaultRecipe.getThumbnailUrl()).isEqualTo("https://someURL");
        assertThat(defaultRecipe.getNumServings()).isEqualTo(3);
        assertThat(defaultRecipe.getTags().size()).isEqualTo(2);
        assertThat(defaultRecipe.getTags().get(0).getName()).isEqualTo("default");
        assertThat(defaultRecipe.getTags().get(1).getIdTag()).isEqualTo(2);
        assertThat(defaultRecipe.getTags().get(1).getName()).isEqualTo("Lunch");
    }
}
