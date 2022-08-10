package pl.trzcinski.emil.recipeproject.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.server.ResponseStatusException;
import pl.trzcinski.emil.recipeproject.model.Meals;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.DisplayName.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@ExtendWith(MockitoExtension.class)
class RecipeApiTest {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Mock
    RecipeApi recipeApi;

    @ParameterizedTest(name = "Test {index} => Kcal = {0}, Time = {1}, Recipes = {2}")
    @DisplayName("1. Successfully Get Meal ")
    @CsvSource({"450, 20, 1", "800, 50, 2", "1500, 60, 3", "2400, 90, 3", "800, 50, 2", "450, 20, 1", "500, 30, 1", "1500, 60, 3"})
    public void should_Get_One_Meal(int kcal, int time, int recipes) throws Exception {
        //given
        //when
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/meals?expectedKcal=" + kcal +
                        "&expectedTotalTimeMinutes=" + time + " &numberOfMeals=" + recipes + ""))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
        //then
        Meals meals = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Meals.class);

        assertThat(meals).isNotNull();
        assertThat(meals.getRecipeSet().size()).isEqualTo(recipes);
        assertThat(meals.getShoppingList()).isNotNull();
    }

    @ParameterizedTest(name = "Test {index} => Returns Too Many Requests")
    @DisplayName("8. Too Many Requests")
    @CsvSource({"800", "850", "900"})
    public void should_Not_Get_Meal_Too_Many_Requests(int Kcal) throws Exception {
        //given
        //when
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/meals?expectedKcal=" + Kcal + "&expectedTotalTimeMinutes=20&numberOfMeals=1"))
                .andDo(print())
                .andExpect(status().is(429))
                .andReturn();
        //then
        Meals meals = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Meals.class);
        assertThat(meals).isNotNull();
    }

    @Test
    @DisplayName("Can`t Get Four Meal")
    public void should_Not_Get_Four_Meal() throws Exception {
        //given
        given(recipeApi.getRecipe(300, 20, 4)).willThrow(ResponseStatusException.class);
        //when
        //then
        assertThrows(ResponseStatusException.class, () -> recipeApi.getRecipe(300, 20, 4));
    }

    @Test
    @DisplayName("Can`t Get Zero Meal")
    public void should_Not_Get_Zero_Meal() throws Exception {
        //given
        given(recipeApi.getRecipe(300, 20, 0)).willThrow(ResponseStatusException.class);
        //when
        //then
        assertThrows(ResponseStatusException.class, () -> recipeApi.getRecipe(300, 20, 0));
    }

    @Test
    @DisplayName("Can`t Get One Meal With Less Than 300 KcalL")
    public void should_Not_Get_One_Meal_Less_Than_300_Kcal() throws Exception {
        //given
        given(recipeApi.getRecipe(299, 20, 1)).willThrow(ResponseStatusException.class);
        //when
        //then
        assertThrows(ResponseStatusException.class, () -> recipeApi.getRecipe(299, 20, 1));
    }

    @Test
    @DisplayName("Can`t Get One Meal With More Than 900 Kcal")
    public void should_Not_Get_One_Meal_Less_Than_900_Kcal() throws Exception {
        //given
        given(recipeApi.getRecipe(901, 20, 1)).willThrow(ResponseStatusException.class);
        //when
        //then
        assertThrows(ResponseStatusException.class, () -> recipeApi.getRecipe(901, 20, 1));
    }

    @Test
    @DisplayName("Can`t Get One Meal With Less Than 20 minutes")
    public void should_Not_Get_One_Meal_Less_Than_20_Min() throws Exception {
        //given
        given(recipeApi.getRecipe(500, 19, 1)).willThrow(ResponseStatusException.class);
        //when
        //then
        assertThrows(ResponseStatusException.class, () -> recipeApi.getRecipe(500, 19, 1));
    }
}
