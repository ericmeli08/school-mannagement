package com.mlschool.schoolmannagement.controller;


import java.util.List;


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

import com.mlschool.schoolmannagement.model.Eleve;
import com.mlschool.schoolmannagement.model.EleveDto;
import com.mlschool.schoolmannagement.repository.EleveRepository;
import com.mlschool.schoolmannagement.service.ClasseService;
import com.mlschool.schoolmannagement.service.EleveService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;


// @RestController
@Controller
@RequestMapping("/eleve")
@AllArgsConstructor
public class EleveController {

    
    private final EleveService eleveService;

    private final ClasseService classeService;

    @GetMapping("/inscription")
    public String showInscription(Model model){
        EleveDto eleveDto = new EleveDto();
        model.addAttribute("eleveDto", eleveDto);
        model.addAttribute("classes", classeService.findAll(Sort.by(Sort.Direction.DESC, "id")));
        
        return "eleve/inscription";
    }
    
    
    @PostMapping("/inscription")
    public String registerInscription(Model model,@Valid @ModelAttribute EleveDto eleveDto,BindingResult result){
        
        if(eleveDto.getPhoto().isEmpty()){
            result.addError(new FieldError("eleveDto", "photo", "L'image est obligatoire !"));
        }
        
        if(result.hasErrors()){
            model.addAttribute("classes", classeService.findAll(Sort.by(Sort.Direction.DESC, "id")));
            return "eleve/inscription";
        }

        String storageFileName = eleveService.saveImage(eleveDto.getPhoto());
        eleveDto.setStorageFileName(storageFileName);

        Eleve eleve = new Eleve();
        eleveDto.transfertDataToEleve(eleve);

        eleveService.save(eleve);
        eleveService.updateMatricule(); 
        
        return "redirect:/eleve/listeleve";
    }

    @PostMapping("/edit")
    public String registerEdit(Model model,@RequestParam int id,@Valid @ModelAttribute EleveDto eleveDto,BindingResult result){

        if(result.hasErrors()){
            model.addAttribute("classes", classeService.findAll(Sort.by(Sort.Direction.DESC, "id")));
            return "eleve/edit";
        }
        Eleve eleve = eleveService.findById(id);
        eleveDto.transfertDataToEleve(eleve);

        if(!eleveDto.getPhoto().isEmpty()){
            
            String storageFileName = eleveService.newSaveImage(eleveDto.getPhoto(),eleve.getPhoto());
            eleve.setPhoto(storageFileName);
        }

        eleveService.save(eleve);
        
        return "redirect:/eleve/listeleve";
    }

    @GetMapping("/edit")
    public String edit(Model model,@RequestParam int id){

        Eleve eleve = eleveService.findById(id);
        model.addAttribute("eleve", eleve);

        EleveDto eleveDto = new EleveDto();
        eleveDto.transfertDataToEleveDto(eleve);

        model.addAttribute("eleveDto", eleveDto);
        model.addAttribute("classes", classeService.findAll(Sort.by(Sort.Direction.DESC, "id")));

        return "eleve/edit";
    }


    @GetMapping("/delete")
    public String delete(Model model,@RequestParam int id){

        Eleve eleve = eleveService.findById(id);
        eleveService.deleteImage(eleve.getPhoto());

        eleveService.delete(eleve);

        return "redirect:/eleve/listeleve";
    }



    @GetMapping("/listeleve")
    public String listEleve(Model model) {
        List<Eleve> eleves = eleveService.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("eleves", eleves);
        return new String("eleve/listeleve");
    }
  
    @GetMapping("/listperiode")
    public String listPeriode(Model model) {
        List<Eleve> eleves = eleveService.findAll(Sort.by(Sort.Direction.DESC, "periode"));
        model.addAttribute("eleves", eleves);
        return new String("eleve/listperiode");
    }
  
    @GetMapping("/listclasse")
    public String listClasse(Model model ) {
        List<Eleve> eleves = eleveService.findAll(Sort.by(Sort.Direction.DESC, "idclasse"));
        model.addAttribute("eleves", eleves);
        return new String("eleve/listclasse");
    }
    
    
}   
