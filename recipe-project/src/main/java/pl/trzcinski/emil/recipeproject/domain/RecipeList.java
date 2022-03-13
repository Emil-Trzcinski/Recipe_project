package pl.trzcinski.emil.recipeproject.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class RecipeList {
    private long count;
    private List<Recipe> results;
}


