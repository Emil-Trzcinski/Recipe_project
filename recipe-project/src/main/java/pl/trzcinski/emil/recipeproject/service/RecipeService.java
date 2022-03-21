package pl.trzcinski.emil.recipeproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.trzcinski.emil.recipeproject.model.Recipe;
import pl.trzcinski.emil.recipeproject.model.RecipeList;
import pl.trzcinski.emil.recipeproject.repository.RecipeRepository;

import java.util.Optional;

import static pl.trzcinski.emil.recipeproject.utility.RecipeListFilter.listFiltering;

@Slf4j
@Service
public class RecipeService {

    private RecipeList recipeList;
    private final RecipeListMapperService recipeListMapperService;
    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeList recipeList, RecipeListMapperService recipeListMapperService, RecipeRepository recipeRepository) {
        this.recipeList = recipeList;
        this.recipeListMapperService = recipeListMapperService;
        this.recipeRepository = recipeRepository;
    }

    public RecipeList recipeListFiltering() throws Exception {
        recipeList = recipeListMapperService.getListFromResponseBody();
        return listFiltering(recipeList);
    }

    public Recipe getRecipeWithParameters(int kcal, int prepareTotalTimeMinutes) throws Exception {
        recipeList = recipeListFiltering();
        Optional<Recipe> recipeTemp;

        recipeTemp = recipeList.getResults().stream()
                .map(recipe ->
                    (recipe.getNutrition().getCalories() > 0 &&
                            recipe.getNutrition().getCalories() <= kcal &&
                            recipe.getTotalTimeMinutes() > 0  &&
                            recipe.getTotalTimeMinutes() <= prepareTotalTimeMinutes) ?
                            recipe : null)
                .reduce((recipe, recipe2) -> {
                    if (recipe2 != null) {
                        return recipe.getNutrition().getCalories() > recipe2.getNutrition().getCalories() ?
                                recipe : recipe2;
                    }
                    return recipe;
                });

        return recipeTemp.get();
    }

    public void getNameFromRecipeList(RecipeList recipeList) {
        log.info("--------RecipeList----------");

        recipeList.getResults().stream()
                .map(recipe ->
                        "\n Recipe: " + recipe.getName()
                                + "\n---- Time: " + recipe.getCookTimeMinutes()
                                + "\n---- PrepTime: " + recipe.getPrepTimeMinutes()
                                + "\n---- TotalTime: " + recipe.getTotalTimeMinutes()
                                + "\n---- Kcal: " + recipe.getNutrition().getCalories()
                                + "\n---- Id: " + recipe.getId())
                .forEach(log::info);
    }

    public void getNameFromRecipe(Recipe recipe) {
        log.info("--------Recipe----------");
        log.info(recipe.toString());
    }








    /*

    do poprawy...

    public Recipe getOneRecipe(RecipeList recipeList, int kcal) {
            List<Recipe> firstRecipe = recipeList.getResults().stream()
                    .filter(recipe -> recipe.getNutrition().getCalories() >= Math.max(kcal, kcal + 1000))
                    .toList();

            log.info("----------getOneRecipe---------");
            log.info(String.valueOf(firstRecipe));

            return firstRecipe.get(0);
    }

     */
}
