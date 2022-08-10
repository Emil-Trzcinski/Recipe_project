package pl.trzcinski.emil.recipeproject.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.trzcinski.emil.recipeproject.model.Meals;
import pl.trzcinski.emil.recipeproject.service.MealsService;

@Slf4j
@EnableCaching
@RestController
public class RecipeApi {

    private final MealsService mealsService;
    private static final int BOTTOM_LIMIT_KCAL = 300;
    private static final int UPPER_LIMIT_KCAL = 900;
    private static final int LIMIT_TIME = 20;

    public RecipeApi(MealsService mealsService) {
        this.mealsService = mealsService;
    }

    @GetMapping("/api/v1/meals")
    @ResponseBody
    public ResponseEntity<Meals> getRecipe(@RequestParam int expectedKcal, int expectedTotalTimeMinutes,
                                           @RequestParam(defaultValue = "1") int numberOfMeals) throws Exception {

        if (numberOfMeals < 1 || numberOfMeals > 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST
                    , "----- You expect wrong number of meals, " +
                    " please insert correct value, starts from 1 to 3 meals -----");
        }

        if (expectedKcal < BOTTOM_LIMIT_KCAL * numberOfMeals) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST
                    , "----- You expect to low Kcal per meal, " +
                    "please increase your expected value, recommended is more than 300 Kcal per meal -----");
        }

        if (expectedKcal > UPPER_LIMIT_KCAL * numberOfMeals) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST
                    , "----- You expect to much Kcal per meal, " +
                    "please decrease your expected value, recommended is less then 900 Kcal per meal -----");
        }

        if (expectedTotalTimeMinutes < LIMIT_TIME * numberOfMeals) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST
                    , "----- You expect to little Time to cook meals, " +
                    " please increase your expected value, recommended is 20 minutes per meal -----");
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(mealsService.getMeals(expectedKcal, expectedTotalTimeMinutes, numberOfMeals));
    }
}
