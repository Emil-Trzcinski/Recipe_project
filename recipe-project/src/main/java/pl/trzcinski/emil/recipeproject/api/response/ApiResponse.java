package pl.trzcinski.emil.recipeproject.api.response;

import org.springframework.stereotype.Controller;
import pl.trzcinski.emil.recipeproject.model.Recipe;
import pl.trzcinski.emil.recipeproject.service.RecipeService;

import java.util.Set;


@Controller
public class ApiResponse {

    private final RecipeService recipeService;

    public ApiResponse(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    public Set<Recipe> responseFromRecipeService(int expectedKcal, int expectedTotalTimeMinutes, int numberOfMeals) throws Exception {
        return recipeService.getListOfRecipesWithAllParameters(expectedKcal, expectedTotalTimeMinutes, numberOfMeals);
    }

}
