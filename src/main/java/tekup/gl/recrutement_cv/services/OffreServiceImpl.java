package tekup.gl.recrutement_cv.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import tekup.gl.recrutement_cv.entities.*;
import tekup.gl.recrutement_cv.repositories.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Transactional
@Service
@AllArgsConstructor
public class OffreServiceImpl implements OffreService{
    private OffreRepository offreRepository;
    private SkillsRepository skillsRepository;
    private Offres_skillsRespository offresSkillsRespository;
    private Offres_certifsRespository offresCertifsRespository;
    private CandidatRepository candidatRepository;
    private CertifRepository certifRepository;
    private DemandeRepository demandeRepository;


    @Override
    public Offre saveOffre(Offre offre) {
        Offre offre1= offreRepository.save(offre);
        return offre1;
    }

    @Override
    public List<Offre> getAll() {
        List<Offre> offreListAll=offreRepository.findAll();
        List<Offre> offreListPubliés = new ArrayList<>();
        for (Offre offre: offreListAll){
            if (offre.isPublication()==true){
                offreListPubliés.add(offre);
            }

        }
        return offreListPubliés;
    }


    @Override
    public List<Offre> getOffresNonPubliés() {
        List<Offre> offreListAll=offreRepository.findAll();
        List<Offre> offreListNonPubliés = new ArrayList<>();
        for (Offre offre: offreListAll){
            if (offre.isPublication()==false){
                offreListNonPubliés.add(offre);
            }

        }
        return offreListNonPubliés;
    }

    @Override
    public Offre getOffreById(long id) {
        return offreRepository.findById(id).orElse(null);
    }

    @Override
    public offres_skills save_Offre_Skills(Long id, Skills skills) {
        skillsRepository.save(skills);
        Offre offre = offreRepository.findById(id).orElse(null);
        offres_skills offresSkills= new offres_skills();
        offresSkills.setSkills(skills);
        offresSkills.setOffre(offre);
         return offresSkillsRespository.save(offresSkills);

    }

    @Override
    public offres_certifs save_Offre_Certifs(Long id, Certif certif) {
        certifRepository.save(certif);
        Offre offre = offreRepository.findById(id).orElse(null);
        offres_certifs offresCertifs=new offres_certifs();
        offresCertifs.setCertif(certif);
        offresCertifs.setOffre(offre);
        return offresCertifsRespository.save(offresCertifs);

    }


    @Override
    public Long getIdForOffre() {
        List<Offre> offreList=offreRepository.findAll();
        Long id=0L;
        for (Offre offre:offreList)
            id = offre.getOffre_id();
        return id;
    }
//    @Override
//    public offres_skills save_Offre_Skills(Date date, Skills skills) {
//        skillsRepository.save(skills);
//        List<Offre> offre = offreRepository.findByDate_offre(date);
//        Offre offre1=offre.get(0);
//        offres_skills offresSkills= new offres_skills();
//        offresSkills.setSkills(skills);
//        offresSkills.setOffre(offre1);
//        return offresSkillsRespository.save(offresSkills);

  //  }

    @Override
    public Skills save_skills(Skills skills) {
        return skillsRepository.save(skills);
    }

    @Override
    public void delete_Offre(Long id) {
        Offre offre = offreRepository.findById(id).orElse(null);
        if (offre!=null){

            List<offres_skills> offres_skillsList=offre.getOffres_skills();
            if (offres_skillsList!=null) {
                for (offres_skills offresSkills : offres_skillsList) {
                    dele_offre_skills(offresSkills.getId_offre_skill());
                }
            }
            List<offres_certifs> offresCertifsList=offre.getOffres_certifs();
            if (offres_skillsList!=null) {
                for (offres_certifs offresCertif : offresCertifsList) {
                    dele_offre_certifs(offresCertif.getId_offre_certif());
                }
            }
            offreRepository.delete(offre);
           // delete_Offre(id);
        }


    }

    @Override
    public void dele_offre_skills(Long id) {
        offres_skills offresSkills=offresSkillsRespository.findById(id).orElse(null);
        if (offresSkills!=null)
            offresSkillsRespository.delete(offresSkills);

    }

    @Override
    public void dele_offre_certifs(Long id) {
        offres_certifs offresCertifs=offresCertifsRespository.findById(id).orElse(null);
        if (offresCertifs!=null)
            offresCertifsRespository.delete(offresCertifs);
    }


//    @Override
//    public offres_skills save_Offre_Skills(Offre offre, Skills skills) {
//        String name_skills=skills.getNom_Skill();
//        Skills skills1=skillsRepository.findById(name_skills).orElse(null);
//        if (skills1==null) {
//            skillsRepository.save(skills);
//        }
//        offres_skills offres_skills=new offres_skills();
//        offres_skills.setSkills(skills);
//        offres_skills.setOffre(offre);
//        offres_skills offres_skills1=offresSkillsRespository.save(offres_skills);
//        return offres_skills1;
//    }

    @Override
    public offres_skills update_Offre_Skills(Long id, Skills skills) {
        skillsRepository.save(skills);
        Offre offre = offreRepository.findById(id).orElse(null);
        List<offres_skills> offres_skillsList=offre.getOffres_skills();
//        for (offres_skills offresSkills:offres_skillsList){
//            offresSkillsRespository.delete(offresSkills);
//        }
        offres_skills offresSkills= new offres_skills();
        offresSkills.setSkills(skills);
        offresSkills.setOffre(offre);
        return offresSkillsRespository.save(offresSkills);

    }

    @Override
    public List<Candidat> getAllCandidat() {
        return candidatRepository.findAll();
    }

    @Override
    public Candidat saveCandidat(Candidat candidat, Long id) {
        Offre offre = offreRepository.findById(id).orElse(null);
        Demande demande = new Demande();
        demande.setOffre(offre);
        demande.setCandidat(candidat);
        Candidat candidat1= candidatRepository.findById(candidat.getId_Candidat()).orElse(null);
        if (candidat1== null)
          candidatRepository.save(candidat);
        demandeRepository.save(demande);
        return candidat;
    }

    @Override
    public void publierOffre(Long id) {
        Offre offre = offreRepository.findById(id).orElse(null);
        if (offre!=null){
        offre.setPublication(true);
        offreRepository.save(offre);}
    }

    @Override
    public List<Demande> getAllDemande() {
        return demandeRepository.findAll();
    }
//    public byte[] getCVCandidateAsPDF(String id) throws IOException {
//        Candidat candidat = candidatRepository.findById(id).orElse(null);
//
//        if (candidat != null && candidat.getCv() != null) {
//            // Convertir les données binaires du CV en un document PDF
//
//            ByteArrayInputStream inputStream = new ByteArrayInputStream(candidat.getCv());
//
//            PDDocument pdfDocument = new PDDocument();
//
//            PDPage page = new PDPage();
//            pdfDocument.addPage(page);
//
//            // Écrire les données binaires dans le document PDF
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            pdfDocument.save(outputStream);
//            pdfDocument.close();
//
//            return outputStream.toByteArray();
//        }
//
//        return null;
//    }
public byte[] getCVCandidateAsPDF(String id) throws IOException {
    Candidat candidat = candidatRepository.findById(id).orElse(null);

    if (candidat != null && candidat.getCv() != null) {
        // Convertir les données binaires du CV en String
        String cvText = new String(candidat.getCv(),  StandardCharsets.UTF_8);

        // Créer un nouveau document PDF
        PDDocument pdfDocument = new PDDocument();

        // Créer une page PDF
        PDPage page = new PDPage();
        pdfDocument.addPage(page);

        // Créer un contenu de page PDPageContentStream

        PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, page);

        // Ajouter le texte au document PDF
        PDType0Font font = PDType0Font.load(pdfDocument, getClass().getResourceAsStream("to/Helvetica-Bold.ttf"));

        contentStream.setFont(font, 12); // Définir la police et la taille
        contentStream.beginText();
        contentStream.newLineAtOffset(100, 700); // Position du texte sur la page
        contentStream.showText(cvText); // Ajouter le texte du CV
        contentStream.endText();
        contentStream.close();

        // Enregistrez le document PDF dans un tableau de bytes
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        pdfDocument.save(outputStream);
        pdfDocument.close();

        return outputStream.toByteArray();
    }

    return null;
}

    @Override
    public Candidat getCandidat(String id) {
        return candidatRepository.findById(id).orElse(null);
    }

    @Override
    public String extractCV(String url) throws IOException {
        Path chemiUrl= FileSystems.getDefault().getPath(url);
        PDDocument pdDocument = PDDocument.load(new File(chemiUrl.toUri()));

        PDFTextStripper pdfTextStripper=new PDFTextStripper();
        String Text=pdfTextStripper.getText(pdDocument);
        pdDocument.close();
        return Text;

    }

    @Override
    public List<String> getOffreSkillsByOffre(Long id) {
        List<Skills> skillsList=new ArrayList<Skills>();
        List<String> nomSkillsList= new ArrayList<>();
        Offre offre = offreRepository.findById(id).orElse(null);
        if (offre!=null){
            List<offres_skills> offres_skillsList=offre.getOffres_skills();
            for (offres_skills offresSkills:offres_skillsList){
                skillsList.add(offresSkills.getSkills());
            }
            for (Skills skills1:skillsList){
                nomSkillsList.add(skills1.getNom_Skill().toUpperCase());
            }
            return nomSkillsList;
        }

        else {
            return null;
        }


    }

    @Override
    public int getScoreCandidat(Long id_offre, String id_candidat) {
        return 0;
    }

    @Override
    public List<String> getSkillsCvCandidat(String url) {

        return null;
    }

    @Override
    public void deleteCandidat(Candidat candidat) {
        candidatRepository.delete(candidat);
    }

    @Override
    public void deleteDemande(Demande demande) {
        demandeRepository.delete(demande);
    }

    @Override
    public Demande getDemadeById(int id) {
        Demande demande = null;
        List<Demande> demandeList = getAllDemande();
        for (Demande demande1 : demandeList){
            if (demande1.getId_Demande() == id){
                demande = demande1;
            }
        }
        return demande;
    }


}

