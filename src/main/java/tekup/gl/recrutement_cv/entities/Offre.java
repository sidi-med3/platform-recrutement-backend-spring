package tekup.gl.recrutement_cv.entities;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.engine.jdbc.Size;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Offre {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long offre_id;
    private String Title;
    private Date date_offre;

    @OneToMany(mappedBy = "offre")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Demande> demandes=new ArrayList<>();
    @OneToMany(mappedBy = "offre")
    private List<offres_certifs> offres_certifs=new ArrayList<>();
    @OneToMany(mappedBy = "offre")
    private List<offres_skills> offres_skills=new ArrayList<>();
    @Column(length = 2000)
    private String description;
    private boolean publication;
}
