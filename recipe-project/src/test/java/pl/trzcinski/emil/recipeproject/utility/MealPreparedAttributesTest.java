package pl.trzcinski.emil.recipeproject.utility;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;
import static pl.trzcinski.emil.recipeproject.utility.MealPreparedAttributes.calculateKcalPerMeal;
import static pl.trzcinski.emil.recipeproject.utility.MealPreparedAttributes.calculateTimePerMeal;

class MealPreparedAttributesTest {

    @ParameterizedTest(name = "Test {index} => Division {0} and {1} returns {2}")
    @CsvSource({ "2, 1, 2", "4, 2, 2", "20, 4, 5", "5, 3 , 1", "10, 10, 1", "0, 1, 0"})
    void should_calculate_Kcal_PerMeal(int expectedKcal, int numberOfMeals, int expected) {
        //given
        //when
        int result = calculateKcalPerMeal(expectedKcal, numberOfMeals);
        //then
        assertEquals(expected, result);
    }

    @ParameterizedTest(name = "Test {index} => Division {0} and {1} returns {2}")
    @CsvSource({"-5, 4", "5, -5"})
    void should_throw_IllegalArgumentException_when_calculate_Kcal_PerMeal(int expectedKcal, int numberOfMeals) {
        //given
        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> calculateKcalPerMeal(expectedKcal, numberOfMeals));
        //then
        assertTrue(exception.getMessage().contains("less than 0"));
    }

    @ParameterizedTest(name = "Test {index} => Division {0} and {1} returns {2}")
    @CsvSource({"4, 0"})
    void should_throw_ArithmeticException_when_calculate_Kcal_PerMeal(int expectedKcal, int numberOfMeals) {
        //given
        //when
        Exception exception = assertThrows(ArithmeticException.class, () -> calculateKcalPerMeal(expectedKcal, numberOfMeals));
        //then
        assertTrue(exception.getMessage().contains("Division by 0"));
    }

    @ParameterizedTest(name = "Test {index} => Division {0} and {1} returns {2}")
    @CsvSource({ "2, 1, 2", "4, 2, 2", "20, 4, 5", "5, 3 , 1", "10, 10, 1, 0, 1, 0"})
    void should_calculate_Time_Per_Meal(int expectedTotalTimeMinutes, int numberOfMeals, int expected) {
        //given
        //when
        int result = calculateTimePerMeal(expectedTotalTimeMinutes, numberOfMeals);
        //then
        assertEquals(expected, result);
    }

    @ParameterizedTest(name = "Test {index} => Division {0} and {1} returns {2}")
    @CsvSource({"-5, 4", "5, -5"})
    void should_throw_IllegalArgumentException_when_calculate_Time_Per_Meal(int expectedKcal, int numberOfMeals) {
        //given
        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> calculateTimePerMeal(expectedKcal, numberOfMeals));
        //then
        assertTrue(exception.getMessage().contains("less than 0"));
    }

    @ParameterizedTest(name = "Test {index} => Division {0} and {1} returns {2}")
    @CsvSource({"4, 0"})
    void should_throw_ArithmeticException_when_calculate_Time_Per_Meal(int expectedKcal, int numberOfMeals) {
        //given
        //when
        Exception exception = assertThrows(ArithmeticException.class, () -> calculateTimePerMeal(expectedKcal, numberOfMeals));
        //then
        assertTrue(exception.getMessage().contains("Division by 0"));
    }

}