package pl.trzcinski.emil.recipeproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "section_id", nullable = false)
    private Long sectionId;

    @OneToMany(mappedBy = "section")
    @Cascade(CascadeType.SAVE_UPDATE)
    @JsonProperty("components")
    private List<Component> components = null;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @Transient
    @JsonProperty("name")
    private Object name;

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long id) {
        this.sectionId = id;
    }

    @Override
    public String toString() {
        return "Section{" +
                "\n components=" + components +
                ",\n name=" + name +
                '}';
    }
}
