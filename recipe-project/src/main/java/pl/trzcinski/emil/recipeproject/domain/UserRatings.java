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
    "count_positive",
    "count_negative",
    "score"
})

@Generated("jsonschema2pojo")
public class UserRatings {

    @JsonProperty("count_positive")
    private Integer countPositive;
    @JsonProperty("count_negative")
    private Integer countNegative;
    @JsonProperty("score")
    private Object score;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("count_positive")
    public Integer getCountPositive() {
        return countPositive;
    }

    @JsonProperty("count_positive")
    public void setCountPositive(Integer countPositive) {
        this.countPositive = countPositive;
    }

    public UserRatings withCountPositive(Integer countPositive) {
        this.countPositive = countPositive;
        return this;
    }

    @JsonProperty("count_negative")
    public Integer getCountNegative() {
        return countNegative;
    }

    @JsonProperty("count_negative")
    public void setCountNegative(Integer countNegative) {
        this.countNegative = countNegative;
    }

    public UserRatings withCountNegative(Integer countNegative) {
        this.countNegative = countNegative;
        return this;
    }

    @JsonProperty("score")
    public Object getScore() {
        return score;
    }

    @JsonProperty("score")
    public void setScore(Object score) {
        this.score = score;
    }

    public UserRatings withScore(Object score) {
        this.score = score;
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

    public UserRatings withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
