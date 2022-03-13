package pl.trzcinski.emil.recipeproject.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import pl.trzcinski.emil.recipeproject.domain.Recipe;
import pl.trzcinski.emil.recipeproject.domain.RecipeList;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CallToExternalApiService {

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
            Response response = getResponse("list?from=0&size=20");

            String responseBody = response.body().string();

            ObjectMapper objectMapper = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

              return  objectMapper.readValue(responseBody, RecipeList.class);

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
                .map(k -> "Recipe: " + k.getName() + " --Id: " + k.getId()) /*+ " --Kcal: " + k.getNutrition().getCalories())*/
                .forEach(log::info);
    }

    public void getNameFromRecipe(Recipe recipe) {
        log.info("--------Recipe----------");
        log.info(recipe.toString());
    }


}
