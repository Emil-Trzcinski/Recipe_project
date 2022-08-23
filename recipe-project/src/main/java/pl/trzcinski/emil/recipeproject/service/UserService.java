package pl.trzcinski.emil.recipeproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.trzcinski.emil.recipeproject.model.User;
import pl.trzcinski.emil.recipeproject.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

/**
 * UserService zajmuje się obługą użytkowników
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
     * tworzy użytkownika i dodaje go do bazy danych
     * @param userName login użytkownia
     * @return użytkownika
     */
    public User createUser(String userName) {

        User user = new User(null, userName, userIdentifierService.createIdentifier(), null);
        log.info("--------User Identifier : " + user.getIdentifier());
        userRepository.save(user);
        return user;
    }

    /**
     * sprawdza czy dany użytkownik istnieje już w bazie danych
     * @param userName login użytkownika
     * @return true jeżlei użytkownik istnieje
     */
    public boolean userExist(String userName) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findUserByUserName(userName));

        return userOptional.isPresent();
    }

    /**
     * pobiera użytkownika z bazdy danych*
     * <p>
     * jeżlei użytkownik nie został zleziony zwraca wyjątek
     * @param identifier unikalny identyfikator
     * @return użytkownika z bazy danych
     * @throws  EntityNotFoundException użytkownik nie został znaleziony
     */
    public User getUser(int identifier) {

        Optional<User> userOptional = Optional.ofNullable(userRepository.findUserByIdentifier(identifier));

        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("User don`t exist");
        }

        return userOptional.get();
    }
}
