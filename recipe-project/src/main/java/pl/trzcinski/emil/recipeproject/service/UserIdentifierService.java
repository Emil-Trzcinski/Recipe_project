package pl.trzcinski.emil.recipeproject.service;

import org.springframework.stereotype.Service;
import pl.trzcinski.emil.recipeproject.model.User;
import pl.trzcinski.emil.recipeproject.repository.UserRepository;

import java.util.Optional;
import java.util.Random;

/**
 * UserIdentifierService zajmuje sie generowaniem pesudolosowych identyfikatorow dla rejestrujacych sie uzytkownikow
 */
@Service
public class UserIdentifierService {

    private final UserRepository userRepository;

    public UserIdentifierService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * tworzy pseudolosowy identyfikator w zakresie od 1 do 2_147_483_647 włącznie
     * <p>
     * kazdoroazowo sprawdza czy dany identyfikator nie istnieje juz w bazie danych, przypadku istanienia genereuje nowy
     * aż do uzyskania unikalnego identyfikatora
     * @return identyfikator
     */
    public int createIdentifier() {
        Random random = new Random();
        int upper = Integer.MAX_VALUE - 1;
        int lower = 1;

        int result;

        do {
            result = random.nextInt(upper - lower + 1) + lower;

        } while (!notExist(result));

        return result;
    }

    /**
     * sprawdza czy użytkownik o danym identyfikatorze nie istnieje w bazie danych
     * @param number identyfikator
     * @return true jeżlie użytkownik o danym identyfikatorze nie istnieje w bazie danych
     */
    private boolean notExist(int number) {
        Optional<User> longOptional = Optional.ofNullable(userRepository.findUserByIdentifier(number));

        return longOptional.isEmpty();
    }
}
