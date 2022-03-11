
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
    "end_time",
    "temperature",
    "appliance",
    "id",
    "display_text",
    "position",
    "start_time"
})
@Generated("jsonschema2pojo")
public class Instruction {

    @JsonProperty("end_time")
    private Integer endTime;
    @JsonProperty("temperature")
    private Object temperature;
    @JsonProperty("appliance")
    private Object appliance;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("display_text")
    private String displayText;
    @JsonProperty("position")
    private Integer position;
    @JsonProperty("start_time")
    private Integer startTime;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("end_time")
    public Integer getEndTime() {
        return endTime;
    }

    @JsonProperty("end_time")
    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public Instruction withEndTime(Integer endTime) {
        this.endTime = endTime;
        return this;
    }

    @JsonProperty("temperature")
    public Object getTemperature() {
        return temperature;
    }

    @JsonProperty("temperature")
    public void setTemperature(Object temperature) {
        this.temperature = temperature;
    }

    public Instruction withTemperature(Object temperature) {
        this.temperature = temperature;
        return this;
    }

    @JsonProperty("appliance")
    public Object getAppliance() {
        return appliance;
    }

    @JsonProperty("appliance")
    public void setAppliance(Object appliance) {
        this.appliance = appliance;
    }

    public Instruction withAppliance(Object appliance) {
        this.appliance = appliance;
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

    public Instruction withId(Integer id) {
        this.id = id;
        return this;
    }

    @JsonProperty("display_text")
    public String getDisplayText() {
        return displayText;
    }

    @JsonProperty("display_text")
    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public Instruction withDisplayText(String displayText) {
        this.displayText = displayText;
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

    public Instruction withPosition(Integer position) {
        this.position = position;
        return this;
    }

    @JsonProperty("start_time")
    public Integer getStartTime() {
        return startTime;
    }

    @JsonProperty("start_time")
    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Instruction withStartTime(Integer startTime) {
        this.startTime = startTime;
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

    public Instruction withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
