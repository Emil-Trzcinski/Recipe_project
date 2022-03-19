/*

package pl.trzcinski.emil.recipeproject.api.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.trzcinski.emil.recipeproject.model.Recipe;
import pl.trzcinski.emil.recipeproject.model.RecipeList;
import pl.trzcinski.emil.recipeproject.service.CallToExternalApiService;

@Slf4j
@RestController
public class TestsExternalApiRequest {

    private final CallToExternalApiService callToExternalApiService;
    public TestsExternalApiRequest(CallToExternalApiService callToExternalApiService) {
        this.callToExternalApiService = callToExternalApiService;
    }

    @GetMapping("/list")
    public RecipeList getListFromExternalApi() throws Exception {

        callToExternalApiService.getNameFromRecipeList(callToExternalApiService.getListFromExternalApi());
        return callToExternalApiService.getListFromExternalApi();
    }

    @GetMapping("/recipe")
    public Recipe getRecipeFromExternalApi() throws Exception {

        callToExternalApiService.getNameFromRecipe(callToExternalApiService.getRecipeFromExternalApi());
        return callToExternalApiService.getRecipeFromExternalApi();
    }

}


 */