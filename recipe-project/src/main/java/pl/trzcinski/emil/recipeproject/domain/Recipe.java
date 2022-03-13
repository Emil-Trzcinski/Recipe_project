package pl.trzcinski.emil.recipeproject.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)

@Data
@Component
public class Recipe {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("nutrition")
    private Nutrition nutrition;
    @JsonProperty("name")
    private String name;
    @JsonProperty("instructions")
    private List<Instruction> instructions = null;
    @JsonProperty("total_time_minutes")
    private Object totalTimeMinutes;
    @JsonProperty("prep_time_minutes")
    private Object prepTimeMinutes;
    @JsonProperty("cook_time_minutes")
    private Object cookTimeMinutes;
    @JsonProperty("thumbnail_url")
    private String thumbnailUrl;
    @JsonProperty("num_servings")
    private Integer numServings;

    @JsonProperty("sections")
    private List<Section> sections = null;

    @Override
    public String toString() {
        return "RecipeeeBoom{" +
                "\n id=" + id +
                ",\n nutrition=" + nutrition +
                ",\n name='" + name + '\'' +
                ",\n instructions=" + instructions +
                ",\n totalTimeMinutes=" + totalTimeMinutes +
                ",\n prepTimeMinutes=" + prepTimeMinutes +
                ",\n cookTimeMinutes=" + cookTimeMinutes +
                ",\n thumbnailUrl='" + thumbnailUrl + '\'' +
                ",\n numServings=" + numServings +
                "\n ------------------------------------------" +
                ",\n sections=" + sections +
                "\n ------------------------------------------" +
                '}';
    }
}