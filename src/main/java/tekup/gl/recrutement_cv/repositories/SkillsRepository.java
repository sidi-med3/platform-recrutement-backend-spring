package tekup.gl.recrutement_cv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tekup.gl.recrutement_cv.entities.Skills;

import java.util.ArrayList;
import java.util.List;

public interface SkillsRepository extends JpaRepository<Skills,String> {
}
