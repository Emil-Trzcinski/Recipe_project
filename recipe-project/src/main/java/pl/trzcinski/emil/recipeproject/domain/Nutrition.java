package pl.trzcinski.emil.recipeproject.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Nutrition {

    @JsonProperty("calories")
    private Integer calories;
    @JsonProperty("carbohydrates")
    private Integer carbohydrates;
    @JsonProperty("fat")
    private Integer fat;
    @JsonProperty("protein")
    private Integer protein;
    @JsonProperty("sugar")
    private Integer sugar;
    @JsonProperty("fiber")
    private Integer fiber;

    @Override
    public String toString() {
        return "Nutrition" +
                "\n calories=" + calories +
                ",\n carbohydrates=" + carbohydrates +
                ",\n fat=" + fat +
                ",\n protein=" + protein +
                ",\n sugar=" + sugar +
                ",\n fiber=" + fiber;
    }
}
