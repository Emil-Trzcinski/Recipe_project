package pl.trzcinski.emil.recipeproject.exceptions;

public class RecipeNotFoundException extends RuntimeException {

    public RecipeNotFoundException() {
        super(String.format("Can`t find expected recipe in DB"));
    }
}
