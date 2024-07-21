package com.mlschool.schoolmannagement.model.affectation;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;

import com.mlschool.schoolmannagement.model.classe.Classe;
import com.mlschool.schoolmannagement.model.matiere.Matiere;
import com.mlschool.schoolmannagement.model.professeur.Professeur;
import com.mlschool.schoolmannagement.model.typematiere.TypeMatiere;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Affectation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer idmatiere;

    private Integer coefficient;

    @Column(name="idclasse")
    private Integer idclasse;

    @Column(name = "idprofesseur")
    private Integer idprofesseur;

    @ManyToOne
    @JoinColumn(name="idclasse", insertable = false,updatable = false)
    private Classe classe;

    @ManyToOne
    @JoinColumn(name="idprofesseur", insertable = false,updatable = false)
    private Professeur professeur;

    @ManyToOne
    @JoinColumn(name="idmatiere", insertable = false,updatable = false)
    private Matiere matiere;

    @CreatedDate
    @Column(name="createddate")
    private Date createdDate;
    
}
