package pl.trzcinski.emil.recipeproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Long nutritionId;

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

    @OneToOne(mappedBy = "nutrition")
    @JsonIgnore
    private Recipe recipe;
}
