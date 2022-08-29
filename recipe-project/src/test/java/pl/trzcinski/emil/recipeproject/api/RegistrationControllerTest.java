package pl.trzcinski.emil.recipeproject.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@ExtendWith(MockitoExtension.class)
class RegistrationControllerTest {

    @Mock
    RegistrationController registrationController;

    @Mock
    ResponseEntity<String> responseEntity;

    @Test
    @DisplayName("Successfully Create User")
    void should_Register_User() {
        //given
        responseEntity = new ResponseEntity<>("Success: 123", HttpStatus.CREATED);
        given(registrationController.register("someName")).willReturn(responseEntity);

        //when
        ResponseEntity<String> response = registrationController.register("someName");

        //then
        then(registrationController).should().register("someName");
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isEqualTo("Success: 123");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    }

    @Test
    @DisplayName("Can`t Register, Too Short Login")
    void should_Not_Register_User_Too_Short_Login() {
        //given
        responseEntity = new ResponseEntity<>("Login length is to low, minimum is 5 characters", HttpStatus.BAD_REQUEST);
        given(registrationController.register("Name")).willReturn(responseEntity);

        //when
        ResponseEntity<String> response = registrationController.register("Name");

        //then
        then(registrationController).should().register("Name");
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isEqualTo("Login length is to low, minimum is 5 characters");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("Can`t Register, User Exist")
    void should_Not_Register_User_Already_Exist() {
        //given
        responseEntity = new ResponseEntity<>("User already exist, please use oder login", HttpStatus.BAD_REQUEST);
        given(registrationController.register("someName")).willReturn(responseEntity);

        //when
        ResponseEntity<String> response = registrationController.register("someName");

        //then
        then(registrationController).should().register("someName");
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isEqualTo("User already exist, please use oder login");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}