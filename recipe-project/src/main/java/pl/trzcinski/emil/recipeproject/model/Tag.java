
package pl.trzcinski.emil.recipeproject.model;



import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import javax.persistence.*;

@JsonInclude(JsonInclude.Include.NON_NULL)

@Data
@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tag", nullable = false)
    private Long idTag;

    @JsonProperty("name")
    private String name;

    public Long getIdTag() {
        return idTag;
    }

    public void setIdTag(Long idTag) {
        this.idTag = idTag;
    }
}
