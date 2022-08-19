package pl.trzcinski.emil.recipeproject.api.integrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
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
import pl.trzcinski.emil.recipeproject.api.MealsController;
import pl.trzcinski.emil.recipeproject.model.Meals;
import pl.trzcinski.emil.recipeproject.model.User;
import pl.trzcinski.emil.recipeproject.repository.UserRepository;
import pl.trzcinski.emil.recipeproject.service.UserService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.DisplayName.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@ExtendWith(MockitoExtension.class)
public class MealsControllerIntegrationTest {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Mock
    MealsController mealsController;

    User user;


    @BeforeEach
    public void setUp() {
        user = new User(1L, "someName", 1657654684, null);

        userRepository.save(user);
    }


    @ParameterizedTest(name = "Test {index} => identifier= {0}, Kcal = {1}, Time = {2}, Recipes = {3}")
    @DisplayName("1. Successfully Get Meal ")
    @CsvSource({"1657654684, 450, 20, 1", "1657654684, 800, 50, 2", "1657654684, 1500, 60, 3", "1657654684, 2400, 90, 3", "1657654684, 800, 50, 2",
            "1657654684, 450, 20, 1", "1657654684, 500, 30, 1", "1657654684, 1500, 60, 3"})
    public void should_Get_Meal(int identifier, int kcal, int time, int recipes) throws Exception {
        //given
        //when
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/meals?identifier=" + identifier + "&expectedKcal=" + kcal +
                        "&expectedTotalTimeMinutes=" + time + " &numberOfMeals=" + recipes))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
        //then
        User result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);

        assertThat(result).isNotNull();
        assertThat(result.getMealsSet().stream().map(meals -> meals.getRecipeSet().size()).toList().get(0)).isEqualTo(recipes);
        assertThat(result.getMealsSet().stream().map(Meals::getShoppingList)).isNotNull();
    }

    @ParameterizedTest(name = "Test {index} => Returns Too Many Requests")
    @DisplayName("8. Too Many Requests")
    @CsvSource({"800", "850", "900"})
    public void should_Not_Get_Meal_Too_Many_Requests(int Kcal) throws Exception {
        //given
        //when
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/meals?identifier=1389091090&expectedKcal=" + Kcal +
                        "&expectedTotalTimeMinutes=20&numberOfMeals=1"))
                .andDo(print())
                .andExpect(status().is(429))
                .andReturn();
        //then
        User result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);
        assertThat(result).isNotNull();
    }
}
