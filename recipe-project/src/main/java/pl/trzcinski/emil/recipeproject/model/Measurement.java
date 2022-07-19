package pl.trzcinski.emil.recipeproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "measurement_id", nullable = false)
    private Long measurementId;

    @ManyToOne
    @JoinColumn(name = "component_id", referencedColumnName = "component_id")
    @JsonIgnore
    private Component component;

    @JsonProperty("quantity")
    private String quantity;

    @OneToOne
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "unit_id")
    @JsonProperty("unit")
    private Unit unit;

}
