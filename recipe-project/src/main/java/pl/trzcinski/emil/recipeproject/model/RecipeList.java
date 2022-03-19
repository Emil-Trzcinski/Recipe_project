package pl.trzcinski.emil.recipeproject.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)

@Data
@Component

public class RecipeList {

    private List<Recipe> results;
}




