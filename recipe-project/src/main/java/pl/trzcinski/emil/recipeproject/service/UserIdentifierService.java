package pl.trzcinski.emil.recipeproject.service;

import org.springframework.stereotype.Service;
import pl.trzcinski.emil.recipeproject.model.User;
import pl.trzcinski.emil.recipeproject.repository.UserRepository;

import java.util.Optional;
import java.util.Random;

@Service
public class UserIdentifierService {

    private final UserRepository userRepository;

    public UserIdentifierService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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

    private boolean notExist(int number) {
        Optional<User> longOptional = Optional.ofNullable(userRepository.findUserByIdentifier(number));

        return longOptional.isEmpty();
    }
}
