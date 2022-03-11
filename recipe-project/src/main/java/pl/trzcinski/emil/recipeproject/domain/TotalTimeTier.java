
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
    "display_tier",
    "tier"
})
@Generated("jsonschema2pojo")
public class TotalTimeTier {

    @JsonProperty("display_tier")
    private String displayTier;
    @JsonProperty("tier")
    private String tier;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("display_tier")
    public String getDisplayTier() {
        return displayTier;
    }

    @JsonProperty("display_tier")
    public void setDisplayTier(String displayTier) {
        this.displayTier = displayTier;
    }

    public TotalTimeTier withDisplayTier(String displayTier) {
        this.displayTier = displayTier;
        return this;
    }

    @JsonProperty("tier")
    public String getTier() {
        return tier;
    }

    @JsonProperty("tier")
    public void setTier(String tier) {
        this.tier = tier;
    }

    public TotalTimeTier withTier(String tier) {
        this.tier = tier;
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

    public TotalTimeTier withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
