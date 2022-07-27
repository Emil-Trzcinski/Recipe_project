package pl.trzcinski.emil.recipeproject.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;
import org.springframework.stereotype.Service;
import pl.trzcinski.emil.recipeproject.model.RecipeList;

import java.io.IOException;
import java.util.Objects;

@Service
public class RecipeListMapperService {

    ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public RecipeList getListFromResponseBody(Response response) throws IOException, NullPointerException,
            StreamReadException, DatabindException, JsonProcessingException, JsonMappingException {

        return objectMapper.readValue(Objects.requireNonNull(response.body()).string(), RecipeList.class);
    }
}
