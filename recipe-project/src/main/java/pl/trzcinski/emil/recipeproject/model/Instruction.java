package pl.trzcinski.emil.recipeproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Instruction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "instruction_id")
    private Long instruction_id;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @Column(columnDefinition = "varchar(max)")
    @JsonProperty("display_text")
    private String displayText;

    public Long getInstruction_id() {
        return instruction_id;
    }

    public void setInstruction_id(Long id) {
        this.instruction_id = id;
    }

    @Override
    public String toString() {
        return "\n Instruction - " +
                "" + displayText;
    }
}
