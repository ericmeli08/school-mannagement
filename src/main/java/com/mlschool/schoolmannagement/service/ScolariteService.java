package com.mlschool.schoolmannagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mlschool.schoolmannagement.model.Eleve;
import com.mlschool.schoolmannagement.model.scolarite.Scolarite;
import com.mlschool.schoolmannagement.repository.EleveRepository;
import com.mlschool.schoolmannagement.repository.ScolariteRepository;

@Service
public class ScolariteService {

    @Autowired
    private ScolariteRepository scolariteRepository ;

    public Double getMontantTotalByIdEleve(Integer id){
        return scolariteRepository.getMontantTotalByIdEleve(id);
    }

    public List<Scolarite> getListVersementbyIdEleve(Integer id){
        return scolariteRepository.getListVersementbyIdEleve(id);
    }

    public void save(Scolarite versement){
        scolariteRepository.save(versement);
    }

    public List<Object> getListIdEleveAndSumVersement(){
        List<Object[]> list = scolariteRepository.getListIdEleveAndSumVersement();
        ArrayList<Object> tableau = new ArrayList<>();
        for(Object[] objects : list){       
            tableau.add(new Object(){
                public Eleve eleve = (Eleve) objects[0];
                public Double solde = (Double) objects[1];
                public Long nombre = (Long) objects[2];
            });
        } 

        return tableau;
    }

    public List<Object> getListClasseSoldeAcompte(){
        List<Object[]> list = scolariteRepository.getListClasseSoldeAcompte();
        ArrayList<Object> tableau = new ArrayList<>();
        for(Object[] objects : list){       
            tableau.add(new Object(){
                public String classe = (String) objects[0];
                public Long solde = (Long) objects[1];
                public Long acompte = (Long) objects[2];
            });
        } 

        return tableau;
    }





    
}
