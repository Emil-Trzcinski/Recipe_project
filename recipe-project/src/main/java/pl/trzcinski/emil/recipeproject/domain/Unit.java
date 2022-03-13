package pl.trzcinski.emil.recipeproject.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Unit {

    @JsonProperty("name")
    private String name;
    @JsonProperty("system")
    private String system;
}
