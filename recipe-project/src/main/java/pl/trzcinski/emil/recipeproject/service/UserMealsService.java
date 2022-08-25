package pl.trzcinski.emil.recipeproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.trzcinski.emil.recipeproject.model.Meals;
import pl.trzcinski.emil.recipeproject.model.User;
import pl.trzcinski.emil.recipeproject.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Set;

/**
 * UserMealsService obtains a set of meals for a specific user, according to his expectations
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
     * receive a usage-specific set of meals it expects
     *
     * @param identifier user identifier
     * @param expectedKcal expected Kcal for meals
     * @param expectedTotalTimeMinutes expected amount of time to prepare meals
     * @param numberOfMeals number of meals
     * @return user with prepared meals, total calorific value, preparation time and shopping list
     * @throws EntityNotFoundException user not found
     *
     */
    public User getUserWithMeals(int identifier, int expectedKcal, int expectedTotalTimeMinutes, int numberOfMeals) {

        User preparedUser = userService.getUser(identifier);

        Set<Meals> expectedMealsSet = new HashSet<>();
        expectedMealsSet.add(mealsService.getMeals(expectedKcal, expectedTotalTimeMinutes, numberOfMeals));
        preparedUser.setMealsSet(expectedMealsSet);

        return preparedUser;
    }
}
