package pl.trzcinski.emil.recipeproject.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)

@Data
@Component
@Entity(name = "Recipies")
public class Recipe {

    @Id
    @GeneratedValue
    @Column(name = "table_id", nullable = false)
    private Long tableId;

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @ManyToMany
    @JsonProperty("tags")
    private List<Tag> tags = null;

    @ManyToOne
    @JoinColumn(name = "nutrition_id")
    @JsonProperty("nutrition")
    private Nutrition nutrition;
    @JsonProperty("instructions")

    @ManyToMany
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

    @JsonProperty("thumbnail_url")
    private String thumbnailUrl;

    @JsonProperty("num_servings")
    private Integer numServings;


    @ManyToMany
    @JsonProperty("sections")
    private List<Section> sections = null;



    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    @Override
    public String toString() {
        return "RecipeeeBoom {" +
                "\n id=" + id +
                ",\n name='" + name + '\'' +
                ",\n nutrition=" + nutrition +
                ",\n totalTimeMinutes=" + totalTimeMinutes +
                ",\n prepTimeMinutes=" + prepTimeMinutes +
                ",\n cookTimeMinutes=" + cookTimeMinutes +
                ",\n instructions=" + instructions +
                "\n ------------------------------------------" +
                ",\n sections=" + sections +
                "\n ------------------------------------------" +
                ",\n numServings=" + numServings +
                ",\n thumbnailUrl='" + thumbnailUrl +
                '}';
    }
}