package com.mlschool.schoolmannagement.controller;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mlschool.schoolmannagement.service.AffectationService;

import com.mlschool.schoolmannagement.service.EleveService;

import com.mlschool.schoolmannagement.service.ScolariteService;

import lombok.AllArgsConstructor;


@Controller
@RequestMapping("/accueil")
@AllArgsConstructor
public class AccueilController {
    
    private final ScolariteService scolariteService;

    private final AffectationService affectationService;
        
    private final EleveService eleveService;

    
    @GetMapping("")
    public String showHome(Model model){
        model.addAttribute("classeEleves", eleveService.getClassesAndStudents());
        model.addAttribute("classeProfesseurs",affectationService.getClasseProfesseurs());
        model.addAttribute("classeSoldeAcomptes",scolariteService.getListClasseSoldeAcompte());
        return "Accueil";
    }

    
}
