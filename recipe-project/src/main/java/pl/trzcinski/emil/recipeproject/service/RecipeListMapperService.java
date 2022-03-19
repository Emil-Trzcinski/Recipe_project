package pl.trzcinski.emil.recipeproject.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.trzcinski.emil.recipeproject.api.request.ExternalApiRequest;
import pl.trzcinski.emil.recipeproject.model.RecipeList;

@Slf4j
@Service
public class RecipeListMapperService {

    private final ExternalApiRequest externalApiRequest;

    public RecipeListMapperService(ExternalApiRequest externalApiRequest) {
        this.externalApiRequest = externalApiRequest;
    }

    public RecipeList getListFromResponseBody() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return objectMapper.readValue(externalApiRequest.getResponseBodyFromResponse(), RecipeList.class);
    }
}
