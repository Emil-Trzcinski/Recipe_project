package pl.trzcinski.emil.recipeproject.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;
import pl.trzcinski.emil.recipeproject.domain.Recipe;
import pl.trzcinski.emil.recipeproject.domain.RecipeList;

@Slf4j
@Service
public class CallToExternalApiService {

    private final String headerHostName = "x-rapidapi-host";
    private final String urlTasty = "https://tasty.p.rapidapi.com/recipes/list?from=0&size=20";
    private final String headerHostValue = "tasty.p.rapidapi.com";
    private final String headerKeyName = "x-rapidapi-key";
    private final String headerKeyValue = "599499f508msh901b6a991084120p1b3173jsn2ea6a449da3e";

    public RecipeList getListFromExternalApi() throws Exception {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(urlTasty)
                .get()
                .addHeader(headerHostName, headerHostValue)
                .addHeader(headerKeyName, headerKeyValue)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();

            ObjectMapper objectMapper = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            return objectMapper.readValue(responseBody, RecipeList.class);

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public Recipe getRecipeFromExternalApi() throws Exception {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://tasty.p.rapidapi.com/recipes/get-more-info?id=8144")
                .get()
                .addHeader(headerHostName, headerHostValue)
                .addHeader(headerKeyName, headerKeyValue)
                .build();

        try {
            Response response = client.newCall(request).execute();
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
                .map(k -> "Recipe: " + k.getName() + " --Id: " + k.getId())
                .forEach(log::info);
    }

    public void getNameFromRecipe(Recipe recipe) {
        log.info("--------Recipe----------");

        log.info(recipe.getName());
        log.info(recipe.toString());
    }
}
