package pl.trzcinski.emil.recipeproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Component {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "component_id")
    private Long component_id;

    @OneToMany
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "component_id")
    @JsonProperty("measurements")
    private Collection<Measurement> measurements = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "section_id", referencedColumnName = "section_id")
    @JsonIgnore
    private Section section;

    @JsonProperty("raw_text")
    private String rawText;

    @Override
    public String toString() {
        return " \n Component{" +
                "\n measurements=" + measurements + " składnik ='" + rawText + '\'' +
                '}';
    }
}
