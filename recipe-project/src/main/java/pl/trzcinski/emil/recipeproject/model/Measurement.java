package pl.trzcinski.emil.recipeproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


import javax.persistence.*;

@Data
@Entity
public class Measurement {

    //private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "measurement_id", nullable = false)
    private Long measurementId;

    @ManyToOne
    @JoinColumn(name = "component_id", nullable = false)
    private Component component;


    @JsonProperty("quantity")
    private String quantity;

    @OneToOne
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "unit_id")
    @JsonProperty("unit")
    private Unit unit;

    public Long getMeasurementId() {
        return measurementId;
    }

    public void setMeasurementId(Long id) {
        this.measurementId = id;
    }
}
