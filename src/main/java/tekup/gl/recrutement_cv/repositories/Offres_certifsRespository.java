package tekup.gl.recrutement_cv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tekup.gl.recrutement_cv.entities.offres_certifs;
import tekup.gl.recrutement_cv.entities.offres_skills;

public interface Offres_certifsRespository extends JpaRepository<offres_certifs,Long> {

}
