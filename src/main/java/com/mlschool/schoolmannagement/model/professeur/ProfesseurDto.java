package com.mlschool.schoolmannagement.model.professeur;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;



@Data
public class ProfesseurDto {
    private int id;

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd") ;

    @NotEmpty(message = "Le nom est obligatoire !")
    private String nom;

    @NotEmpty(message = "Le prenom est obligatoire !")
    private String prenom;
    
    @NotNull(message = "Le sexe est obligatoire !")
    private String sexe;

    @NotEmpty(message = "La date de naissance est obligatoire !")
    private String dateNaissance;

    @NotEmpty(message = "Le Lieu est obligatoire !")
    private String lieuNaissance;

    private String email;

    @NotEmpty(message = "L'adresse est obligatoire !")
    private String adresse;
    
    @Size(min = 8 ,message = "Le contact doit avoir au moins 8 chiffres")
    @Size(max = 15 ,message = "Le contact ne doit pas avoir moins de 15 chiffres")
    private String contact;

    @NotEmpty(message = "La statut est obligatoire !")
    private String statut;

    @NotEmpty(message = "L'annee est obligatoire !")
    private String annee;
  
    private String storageFileName;

    private String matricule;
    private String dateCreation;

    private MultipartFile photo;

    public void transfertDataToProfesseurDto(Professeur Professeur){
        setId(Professeur.getId());
        setMatricule(Professeur.getMatricule());
        setAdresse(Professeur.getAdresse());
        setAnnee(simpleDateFormat.format(Professeur.getAnnee()) );
        setContact(Professeur.getContact());
        setLieuNaissance(Professeur.getLieuNaissance());
        setDateNaissance(simpleDateFormat.format(Professeur.getDateNaissance()));
        setDateCreation(Professeur.getDateCreation().toString() );
        if (!Professeur.getEmail().isEmpty()) {
            setEmail(Professeur.getEmail());
        }
        setNom(Professeur.getNom());
        setStatut(Professeur.getStatut());
        setStorageFileName(Professeur.getPhoto());
        setPrenom(Professeur.getPrenom());
        setSexe(Professeur.getSexe());
    }

    public void transfertDataToProfesseur(Professeur professeur){
        professeur.setId(getId());
        professeur.setMatricule(getMatricule());
        professeur.setAdresse(getAdresse());
        try {
            professeur.setAnnee(simpleDateFormat.parse(getAnnee()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        professeur.setContact(getContact());
        professeur.setLieuNaissance(getLieuNaissance());
        try {
            professeur.setDateNaissance(simpleDateFormat.parse(getDateNaissance()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        professeur.setDateCreation(new Date());
        
        if (!getEmail().isEmpty()) {
            professeur.setEmail(getEmail());
        }
        professeur.setNom(getNom());
        professeur.setStatut(getStatut());
        if(getStorageFileName() != null && !getStorageFileName().isEmpty()){
            professeur.setPhoto(getStorageFileName());
        }
        professeur.setPrenom(getPrenom());
        professeur.setSexe(getSexe());
    }
    
}
