package pl.trzcinski.emil.recipeproject.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import pl.trzcinski.emil.recipeproject.model.User;
import pl.trzcinski.emil.recipeproject.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;


@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserService userService;
    @Mock
    UserRepository userRepository;

    User user;

    @BeforeEach
    void setUp() {
        user = new User();

        user.setUserName("testName");
        user.setIdentifier(10);
        user.setUserId(2L);

    }

    @Test
    @DisplayName("Successfully Find User By Name")
    void should_Find_User_By_Name() {
        //given
        given(userRepository.findUserByUserName("testName")).willReturn(user);

        //when
        User resultUser = userRepository.findUserByUserName("testName");

        //then
        then(userRepository).should().findUserByUserName("testName");
        assertThat(resultUser.getUserName()).isEqualTo("testName");
    }

    @Test
    @DisplayName("Not Found User")
    void should_not_find_user() {
        //given
        given(userRepository.findUserByUserName(anyString())).willReturn(null);

        //when
        User user = userRepository.findUserByUserName(anyString());

        //then
        assertThat(user).isNull();
    }

    @Test
    @DisplayName("Successfully Find User By Identifier")
    void should_Get_User() {
        //given
        given(userService.getUser(10)).willReturn(user);

        //when
        User userResult = userService.getUser(10);

        //then
        then(userService).should().getUser(10);
        assertThat(userResult).isNotNull();
        assertThat(userResult).isEqualTo(user);
    }

    @Test
    @DisplayName("Find User Will Throw RuntimeException")
    void should_Throw_Exception_When_Try_Find_User() {
        //given
        given(userService.getUser(anyInt())).willThrow(RuntimeException.class);

        //when
        //then
        assertThrows(RuntimeException.class, () -> userService.getUser(anyInt()));
    }
}