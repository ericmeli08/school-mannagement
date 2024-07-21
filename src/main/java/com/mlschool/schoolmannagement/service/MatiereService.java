package com.mlschool.schoolmannagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mlschool.schoolmannagement.model.matiere.Matiere;
import com.mlschool.schoolmannagement.repository.MatiereRepository;

import lombok.Data;

@Service
@Data
public class MatiereService {

    @Autowired
    private MatiereRepository matiereRepository;

    public List<Matiere> findAll(Sort sort){
        return matiereRepository.findAll(sort);
    }
}
