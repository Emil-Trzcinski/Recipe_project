package pl.trzcinski.emil.recipeproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity(name = "Instruction")
public class Instruction {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonProperty("display_text")
    private String displayText;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "\n Instruction - " +
                "" + displayText;
    }
}
