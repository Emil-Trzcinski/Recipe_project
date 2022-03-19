package pl.trzcinski.emil.recipeproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity(name = "Unit")
public class Unit {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonProperty("name")
    private String name;
    @JsonProperty("system")
    private String system;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
