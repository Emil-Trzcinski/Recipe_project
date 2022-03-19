package pl.trzcinski.emil.recipeproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.trzcinski.emil.recipeproject.model.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

}
