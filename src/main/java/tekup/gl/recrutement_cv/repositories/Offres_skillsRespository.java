package tekup.gl.recrutement_cv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tekup.gl.recrutement_cv.entities.Offre;
import tekup.gl.recrutement_cv.entities.Skills;
import tekup.gl.recrutement_cv.entities.offres_skills;

import java.util.List;

public interface Offres_skillsRespository extends JpaRepository<offres_skills,Long> {

}
