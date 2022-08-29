package pl.trzcinski.emil.recipeproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.trzcinski.emil.recipeproject.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUserName(String userName);

    User findUserByIdentifier(int identifier);
}
