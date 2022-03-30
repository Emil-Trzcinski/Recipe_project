package pl.trzcinski.emil.recipeproject.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@JsonInclude(JsonInclude.Include.NON_NULL)

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id", nullable = false)
    private Long recipeId;

//    @JsonProperty("id")
//    private Integer id;

    @JsonProperty("name")
    private String name;

    @OneToOne
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "nutrition_id")
    @JsonProperty("nutrition")
    private Nutrition nutrition;

    @OneToMany(mappedBy = "recipe")
    @Cascade(CascadeType.ALL)
    @JsonProperty("instructions")
    private Collection<Instruction> instructions = new ArrayList<>();

    @JsonProperty("total_time_minutes")
    private Integer totalTimeMinutes;

    @JsonProperty("prep_time_minutes")
    private Integer prepTimeMinutes;

    @JsonProperty("cook_time_minutes")
    private Integer cookTimeMinutes;

    @OneToMany(mappedBy = "recipe")
    @Cascade(CascadeType.ALL)
    @JsonProperty("sections")
    private Collection<Section> sections = new ArrayList<>();

    @JsonProperty("thumbnail_url")
    private String thumbnailUrl;

    @JsonProperty("num_servings")
    private Integer numServings;

    @OneToMany(mappedBy = "recipe")
    @Cascade(CascadeType.ALL)
    @JsonProperty("tags")
    private Collection<Tag> tags = new ArrayList<>();

    @Override
    public String toString() {
        return "RecipeeeBoom {" +
                //"\n id=" + id +
                ",\n name='" + name + '\'' +
                ",\n nutrition=" + nutrition +
                ",\n instructions=" + instructions +
                " \n ------------------------------------------" +
                ",\n totalTimeMinutes=" + totalTimeMinutes +
                ",\n prepTimeMinutes=" + prepTimeMinutes +
                ",\n cookTimeMinutes=" + cookTimeMinutes +
                " \n ------------------------------------------" +
                ",\n sections=" + sections +
                "\n ------------------------------------------" +
                ",\n thumbnailUrl='" + thumbnailUrl +
                ",\n numServings=" + numServings +
                '}';
    }

}