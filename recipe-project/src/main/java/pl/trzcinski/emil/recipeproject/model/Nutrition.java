package pl.trzcinski.emil.recipeproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


import javax.persistence.*;

@Data
@Entity
public class Nutrition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nutrition_id", nullable = false)
    private Long id;

    @OneToOne(mappedBy = "nutrition")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Nutrition" +
                "\n calories=" + calories +
                ",\n carbohydrates=" + carbohydrates +
                ",\n fat=" + fat +
                ",\n protein=" + protein +
                ",\n sugar=" + sugar +
                ",\n fiber=" + fiber;
    }
}
