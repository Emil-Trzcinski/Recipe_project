package pl.trzcinski.emil.recipeproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.trzcinski.emil.recipeproject.model.Meals;
import pl.trzcinski.emil.recipeproject.model.User;
import pl.trzcinski.emil.recipeproject.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

/**
 * UserMealsService serwis usyskujacy dla konkretnego uzytkownia oczekiwany przez niego zestaw przepisow
 */
@Slf4j
@Service
public class UserMealsService {

    UserService userService;
    UserRepository userRepository;
    MealsService mealsService;

    public UserMealsService(UserService userService, UserRepository userRepository, MealsService mealsService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.mealsService = mealsService;
    }

    /**
     * pobiera dla konkretnego uzytkowania zestaw oczekiwanych przez niego posilkow
     *
     * @param identifier identyfikator uzytkownika
     * @param expectedKcal oczekiwana ilosc Kcal na posilki
     * @param expectedTotalTimeMinutes oczekiwana ilosc czas na przygotowanie posilkow
     * @param numberOfMeals liczba posikow
     * @return uzytkowania wraz z przygotowanymi posilkami i lista zakupowa
     * @throws Exception
     */
    public User getUserWithMeals(int identifier, int expectedKcal, int expectedTotalTimeMinutes, int numberOfMeals) throws Exception {

        User preparedUser = userService.getUser(identifier);

        Set<Meals> expectedMealsSet = new HashSet<>();
        expectedMealsSet.add(mealsService.getMeals(expectedKcal, expectedTotalTimeMinutes, numberOfMeals));
        preparedUser.setMealsSet(expectedMealsSet);

        return preparedUser;
    }
}
