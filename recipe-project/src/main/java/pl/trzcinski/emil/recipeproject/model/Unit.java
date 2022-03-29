package pl.trzcinski.emil.recipeproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "unit_id", nullable = false)
    private Long unitId;

    @OneToOne(mappedBy = "unit")
    private Measurement measurement;

    @JsonProperty("name")
    private String name;
    @JsonProperty("system")
    private String system;

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long id) {
        this.unitId = id;
    }
}
