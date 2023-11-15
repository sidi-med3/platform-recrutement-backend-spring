package tekup.gl.recrutement_cv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tekup.gl.recrutement_cv.entities.Demande;

public interface DemandeRepository extends JpaRepository<Demande,Long>
{
}
