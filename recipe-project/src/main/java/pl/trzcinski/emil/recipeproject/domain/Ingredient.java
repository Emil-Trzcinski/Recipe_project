
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

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "display_singular",
    "display_plural",
    "created_at",
    "updated_at"
})
@Generated("jsonschema2pojo")
public class Ingredient {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("display_singular")
    private String displaySingular;
    @JsonProperty("display_plural")
    private String displayPlural;
    @JsonProperty("created_at")
    private Integer createdAt;
    @JsonProperty("updated_at")
    private Integer updatedAt;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    public Ingredient withId(Integer id) {
        this.id = id;
        return this;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Ingredient withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("display_singular")
    public String getDisplaySingular() {
        return displaySingular;
    }

    @JsonProperty("display_singular")
    public void setDisplaySingular(String displaySingular) {
        this.displaySingular = displaySingular;
    }

    public Ingredient withDisplaySingular(String displaySingular) {
        this.displaySingular = displaySingular;
        return this;
    }

    @JsonProperty("display_plural")
    public String getDisplayPlural() {
        return displayPlural;
    }

    @JsonProperty("display_plural")
    public void setDisplayPlural(String displayPlural) {
        this.displayPlural = displayPlural;
    }

    public Ingredient withDisplayPlural(String displayPlural) {
        this.displayPlural = displayPlural;
        return this;
    }

    @JsonProperty("created_at")
    public Integer getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }

    public Ingredient withCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    @JsonProperty("updated_at")
    public Integer getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("updated_at")
    public void setUpdatedAt(Integer updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Ingredient withUpdatedAt(Integer updatedAt) {
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

    public Ingredient withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
