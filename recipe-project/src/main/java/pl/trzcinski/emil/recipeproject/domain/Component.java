package pl.trzcinski.emil.recipeproject.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class Component {

    @JsonProperty("measurements")
    private List<Measurement> measurements = null;
    @JsonProperty("raw_text")
    private String rawText;

    @Override
    public String toString() {
        return "Component{" +
                "measurements=" + measurements +
                "\n, składniki='" + rawText + '\'' +
                '}';
    }
}
