package com.mlschool.schoolmannagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mlschool.schoolmannagement.model.Eleve;

@Repository
public interface EleveRepository extends JpaRepository<Eleve,Integer> {

    @Query("SELECT e FROM Eleve e  where e.idclasse = :idclasse")
    List<Eleve> findElevesByClasse(@Param("idclasse") Integer idclasse);

    @Query("SELECT e FROM Eleve e  WHERE e.idclasse = :idclasse  AND e.id NOT IN (SELECT a.idEleve FROM Evaluation a WHERE a.affectation.idmatiere = :idmatiere AND a.eleve.idclasse = :idclasse)")
    List<Eleve> findElevesByClasseAndMatiereNoNote(@Param("idclasse") Integer idclasse,@Param("idmatiere") Integer idmatiere);

    Eleve findByMatricule(String matricule);

    @Query("SELECT e.classe.classe,COUNT(e.id) FROM Eleve e GROUP BY e.idclasse")
    List<Object[]> getClassesAndStudents();

}
