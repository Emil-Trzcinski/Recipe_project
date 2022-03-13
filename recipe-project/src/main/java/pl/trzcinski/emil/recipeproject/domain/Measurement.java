package pl.trzcinski.emil.recipeproject.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Measurement {

    @JsonProperty("quantity")
    private String quantity;
    @JsonProperty("unit")
    private Unit unit;
}
