package com.mlschool.schoolmannagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AcceuilService {

    
    @Autowired
    private ProfesseurService professeurService;

    @Autowired
    private ClasseService classeService;

    @Autowired
    private MatiereService matiereService;
    
}
