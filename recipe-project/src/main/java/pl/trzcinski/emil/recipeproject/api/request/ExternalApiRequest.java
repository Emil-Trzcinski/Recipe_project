package pl.trzcinski.emil.recipeproject.api.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.trzcinski.emil.recipeproject.domain.Recipe;
import pl.trzcinski.emil.recipeproject.domain.RecipeList;
import pl.trzcinski.emil.recipeproject.service.CallToExternalApiService;

@Slf4j
@RestController
public class ExternalApiRequest {

    private final CallToExternalApiService callToExternalApiSevice;

    public ExternalApiRequest(CallToExternalApiService callToExternalApiSevice) {
        this.callToExternalApiSevice = callToExternalApiSevice;
    }

    @GetMapping("/list")
    public RecipeList getListFromExternalApi() throws Exception {

        callToExternalApiSevice.getNameFromRecipeList(callToExternalApiSevice.getListFromExternalApi());
        return callToExternalApiSevice.getListFromExternalApi();
    }

    @GetMapping("/recipe")
    public Recipe getRecipeFromExternalApi() throws Exception {

        callToExternalApiSevice.getNameFromRecipe(callToExternalApiSevice.getRecipeFromExternalApi());
        return callToExternalApiSevice.getRecipeFromExternalApi();
    }

}
