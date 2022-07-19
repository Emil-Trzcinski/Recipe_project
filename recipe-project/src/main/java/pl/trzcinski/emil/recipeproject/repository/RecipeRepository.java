package pl.trzcinski.emil.recipeproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.trzcinski.emil.recipeproject.model.Recipe;

import java.util.Set;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Set<Recipe> findByNutrition_CaloriesLessThanEqualAndCookTimeMinutesLessThanEqual
            (int expectedKcal, int expectedTotalTimeMinutes);

    Recipe findByName(String name);
}
