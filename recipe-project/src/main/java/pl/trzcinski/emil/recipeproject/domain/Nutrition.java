package pl.trzcinski.emil.recipeproject.domain;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.stereotype.Component;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "calories",
    "carbohydrates",
    "fat",
    "protein",
    "sugar",
    "fiber",
    "updated_at"
})

@Generated("jsonschema2pojo")
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
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("calories")
    public Integer getCalories() {
        return calories;
    }

    @JsonProperty("calories")
    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Nutrition withCalories(Integer calories) {
        this.calories = calories;
        return this;
    }

    @JsonProperty("carbohydrates")
    public Integer getCarbohydrates() {
        return carbohydrates;
    }

    @JsonProperty("carbohydrates")
    public void setCarbohydrates(Integer carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public Nutrition withCarbohydrates(Integer carbohydrates) {
        this.carbohydrates = carbohydrates;
        return this;
    }

    @JsonProperty("fat")
    public Integer getFat() {
        return fat;
    }

    @JsonProperty("fat")
    public void setFat(Integer fat) {
        this.fat = fat;
    }

    public Nutrition withFat(Integer fat) {
        this.fat = fat;
        return this;
    }

    @JsonProperty("protein")
    public Integer getProtein() {
        return protein;
    }

    @JsonProperty("protein")
    public void setProtein(Integer protein) {
        this.protein = protein;
    }

    public Nutrition withProtein(Integer protein) {
        this.protein = protein;
        return this;
    }

    @JsonProperty("sugar")
    public Integer getSugar() {
        return sugar;
    }

    @JsonProperty("sugar")
    public void setSugar(Integer sugar) {
        this.sugar = sugar;
    }

    public Nutrition withSugar(Integer sugar) {
        this.sugar = sugar;
        return this;
    }

    @JsonProperty("fiber")
    public Integer getFiber() {
        return fiber;
    }

    @JsonProperty("fiber")
    public void setFiber(Integer fiber) {
        this.fiber = fiber;
    }

    public Nutrition withFiber(Integer fiber) {
        this.fiber = fiber;
        return this;
    }

    @JsonProperty("updated_at")
    public String getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("updated_at")
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Nutrition withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Nutrition withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        return " Nutrition { " +
                "\n calories=" + calories +
                "\n, carbohydrates=" + carbohydrates +
                "\n, fat=" + fat +
                "\n, protein=" + protein +
                "\n, sugar=" + sugar +
                "\n, fiber=" + fiber +
                "\n, updatedAt='" + updatedAt + '\'' +
                "\n, additionalProperties=" + additionalProperties +
                '}';
    }
}
