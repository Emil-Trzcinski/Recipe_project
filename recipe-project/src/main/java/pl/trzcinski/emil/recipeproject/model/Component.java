package pl.trzcinski.emil.recipeproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Component {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "component_id", nullable = false)
    private Long componentId;

    @OneToMany
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "component_id")
    @JsonProperty("measurements")
    private List<Measurement> measurements = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "section_id", referencedColumnName = "section_id")
    @JsonIgnore
    private Section section;

    @OneToOne
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "ingredient_id")
    @JsonProperty("ingredient")
    private Ingredient ingredient;

    @JsonProperty("raw_text")
    private String rawText;
}
