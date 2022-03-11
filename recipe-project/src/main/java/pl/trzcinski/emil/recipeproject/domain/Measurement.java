
package pl.trzcinski.emil.recipeproject.domain;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "quantity",
    "unit"
})
@Generated("jsonschema2pojo")
public class Measurement {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("quantity")
    private String quantity;
    @JsonProperty("unit")
    private Unit unit;
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

    public Measurement withId(Integer id) {
        this.id = id;
        return this;
    }

    @JsonProperty("quantity")
    public String getQuantity() {
        return quantity;
    }

    @JsonProperty("quantity")
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Measurement withQuantity(String quantity) {
        this.quantity = quantity;
        return this;
    }

    @JsonProperty("unit")
    public Unit getUnit() {
        return unit;
    }

    @JsonProperty("unit")
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Measurement withUnit(Unit unit) {
        this.unit = unit;
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

    public Measurement withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
