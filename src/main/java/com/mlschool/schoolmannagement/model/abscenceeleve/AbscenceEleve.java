package com.mlschool.schoolmannagement.model.abscenceeleve;

import java.util.Date;

import com.mlschool.schoolmannagement.model.Eleve;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class AbscenceEleve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name= "dateabscence")
    private Date dateAbscence;

    @Column(name="nombreheure")
    private Integer nombreHeure;

    @Column(name="statut")
    private String statut;

    @Column(name="ideleve")
    private Integer idEleve; 

    @ManyToOne
    @JoinColumn(name="ideleve",insertable=false,updatable=false)
    private Eleve eleve; 
}
