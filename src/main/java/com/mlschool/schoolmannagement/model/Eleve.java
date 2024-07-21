package com.mlschool.schoolmannagement.model;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;

import com.mlschool.schoolmannagement.model.classe.Classe;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "eleve")
@Data
public class Eleve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String matricule;
    private String nom;
    private String prenom;
    private String sexe;
    private Date dateNaissance;
    private String lieuNaissance;
    private String email;
    private String adresse;
    private String contact;
    private String parent;
    private String contactParent;
    
    @CreatedDate
    private Date dateCreation;
    private String periode;
    private Date annee;

    @Column(name = "idclasse")
    private Integer idclasse;

    @ManyToOne
    @JoinColumn(name = "idclasse",insertable = false, updatable = false)
    private Classe classe;

    private String photo;
    

    
}
