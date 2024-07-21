package com.mlschool.schoolmannagement.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.mlschool.schoolmannagement.model.Search;
import com.mlschool.schoolmannagement.model.evaluation.Evaluation;
import com.mlschool.schoolmannagement.model.Eleve;
import com.mlschool.schoolmannagement.model.scolarite.Scolarite;
import com.mlschool.schoolmannagement.model.scolarite.ScolariteDto;
import com.mlschool.schoolmannagement.repository.EleveRepository;
import com.mlschool.schoolmannagement.repository.ScolariteRepository;
import com.mlschool.schoolmannagement.service.EleveService;
import com.mlschool.schoolmannagement.service.ScolariteService;

import jakarta.validation.Valid;


@Controller
@RequestMapping("/scolarite")
public class ScolariteController {

    @Autowired
    private EleveService eleveService ;

    @Autowired
    private ScolariteService scolariteService;

    @GetMapping("/fixationmontant")
    public String fixationMontant() {
        return new String("/scolarite/fixationmontant");
    }

    @GetMapping("/nouveaureglement")
    public String nouveaureglement(Model model) {
        model.addAttribute("scolariteDto", new ScolariteDto());
        return new String("/scolarite/nouveaureglement");
    }

    @PostMapping("/registerversement")
    public String enregisterVersement(@RequestParam Integer ideleve,@RequestParam Double montant ) {
        Scolarite scolarite = new Scolarite();
        scolarite.setIdeleve(ideleve);  
        scolarite.setVersement(montant);
        scolarite.setDateversement(new Date());

        scolariteService.save(scolarite); 
        return new String("redirect:/scolarite/nouveaureglement");
    }

    @PostMapping("/nouveaureglement")
    public String postNouveauReglement(Model model,@RequestParam String search) {
        if(search == null || search.isEmpty()){
            model.addAttribute("errorSearch", "le champ est vide !");
            return "scolarite/nouveaureglement"; 
        }

        if(eleveService.getIdFromMatricule(search) == null){
            model.addAttribute("errorSearch", "Matricule invalide !");
            return "scolarite/nouveaureglement"; 
        }
        
        Eleve eleve = eleveService.findByMatricule(search);
        if(eleve == null){
            model.addAttribute("errorSearch", "Cet eleve n'existe pas !");
            return "scolarite/nouveaureglement"; 
        }

        Double solde = scolariteService.getMontantTotalByIdEleve(eleve.getId());
        model.addAttribute("eleve", eleve);
        if (solde != null) {
            model.addAttribute("solde", scolariteService.getMontantTotalByIdEleve(eleve.getId()));   
        }
        model.addAttribute("versements", scolariteService.getListVersementbyIdEleve(eleve.getId()));
        
        return new String("scolarite/nouveaureglement");
    }

    @GetMapping("/eleveretardpayement")
    public String eleveretardpayement(Model model) {  
        model.addAttribute("listeretard", scolariteService.getListIdEleveAndSumVersement());
        return new String("/scolarite/eleveretardpayement");
    }
    
    @GetMapping("/elevesoldepayement")
    public String elevesoldepayement(Model model) {
        model.addAttribute("listesolde", scolariteService.getListIdEleveAndSumVersement());
        return new String("/scolarite/elevesoldepayement");
    }

    @GetMapping("/listelevepayement")
    public String listelevepayement(Model model) {
        model.addAttribute("listepayements", scolariteService.getListIdEleveAndSumVersement());
        return new String("/scolarite/listelevepayement");
    }
    
    
}
