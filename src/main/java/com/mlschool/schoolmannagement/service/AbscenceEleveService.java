package com.mlschool.schoolmannagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.mlschool.schoolmannagement.model.abscenceeleve.AbscenceEleve;
import com.mlschool.schoolmannagement.repository.AbscenceEleveRepository;

@Service
public class AbscenceEleveService {

    @Autowired
    private AbscenceEleveRepository abscenceEleveRepository;

    public void save(AbscenceEleve abscenceEleve){
        abscenceEleveRepository.save(abscenceEleve);
    }

    public Double getTotalHeureAbscenceByIdEleve( Integer id){
        return abscenceEleveRepository.getTotalHeureAbscenceByIdEleve(id);
    }

    public Double getTotalHeureAbscenceJustifyByIdEleve(Integer id){
        return abscenceEleveRepository.getTotalHeureAbscenceJustifyByIdEleve(id);
    }

    public List<AbscenceEleve> findAll(Sort sort){
        return abscenceEleveRepository.findAll(sort);
    }

    public List<AbscenceEleve> getAllAbscencesById(Integer id){
        return abscenceEleveRepository.getAllAbscencesById(id);
    }

    public Integer getSumAbscenceEleve(Integer id){
        return abscenceEleveRepository.getSumAbscenceEleve(id);
    }

    public Integer getSumAbscenceEleveJustifier(Integer id){
        return abscenceEleveRepository.getSumAbscenceEleveJustifier(id);
    }

    public Integer getSumAbscenceEleveNonJustifier(Integer id){
        return abscenceEleveRepository.getSumAbscenceEleveNonJustifier(id);
    }

}
