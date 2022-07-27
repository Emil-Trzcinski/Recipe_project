package pl.trzcinski.emil.recipeproject.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.trzcinski.emil.recipeproject.model.Recipe;
import pl.trzcinski.emil.recipeproject.model.RecipeList;
import pl.trzcinski.emil.recipeproject.utility.builders.RecipeBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class RecipeListMapperServiceTest {

    @Mock
    private ObjectMapper objectMapper;
    private RecipeListMapperService recipeListMapperService;

    private final List<Recipe> listOfRecipe = new ArrayList<>();

    private final RecipeList recipeList = new RecipeList(listOfRecipe);

    private Response response;


    @Test
    void get_ListFromResponseBody() throws IOException {
        //given
        listOfRecipe.add(new RecipeBuilder().withDefaultRecipe().build());

        // when
        lenient().when(objectMapper.readValue(anyString(), eq(RecipeList.class))).thenReturn(recipeList);

//        assertTrue(recipeListMapperUtility.getListFromResponseBody(response).getResults().size() > 0);

        //then
//        assertTrue(recipeList.getResults().size() > 0);

    }
}