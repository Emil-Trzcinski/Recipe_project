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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "measurement_id")
    private Long measurementId;

    @ManyToOne
    @JoinColumn(name = "component_id")
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
