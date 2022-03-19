package pl.trzcinski.emil.recipeproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name  = "Section")
public class Section {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToMany
    @JsonProperty("components")
    private List<Component> components = null;

    @Transient
    @JsonProperty("name")
    private Object name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Section{" +
                "\n components=" + components +
                ",\n name=" + name +
                '}';
    }
}
