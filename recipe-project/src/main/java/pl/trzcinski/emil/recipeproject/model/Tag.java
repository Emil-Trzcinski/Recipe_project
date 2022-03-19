
package pl.trzcinski.emil.recipeproject.model;



import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@JsonInclude(JsonInclude.Include.NON_NULL)

@Data
@Entity(name = "Tag")
public class Tag {

    @Id
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
