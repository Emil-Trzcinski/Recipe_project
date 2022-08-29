package pl.trzcinski.emil.recipeproject.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreType
@Component
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "user_id", nullable = false)
    private Long userId;

    private String userName;

    private int identifier;

    @ManyToMany
    @Cascade(CascadeType.ALL)
    @JoinTable(name = "user_meals",
            joinColumns = @JoinColumn(name ="user_id"), inverseJoinColumns = @JoinColumn(name = "meals_id"))
    private Set<Meals> mealsSet;
}

