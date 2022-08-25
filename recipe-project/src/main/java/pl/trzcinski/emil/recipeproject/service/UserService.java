package pl.trzcinski.emil.recipeproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.trzcinski.emil.recipeproject.model.User;
import pl.trzcinski.emil.recipeproject.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

/**
 * UserService user services
 */
@Slf4j
@Service
public class UserService {

    private final UserIdentifierService userIdentifierService;
    private final UserRepository userRepository;

    public UserService(UserIdentifierService userIdentifierService, UserRepository userRepository) {
        this.userIdentifierService = userIdentifierService;
        this.userRepository = userRepository;
    }

    /**
     * creates a user and adds it to the database
     *
     * @param userName user's login
     * @return user
     */
    public User createUser(String userName) {

        User user = new User(null, userName, userIdentifierService.createIdentifier(), null);
        log.info("--------User Identifier : " + user.getIdentifier());
        userRepository.save(user);
        return user;
    }

    /**
     * checks if the given user already exists in the database
     *
     * @param userName user's login
     * @return true if the user exists
     */
    public boolean userExist(String userName) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findUserByUserName(userName));

        return userOptional.isPresent();
    }

    /**
     * received user from database
     * <p>
     * if the user doesn't exist return exception
     *
     * @param identifier unique identifier
     * @return user from database
     * @throws EntityNotFoundException user doesn't exist
     */
    public User getUser(int identifier) {

        Optional<User> userOptional = Optional.ofNullable(userRepository.findUserByIdentifier(identifier));

        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("user doesn't exist");
        }

        return userOptional.get();
    }
}
