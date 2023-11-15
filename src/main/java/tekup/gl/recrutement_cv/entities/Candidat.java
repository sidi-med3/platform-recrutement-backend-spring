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
public class Candidat {
    @Id
    private String id_Candidat;
    private String nom;
    private String email;
    @Column(length = 1048576)
    @Lob
    private byte[] cv;
    @OneToMany(mappedBy = "candidat")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Demande> demandes=new ArrayList<>();
}
