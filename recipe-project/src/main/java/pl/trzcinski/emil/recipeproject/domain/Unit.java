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
    "name",
    "abbreviation",
    "display_singular",
    "display_plural",
    "system"
})

@Generated("jsonschema2pojo")
public class Unit {

    @JsonProperty("name")
    private String name;
    @JsonProperty("abbreviation")
    private String abbreviation;
    @JsonProperty("display_singular")
    private String displaySingular;
    @JsonProperty("display_plural")
    private String displayPlural;
    @JsonProperty("system")
    private String system;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Unit withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("abbreviation")
    public String getAbbreviation() {
        return abbreviation;
    }

    @JsonProperty("abbreviation")
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public Unit withAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
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

    public Unit withDisplaySingular(String displaySingular) {
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

    public Unit withDisplayPlural(String displayPlural) {
        this.displayPlural = displayPlural;
        return this;
    }

    @JsonProperty("system")
    public String getSystem() {
        return system;
    }

    @JsonProperty("system")
    public void setSystem(String system) {
        this.system = system;
    }

    public Unit withSystem(String system) {
        this.system = system;
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

    public Unit withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
