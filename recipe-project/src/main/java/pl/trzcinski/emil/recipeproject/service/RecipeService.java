package pl.trzcinski.emil.recipeproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.trzcinski.emil.recipeproject.model.Recipe;
import pl.trzcinski.emil.recipeproject.model.RecipeList;
import pl.trzcinski.emil.recipeproject.repository.RecipeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Recipe getListOfRecipesWithParameters(int kcal, int prepareTotalTimeMinutes) throws Exception {
        recipeList = recipeListFiltering();
        List<Recipe> recipeTemp;

        recipeTemp = recipeList.getResults().stream()
                .filter(recipe ->
                        (recipe.getNutrition().getCalories() > 0 &&
                                recipe.getNutrition().getCalories() <= kcal &&
                                recipe.getTotalTimeMinutes() > 0  &&
                                recipe.getTotalTimeMinutes() <= prepareTotalTimeMinutes))
                .collect(Collectors.toList());

        return getRecipeFromListOfRecipesWithPara(recipeTemp);
    }

    private Recipe getRecipeFromListOfRecipesWithPara(List<Recipe> recipeTemp) {
        Optional<Recipe> optionalRecipe;
        optionalRecipe = recipeTemp.stream()
                .reduce((recipe, recipe2) -> {
                    if (recipe2 != null) {
                        return recipe.getNutrition().getCalories() > recipe2.getNutrition().getCalories() ?
                                recipe : recipe2;
                    }
                    return recipe;
                });

           Recipe recipeToSave = optionalRecipe.get();
           recipeRepository.saveAndFlush(recipeToSave);

        return optionalRecipe.get();
    }

    public void getNameFromRecipeList(RecipeList recipeList) {
        log.info("--------RecipeList----------");

        recipeList.getResults().stream()
                .map(recipe ->
                        "\n Recipe: " + recipe.getName()
                                + "\n---- Time: " + recipe.getCookTimeMinutes()
                                + "\n---- PrepTime: " + recipe.getPrepTimeMinutes()
                                + "\n---- TotalTime: " + recipe.getTotalTimeMinutes()
                                + "\n---- Kcal: " + recipe.getNutrition().getCalories())
                                //+ "\n---- Id: " + recipe.getId())
                .forEach(log::info);
    }

    public void getNameFromRecipe(Recipe recipe) {
        log.info("--------Recipe----------");
        log.info(recipe.toString());
    }
}
