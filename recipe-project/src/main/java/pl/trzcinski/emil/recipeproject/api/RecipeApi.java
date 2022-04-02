package pl.trzcinski.emil.recipeproject.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.trzcinski.emil.recipeproject.api.response.ApiResponse;
import pl.trzcinski.emil.recipeproject.model.Recipe;

import java.util.Set;

@Slf4j
@RestController
public class RecipeApi {

    private final ApiResponse apiResponse;

    public RecipeApi(ApiResponse apiResponse) {
        this.apiResponse = apiResponse;

    }

    @GetMapping("/przepis")
    public Set<Recipe> getRecipe(@RequestParam int expectedKcal, int expectedTotalTimeMinutes, int numberOfMeals) throws Exception {
        // metoda wysyłająca informację do recipeservcie - hardcode
        return apiResponse.responseFromRecipeService(expectedKcal, expectedTotalTimeMinutes, numberOfMeals);



//        recipeService.logNameFromRecipeSet(recipeService.getListOfRecipesWithAllParameters(expectedKcal, expectedTotalTimeMinutes, numberOfMeals));
//        return recipeService.getListOfRecipesWithAllParameters(expectedKcal, expectedTotalTimeMinutes, numberOfMeals).toString();
    }
}
