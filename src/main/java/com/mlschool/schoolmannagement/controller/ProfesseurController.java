package com.mlschool.schoolmannagement.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mlschool.schoolmannagement.model.affectation.Affectation;
import com.mlschool.schoolmannagement.model.professeur.Professeur;
import com.mlschool.schoolmannagement.model.professeur.ProfesseurDto;
import com.mlschool.schoolmannagement.service.AffectationService;
import com.mlschool.schoolmannagement.service.ClasseService;
import com.mlschool.schoolmannagement.service.MatiereService;
import com.mlschool.schoolmannagement.service.ProfesseurService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/professeur")
public class ProfesseurController {

    @Autowired
    private ProfesseurService professeurService;

    @Autowired
    private ClasseService classeService;

    @Autowired
    private MatiereService matiereService;

    @Autowired
    private AffectationService affectationService;
    
    @GetMapping("/nouveauprofesseur")
    public String newTeacher(Model model){
        model.addAttribute("professeurDto", new ProfesseurDto());
        return "/professeur/nouveauprofesseur";
    }

    @PostMapping("/nouveauprofesseur")
    public String newTeacherregister(@Valid @ModelAttribute ProfesseurDto professeurDto,BindingResult result){
        
        if(professeurDto.getPhoto().isEmpty()){
            result.addError(new FieldError("professeurDto", "photo", "L'image est obligatoire !"));
        }
        
        if(result.hasErrors()){
            return "/professeur/nouveauprofesseur";
        }

        String storageFileName = professeurService.saveImage(professeurDto.getPhoto());
        professeurDto.setStorageFileName(storageFileName);

        Professeur Professeur = new Professeur();
        professeurDto.transfertDataToProfesseur(Professeur);

        professeurService.save(Professeur);
        professeurService.updateMatricule();
        
        
        return "redirect:/professeur/listprofesseur";
    }
    
    @GetMapping("/listprofesseur")
    public String listprofesseur(Model model){
        model.addAttribute("professeurs",professeurService.findAll(Sort.by(Sort.Direction.DESC, "id")) );
        return "/professeur/listprofesseur";
    }
   
    @GetMapping("/affectation")
    public String affectation(Model model){
        model.addAttribute("professeurs",professeurService.findAll(Sort.by(Sort.Direction.DESC, "id")) );
        model.addAttribute("classes", classeService.findAll(Sort.by(Sort.Direction.DESC,"id")));
        model.addAttribute("matieres", matiereService.findAll(Sort.by(Sort.Direction.DESC,"id")));
        model.addAttribute("affectation",new Affectation());
        model.addAttribute("affectations",affectationService.findAll(Sort.by(Sort.Direction.DESC,"id")));
        return "/professeur/affectation";
    }
    
    @PostMapping("/affectation")
    public String postAffectation(Model model,@ModelAttribute Affectation affectation,BindingResult result){
        
        if(affectation.getCoefficient()  == null || affectation.getCoefficient() <= 0 ){
            result.addError(new FieldError("affectation",  "coefficient", "Coefficient Obligatoire !"));
            model.addAttribute("professeurs",professeurService.findAll(Sort.by(Sort.Direction.DESC, "id")) );
            model.addAttribute("classes", classeService.findAll(Sort.by(Sort.Direction.DESC,"id")));
            model.addAttribute("matieres", matiereService.findAll(Sort.by(Sort.Direction.DESC,"id")));
            model.addAttribute("affectations",affectationService.findAll(Sort.by(Sort.Direction.DESC,"id")));
            return "/professeur/affectation";
        }

        if(affectationService.isAffectationExist(affectation)){
            model.addAttribute("errorAffectationExist","Cette affectation existe deja !"); 
            model.addAttribute("professeurs",professeurService.findAll(Sort.by(Sort.Direction.DESC, "id")) );
            model.addAttribute("classes", classeService.findAll(Sort.by(Sort.Direction.DESC,"id")));
            model.addAttribute("matieres", matiereService.findAll(Sort.by(Sort.Direction.DESC,"id")));
            model.addAttribute("affectations",affectationService.findAll(Sort.by(Sort.Direction.DESC,"id")));
            return "/professeur/affectation";
        }

        affectation.setCreatedDate(new Date());

        affectationService.save(affectation);

        return "redirect:/professeur/affectation";
    }
   
    @GetMapping("/listprofesseurclasse")
    public String listprofesseurclasse(){
        return "/professeur/listprofesseurclasse";
    }

    
    @PostMapping("/edit")
    public String registerEdit(Model model,@RequestParam int id,@Valid @ModelAttribute ProfesseurDto professeurDto,BindingResult result){

        if(result.hasErrors()){
            model.addAttribute("classes", professeurService.findAll(Sort.by(Sort.Direction.DESC, "id")));
            return "professeur/edit";
        }
        Professeur professeur = professeurService.findById(id);
        professeurDto.transfertDataToProfesseur(professeur);

        if(!professeurDto.getPhoto().isEmpty()){
            
            String storageFileName = professeurService.newSaveImage(professeurDto.getPhoto(),professeur.getPhoto());
            professeur.setPhoto(storageFileName);
        }

        professeurService.save(professeur);
        
        return "redirect:/professeur/listprofesseur";
    }

    @GetMapping("/edit")
    public String edit(Model model,@RequestParam int id){

        Professeur professeur = professeurService.findById(id);
        model.addAttribute("Professeur", professeur);

        ProfesseurDto professeurDto = new ProfesseurDto();
        professeurDto.transfertDataToProfesseurDto(professeur);

        model.addAttribute("professeurDto", professeurDto);

        return "professeur/edit";
    }


    @GetMapping("/delete")
    public String delete(Model model,@RequestParam int id){

        Professeur professeur = professeurService.findById(id);
        professeurService.deleteImage(professeur.getPhoto());

        professeurService.delete(professeur);

        return "redirect:/professeur/listprofesseur";
    }

}
