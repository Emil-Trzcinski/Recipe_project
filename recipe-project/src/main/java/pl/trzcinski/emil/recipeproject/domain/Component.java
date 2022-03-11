
package pl.trzcinski.emil.recipeproject.domain;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "extra_comment",
    "position",
    "measurements",
    "ingredient",
    "id",
    "raw_text"
})
@Generated("jsonschema2pojo")
public class Component {

    @JsonProperty("extra_comment")
    private String extraComment;
    @JsonProperty("position")
    private Integer position;
    @JsonProperty("measurements")
    private List<Measurement> measurements = null;
    @JsonProperty("ingredient")
    private Ingredient ingredient;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("raw_text")
    private String rawText;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("extra_comment")
    public String getExtraComment() {
        return extraComment;
    }

    @JsonProperty("extra_comment")
    public void setExtraComment(String extraComment) {
        this.extraComment = extraComment;
    }

    public Component withExtraComment(String extraComment) {
        this.extraComment = extraComment;
        return this;
    }

    @JsonProperty("position")
    public Integer getPosition() {
        return position;
    }

    @JsonProperty("position")
    public void setPosition(Integer position) {
        this.position = position;
    }

    public Component withPosition(Integer position) {
        this.position = position;
        return this;
    }

    @JsonProperty("measurements")
    public List<Measurement> getMeasurements() {
        return measurements;
    }

    @JsonProperty("measurements")
    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }

    public Component withMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
        return this;
    }

    @JsonProperty("ingredient")
    public Ingredient getIngredient() {
        return ingredient;
    }

    @JsonProperty("ingredient")
    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Component withIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
        return this;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    public Component withId(Integer id) {
        this.id = id;
        return this;
    }

    @JsonProperty("raw_text")
    public String getRawText() {
        return rawText;
    }

    @JsonProperty("raw_text")
    public void setRawText(String rawText) {
        this.rawText = rawText;
    }

    public Component withRawText(String rawText) {
        this.rawText = rawText;
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

    public Component withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
