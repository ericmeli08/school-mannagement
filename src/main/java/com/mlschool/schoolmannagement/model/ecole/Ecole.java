package com.mlschool.schoolmannagement.model.ecole;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Ecole {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;

    private String adresse;

    private String email;

    private String logo;
   
    private String ville;

    private String contact;

    private String directeur;

    private Date annee;
}
