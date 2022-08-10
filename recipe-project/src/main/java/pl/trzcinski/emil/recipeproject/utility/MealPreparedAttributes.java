package pl.trzcinski.emil.recipeproject.utility;

public class MealPreparedAttributes {

    private MealPreparedAttributes() {
        //defensive move to block creating instance of this class
    }

    private static int calculation(int expectedKcal, int numberOfMeals) {
        int result = 0;
        if (expectedKcal < 0 || numberOfMeals < 0) {
            throw new IllegalArgumentException("less than 0");
        }

        try {
            result = expectedKcal / numberOfMeals;
        } catch (ArithmeticException e) {
            throw new ArithmeticException("Division by 0");
        }
        return result;
    }

    public static int calculateTimePerMeal(int expectedTotalTimeMinutes, int numberOfMeals) {
        return calculation(expectedTotalTimeMinutes, numberOfMeals);
    }

    public static int calculateKcalPerMeal(int expectedKcal, int numberOfMeals) {
        return calculation(expectedKcal, numberOfMeals);
    }


}
