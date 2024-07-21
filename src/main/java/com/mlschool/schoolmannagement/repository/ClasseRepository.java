package com.mlschool.schoolmannagement.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.mlschool.schoolmannagement.model.classe.Classe;

@Repository
public interface ClasseRepository extends JpaRepository<Classe,Integer> {
    

}
