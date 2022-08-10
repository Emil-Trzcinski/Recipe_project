package pl.trzcinski.emil.recipeproject.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Service;
import pl.trzcinski.emil.recipeproject.model.RecipeList;

import java.io.IOException;

@Slf4j
@Service
public class RecipeListMapperService {

    ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public RecipeList getListFromResponseBody(ResponseBody responseBody) throws IOException, NullPointerException,
            StreamReadException, DatabindException, JsonProcessingException, JsonMappingException {
        RecipeList recipeList = new RecipeList();

        try {
            recipeList = objectMapper.readValue(responseBody.string(), RecipeList.class);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return recipeList;
    }
}
