package com.mlschool.schoolmannagement.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mlschool.schoolmannagement.model.affectation.Affectation;
import com.mlschool.schoolmannagement.model.evaluation.Evaluation;
import com.mlschool.schoolmannagement.model.modelResult.ResultMoyenneByClassePeriode;
import com.mlschool.schoolmannagement.service.AffectationService;
import com.mlschool.schoolmannagement.service.ClasseService;
import com.mlschool.schoolmannagement.service.EleveService;
import com.mlschool.schoolmannagement.service.EvaluationService;
import com.mlschool.schoolmannagement.service.MatiereService;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/note")
@AllArgsConstructor
public class NoteController {

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd") ;

    private final EvaluationService evaluationService;

    private final ClasseService classeService;

    private final MatiereService matiereService;

    private final AffectationService affectationService;
        
    private final EleveService eleveService;


    @GetMapping("/consultationevaluation")
    public String consultationevaluation(Model model) {
        model.addAttribute("classes", classeService.findAll(Sort.by(Sort.Direction.DESC,"id")));
        model.addAttribute("matieres", matiereService.findAll(Sort.by(Sort.Direction.DESC,"id")));
        model.addAttribute("evaluation", new Evaluation());
        return "/note/consultationevaluation";
    }

    @PostMapping("/consultationevaluation")
    public String postConsultationevaluation(Model model,@ModelAttribute Evaluation evaluation) {
        model.addAttribute("classes", classeService.findAll(Sort.by(Sort.Direction.DESC,"id")));
        model.addAttribute("matieres", matiereService.findAll(Sort.by(Sort.Direction.DESC,"id")));
       
        Affectation affectation = affectationService.findByClasseMatiere(evaluation.getAffectation().getIdclasse(), evaluation.getAffectation().getIdmatiere());
        
        if(affectation == null){
            model.addAttribute("error", "Aucune affectation pour cette composition !");
            return "/note/consultationevaluation";
        }

        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n premier : "+affectation.getId()+"\n\n\n\n\n\n\n\n\n\n\n\n\n deuxieme: "+evaluation.getPeriode()+"\n\n\n\n\n\n\n\n\n\n\n\n\n");


        List<Evaluation> evaluations = evaluationService.getEvaluationsbyAffectationAndPeriode(affectation.getId(), evaluation.getPeriode()); 
       
        if(evaluations.isEmpty()){
            model.addAttribute("error", "Aucune evaluation !");
            return "/note/consultationevaluation";
        }
        
        model.addAttribute("evaluations",evaluations );
        return "/note/consultationevaluation";
    }
  
    @GetMapping("/consultationmoyenne")
    public String consultationmoyenne(Model model) {
        model.addAttribute("classes", classeService.findAll(Sort.by(Sort.Direction.DESC,"id")));
        
        return "/note/consultationmoyenne";
    }

    @PostMapping("/consultationmoyenne")
    public String postConsultationmoyenne(Model model,@RequestParam String periode,@RequestParam Integer idclasse) {
        model.addAttribute("classes", classeService.findAll(Sort.by(Sort.Direction.DESC,"id")));
        List<ResultMoyenneByClassePeriode> moyennes =  evaluationService.getMoyenneAndRankByClasse(idclasse,periode);
        if(moyennes.isEmpty()){
            model.addAttribute("error"," Aucune evaluation !");
            return "/note/consultationmoyenne";
        }
        model.addAttribute("moyennes",moyennes);
        return "/note/consultationmoyenne";
    }
    
    @GetMapping("/enregistrementnote")
    public String enregistrementnote(Model model) {
        model.addAttribute("classes", classeService.findAll(Sort.by(Sort.Direction.DESC,"id")));
        model.addAttribute("matieres", matiereService.findAll(Sort.by(Sort.Direction.DESC,"id")));
        model.addAttribute("affectation",new Affectation());

        return "/note/enregistrementnote";
    }

    @PostMapping("/enregistrementnote")
    public String postEnregistrementnote(Model model,@ModelAttribute Affectation affectation,@RequestParam String periode,@RequestParam  String date){
        model.addAttribute("classes", classeService.findAll(Sort.by(Sort.Direction.DESC,"id")));
        model.addAttribute("matieres", matiereService.findAll(Sort.by(Sort.Direction.DESC,"id")));
       
        if(eleveService.findElevesByClasse(affectation.getIdclasse()).isEmpty()) {
            model.addAttribute("errorAffectation", "Aucune eleve dans cette classe !");
            return "/note/enregistrementnote";
        }
        if(affectationService.findByClasseMatiere(affectation.getIdclasse(), affectation.getIdmatiere()) == null){
            model.addAttribute("errorAffectation", "Aucune affectation pour cette composition !");
            return "/note/enregistrementnote";
        }
        if (eleveService.findElevesByClasseAndMatiereNoNote(affectation.getIdclasse(),affectation.getIdmatiere()).isEmpty()) {
            model.addAttribute("errorAffectation", "Tous les eleves ont une note !");
            return "/note/enregistrementnote";
        }
        model.addAttribute("affectation",affectation);
        model.addAttribute("eleves", eleveService.findElevesByClasseAndMatiereNoNote(affectation.getIdclasse(),affectation.getIdmatiere()));
        model.addAttribute("periode",periode);
        model.addAttribute("date",date);
            
        return "/note/enregistrementnote";
    }

    @PostMapping("/registernote") 
    public String postRegisternote(Model model,@RequestParam Map<String,String> notes,@RequestParam Integer idclasse,@RequestParam Integer idmatiere,@RequestParam String periode,@RequestParam String date) {

        for (Map.Entry<String,String> entry : notes.entrySet()) {
            if(entry.getKey().indexOf("notes")!=-1){
                Integer ideleve = Integer.parseInt(entry.getKey().substring(entry.getKey().indexOf("[")+1, entry.getKey().indexOf("]")));
                Evaluation evaluation = new Evaluation();
                evaluation.setIdEleve(ideleve);
                
                evaluation.setNote(Double.parseDouble(entry.getValue()));
                evaluation.setPeriode(periode);
                try {
                    evaluation.setDate(simpleDateFormat.parse(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                evaluation.setIdAffectation(affectationService.findByClasseMatiere(idclasse, idmatiere).getId());
                evaluationService.save(evaluation);
            }
        }
        return "redirect:/note/enregistrementnote";
    }
    
    
}
