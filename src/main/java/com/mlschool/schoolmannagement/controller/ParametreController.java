package com.mlschool.schoolmannagement.controller;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mlschool.schoolmannagement.model.ecole.Ecole;
import com.mlschool.schoolmannagement.model.ecole.EcoleDto;
import com.mlschool.schoolmannagement.service.EcoleService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/parametre")
@AllArgsConstructor
public class ParametreController {
 
    private final EcoleService ecoleService;
    
    @GetMapping("/ecole")
    public String ecole(Model model){
        model.addAttribute("ecoleDto", new EcoleDto());
        model.addAttribute("ecoles", ecoleService.findAll(Sort.by(Sort.Direction.DESC, "id")));

        return "/parametre/ecole";
    }
    
    @PostMapping("/ecole")
    public String postEcole(Model model,@ModelAttribute @Valid EcoleDto ecoleDto,BindingResult result){
        
        if(result.hasErrors()){
            model.addAttribute("ecoleDto",ecoleDto);
            model.addAttribute("errorFile", "Aucun fichier selectionner");
            return "/parametre/ecole";
        }

        String storageFileName = ecoleService.saveImage(ecoleDto.getFile());
        ecoleDto.setLogo(storageFileName);
        Ecole ecole = new Ecole();
        ecoleDto.transfertDataToEcole(ecole);

        ecoleService.save(ecole);

        return "redirect:/parametre/ecole";
    }

    @GetMapping("/classe")
    public String classe(){
        return "/parametre/classe";
    }

    @GetMapping("/matiere")
    public String matiere(){
        return "/parametre/matiere";
    }

    @GetMapping("/fraisscolaire")
    public String fraisscolaire(){
        return "/parametre/fraisscolaire";
    }

    @GetMapping("/coef_matiere")
    public String coefMatiere(){
        return "/parametre/coef_matiere";
    }

    @GetMapping("/newadmin")
    public String newadmin(){
        return "/parametre/newadmin";
    }

    @GetMapping("/listadmin")
    public String listadmin(){
        return "/parametre/listadmin";
    }
}
