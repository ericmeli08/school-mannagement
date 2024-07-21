package com.mlschool.schoolmannagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mlschool.schoolmannagement.model.matiere.Matiere;

@Repository
public interface MatiereRepository extends JpaRepository<Matiere,Integer> {

}
