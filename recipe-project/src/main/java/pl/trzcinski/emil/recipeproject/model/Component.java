package pl.trzcinski.emil.recipeproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Component {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "component_id")
    private Long component_id;

    @OneToMany(mappedBy = "component")
    @Cascade(CascadeType.ALL)
    @JsonProperty("measurements")
    private List<Measurement> measurements = null;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    @JsonProperty("raw_text")
    private String rawText;

    public Long getComponent_id() {
        return component_id;
    }

    public void setComponent_id(Long id) {
        this.component_id = id;
    }

    @Override
    public String toString() {
        return " \n Component{" +
                "\n measurements=" + measurements + " składnik ='" + rawText + '\'' +
                '}';
    }
}
