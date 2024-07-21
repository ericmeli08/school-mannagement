package com.mlschool.schoolmannagement.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mlschool.schoolmannagement.model.Eleve;
import com.mlschool.schoolmannagement.model.abscenceeleve.AbscenceEleve;
import com.mlschool.schoolmannagement.service.AbscenceEleveService;
import com.mlschool.schoolmannagement.service.EleveService;

import lombok.AllArgsConstructor;

@Controller 
@RequestMapping("/abscenceeleve")
@AllArgsConstructor
public class AbscenceEleveController {

    
    private final EleveService eleveService ;

    
    private final AbscenceEleveService abscenceEleveService ;

    @GetMapping("/enregistrement")
    public String enregistrement(Model model){
        model.addAttribute("abscenceEleve",new AbscenceEleve());
        return "/abscenceeleve/enregistrement";
    }


    @PostMapping("/enregistrement")
    public String postEnregistrement(Model model,@RequestParam String search){
        model.addAttribute("abscenceEleve",new AbscenceEleve());
        String errorSearch = "errorSearch";
        
        if(search == null || search.isEmpty()){
            model.addAttribute(errorSearch, "le champ est vide !");
            return "/abscenceeleve/enregistrement"; 
        }

        if(eleveService.getIdFromMatricule(search) == null){
            model.addAttribute(errorSearch, "Matricule invalide !");
            return "/abscenceeleve/enregistrement"; 
        }

        Eleve eleve = eleveService.findByMatricule(search);
        if(eleve == null){
            model.addAttribute(errorSearch, "Cet eleve n'existe pas !");
            return "/abscenceeleve/enregistrement"; 
        }

        model.addAttribute("eleve", eleve);
        model.addAttribute("total", abscenceEleveService.getTotalHeureAbscenceByIdEleve(eleve.getId()));
        model.addAttribute("justifier", abscenceEleveService.getTotalHeureAbscenceJustifyByIdEleve(eleve.getId()));
        model.addAttribute("abscenceEleves", abscenceEleveService.getAllAbscencesById(eleve.getId()));

        return "/abscenceeleve/enregistrement"; 
    }

    @PostMapping("/registerabscenceEleve") 
    public String enregisterVersement(@RequestParam Integer ideleve, @ModelAttribute AbscenceEleve abscenceEleve,BindingResult result ) {
        abscenceEleve.setDateAbscence(new Date());
        abscenceEleve.setIdEleve(ideleve);
        abscenceEleveService.save(abscenceEleve); 
        return "redirect:/abscenceeleve/consultation";
    }


    @GetMapping("/consultation")
    public String consultation(Model model){
        model.addAttribute("abscenceEleves", abscenceEleveService.findAll(Sort.by(Sort.Direction.DESC,"id")));
        return "/abscenceeleve/consultation";
    }
}
