package pl.trzcinski.emil.recipeproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    @OneToMany
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "section_id")
    @JsonProperty("components")
    private List<Component> components = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "recipe_id", referencedColumnName = "recipe_id")
    @JsonIgnore
    private Recipe recipe;

//    @Transient
//    @JsonProperty("name")
//    private Object name;

    @Override
    public String toString() {
        return "Section{" +
                ", components=" + components +
//                ", name=" + name +
                '}';
    }
}
