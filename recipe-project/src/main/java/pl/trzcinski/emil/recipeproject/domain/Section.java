package pl.trzcinski.emil.recipeproject.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class Section {
    @JsonProperty("components")
    private List<Component> components = null;
    @JsonProperty("name")
    private Object name;

    @Override
    public String toString() {
        return "Section{" +
                "\n components=" + components +
                ",\n name=" + name +
                '}';
    }
}
