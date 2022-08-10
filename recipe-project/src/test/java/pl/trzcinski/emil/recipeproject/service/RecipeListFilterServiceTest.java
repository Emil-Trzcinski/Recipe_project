package pl.trzcinski.emil.recipeproject.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.trzcinski.emil.recipeproject.model.RecipeList;
import pl.trzcinski.emil.recipeproject.utility.builders.RecipeListBuilder;

import static org.junit.jupiter.api.Assertions.*;

class RecipeListFilterServiceTest {

    RecipeListFilterService recipeListFilterService;

    @BeforeEach
    public void setUp() {
        recipeListFilterService = new RecipeListFilterService();
    }

    @AfterEach
    public void after() {
        recipeListFilterService = null;
    }

    @Test
    @DisplayName("Recipe List Don`t Have Nulls")
    void should_Recipe_List_Filters_Return_List_Without_Nulls() {
        //given
        RecipeList recipeList = new RecipeListBuilder().withDefaultRecipeList().build();

        //when
        RecipeList filteredRecipeList = recipeListFilterService.listFiltering(recipeList);

        //then
        assertNotNull(filteredRecipeList.getResults().get(0));
    }

    @Test
    @DisplayName("Recipe List Have Expected Size")
    void should_Recipe_List_Filters_Return_List_With_Size_2() {
        //given
        RecipeList recipeList = new RecipeListBuilder().withDefaultRecipeList().build();

        //when
        RecipeList filteredRecipeList = recipeListFilterService.listFiltering(recipeList);

        //then
        assertEquals(filteredRecipeList.getResults().size(), 2);
    }

    @Test
    @DisplayName("Recipe List With Nulls Will Throw Exception")
    void should_Throw_Exception_When_Recipe_List_Filters_Have_Nulls() {
        //given
        RecipeList recipeList = new RecipeListBuilder().build();

        //when
        Exception exception = assertThrows(RuntimeException.class, () -> recipeListFilterService.listFiltering(recipeList));

        //then
        assertTrue(exception.getMessage().contains("Empty recipe list"));
    }
}