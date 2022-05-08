package pl.trzcinski.emil.recipeproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "instruction_id", nullable = false)
    private Long instruction_id;

    @Column(name = "Instruction", columnDefinition = "varchar(max)")
    @JsonProperty("display_text")
    private String displayText;

    @ManyToOne
    @JoinColumn(name = "recipe_id", referencedColumnName = "recipe_id")
    @JsonIgnore
    private Recipe recipe;

    @Override
    public String toString() {
        return "\n Instruction - " +
                "" + displayText;
    }

}
