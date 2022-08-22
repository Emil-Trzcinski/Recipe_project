package pl.trzcinski.emil.recipeproject.service;

import org.springframework.stereotype.Service;
import pl.trzcinski.emil.recipeproject.model.Meals;
import pl.trzcinski.emil.recipeproject.model.Recipe;
import pl.trzcinski.emil.recipeproject.repository.MealsRepository;
import pl.trzcinski.emil.recipeproject.repository.RecipeRepository;

import java.util.Set;

import static pl.trzcinski.emil.recipeproject.service.Conversion.CONVERTER;

/**
 * DataBaseMealsService zajmuje się komunikacja z baza danych gotowych posilkow i przepisow
 */
@Service
public class DataBaseMealsService {

    private final MealsRepository mealsRepository;
    private final RecipeRepository recipeRepository;

    public DataBaseMealsService(MealsRepository mealsRepository, RecipeRepository recipeRepository) {

        this.mealsRepository = mealsRepository;
        this.recipeRepository = recipeRepository;
    }

    /**
     * zapisuje i zwraca posiłek
     * @param meals posiłek
     * @return posiłek
     */
    public Meals create(Meals meals) {
        return mealsRepository.save(meals);
    }

    /**
     * wyszukuje posiłek o danej nazwie
     * @param name nazawa posiłku
     * @return posiłek
     */
    public Recipe findByName(String name) {
        return recipeRepository.findByName(name);
    }

    /**
     * wyszukuje oczekiwany posiłek o wartosciach najbardziej zblizonych do oczekiwanych
     * @param expectedKcal liczba kalorii
     * @param expectedTotalTimeMinutes przewidywane czas przyrzadzania
     * @param size liczba posilkow
     * @return posilek
     */
    public Meals findExpectedMeals(int expectedKcal, int expectedTotalTimeMinutes, int size) {
        return mealsRepository.findTopByTotalKcalOfMealsBetweenAndSumOfCookTotalTimeLessThanEqualAndRecipeSetSizeEquals
                (calculateMinimumOfKcal(expectedKcal), expectedKcal, expectedTotalTimeMinutes, size);
    }

    /**
     * wyszukuje zestaw uniekalnych przepisow pomiedzy wartoscia minimalną a oczekiwana
     * @param expectedKcal ozcekiwana ilosc kaloryczna
     * @param expectedTotalTimeMinutes oczekiwany zcas przyrzadzenia
     * @return zestaw unikalnych przepisow
     */
    public Set<Recipe> findExpectedRecipeSet(int expectedKcal, int expectedTotalTimeMinutes) {
        return recipeRepository.
                findByNutrition_CaloriesBetweenAndCookTimeMinutesLessThanEqual
                        (calculateMinimumOfKcal(expectedKcal), expectedKcal, expectedTotalTimeMinutes);
    }

    /**
     * sprawdza czy bazada danych nie jest pusta
     * @return true jeżeli w bazie są posiłki
     */
    public boolean isNotEmpty() {
        return mealsRepository.count() > 0;
    }

    /**
     * oblicza dolny zakres zapytania do bazy danych
     * @param expectedKcal oczekiwana ilość kcal
     * @return dolny zakres kcal
     */
    private Integer calculateMinimumOfKcal(int expectedKcal) {
        return (int) (expectedKcal * CONVERTER);
    }
}
