package pl.trzcinski.emil.recipeproject.utility.builders;

import pl.trzcinski.emil.recipeproject.model.Recipe;
import pl.trzcinski.emil.recipeproject.model.Tag;

public class TagBuilder {

    private Long idTag;

    private String name;

    private Recipe recipe;

    public TagBuilder withDefaultTag() {
        this.idTag = withDefaultId().idTag;
        this.name = withDefaultName().name;
        return this;
    }

    public TagBuilder withDefaultId() {
        this.idTag = 1L;
        return this;
    }

    public TagBuilder withIdTag(Long idTag) {
        this.idTag = idTag;
        return this;
    }

    public TagBuilder withDefaultName() {
        this.name = "default";
        return this;
    }

    public TagBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public TagBuilder withRecipe(Recipe recipe) {
        this.recipe = recipe;
        return this;
    }

    public Tag build() {
        return new Tag(idTag, name, recipe);
    }


}
