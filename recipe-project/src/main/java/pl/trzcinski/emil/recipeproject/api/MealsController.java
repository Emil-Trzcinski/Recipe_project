package pl.trzcinski.emil.recipeproject.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.trzcinski.emil.recipeproject.model.User;
import pl.trzcinski.emil.recipeproject.service.UserMealsService;

import javax.persistence.EntityNotFoundException;

/**
 * MealsController process incoming user requests
 */
@Slf4j
@EnableCaching
@RestController
public class MealsController {
    private final UserMealsService userMealsService;
    private static final int BOTTOM_LIMIT_KCAL = 300;
    private static final int UPPER_LIMIT_KCAL = 900;
    private static final int LIMIT_TIME = 20;


    public MealsController(UserMealsService userMealsService) {
        this.userMealsService = userMealsService;
    }

    /**
     * gets recipes for a specific user, default one meal
     * <p>
     * if the query contains incorrect parameters it returns information about the incorrect query and HttpStatus.BAD_REQUEST
     * @param identifier unique user identifier
     * @param expectedKcal expected calorific value of meals
     * @param expectedTotalTimeMinutes expected time of preparation
     * @param numberOfMeals number of meals
     * @return user with prepared recipes and HttpStatus OK
     * @throws EntityNotFoundException - user doesn't exist
     */
    @GetMapping("/api/v1/meals")
    @ResponseBody
    public ResponseEntity<User> getRecipe(@RequestParam int identifier, int expectedKcal, int expectedTotalTimeMinutes,
                                          @RequestParam(defaultValue = "1") int numberOfMeals) throws EntityNotFoundException {


        if (identifier <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST
                    , "-----  wrong identifier, " +
                    " please insert correct identifier -----");
        }

        if (numberOfMeals < 1 || numberOfMeals > 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST
                    , "----- You expect wrong number of meals, " +
                    " please insert correct value, starts from 1 to 3 meals -----");
        }

        if (expectedKcal < BOTTOM_LIMIT_KCAL * numberOfMeals) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST
                    , "----- You expect too low Kcal per meal, " +
                    "please increase your expected value, recommended is more than 300 Kcal per meal -----");
        }

        if (expectedKcal > UPPER_LIMIT_KCAL * numberOfMeals) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST
                    , "----- You expect too much Kcal per meal, " +
                    "please decrease your expected value, recommended is less than 900 Kcal per meal -----");
        }

        if (expectedTotalTimeMinutes < LIMIT_TIME * numberOfMeals) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST
                    , "----- You expect too short Time to cook meals, " +
                    " please increase your expected value, recommended is 20 minutes per meal -----");
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(userMealsService.getUserWithMeals(identifier, expectedKcal, expectedTotalTimeMinutes, numberOfMeals));
    }
}
