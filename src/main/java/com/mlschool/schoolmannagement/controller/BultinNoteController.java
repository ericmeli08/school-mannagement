package com.mlschool.schoolmannagement.controller;

import java.io.IOException;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itextpdf.text.DocumentException;
import com.mlschool.schoolmannagement.model.Eleve;

import com.mlschool.schoolmannagement.service.AbscenceEleveService;
import com.mlschool.schoolmannagement.service.BulletinService;
import com.mlschool.schoolmannagement.service.EcoleService;
import com.mlschool.schoolmannagement.service.EleveService;
import com.mlschool.schoolmannagement.service.EvaluationService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/bultinnote")
@AllArgsConstructor
public class BultinNoteController {

    private final EcoleService ecoleService;

    private final AbscenceEleveService abscenceEleveService;

    private final EleveService eleveService;

    private final BulletinService bulletinService;

    private final EvaluationService evaluationService;

    @GetMapping("/consultation")
    public String consultation(Model model){
        model.addAttribute("eleves",eleveService.findAll(Sort.by(Sort.Direction.DESC, "id")) );
        return "/bultinnote/consultation";
    }

    
    @PostMapping("/bulletin")
    public String bulletin(Model model,@RequestParam String periode,@RequestParam Integer id){
        model.addAttribute("ecole",ecoleService.findAll(Sort.by(Sort.Direction.DESC, "id")).get(0));
        model.addAttribute("periode", periode);
        model.addAttribute("id", id);
        Eleve eleve = eleveService.findById(id);
        model.addAttribute("eleve", eleve);
        model.addAttribute("nombre", evaluationService.getNombreEleveByClasse(eleve.getIdclasse()));

        model.addAttribute("litteraires",evaluationService.getListNoteEleveByTypeMatiereAndPeriode( id, periode,2) );
        model.addAttribute("bilanLitteraire",evaluationService.traitementBilan( id, periode,2) );
        
        model.addAttribute("scientifiques",evaluationService.getListNoteEleveByTypeMatiereAndPeriode( id, periode,1) );
        model.addAttribute("bilanScientifique",evaluationService.traitementBilan( id, periode,1) );
       
        model.addAttribute("autres",evaluationService.getListNoteEleveByTypeMatiereAndPeriode( id, periode,3) );
        model.addAttribute("bilanAutre",evaluationService.traitementBilan( id, periode,3) );
        
        model.addAttribute("bilanGeneral",evaluationService.getBilanGeneral(id,periode));


        model.addAttribute("lastMoyenne", evaluationService.getLastMoyenneByClasse(eleve.getIdclasse(),periode));
        model.addAttribute("firstMoyenne", evaluationService.getFirstMoyenneByClasse(eleve.getIdclasse(),periode));
        model.addAttribute("classMoyenne", evaluationService.getMoyenneByClasse(eleve.getIdclasse(),periode));
        
        
        model.addAttribute("abscences", abscenceEleveService.getSumAbscenceEleve(id));
        model.addAttribute("abscenceJustifier", abscenceEleveService.getSumAbscenceEleveJustifier(id));
        model.addAttribute("abscenceNonJustifier", abscenceEleveService.getSumAbscenceEleveNonJustifier(id));
        
        return "/bultinnote/bulletin";  
    } 
    
    @PostMapping("/imprimer")
    public String imprimer(Model model,@RequestParam String periode,@RequestParam Integer id){
        try {
            bulletinService.Imprimer(periode, id);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        return "redirect:/bultinnote/consultation";
    }

    
}
