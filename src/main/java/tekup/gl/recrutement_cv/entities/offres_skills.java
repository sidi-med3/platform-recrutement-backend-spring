package tekup.gl.recrutement_cv.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor

public class offres_skills {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)

    private  Long id_offre_skill;
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Offre offre;
    @ManyToOne
    private Skills skills;
}
