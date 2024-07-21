package com.mlschool.schoolmannagement.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mlschool.schoolmannagement.model.evaluation.Evaluation;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation,Integer> {

    @Query("SELECT e FROM Evaluation e WHERE e.periode = :periode AND e.idAffectation = :idAffectation ")
    List<Evaluation> getEvaluationsbyAffectationAndPeriode(@Param("idAffectation") Integer idaffectation,@Param("periode") String periode );


    @Query("""
        SELECT \
            e, \
            SUM(e.note * e.affectation.coefficient) as total , \
            SUM(e.note * e.affectation.coefficient) / SUM(e.affectation.coefficient) AS moyenne, \
            SUM(e.affectation.coefficient) as coefficients \
        FROM \
            Evaluation e \
        WHERE \
            e.affectation.idclasse = :classe AND e.periode = :periode  \
        GROUP BY e.idEleve  \
        ORDER BY moyenne  DESC\
        """) 
    List<Object[]> getMoyenneAndRankByClasse(@Param("classe") Integer idclasse,@Param("periode") String periode);

//     @Query(""+
//     "SELECT "+
//         "e,"+ 
//         "SUM(e.note * e.affectation.coefficient) as total ,"+
//         "SUM(e.note * e.affectation.coefficient) / SUM(e.affectation.coefficient) AS moyenne, "+
//         "SUM(e.affectation.coefficient) as coefficients"+ 
//     "FROM Evaluation e "+
//     "WHERE e.affectation.idclasse = :classe AND e.periode = :periode  "+
//     "GROUP BY e.idEleve  ORDER BY moyenne  DESC"+
//     "") 
// List<Object[]> getMoyenneAndRankByClasse(@Param("classe") Integer idclasse,@Param("periode") String periode);

    
    @Query("SELECT ev, (SELECT COUNT(e.idEleve) FROM Evaluation e WHERE e.note > ev.note AND e.periode = ev.periode AND e.affectation.matiere.idtypematiere = ev.affectation.matiere.idtypematiere AND e.affectation.idmatiere = ev.affectation.idmatiere ) as rang FROM Evaluation ev WHERE ev.periode = :periode AND idEleve = :ideleve AND ev.affectation.matiere.idtypematiere = :typematiere ")
    ArrayList<Object[]> getListNoteEleveByTypeMatiereAndPeriode(@Param("ideleve") Integer ideleve,@Param("periode") String periode,@Param("typematiere") Integer idType ); 

    @Query("SELECT  COUNT(e.id)  FROM  Eleve e WHERE e.idclasse = :idclasse") 
    Long getNombreEleveByClasse (@Param("idclasse") Integer classe);   
    
    @Query(
        """
        SELECT COUNT(moyennes.ideleve) as rang FROM \
         ( \
         SELECT e.eleve.id as ideleve, SUM(e.note * e.affectation.coefficient) / SUM(e.affectation.coefficient) as moyenne \
         FROM Evaluation e \
         WHERE e.affectation.classe.id = :classe AND e.periode = :periode  \
         ) moyennes WHERE moyennes.ideleve != :ideleve \
        """  
    )
    Long getRangEleveById ( Integer ideleve,@Param("classe") Integer idclasse,@Param("periode") String periode);  
   
    @Query("""
             SELECT SUM(e.note * e.affectation.coefficient) / SUM(e.affectation.coefficient) as moyenne \
             FROM Evaluation e \
             WHERE e.eleve.id = :ideleve AND e.periode = :periode \
            """)
    Double getMoyenneEleveById ( Integer ideleve,@Param("periode") String periode);  

}


