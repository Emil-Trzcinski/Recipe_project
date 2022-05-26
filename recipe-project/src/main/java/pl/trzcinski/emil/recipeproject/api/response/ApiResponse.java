package pl.trzcinski.emil.recipeproject.api.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;

import pl.trzcinski.emil.recipeproject.model.Meals;
import pl.trzcinski.emil.recipeproject.service.MealsService;

@Controller
public class ApiResponse {

    private final MealsService mealsService;
    private final int limitKcal  = 300;
    private final int limitTime  = 20;

    public ApiResponse(MealsService mealsService) {
        this.mealsService = mealsService;
    }

    public ResponseEntity<Meals> responseFromRecipeService(int expectedKcal, int expectedTotalTimeMinutes,
                                                           int numberOfMeals) throws Exception {

        if (numberOfMeals < 1 || numberOfMeals > 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST
                    , "----- You expect wrong number of meals, " +
                    " please insert correct value, starts from 1 to 3 meals -----");
        }

        if (expectedKcal < limitKcal * numberOfMeals) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST
                    , "----- You expect to low Kcal per meal, " +
                    "please increase your expected value, recommended is 300 Kcal per meal -----");
        }

        if (expectedTotalTimeMinutes < limitTime * numberOfMeals) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST
                    , "----- You expect to little Time to cook meals, " +
                    " please increase your expected value, recommended is 20 minutes per meal -----");
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(mealsService
                        .getMeals(expectedKcal, expectedTotalTimeMinutes, numberOfMeals));
    }
}
