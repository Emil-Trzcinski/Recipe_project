package pl.trzcinski.emil.recipeproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "Measurement")
public class Measurement {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonProperty("quantity")
    private String quantity;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    @JsonProperty("unit")
    private Unit unit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
