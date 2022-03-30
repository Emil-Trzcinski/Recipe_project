package pl.trzcinski.emil.recipeproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Instruction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "instruction_id")
    private Long instruction_id;

    @Column(columnDefinition = "varchar(max)")
    @JsonProperty("display_text")
    private String displayText;

    @ManyToOne//(optional = false)
    @JoinColumn(name = "id", referencedColumnName = "recipe_id")
    private Recipe recipe;

    @Override
    public String toString() {
        return "\n Instruction - " +
                "" + displayText;
    }

}
