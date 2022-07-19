package pl.trzcinski.emil.recipeproject.utility.builders;

import pl.trzcinski.emil.recipeproject.model.Component;
import pl.trzcinski.emil.recipeproject.model.Recipe;
import pl.trzcinski.emil.recipeproject.model.Section;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SectionBuilder {

    private Long sectionId;

    private List<Component> components = new ArrayList<>();

    private Recipe recipe;

    public SectionBuilder withDefaultSection() {
        this.sectionId = withDefaultSectionId().sectionId;
        this.components = withDefaultComponentList().components;
        return this;
    }

    public SectionBuilder withDefaultSectionId() {
        this.sectionId = 1L;
        return this;
    }

    public SectionBuilder withSectionId(Long sectionId) {
        this.sectionId = sectionId;
        return this;
    }

    public SectionBuilder withDefaultComponentList() {
        Component component = new ComponentBuilder().withDefaultComponent().build();
        Component secComponent = new ComponentBuilder().withComponent_id(5L).withRawText("Text").build();
        this.components = new ArrayList<>(Arrays.asList(component, secComponent));
        return this;
    }

    public SectionBuilder withComponentList(List<Component> components) {
        this.components = components;
        return this;
    }

    public SectionBuilder withRecipe(Recipe recipe) {
        this.recipe = recipe;
        return this;
    }

    public Section build() {
        return new Section(sectionId, components, recipe);
    }

}
