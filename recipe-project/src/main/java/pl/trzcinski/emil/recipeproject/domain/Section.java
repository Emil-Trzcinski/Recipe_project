
package pl.trzcinski.emil.recipeproject.domain;

import java.util.HashMap;
import java.util.List;
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
    "components",
    "name",
    "position"
})
@Generated("jsonschema2pojo")
public class Section {

    @JsonProperty("components")
    private List<Component> components = null;
    @JsonProperty("name")
    private Object name;
    @JsonProperty("position")
    private Integer position;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("components")
    public List<Component> getComponents() {
        return components;
    }

    @JsonProperty("components")
    public void setComponents(List<Component> components) {
        this.components = components;
    }

    public Section withComponents(List<Component> components) {
        this.components = components;
        return this;
    }

    @JsonProperty("name")
    public Object getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(Object name) {
        this.name = name;
    }

    public Section withName(Object name) {
        this.name = name;
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

    public Section withPosition(Integer position) {
        this.position = position;
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

    public Section withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
