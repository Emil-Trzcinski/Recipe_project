package pl.trzcinski.emil.recipeproject.utility;

/**
 * MealPreparedAttributes It is used to convert the given values into a specific number of meals
 */
public class MealPreparedAttributes {

    private MealPreparedAttributes() {
        //defensive move to block creating instance of this class
    }

    /**
     * Calculator for converting the amount to each meal
     *
     * @param givenValue    the value from which the quantity will be converted into a specific meal
     * @param numberOfMeals number of meals
     * @return specific amount for one meal
     * @throws ArithmeticException      divide by 0
     * @throws IllegalArgumentException with values less than 0
     */

    private static int calculation(int givenValue, int numberOfMeals) {
        int result = 0;
        if (givenValue < 0 || numberOfMeals < 0) {
            throw new IllegalArgumentException("less than 0");
        }

        try {
            result = givenValue / numberOfMeals;
        } catch (ArithmeticException e) {
            throw new ArithmeticException("Division by 0");
        }
        return result;
    }

    /**
     * calculator to convert the amount of time needed for each meal
     *
     * @param expectedTotalTimeMinutes total time from which the time for a specific meal will be calculated
     * @param numberOfMeals            number of meals
     * @return specific amount for one meal
     * @throws ArithmeticException      divide by 0
     * @throws IllegalArgumentException with values less than 0
     */
    public static int calculateTimePerMeal(int expectedTotalTimeMinutes, int numberOfMeals) {
        return calculation(expectedTotalTimeMinutes, numberOfMeals);
    }

    /**
     * calculator to convert the amount of kcal to each meal
     *
     * @param expectedKcal  total number of kcal from which the amount will be calculated for a specific meal
     * @param numberOfMeals number of meals
     * @return specific amount of Kcal for one meal
     * @throws ArithmeticException      divide by 0
     * @throws IllegalArgumentException with values less than 0
     */
    public static int calculateKcalPerMeal(int expectedKcal, int numberOfMeals) {
        return calculation(expectedKcal, numberOfMeals);
    }

}
