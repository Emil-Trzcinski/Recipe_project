package pl.trzcinski.emil.recipeproject.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import pl.trzcinski.emil.recipeproject.model.Meals;
import pl.trzcinski.emil.recipeproject.model.Recipe;
import pl.trzcinski.emil.recipeproject.repository.MealsRepository;
import pl.trzcinski.emil.recipeproject.repository.RecipeRepository;
import pl.trzcinski.emil.recipeproject.utility.builders.MealsBuilder;
import pl.trzcinski.emil.recipeproject.utility.builders.RecipeBuilder;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static pl.trzcinski.emil.recipeproject.service.Conversion.CONVERTER;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@ExtendWith(MockitoExtension.class)
class DataBaseMealsServiceTest {

    @InjectMocks
    DataBaseMealsService dataBaseMealsService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    MealsRepository mealsRepository;

    Recipe recipe;
    Meals meal;

    @Test
    @DisplayName("Successfully Create Meals")
    void should_Successfully_Create_Meals() {
        //given
        meal = new MealsBuilder().withDefaultMeal().build();
        given(mealsRepository.save(any(Meals.class))).willReturn(meal);

        //when
        Meals testMeal = dataBaseMealsService.create(meal);

        //then
        then(mealsRepository).should().save(testMeal);
        assertThat(testMeal).isNotNull();
        assertThat(testMeal.getMealsId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Successfully Find Recipe By Name")
    void should_Successfully_Find_By_Name() {
        //given
        recipe = new RecipeBuilder().withName("burrito").build();
        given(recipeRepository.findByName("burrito")).willReturn(recipe);

        //when
        Recipe expectedRecipe = recipeRepository.findByName("burrito");

        //then
        then(recipeRepository).should().findByName("burrito");
        assertThat(expectedRecipe.getName()).isEqualTo("burrito");
    }

    @Test
    @DisplayName("Can`t Find By Name")
    void should_Not_Find_By_Name() {
        //given
        given(recipeRepository.findByName(anyString())).willReturn(null);

        //when
        Recipe expectedRecipe = recipeRepository.findByName(anyString());

        //then
        assertThat(expectedRecipe).isNull();
    }

    @Test
    @DisplayName("Find By Name Throw Illegal Argument Exception")
    void should_Find_By_Name_Throw_Illegal_Argument_Exception() {
        //given
        given(recipeRepository.findByName(any())).willThrow(IllegalArgumentException.class);

        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> recipeRepository.findByName(any()));
    }

    @Test
    @DisplayName("Successfully Find Expected Meals")
    void should_Successfully_Find_Expected_Meals() {
        //given
        meal = new MealsBuilder().withDefaultMeal().build();
        given(mealsRepository
                .findTopByTotalKcalOfMealsBetweenAndSumOfCookTotalTimeLessThanEqualAndRecipeSetSizeEquals(
                        (int) (900 * CONVERTER),900, 60, 3))
                .willReturn(meal);
        //when
        Meals expectedMeal = dataBaseMealsService.findExpectedMeals(900, 60, 3);

        //then
        then(mealsRepository).should()
                .findTopByTotalKcalOfMealsBetweenAndSumOfCookTotalTimeLessThanEqualAndRecipeSetSizeEquals(
                        (int) (900 * CONVERTER),900, 60, 3);
        assertThat(expectedMeal).isNotNull();
        assertThat(expectedMeal).isEqualTo(meal);
    }

//    @Test
//    @DisplayName("Not Find Expected Meals And Throw NullPointerException")
//    void should_Not_Find_Expected_Meals_And_Throw_NullPointerException() {
//        //given
//        given(mealsRepository
//                .findTopByTotalKcalOfMealsBetweenAndSumOfCookTotalTimeLessThanEqualAndRecipeSetSizeEquals(anyInt(), anyInt(), anyInt(), anyInt()))
//                .willThrow(NullPointerException.class);
//
//        //when
//        //then
//        assertThrows(NullPointerException.class, () -> dataBaseMealsService.findExpectedMeals(anyInt(), anyInt(), anyInt()));
//    }

    @Test
    @DisplayName("Successfully Find Expected Recipe Set")
    void should_Find_Expected_Recipe_Set() {
        //given
        Set<Recipe> setOfRecipes = new HashSet<>();
        given(recipeRepository.findByNutrition_CaloriesBetweenAndCookTimeMinutesLessThanEqual(
                (int) (600 * CONVERTER), 600, 40))
                .willReturn(setOfRecipes);
        //when
        Set<Recipe> expectedRecipeSet = dataBaseMealsService.findExpectedRecipeSet(600, 40);

        //then
        then(recipeRepository).should().findByNutrition_CaloriesBetweenAndCookTimeMinutesLessThanEqual(
                (int) (600 * CONVERTER), 600, 40);
        assertThat(expectedRecipeSet).isNotNull();
        assertThat(expectedRecipeSet).isEqualTo(setOfRecipes);
    }

//    @Test
//    @DisplayName("Not Find Expected Recipe Set")
//    void should_Not_Find_Expected_Recipe_Set() {
//        //given
//        given(recipeRepository.findByNutrition_CaloriesBetweenAndCookTimeMinutesLessThanEqual(anyInt(), anyInt(), anyInt()))
//                .willReturn(null);
//        //when
//        Set<Recipe> expectedRecipeSet = dataBaseMealsService.findExpectedRecipeSet(anyInt(), anyInt());
//
//        //then
//        then(recipeRepository).should().findByNutrition_CaloriesBetweenAndCookTimeMinutesLessThanEqual(anyInt(), anyInt(), anyInt());
//        assertThat(expectedRecipeSet).isNull();
//    }

    @Test
    @DisplayName("DataBase is not empty")
    void should_DataBase_Not_Be_Empty() {
        //given
        given(mealsRepository.count()).willReturn(3L);

        //when
        long result = mealsRepository.count();

        //then
        then(mealsRepository).should().count();
        assertThat(result).isEqualTo(3L);
    }
}