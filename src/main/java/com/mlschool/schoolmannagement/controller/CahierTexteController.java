package com.mlschool.schoolmannagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cahiertexte")
public class CahierTexteController {
    

    @GetMapping("/remplir")
    public String remplir(){
        return "/cahiertexte/remplir";
    }

    @GetMapping("/consultation")
    public String consultation(){
        return "/cahiertexte/consultation";
    }
}
