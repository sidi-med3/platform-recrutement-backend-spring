package tekup.gl.recrutement_cv.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Certif {
    @Id
    private String Nom_certif;
    @OneToMany(mappedBy = "certif")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<offres_certifs> offres_certifs=new ArrayList<>();
}
