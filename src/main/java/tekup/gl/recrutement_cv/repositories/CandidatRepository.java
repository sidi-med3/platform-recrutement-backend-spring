package tekup.gl.recrutement_cv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tekup.gl.recrutement_cv.entities.Candidat;
import tekup.gl.recrutement_cv.entities.Certif;

public interface CandidatRepository extends JpaRepository<Candidat,String> {
}
