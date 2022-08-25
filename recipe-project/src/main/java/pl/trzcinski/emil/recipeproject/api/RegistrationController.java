package pl.trzcinski.emil.recipeproject.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.trzcinski.emil.recipeproject.model.User;
import pl.trzcinski.emil.recipeproject.service.UserService;

/**
 * RegistrationController process user registrations
 */
@Slf4j
@EnableCaching
@RestController
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    /**
     * receives a request to add a user to the service
     * <p>
     * if the username is too short it returns and HttpStatus.BAD_REQUEST
     * <p>
     * if the user already exists, it returns the information and HttpStatus.BAD_REQUEST
     * @param userName user login
     * @return returns a unique user Identifier and the status has been created
     */
    @PostMapping("/api/v1/register")
    public ResponseEntity<String> register(@RequestParam String userName) {
        log.info("---------------REGISTERING---------------");

        if (userName.length() <= 4) {
            return new ResponseEntity<>("Login length is to low, minimum is 5 characters", HttpStatus.BAD_REQUEST);
        }

        if (userService.userExist(userName)) {
            return new ResponseEntity<>("User already exist, please use other login", HttpStatus.BAD_REQUEST);
        }

        User user = userService.createUser(userName);

        return new ResponseEntity<>("Successful registered, your Identifier is : " + user.getIdentifier(), HttpStatus.CREATED);
    }
}
