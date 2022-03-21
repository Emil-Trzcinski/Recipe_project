package pl.trzcinski.emil.recipeproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.trzcinski.emil.recipeproject.model.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

}
