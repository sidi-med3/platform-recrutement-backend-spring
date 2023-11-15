package tekup.gl.recrutement_cv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.format.annotation.DateTimeFormat;
import tekup.gl.recrutement_cv.entities.Offre;
import tekup.gl.recrutement_cv.entities.Skills;
import tekup.gl.recrutement_cv.entities.offres_skills;
import tekup.gl.recrutement_cv.services.OffreService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@SpringBootApplication
public class RecrutementCvApplication {


	public static void main(String[] args) {
		SpringApplication.run(RecrutementCvApplication.class, args);}


@Bean
	CommandLineRunner commandLineRunner( OffreService offreService){
		return args -> {
//			Offre offre = offreService.getOffreById(1);
//			System.out.println(offre.getDate_offre());

//			Skills skills = new Skills();
//			skills.setNom_Skill("java");
//			offreService.save_skills(skills);
//			Offre offre=offreService.getOffreById(1);
//			offres_skills  offresSkills = new offres_skills();
//			offresSkills.setOffre(offre);
//			offresSkills.setSkills(skills);
//			offreService.save_Offre_Skills(offresSkills);

//		Offre offre=new Offre();
//		offre.setTitle("titre1");
//		offre.setDate_offre(new Date());
//		offre.setDescription("description 1");
//		offre.setPublication(true);
//		offreService.saveOffre(offre);
		};
	}

}
