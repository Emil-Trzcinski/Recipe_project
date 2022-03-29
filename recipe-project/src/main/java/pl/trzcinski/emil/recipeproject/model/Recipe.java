package pl.trzcinski.emil.recipeproject.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)

@Data
@Component
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @OneToMany
    @Cascade(CascadeType.ALL)
    @JsonProperty("instructions")
    private List<Instruction> instructions = null;

    @Transient
    @JsonProperty("total_time_minutes")
    private Integer totalTimeMinutes;

    @Transient
    @JsonProperty("prep_time_minutes")
    private Integer prepTimeMinutes;

    @Transient
    @JsonProperty("cook_time_minutes")
    private Integer cookTimeMinutes;

    @OneToMany(mappedBy = "recipe")
    @Cascade(CascadeType.SAVE_UPDATE)
    @JsonProperty("sections")
    private List<Section> sections = null;

    @JsonProperty("thumbnail_url")
    private String thumbnailUrl;

    @JsonProperty("num_servings")
    private Integer numServings;

    @OneToMany
    @Cascade({ CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.PERSIST})
    @JsonProperty("tags")
    private List<Tag> tags = null;

    public Long getTableId() {
        return recipeId;
    }

    public void setTableId(Long recipeId) {
        this.recipeId = recipeId;
    }

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