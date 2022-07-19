package pl.trzcinski.emil.recipeproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.trzcinski.emil.recipeproject.model.Meals;

@Repository
@Transactional
public interface MealsRepository extends JpaRepository<Meals, Long> {

        Meals findTopByTotalKcalOfMealsLessThanEqualAndSumOfCookTotalTimeLessThanEqualAndRecipeSetSizeEquals
                (int expectedKcal, int expectedTotalTimeMinutes, int size);
}