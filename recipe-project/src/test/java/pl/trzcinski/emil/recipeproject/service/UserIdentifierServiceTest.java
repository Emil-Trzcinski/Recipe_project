package pl.trzcinski.emil.recipeproject.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@ExtendWith(MockitoExtension.class)
class UserIdentifierServiceTest {

    @Mock
    UserIdentifierService userIdentifierService;

    @Test
    @DisplayName("Successfully Create Identifier")
    public void should_Successfully_Create_Identifier() {
        //given
        given(userIdentifierService.createIdentifier()).willReturn(1000);

        //when
        int result =  userIdentifierService.createIdentifier();

        //then
        then(userIdentifierService).should().createIdentifier();
        assertThat(result).isEqualTo(1000);
    }

}