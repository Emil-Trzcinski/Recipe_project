package pl.trzcinski.emil.recipeproject.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.trzcinski.emil.recipeproject.api.response.ApiResponse;
import pl.trzcinski.emil.recipeproject.model.Meals;

@Slf4j
@EnableCaching
@RestController
public class RecipeApi {

    private final ApiResponse apiResponse;

    public RecipeApi(ApiResponse apiResponse) {
        this.apiResponse = apiResponse;

    }

    @GetMapping("/api/v1/przepis")
    @ResponseBody
    public ResponseEntity<Meals> getRecipe(@RequestParam int expectedKcal, int expectedTotalTimeMinutes,
                                                 @RequestParam(defaultValue = "1") int numberOfMeals) throws Exception {

        // metoda wysyłająca informację do recipeservcie - hardcode
        return apiResponse.responseFromRecipeService(expectedKcal, expectedTotalTimeMinutes, numberOfMeals);



//        recipeService.logNameFromRecipeSet(recipeService.getListOfRecipesWithAllParameters(expectedKcal, expectedTotalTimeMinutes, numberOfMeals));
//        return recipeService.getListOfRecipesWithAllParameters(expectedKcal, expectedTotalTimeMinutes, numberOfMeals).toString();
    }
}
