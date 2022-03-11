package pl.trzcinski.emil.recipeproject.domain;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecipeList {
    private long count;
    private List<Recipe> results;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<Recipe> getResults() {
        return results;
    }

    public void setResults(List<Recipe> results) {
        this.results = results;
    }
}
