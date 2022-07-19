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
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id", nullable = false)
    @JsonIgnore
    private Long ingredient_id;

    @JsonProperty("name")
    private String name;

    @OneToOne(mappedBy = "ingredient")
    @JsonIgnore
    private Component component;
}
