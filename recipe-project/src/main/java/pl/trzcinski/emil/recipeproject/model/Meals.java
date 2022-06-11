package pl.trzcinski.emil.recipeproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreType
@Component
@Entity
public class Meals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meals_id", nullable = false)
    private Long id;

//    @OneToMany
//    @Cascade(CascadeType.ALL)
//    @JoinColumn(name = "meals_id")


    @ManyToMany
    @Cascade(CascadeType.ALL)
    @JoinTable(name = "meals_recipe",
        joinColumns = @JoinColumn(name = "meals_id"), inverseJoinColumns = @JoinColumn(name = "recipe_id"))
    private Set<Recipe> recipeSet = new HashSet<>();

    private int totalKcalOfMeals;
    private int sumOfCookTotalTime;

    @Transient
    private Map<String, String> componentsMap; //zmienic na mape

}
