package pl.trzcinski.emil.recipeproject.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Instruction {

    @JsonProperty("display_text")
    private String displayText;

    @Override
    public String toString() {
        return "\n Instruction - " +
                "" + displayText;
    }
}
