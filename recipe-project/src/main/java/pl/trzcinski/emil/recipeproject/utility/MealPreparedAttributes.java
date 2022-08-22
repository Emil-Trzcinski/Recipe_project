package pl.trzcinski.emil.recipeproject.utility;

/**
 * MealPreparedAttributes sluzy do przliczania podanych wartosci na konkretna ilosc posilkow
 */
public class MealPreparedAttributes {

    private MealPreparedAttributes() {
        //defensive move to block creating instance of this class
    }

    /**
     * Kalkulator służący do przeliczania ilości na poszegolny posiłek
     *
     * @param givenValue wartosc z ktorej zostanie przeliczona ilosc na konkrretny posilek
     * @param numberOfMeals liczba posilkow
     * @return konkretna ilosc na 1 posiłek
     * @throws ArithmeticException       dzielenie przez 0
     * @throws IllegalArgumentException  przy wartosciach mniejszych niz 0
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
     * przy pomocu kalkulatora przelicza ilości potrzebnego czasu na poszegolny posiłek
     *
     * @param expectedTotalTimeMinutes calokowity czas z ktorego zostanie wyliczony czas na konkretny posilek
     * @param numberOfMeals liczba posilkow
     * @return konkretna ilosc Kcal na 1 posiłek
     * @throws ArithmeticException       dzielenie przez 0
     * @throws IllegalArgumentException  przy wartosciach mniejszych niz 0
     */
    public static int calculateTimePerMeal(int expectedTotalTimeMinutes, int numberOfMeals) {
        return calculation(expectedTotalTimeMinutes, numberOfMeals);
    }

    /**
     * przy pomocu kalkulatora przelicza ilości kcal na poszegolny posiłek
     *
     * @param expectedKcal calkowita liczba kcal z ktory zostanie wyliczona ilosc na konkretny posilek
     * @param numberOfMeals liczba posilkow
     * @return konkretna ilosc Kcal na 1 posiłek
     * @throws ArithmeticException       dzielenie przez 0
     * @throws IllegalArgumentException  przy wartosciach mniejszych niz 0
     */
    public static int calculateKcalPerMeal(int expectedKcal, int numberOfMeals) {
        return calculation(expectedKcal, numberOfMeals);
    }

}
