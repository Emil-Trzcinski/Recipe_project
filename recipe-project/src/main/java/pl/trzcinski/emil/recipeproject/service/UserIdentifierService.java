package pl.trzcinski.emil.recipeproject.service;

import org.springframework.stereotype.Service;
import pl.trzcinski.emil.recipeproject.model.User;
import pl.trzcinski.emil.recipeproject.repository.UserRepository;

import java.util.Optional;
import java.util.Random;

/**
 * UserIdentifierService deals with generating random identifiers for registering users
 */
@Service
public class UserIdentifierService {

    private final UserRepository userRepository;

    public UserIdentifierService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * creates a pseudo-random identifier in the range 1 to 2_147_483_647, inclusive
     * <p>
     * checks if a given identifier already exists in the database, if it exists generates a new one
     * until a unique identifier is obtained
     * @return identifier
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
     * checks if the user with the given identifier does not exist in the database
     * @param number identifier
     * @return true if the user with the given identifier does not exist in the database
     */
    private boolean notExist(int number) {
        Optional<User> longOptional = Optional.ofNullable(userRepository.findUserByIdentifier(number));

        return longOptional.isEmpty();
    }
}
