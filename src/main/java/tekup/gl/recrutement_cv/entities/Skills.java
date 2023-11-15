package tekup.gl.recrutement_cv.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Skills {
    @Id
    private String nom_Skill;
    @OneToMany(mappedBy = "skills")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    List<offres_skills> offres_skills=new ArrayList<>();
}
