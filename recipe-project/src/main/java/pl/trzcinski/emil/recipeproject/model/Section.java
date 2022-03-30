package pl.trzcinski.emil.recipeproject.model;

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
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "section_id", nullable = false)
    private Long sectionId;

    @OneToMany(mappedBy = "section")
    @Cascade(CascadeType.ALL)
    @JsonProperty("components")
    private Collection<Component> components = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id", referencedColumnName = "recipe_id")
    private Recipe recipe;

    @Transient
    @JsonProperty("name")
    private Object name;

    @Override
    public String toString() {
        return "Section{" +
                ", components=" + components +
                ", name=" + name +
                '}';
    }
}
