package com.mlschool.schoolmannagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mlschool.schoolmannagement.model.affectation.Affectation;

@Repository
public interface AffectationRepository extends JpaRepository<Affectation,Integer>{

    @Query("SELECT a FROM Affectation a WHERE  a.idclasse = :idclasse AND a.idmatiere = :idmatiere")
    Affectation findByClasseMatiere(@Param("idclasse") Integer  idClasse,@Param("idmatiere") Integer idMatiere); 
    
    @Query("SELECT a.classe.classe,COUNT(a.idprofesseur) FROM Affectation a GROUP BY a.idclasse")
    List<Object[]> getClasseProfesseurs(); 



}
