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
import pl.trzcinski.emil.recipeproject.model.Recipe;
import pl.trzcinski.emil.recipeproject.model.RecipeList;
import pl.trzcinski.emil.recipeproject.utility.builders.RecipeBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    RecipeService recipeService;

    RecipeList recipeList;

    Set<Recipe> recipeSet = new HashSet<>();
    Set<Recipe> secRecipeSet = new HashSet<>();

    @BeforeEach
    public void setUp() {
        recipeList = new RecipeList();

        recipeList.setResults(Arrays.asList(new RecipeBuilder().withDefaultRecipe().build(),
                new RecipeBuilder().withDefaultRecipe().build(), new RecipeBuilder().withDefaultRecipe().build()));
    }

    @AfterEach
    public void after(){
        recipeList = null;
    }

    @Test
    @DisplayName("Successfully Get Set Of Recipes With Expected Parameters")
    void should_Get_Set_Of_Recipes_With_All_Parameters() {

        //given
        recipeSet.add(new RecipeBuilder().withDefaultRecipe().build());
        recipeSet.add(new RecipeBuilder().withDefaultRecipe().build());
        recipeSet.add(new RecipeBuilder().withDefaultRecipe().build());

        given(recipeService.getSetOfRecipesWithAllParameters(900, 63, 3)).willReturn(recipeSet);

        //when
        Set<Recipe> expectedRecipe = recipeService.getSetOfRecipesWithAllParameters(900, 63, 3);

        //then
        then(recipeService).should().getSetOfRecipesWithAllParameters(900, 63, 3);
        assertThat(expectedRecipe).isEqualTo(recipeSet);
        assertThat(expectedRecipe.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("Get Set Of Recipes With Expected Parameters Will Throw IOException")
    void should_Get_Set_Of_Recipes_With_All_Parameters_Throw_IOException() {

        //given
        given(recipeService.getSetOfRecipesWithAllParameters(anyInt(), anyInt(), anyInt()))
                .willThrow(IOException.class);

        //when
        //then
        assertThrows(IOException.class, () -> recipeService.getSetOfRecipesWithAllParameters(anyInt(), anyInt(), anyInt()));

    }

    @Test
    @DisplayName("Get Set Of Recipes With Expected Parameters Will Throw NullPointerException")
    void should_Get_Set_Of_Recipes_With_All_Parameters_Throw_NullPointerException() {

        //given
        given(recipeService.getSetOfRecipesWithAllParameters(anyInt(), anyInt(), anyInt()))
                .willThrow(NullPointerException.class);

        //when
        //then
        assertThrows(NullPointerException.class, () -> recipeService.getSetOfRecipesWithAllParameters(anyInt(), anyInt(), anyInt()));

    }

    @Test
    @DisplayName("Get Set Of Recipes With Expected Parameters Will Throw JsonProcessingException")
    void should_Get_Set_Of_Recipes_With_All_Parameters_Throw_JsonProcessingException() {

        //given
        given(recipeService.getSetOfRecipesWithAllParameters(anyInt(), anyInt(), anyInt()))
                .willThrow(JsonProcessingException.class);

        //when
        //then
        assertThrows(JsonProcessingException.class, () -> recipeService.getSetOfRecipesWithAllParameters(anyInt(), anyInt(), anyInt()));

    }

    @Test
    @DisplayName("Get Set Of Recipes With Expected Parameters Will Throw JsonMappingException")
    void should_Get_Set_Of_Recipes_With_All_Parameters_Throw_JsonMappingException() {

        //given
        given(recipeService.getSetOfRecipesWithAllParameters(anyInt(), anyInt(), anyInt()))
                .willThrow(JsonMappingException.class);

        //when
        //then
        assertThrows(JsonMappingException.class, () -> recipeService.getSetOfRecipesWithAllParameters(anyInt(), anyInt(), anyInt()));

    }

    @Test
    @DisplayName("Removes Recipes In Sets What Has The Same Names")
    void should_Remove_Recipes_What_Has_Same_Names() {

        //given
        recipeSet.add(new RecipeBuilder().withName("Burrito").build());
        recipeSet.add(new RecipeBuilder().withName("Pizza").build());

        secRecipeSet.add(new RecipeBuilder().withName("Burrito").build());
        secRecipeSet.add(new RecipeBuilder().withName("Soup").build());

        Set<Recipe> resultRecipeSet = new HashSet<>();

        resultRecipeSet.add(new RecipeBuilder().withName("Burrito").build());
        resultRecipeSet.add(new RecipeBuilder().withName("Pizza").build());
        resultRecipeSet.add(new RecipeBuilder().withName("Soup").build());

        given(recipeService.hasSameName(anySet(), anySet())).willReturn(resultRecipeSet);

        //when
        Set<Recipe> result = recipeService.hasSameName(recipeSet, secRecipeSet);

        //then
        then(recipeService).should().hasSameName(anySet(), anySet());
        assertThat(result.size()).isEqualTo(resultRecipeSet.size());
    }

    @Test
    @DisplayName("Has The Same Names Will Throw NullPointerException")
    void should_Throw_NullPointerException_When_Has_Null() {

        //given
        given(recipeService.hasSameName(anySet(), anySet())).willThrow(NullPointerException.class);

        //when
        //then
        assertThrows(NullPointerException.class, () -> recipeService.hasSameName(anySet(), anySet()));
    }
}