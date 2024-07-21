package com.mlschool.schoolmannagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mlschool.schoolmannagement.model.scolarite.Scolarite;


@Repository
public interface ScolariteRepository extends JpaRepository<Scolarite,Integer> {

    @Query("SELECT SUM(s.versement) FROM Scolarite s WHERE s.ideleve = :id ")
    Double getMontantTotalByIdEleve(@Param("id") Integer id);

    @Query("SELECT s FROM Scolarite s WHERE s.ideleve = :ideleve")
    List<Scolarite> getListVersementbyIdEleve(@Param("ideleve") Integer id);
    
    @Query("SELECT e,SUM(s.versement) as sum,COUNT(s.id) as nombre FROM Scolarite s,Eleve e WHERE e.id = s.ideleve GROUP BY s.ideleve ")
    List<Object[]> getListIdEleveAndSumVersement();

    @Query("SELECT e,SUM(s.versement) as sum,COUNT(s.id) as nombre FROM Scolarite s,Eleve e WHERE e.id = s.ideleve GROUP BY s.ideleve ")
    List<Object[]> getListClasseAndStatut();

    @Query("""
                    SELECT DISTINCT s.eleve.classe.classe, \
                    (SELECT COUNT(sa.ideleve) FROM Scolarite sa WHERE sa.eleve.classe.pension < (SELECT SUM(saa.versement) FROM Scolarite saa WHERE saa.ideleve = sa.ideleve)) as solde, \
                    (SELECT COUNT(sb.ideleve) FROM Scolarite sb WHERE sb.eleve.classe.pension > (SELECT SUM(sbb.versement) FROM Scolarite sbb WHERE sbb.ideleve = sb.ideleve)) as Acompte \
                     FROM Scolarite s  GROUP BY s.eleve.classe.classe \
                    """)
    List<Object[]> getListClasseSoldeAcompte();


    
}
