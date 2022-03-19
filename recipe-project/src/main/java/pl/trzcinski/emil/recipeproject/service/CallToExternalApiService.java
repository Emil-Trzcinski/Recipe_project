/*

package pl.trzcinski.emil.recipeproject.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import pl.trzcinski.emil.recipeproject.model.Recipe;
import pl.trzcinski.emil.recipeproject.model.RecipeList;
import pl.trzcinski.emil.recipeproject.repository.RecipeRepository;

import java.io.IOException;

import static pl.trzcinski.emil.recipeproject.utility.RecipeListFilter.listFiltering;

@Slf4j
@Service
public class CallToExternalApiService {

    Recipe recipe;
    RecipeList recipeList;
    RecipeRepository recipeRepository;

    public CallToExternalApiService(Recipe recipe, RecipeList recipeList, RecipeRepository recipeRepository) {
        this.recipe = recipe;
        this.recipeList = recipeList;
        this.recipeRepository = recipeRepository;
    }
    //dodać do enum lub proporities

    private final String headerHostName = "x-rapidapi-host";
    private final String urlTasty = "https://tasty.p.rapidapi.com/recipes/";
    private final String headerHostValue = "tasty.p.rapidapi.com";
    private final String headerKeyName = "x-rapidapi-key";
    private final String headerKeyValue = "599499f508msh901b6a991084120p1b3173jsn2ea6a449da3e";

    @NotNull
    public Response getResponse(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(urlTasty + url)
                .get()
                .addHeader(headerHostName, headerHostValue)
                .addHeader(headerKeyName, headerKeyValue)
                .build();
        return client.newCall(request).execute();
    }

    public RecipeList getListFromExternalApi() throws Exception {
        try {
            Response response = getResponse("list?from=0&size=40");

            String responseBody = response.body().string();

            ObjectMapper objectMapper = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            RecipeList recipeList = objectMapper.readValue(responseBody, RecipeList.class);


            //recipeList.getResults().forEach(recipeRepository.save(recipe -> recipe.);


            return  listFiltering(recipeList);

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public Recipe getRecipeFromExternalApi() throws Exception {
        try {
            Response response = getResponse("get-more-info?id=3088");
            String responseBody = response.body().string();

            ObjectMapper objectMapper = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            return objectMapper.readValue(responseBody, Recipe.class);

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public void getNameFromRecipeList(RecipeList recipeList) {
        log.info("--------RecipeList----------");

        recipeList.getResults().stream()
                .map(recipe ->
                        "\nRecipe: " + recipe.getName()
                        + "\n---- Time: " + recipe.getCookTimeMinutes()
                        + "\n---- PrepTime: " + recipe.getPrepTimeMinutes()
                        + "\n---- TotalTime: " + recipe.getTotalTimeMinutes()
                        + "\n---- Kcal: " + recipe.getNutrition().getCalories()
                        + "\n---- Id: " + recipe.getId())
                .forEach(log::info);
    }

    public void getNameFromRecipe(Recipe recipe) {
        log.info("--------Recipe----------");
        log.info(recipe.toString());
    }
}


 */