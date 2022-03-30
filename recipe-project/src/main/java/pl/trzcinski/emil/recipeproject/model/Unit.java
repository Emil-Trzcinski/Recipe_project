package pl.trzcinski.emil.recipeproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unit_id", nullable = false)
    private Long unitId;

    @OneToOne(mappedBy = "unit")
    private Measurement measurement;

    @JsonProperty("name")
    private String name;
    @JsonProperty("system")
    private String system;
}
