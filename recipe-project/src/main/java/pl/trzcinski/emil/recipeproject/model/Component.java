package pl.trzcinski.emil.recipeproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name =  "Component")
public class Component {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToMany
    @JsonProperty("measurements")
    private List<Measurement> measurements = null;
    @JsonProperty("raw_text")
    private String rawText;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Component{" +
                "measurements=" + measurements +
                "\n, składniki='" + rawText + '\'' +
                '}';
    }
}
