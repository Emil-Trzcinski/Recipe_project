package pl.trzcinski.emil.recipeproject.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.trzcinski.emil.recipeproject.model.*;
import pl.trzcinski.emil.recipeproject.utility.builders.*;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class ShoppingListServiceTest {

    ShoppingListService shoppingListService;
    Meals meals;
    Meals secMeals;

    @BeforeEach
    public void setUp() {
        shoppingListService = new ShoppingListService();

        Ingredient ingredient = new IngredientBuilder()
                .withName("kosher salt")
                .build();

        Ingredient secIngredient = new IngredientBuilder()
                .withName("kosher salt")
                .build();

        Measurement measurement = new MeasurementBuilder()
                .withQuantity("1½")
                .build();

        Measurement secMeasurement = new MeasurementBuilder()
                .withQuantity("1½")
                .build();
        Component component = new ComponentBuilder()
                .withMeasurementsList(new ArrayList<>(Collections.singletonList(measurement)))
                .withIngredient(ingredient)
                .withRawText("1½ teaspoons kosher salt")
                .build();

        Component secComponent = new ComponentBuilder()
                .withMeasurementsList(new ArrayList<>(Collections.singletonList(secMeasurement)))
                .withIngredient(secIngredient)
                .withRawText("1½ teaspoons kosher salt")
                .build();

        Section section = new SectionBuilder()
                .withComponentList(new ArrayList<>(Collections.singletonList(component)))
                .build();

        Section secSection = new SectionBuilder()
                .withComponentList(new ArrayList<>(Arrays.asList(component, secComponent)))
                .build();

        Recipe recipe = new RecipeBuilder()
                .withSection(new ArrayList<>(Collections.singletonList(section)))
                .build();

        Recipe secRecipe = new RecipeBuilder()
                .withSection(new ArrayList<>(Arrays.asList(section, secSection)))
                .build();

        meals = new MealsBuilder()
                .defaultID()
                .withDefaultRecipeSetSize()
                .withDefaultSumOfCookTotalTime()
                .withDefaultTotalKcalOfMeals()
                .withRecipeSet(new HashSet<>(Set.of(recipe)))
                .build();

        secMeals = new MealsBuilder()
                .defaultID()
                .withDefaultRecipeSetSize()
                .withDefaultSumOfCookTotalTime()
                .withDefaultTotalKcalOfMeals()
                .withRecipeSet(new HashSet<>(Set.of(recipe, secRecipe)))
                .build();
    }

    @Test
    @DisplayName("Shopping List is equals too given elements")
    void should_Given_Map_Equals_Too_Shopping_List() {
        //given
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("kosher salt", "1.5");
        //when
        Meals testMeals = new Meals();
        testMeals.setShoppingList(shoppingListService.createShoppingList(meals));
        //then
        assertThat(resultMap, is(testMeals.getShoppingList()));
    }

    @Test
    @DisplayName("Shopping List correct sum same elements")
    void should_Shopping_List_Correct_Sum_Same_Elements() {
        //given
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("kosher salt", "6.0");
        //when
        Meals testMeals = new Meals();
        testMeals.setShoppingList(shoppingListService.createShoppingList(secMeals));
        //then
        assertThat(resultMap, is(testMeals.getShoppingList()));
    }
}