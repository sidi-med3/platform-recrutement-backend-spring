package tekup.gl.recrutement_cv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;
import tekup.gl.recrutement_cv.entities.Offre;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface OffreRepository extends JpaRepository<Offre,Long> {

   // List<Offre> findByDate_offre(Date date_offre);

}
