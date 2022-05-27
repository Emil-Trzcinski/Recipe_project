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
public class Nutrition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nutrition_id", nullable = false)
    private Long id;

    @OneToOne(mappedBy = "nutrition")
    @JsonIgnore
    private Recipe recipe;

    @JsonProperty("calories")
    private Integer calories;

    @JsonProperty("carbohydrates")
    private Integer carbohydrates;

    @JsonProperty("fat")
    private Integer fat;

    @JsonProperty("protein")
    private Integer protein;

    @JsonProperty("sugar")
    private Integer sugar;

    @JsonProperty("fiber")
    private Integer fiber;

}
