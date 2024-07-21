package com.mlschool.schoolmannagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mlschool.schoolmannagement.model.professeur.Professeur;
import java.util.List;


@Repository
public interface ProfesseurRepository extends JpaRepository<Professeur,Integer> {


    List<Professeur> findByMatricule(String matricule);
}
