package pl.trzcinski.emil.recipeproject.utility.builders;

import pl.trzcinski.emil.recipeproject.model.*;

import java.util.ArrayList;
import java.util.List;

public class ComponentBuilder {

    private Long component_id;

    private List<Measurement> measurements = new ArrayList<>();

    private Section section;

    private Ingredient ingredient;

    private String rawText;

    public ComponentBuilder withDefaultComponent() {
        this.component_id = withDefaultComponent_id().component_id;
        this.rawText = withDefaultRawText().rawText;
        return this;
    }


    public ComponentBuilder withDefaultComponent_id() {
        this.component_id = 1L;
        return this;
    }

    public ComponentBuilder withComponent_id(Long component_id) {
        this.component_id = component_id;
        return this;
    }

    public ComponentBuilder withMeasurementsList(List<Measurement> measurements) {
        this.measurements = measurements;
        return this;
    }

    public ComponentBuilder withSection(Section section) {
        this.section = section;
        return this;
    }

    public ComponentBuilder withIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
        return this;
    }

    public ComponentBuilder withDefaultRawText() {
        this.rawText = "default";
        return this;
    }

    public ComponentBuilder withRawText(String rawText) {
        this.rawText = rawText;
        return this;
    }

    public Component build() {
        return new Component(component_id, measurements, section, ingredient, rawText);
    }

}
