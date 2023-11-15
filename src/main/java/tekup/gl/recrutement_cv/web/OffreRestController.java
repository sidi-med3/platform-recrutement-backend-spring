package tekup.gl.recrutement_cv.web;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.LimitedDataBufferList;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tekup.gl.recrutement_cv.entities.*;
import tekup.gl.recrutement_cv.repositories.OffreRepository;
import tekup.gl.recrutement_cv.services.OffreService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

@RestController

@CrossOrigin("*")
public class OffreRestController {
    @Autowired
    private OffreService offreService;
    private String [] DSSkills={"JAVA","PYTHON","JAVASCRIPT","HTML","CSS","SPRINGBOOT","SYMFONY","DJANGO",
            "C#","C++","MYSQL","SQL","MONGODB","MAVEN","SPRING","PHP","ANGULAR","JAVA JEE"};


    @PostMapping("/offre")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public Offre saveOffre(@RequestBody Offre offre) {
        return offreService.saveOffre(offre);
    }

    @RequestMapping("/offres")
    public List<Offre> getAllOffres() {
        return offreService.getAll();
    }

    @GetMapping("/offre/{id}")
    public Offre getOff(@PathVariable Long id) {
        return offreService.getOffreById(id);
    }

    @PostMapping("/offre-skills/{id}")
    public offres_skills saveOffre_skills(@RequestBody Skills skills, @PathVariable Long id) {
        return offreService.save_Offre_Skills(id, skills);
    }

    @PostMapping("/offre-certifs/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public offres_certifs saveOffre_certifs(@RequestBody Certif certif, @PathVariable Long id) {
        return offreService.save_Offre_Certifs(id, certif);
    }

    @GetMapping("/id_offre")
    public Long getIdOffre() {
        Long id = offreService.getIdForOffre();
        System.out.println(id);
        return id;
    }

    @GetMapping("/offres_skills/{id}")
    public List<offres_skills> getOffres_skills(@PathVariable Long id) {
        Offre offre = offreService.getOffreById(id);
        return offre.getOffres_skills();
    }

    @GetMapping("/offres_certifs/{id}")
    public List<offres_certifs> getOffres_certifs(@PathVariable Long id) {
        Offre offre = offreService.getOffreById(id);
        return offre.getOffres_certifs();
    }

    @PutMapping("/update_offre/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public Offre updatOffre(@PathVariable Long id, @RequestBody Offre offre) {
        offre.setOffre_id(id);
        return offreService.saveOffre(offre);
    }

    @PutMapping("/update_skills/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<offres_skills> updateOffre_Skills(
            @PathVariable Long id,
            @RequestBody Skills skills) {

        Offre offre = offreService.getOffreById(id);
        offreService.update_Offre_Skills(id, skills);

//        List<offres_skills> skillsList=offre.getOffres_skills();
//        for (offres_skills offresSkills:skillsList){
//            offreService.save_Offre_Skills(id,offresSkills.getSkills());
//        }
        return offre.getOffres_skills();
    }

    @DeleteMapping("delete_offre/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void deleteOffre(@PathVariable Long id) {
        offreService.delete_Offre(id);
    }

    @GetMapping("/liste_candidat")
    public List<Candidat> getAllCandidat() {
        return offreService.getAllCandidat();
    }

    @PostMapping("/saveCandidat/{id}")
    public Candidat saveCandidat(@RequestParam("id_Candidat") String id_Candidat,
                                 @RequestParam("nom") String nom,
                                 @RequestParam("email") String email,
                                 @RequestParam("cv") MultipartFile cvFile,
                                 @PathVariable Long id) throws IOException {
        Candidat candidat = new Candidat();
        candidat.setId_Candidat(id_Candidat);
        candidat.setNom(nom);
        candidat.setEmail(email);
        candidat.setCv(cvFile.getBytes());
        offreService.saveCandidat(candidat, id);
        return candidat;

    }

    @PutMapping("/publierOffre/{id}")
    public void publierOffre(@PathVariable Long id) {
        offreService.publierOffre(id);
    }

    @GetMapping("/Listes_Offres_Non_Publiés")
    public List<Offre> listesOffresNonPubliés() {
        return offreService.getOffresNonPubliés();
    }

    @GetMapping("/Listes_Demandes")
    public List<Demande> listesDemandes() {
        return offreService.getAllDemande();
    }

    @DeleteMapping("/delete_candidat/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void deleteCandidat(@PathVariable String id){
        Candidat candidat = offreService.getCandidat(id);
        Demande demande = (Demande) offreService.getCandidat(id).getDemandes();
        offreService.deleteDemande(demande
        );
        offreService.deleteCandidat(candidat);
    }
    //    @GetMapping("/CV_Candidat/{id}")
//    public byte[] getCvCandiat(@PathVariable String id) throws IOException {
//        return offreService.getCVCandidateAsPDF(id);
//    }
    //Version 1 => retourn CV comme PDF
    @GetMapping("/{id}/cv")
    public ResponseEntity<Resource> getCvAsPdf(@PathVariable("id") String idCandidat) {
        Candidat candidat = offreService.getCandidat(idCandidat);
        if (candidat != null && candidat.getCv() != null) {
            ByteArrayResource resource = new ByteArrayResource(candidat.getCv());
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=cv.pdf");
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(candidat.getCv().length)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    //Version 2 => return le chemin
    @GetMapping("/{id}/cv2")
    public String getCvAsPdfV2(@PathVariable("id") String idCandidat) throws IOException {
        Candidat candidat = offreService.getCandidat(idCandidat);
        if (candidat != null && candidat.getCv() != null) {
            String pdfFolder="C:\\projects-dev-spring-boot\\Recrutement_CV\\DIR_CVS";
            File pdfFolde=new File(pdfFolder);
            if (!pdfFolde.exists()){
                pdfFolde.mkdirs();
            }
            String pdfFilePath = pdfFolder + "/" + idCandidat + "_cv.pdf";
            Files.write(Paths.get(pdfFilePath),candidat.getCv());
            return pdfFilePath;}
         else {
            return null;
        }
    }

    @GetMapping("/{id}/extractCV")
    public String getCVExtarct(@PathVariable("id") String id) throws IOException {
        String url=getCvAsPdfV2(id);

        return offreService.extractCV(url);
    }

    @GetMapping("/{id}/TextCv")
        public ArrayList<String> getSkillsCV(@PathVariable String id) throws IOException {
         ArrayList<String> SkilCand=new ArrayList<>();
        String textCv = getCVExtarct(id);
        String textCvMAJ=textCv.toUpperCase();

        String[] ArrtexCvMAJ=textCvMAJ.split("[,\\s]+");
        List<String> ArrtextCvMAJList=new ArrayList<>(Arrays.asList(ArrtexCvMAJ));
                for (String keyword:DSSkills){
            if (ArrtextCvMAJList.contains(keyword)) {
                SkilCand.add(keyword);
            }
        }
        return SkilCand;
    }
//    @GetMapping("/{id_candidat}/{id_offre}/TextCv")
//    public int getScore(@PathVariable String id_candidat,@PathVariable Long id_offre ){
//        Offre offre = offreService.getOffreById(id_offre);
//        Candidat candidat= offreService.getCandidat(id_candidat);
//
//
//        return 0;
//    }
@GetMapping("/{id_offre}/SkillsOffre")
public List<String> getScore(@PathVariable Long id_offre ){
    return offreService.getOffreSkillsByOffre(id_offre);
}

@GetMapping("/{id_offre}/{id_candidat}/score")
    public int getScoreCandidat(@PathVariable Long id_offre,@PathVariable String id_candidat) throws IOException{
        int score=0;
        int counter=0,pourcent=0;
         List<String> SkillsOffres=getScore(id_offre);
         List<String> SkillsCandidat=getSkillsCV(id_candidat);
         int len = SkillsOffres.size();
         for (String skills:SkillsOffres){
             if (SkillsCandidat.contains(skills)){
                 score+=10;
                 counter++;
             }
         }
         pourcent=(score*counter)/len;

        return pourcent;
}
    @GetMapping("/{id_offre}/{id_candidat}/status")
    public ResponseEntity<Map<String , String>> getStatus(@PathVariable Long id_offre, @PathVariable String id_candidat) throws IOException{
        int decit=getScoreCandidat(id_offre,id_candidat);
        Map<String,String> response=new HashMap<>();
        if (decit>=50){
             response.put("status","Accepté");
        }
        else {
            response.put("status","Refusé");
        }
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id_offre}/demandes")
    public List<Demande> getDemandeByOffre(@PathVariable long id_offre){
        List<Demande> demandeList_By_Offres=new ArrayList<>();
        Offre offre = offreService.getOffreById(id_offre);
        List<Demande> demandeList = offreService.getAllDemande();
        for (Demande demande:demandeList){
            if (demande.getOffre().getOffre_id()==id_offre){
                demandeList_By_Offres.add(demande);
            }
        }
        return  demandeList_By_Offres;
    }
    @GetMapping("/{id_offre}/nombre_demandes")
    public int getNombreDemandes(@PathVariable long id_offre){
        List<Demande> demandeList_By_Offres=getDemandeByOffre(id_offre);
        return demandeList_By_Offres.size();

    }
}
