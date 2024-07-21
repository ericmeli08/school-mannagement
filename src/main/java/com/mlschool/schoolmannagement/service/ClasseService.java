package com.mlschool.schoolmannagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mlschool.schoolmannagement.model.classe.Classe;
import com.mlschool.schoolmannagement.repository.ClasseRepository;

@Service
public class ClasseService {

    @Autowired
    private ClasseRepository classeRepository;

    public List<Classe> findAll(Sort sort){
        return classeRepository.findAll(sort);
    }
    
}
