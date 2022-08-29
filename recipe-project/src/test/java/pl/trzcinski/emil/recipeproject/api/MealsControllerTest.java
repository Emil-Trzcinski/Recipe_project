package pl.trzcinski.emil.recipeproject.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import pl.trzcinski.emil.recipeproject.model.User;
import pl.trzcinski.emil.recipeproject.utility.builders.MealsBuilder;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@ExtendWith(MockitoExtension.class)
public class MealsControllerTest {

    @Mock
    MealsController mealsController;

    @Mock
    ResponseEntity<User> responseEntity;

    User user;

    @BeforeEach
    void setUp() {
        user = new User(1L, "someName", 1657654684, Set.of(new MealsBuilder().withDefaultMeal().build()));
        responseEntity = new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Test
    @DisplayName("Successfully Create User With Meals")
    void should_Get_User_With_Meals() throws Exception {
        //given
        given(mealsController.getRecipe(1657654684, 300, 20, 3)).willReturn(responseEntity);

        //when
        ResponseEntity<User> response = mealsController.getRecipe(1657654684, 300, 20, 3);

        //then
        then(mealsController).should().getRecipe(1657654684, 300, 20, 3);
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isEqualTo(user);
    }


    @Test
    @DisplayName("Can`t Get Meal There Is Wrong Identifier")
    void should_Get_Wrong_Identifier() throws Exception {
        //given
        given(mealsController.getRecipe(0, 300, 20, 1)).willThrow(ResponseStatusException.class);
        //when
        //then
        assertThrows(ResponseStatusException.class, () -> mealsController.getRecipe(0, 300, 20, 1));
    }

    @Test
    @DisplayName("Can`t Get Four Meal")
    void should_Not_Get_Four_Meal() throws Exception {
        //given
        given(mealsController.getRecipe(1657654684, 300, 20, 4)).willThrow(ResponseStatusException.class);
        //when
        //then
        assertThrows(ResponseStatusException.class, () -> mealsController.getRecipe(1657654684, 300, 20, 4));
    }


    @Test
    @DisplayName("Can`t Get Zero Meal")
    void should_Not_Get_Zero_Meal() throws Exception {
        //given
        given(mealsController.getRecipe(1657654684, 300, 20, 0)).willThrow(ResponseStatusException.class);
        //when
        //then
        assertThrows(ResponseStatusException.class, () -> mealsController.getRecipe(1657654684, 300, 20, 0));
    }

    @Test
    @DisplayName("Can`t Get One Meal With Less Than 300 KcalL")
    void should_Not_Get_One_Meal_Less_Than_300_Kcal() throws Exception {
        //given
        given(mealsController.getRecipe(1657654684, 299, 20, 1)).willThrow(ResponseStatusException.class);
        //when
        //then
        assertThrows(ResponseStatusException.class, () -> mealsController.getRecipe(1657654684, 299, 20, 1));
    }

    @Test
    @DisplayName("Can`t Get One Meal With More Than 900 Kcal")
    void should_Not_Get_One_Meal_Less_Than_900_Kcal() throws Exception {
        //given
        given(mealsController.getRecipe(1657654684, 901, 20, 1)).willThrow(ResponseStatusException.class);
        //when
        //then
        assertThrows(ResponseStatusException.class, () -> mealsController.getRecipe(1657654684, 901, 20, 1));
    }

    @Test
    @DisplayName("Can`t Get One Meal With Less Than 20 minutes")
    void should_Not_Get_One_Meal_Less_Than_20_Min() throws Exception {
        //given
        given(mealsController.getRecipe(1657654684, 500, 19, 1)).willThrow(ResponseStatusException.class);
        //when
        //then
        assertThrows(ResponseStatusException.class, () -> mealsController.getRecipe(1657654684, 500, 19, 1));
    }
}
