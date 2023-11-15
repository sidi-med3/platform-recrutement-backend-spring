package tekup.gl.recrutement_cv.services;

import org.springframework.web.bind.annotation.PathVariable;
import tekup.gl.recrutement_cv.entities.*;
import tekup.gl.recrutement_cv.repositories.DemandeRepository;
import tekup.gl.recrutement_cv.repositories.OffreRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface OffreService {
    Offre saveOffre(Offre offre);
    List<Offre> getAll();
    List<Offre> getOffresNonPubliés();
    Offre getOffreById(long id);
    offres_skills save_Offre_Skills(Long id, Skills skills);
    offres_certifs save_Offre_Certifs(Long id, Certif certif);
    Long getIdForOffre();
//    offres_skills save_Offre_Skills(Date date, Skills skills);
 //   offres_skills save_Offre_Skills(Offre offre, Skills skills);
    Skills save_skills(Skills skills);
    void delete_Offre(Long id);
    void dele_offre_skills(Long id);
    void dele_offre_certifs(Long id);
    offres_skills update_Offre_Skills(Long id, Skills skills);
    List<Candidat> getAllCandidat();
    Candidat saveCandidat(Candidat candidat,Long id);
    void publierOffre(Long id);
    List<Demande> getAllDemande();
    public byte[] getCVCandidateAsPDF(String id) throws IOException;
    Candidat getCandidat(String id);
    String extractCV(String url) throws IOException;
    List<String> getOffreSkillsByOffre(Long id);
     int getScoreCandidat( Long id_offre, String id_candidat);
     List<String> getSkillsCvCandidat(String url);
     void deleteCandidat(Candidat candidat);
    void deleteDemande(Demande demande);
     Demande getDemadeById(int id);
}
