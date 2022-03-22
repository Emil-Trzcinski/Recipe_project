package pl.trzcinski.emil.recipeproject.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.trzcinski.emil.recipeproject.api.request.ExternalApiRequest;
import pl.trzcinski.emil.recipeproject.service.RecipeService;

@Slf4j
@RestController
public class RecipeApi {

    private final ExternalApiRequest externalApiRequest;
    private final RecipeService recipeService;

    public RecipeApi(ExternalApiRequest externalApiRequest, RecipeService recipeService) {
        this.externalApiRequest = externalApiRequest;
        this.recipeService = recipeService;
    }

    @GetMapping("/list")
    public String getListFromExternalApi() throws Exception {
        recipeService.getNameFromRecipeList(recipeService.recipeListFiltering());
        return recipeService.recipeListFiltering().toString();
    }


    @GetMapping("/przepis")
    public String getRecipe(@RequestParam int kcal, int prepareTotalTimeMinutes) throws Exception {
        recipeService.getNameFromRecipe(recipeService.getListOfRecipesWithParameters(kcal, prepareTotalTimeMinutes));
        return recipeService.getListOfRecipesWithParameters(kcal, prepareTotalTimeMinutes).toString();
    }
}
