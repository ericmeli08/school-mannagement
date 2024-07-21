package com.mlschool.schoolmannagement.model.evaluation;

import java.util.Date;

import com.mlschool.schoolmannagement.model.Eleve;
import com.mlschool.schoolmannagement.model.affectation.Affectation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
public class Evaluation {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double note;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name="idaffectation")
    private Integer idAffectation;

    @Column(name="ideleve")
    private Integer idEleve; 

    @Column(name="periode")
    private String periode; 

    @ManyToOne
    @JoinColumn(name="ideleve",insertable=false,updatable=false)
    private Eleve eleve; 
   
    @ManyToOne
    @JoinColumn(name="idaffectation",insertable=false,updatable=false)
    private Affectation affectation; 
}
