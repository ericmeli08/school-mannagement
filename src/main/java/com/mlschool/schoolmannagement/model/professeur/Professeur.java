package com.mlschool.schoolmannagement.model.professeur;

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
@Table(name = "professeur")
@Data
public class Professeur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String matricule;
    private String nom;
    private String prenom;
    private String sexe;
    private Date dateNaissance;
    private String lieuNaissance;
    private String email;
    private String adresse;
    private String contact;
    
    @CreatedDate
    private Date dateCreation;
    private String statut;
    private Date annee;

    private String photo;
    

    
}
