package pl.trzcinski.emil.recipeproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.trzcinski.emil.recipeproject.model.Meals;
import pl.trzcinski.emil.recipeproject.model.User;
import pl.trzcinski.emil.recipeproject.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

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

    public User getUserWithMeals(int identifier, int expectedKcal, int expectedTotalTimeMinutes, int numberOfMeals) throws Exception {

        User preparedUser = userService.getUser(identifier);

        Set<Meals> expectedMealsSet = new HashSet<>();
        expectedMealsSet.add(mealsService.getMeals(expectedKcal, expectedTotalTimeMinutes, numberOfMeals));
        preparedUser.setMealsSet(expectedMealsSet);

        return preparedUser;
    }
}
