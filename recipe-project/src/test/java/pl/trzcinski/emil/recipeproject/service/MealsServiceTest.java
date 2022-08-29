package pl.trzcinski.emil.recipeproject.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import pl.trzcinski.emil.recipeproject.model.Meals;
import pl.trzcinski.emil.recipeproject.model.Recipe;
import pl.trzcinski.emil.recipeproject.model.RecipeList;
import pl.trzcinski.emil.recipeproject.utility.builders.MealsBuilder;
import pl.trzcinski.emil.recipeproject.utility.builders.RecipeBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@ExtendWith(MockitoExtension.class)
class MealsServiceTest {

    @Mock
    MealsService mealsService;

    RecipeList recipeList;
    Set<Recipe> recipeSet = new HashSet<>();
    Set<Recipe> secRecipeSet = new HashSet<>();

    @BeforeEach
    void setUp() {
        recipeList = new RecipeList();

        recipeList.setResults(Arrays.asList(new RecipeBuilder().withDefaultRecipe().build(),
                new RecipeBuilder().withDefaultRecipe().build(), new RecipeBuilder().withDefaultRecipe().build()));
    }

    @AfterEach
    void after() {
        recipeList = null;
    }

    @Test
    @DisplayName("Successfully Get Meal")
    void Should_Get_Meals() throws Exception {

        //given
        Meals meals = new MealsBuilder().withDefaultMeal().build();
        given(mealsService.getMeals(900, 60, 3)).willReturn(meals);

        //when
        Meals resultMeals = mealsService.getMeals(900, 60, 3);

        //then
        then(mealsService).should().getMeals(900, 60, 3);
        assertThat(resultMeals).isEqualTo(meals);
        assertThat(resultMeals).isNotNull();
    }

    @Test
    @DisplayName("Successfully Get Set Of Recipes With Expected Parameters")
    void should_Get_Set_Of_Recipes_With_All_Parameters() {

        //given
        recipeSet.add(new RecipeBuilder().withDefaultRecipe().build());
        recipeSet.add(new RecipeBuilder().withDefaultRecipe().build());

        given(mealsService.getSetOfRecipesWithAllParameters(800, 50, 2)).willReturn(recipeSet);

        //when
        Set<Recipe> expectedRecipe = mealsService.getSetOfRecipesWithAllParameters(800, 50, 2);
        //then
        then(mealsService).should().getSetOfRecipesWithAllParameters(800, 50, 2);
        assertThat(expectedRecipe).isEqualTo(recipeSet);
        assertThat(expectedRecipe.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Get Set Of Recipes With Expected Parameters Will Throw IOException")
    void should_Get_Set_Of_Recipes_With_All_Parameters_Throw_IOException() {

        //given
        given(mealsService.getSetOfRecipesWithAllParameters(anyInt(), anyInt(), anyInt()))
                .willThrow(IOException.class);

        //when
        //then
        assertThrows(IOException.class, () -> mealsService.getSetOfRecipesWithAllParameters(anyInt(), anyInt(), anyInt()));

    }

    @Test
    @DisplayName("Get Set Of Recipes With Expected Parameters Will Throw NullPointerException")
    void should_Get_Set_Of_Recipes_With_All_Parameters_Throw_NullPointerException() {

        //given
        given(mealsService.getSetOfRecipesWithAllParameters(anyInt(), anyInt(), anyInt()))
                .willThrow(NullPointerException.class);

        //when
        //then
        assertThrows(NullPointerException.class, () -> mealsService.getSetOfRecipesWithAllParameters(anyInt(), anyInt(), anyInt()));

    }

    @Test
    @DisplayName("Get Set Of Recipes With Expected Parameters Will Throw JsonProcessingException")
    void should_Get_Set_Of_Recipes_With_All_Parameters_Throw_JsonProcessingException() {

        //given
        given(mealsService.getSetOfRecipesWithAllParameters(anyInt(), anyInt(), anyInt()))
                .willThrow(JsonProcessingException.class);

        //when
        //then
        assertThrows(JsonProcessingException.class, () -> mealsService.getSetOfRecipesWithAllParameters(anyInt(), anyInt(), anyInt()));

    }

    @Test
    @DisplayName("Get Set Of Recipes With Expected Parameters Will Throw JsonMappingException")
    void should_Get_Set_Of_Recipes_With_All_Parameters_Throw_JsonMappingException() {

        //given
        given(mealsService.getSetOfRecipesWithAllParameters(anyInt(), anyInt(), anyInt()))
                .willThrow(JsonMappingException.class);

        //when
        //then
        assertThrows(JsonMappingException.class, () -> mealsService.getSetOfRecipesWithAllParameters(anyInt(), anyInt(), anyInt()));

    }

    @Test
    @DisplayName("Removes Recipes In Sets What Have The Same Names")
    void should_Remove_Recipes_What_Have_Same_Names() {

        //given
        recipeSet.add(new RecipeBuilder().withName("Burrito").build());
        recipeSet.add(new RecipeBuilder().withName("Pizza").build());

        secRecipeSet.add(new RecipeBuilder().withName("Burrito").build());
        secRecipeSet.add(new RecipeBuilder().withName("Soup").build());

        Set<Recipe> resultRecipeSet = new HashSet<>();

        resultRecipeSet.add(new RecipeBuilder().withName("Burrito").build());
        resultRecipeSet.add(new RecipeBuilder().withName("Pizza").build());
        resultRecipeSet.add(new RecipeBuilder().withName("Soup").build());

        given(mealsService.hasSameName(anySet(), anySet())).willReturn(resultRecipeSet);

        //when
        Set<Recipe> result = mealsService.hasSameName(recipeSet, secRecipeSet);

        //then
        then(mealsService).should().hasSameName(anySet(), anySet());
        assertThat(result.size()).isEqualTo(resultRecipeSet.size());
    }

    @Test
    @DisplayName("Has The Same Names Will Throw NullPointerException")
    void should_Throw_NullPointerException_When_Has_Null() {

        //given
        given(mealsService.hasSameName(anySet(), anySet())).willThrow(NullPointerException.class);

        //when
        //then
        assertThrows(NullPointerException.class, () -> mealsService.hasSameName(anySet(), anySet()));
    }
}