package pl.trzcinski.emil.recipeproject.utility;

public class MealPreparedAttributes {

    private MealPreparedAttributes() {
        ////defensive move to block creating instance of this class
    }

    public static int calculateKcalPerMeal(int expectedKcal, int numberOfMeals) {
        return  expectedKcal / numberOfMeals;
    }

    public static int  calculateTimePerMeal(int expectedTotalTimeMinutes, int numberOfMeals) {
        return  expectedTotalTimeMinutes / numberOfMeals;
    }

}
