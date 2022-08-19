package pl.trzcinski.emil.recipeproject.api.integrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RegistrationControllerIntegrationTest {


    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;

    private static final String URL = "/api/v1/register?userName=";

    @Test
    @DisplayName("Successfully Create User")
    void should_Register_User() throws Exception {
        //given
        //when
        mockMvc.perform(post(URL + "Emilio"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

    }
    @Test
    @DisplayName("Can`t Register, Too Short Login")
    void should_Not_Register_user() throws Exception {
        //given
        //when
        mockMvc.perform(post(URL + "Emil"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @DisplayName("Can`t Register, Bad Request")
    void should_Not_Register() throws Exception {
        //given
        //when
        mockMvc.perform(post(URL))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }


}