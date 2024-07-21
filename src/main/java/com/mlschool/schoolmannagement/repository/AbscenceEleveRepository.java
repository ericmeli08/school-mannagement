package com.mlschool.schoolmannagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mlschool.schoolmannagement.model.abscenceeleve.AbscenceEleve;

@Repository
public interface AbscenceEleveRepository extends JpaRepository<AbscenceEleve,Integer> {

    @Query("SELECT SUM(a.nombreHeure) FROM AbscenceEleve a WHERE a.idEleve = :id")
    Double getTotalHeureAbscenceByIdEleve(@Param("id") Integer id); 
   
    @Query("SELECT SUM(a.nombreHeure) FROM AbscenceEleve a WHERE a.idEleve= :id AND a.statut= 'justifier'" )
    Double getTotalHeureAbscenceJustifyByIdEleve(@Param("id") Integer id);

    @Query("SELECT a FROM AbscenceEleve a WHERE a.id= :id")
    List<AbscenceEleve> getAllAbscencesById(@Param("id") Integer id); //

    @Query("SELECT SUM(a.nombreHeure) FROM AbscenceEleve a WHERE a.id= :id")
    Integer getSumAbscenceEleve(@Param("id") Integer id); //

    @Query("SELECT SUM(a.nombreHeure) FROM AbscenceEleve a WHERE a.id= :id AND a.statut = 'justifier'")
    Integer getSumAbscenceEleveJustifier(@Param("id") Integer id); //

    @Query("SELECT SUM(a.nombreHeure) FROM AbscenceEleve a WHERE a.id= :id AND a.statut = 'non justifier'")
    Integer getSumAbscenceEleveNonJustifier(@Param("id") Integer id); //

}
