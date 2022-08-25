package pl.trzcinski.emil.recipeproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
@Entity
@Builder
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id", nullable = false)
    private Long recipeId;

    @JsonProperty("name")
    private String name;

    @OneToOne
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "nutrition_id")
    @JsonProperty("nutrition")
    private Nutrition nutrition;

    @OneToMany
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "recipe_id")
    @JsonProperty("instructions")
    private List<Instruction> instructions;

    @JsonProperty("total_time_minutes")
    private Integer totalTimeMinutes;

    @JsonProperty("prep_time_minutes")
    private Integer prepTimeMinutes;

    @JsonProperty("cook_time_minutes")
    private Integer cookTimeMinutes;

    @OneToMany
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "recipe_id")
    @JsonProperty("sections")
    private List<Section> sections;

    @JsonProperty("thumbnail_url")
    private String thumbnailUrl;

    @JsonProperty("num_servings")
    private Integer numServings;

    @OneToMany
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "recipe_id")
    @JsonProperty("tags")
    private List<Tag> tags;

    @JsonIgnore
    @ManyToMany(mappedBy = "recipeSet")
    private Set<Meals> meals;
}