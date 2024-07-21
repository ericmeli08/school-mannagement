package com.mlschool.schoolmannagement.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mlschool.schoolmannagement.model.affectation.Affectation;
import com.mlschool.schoolmannagement.repository.AffectationRepository;

import lombok.Data;

@Service
@Data
public class AffectationService {

    @Autowired
    private AffectationRepository affectationRepository;

    public Boolean isAffectationExist(Affectation affectation){
        return affectationRepository.findByClasseMatiere( affectation.getIdclasse(), affectation.getIdmatiere()) != null;

    }

    public Affectation findByClasseMatiere(Integer classe, Integer matiere){
        return affectationRepository.findByClasseMatiere(classe, matiere);
    }

    public void save(Affectation affectation){
        affectationRepository.save(affectation);
    }

    public List<Affectation> findAll(Sort sort){
        return affectationRepository.findAll(sort);
    }

    public ArrayList<Object> getClasseProfesseurs(){
        ArrayList<Object> result = new ArrayList<Object>();
        List<Object[]> classes = affectationRepository.getClasseProfesseurs();
        for (Object[] objects : classes) {
            result.add(new Object(){
                public String classe = objects[0].toString();
                public Long professeurs = (Long) (objects[1]);
            });
        }
        return result;
    }

}
