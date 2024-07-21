package com.mlschool.schoolmannagement.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.mlschool.schoolmannagement.model.classe.Classe;
import com.mlschool.schoolmannagement.model.Eleve;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;



@Data
public class EleveDto {
    private int id;

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd") ;

    @NotEmpty(message = "Le nom est obligatoire !")
    private String nom;

    @NotEmpty(message = "Le prenom est obligatoire !")
    private String prenom;
    
    @NotNull(message = "Le sexe est obligatoire !")
    private String sexe;

    private String dateNaissance;

    @NotEmpty(message = "Le Lieu est obligatoire !")
    private String lieuNaissance;

    private String email;

    @NotEmpty(message = "L'adresse est obligatoire !")
    private String adresse;
    
    @Size(min = 8 ,message = "Le contact doit avoir au moins 8 chiffres")
    @Size(max = 15 ,message = "Le contact ne doit pas avoir moins de 15 chiffres")
    private String contact;

    @NotEmpty(message = "Le parent est obligatoire !")
    private String parent;
 
    @Size(min = 8 ,message = "Le contact doit avoir au moins 8 chiffres")
    @Size(max = 15 ,message = "Le contact ne doit pas avoir moins de 15 chiffres")
    private String contactParent;

    @NotEmpty(message = "La periode est obligatoire !")
    private String periode;

    @NotEmpty(message = "L'annee est obligatoire !")
    private String annee;

    @NotNull(message = "La classe est obligatoire !")
    private Integer idclasse;
  
    private String storageFileName;

    private String matricule;
    private Classe classe;
    private String dateCreation;

    private MultipartFile photo;

    public void transfertDataToEleveDto(Eleve eleve){
        setId(eleve.getId());
        setMatricule(eleve.getMatricule());
        setAdresse(eleve.getAdresse());
        setAnnee(simpleDateFormat.format(eleve.getAnnee()) );
        setContact(eleve.getContact());
        setContactParent(eleve.getContactParent());
        setLieuNaissance(eleve.getLieuNaissance());
        setDateNaissance(simpleDateFormat.format(eleve.getDateNaissance()));
        setDateCreation(eleve.getDateCreation().toString() );
        if (!eleve.getEmail().isEmpty()) {
            setEmail(eleve.getEmail());
        }
        setIdclasse(eleve.getIdclasse());
        setClasse(eleve.getClasse());
        setNom(eleve.getNom());
        setParent(eleve.getParent());
        setPeriode(eleve.getPeriode());
        setStorageFileName(eleve.getPhoto());
        setPrenom(eleve.getPrenom());
        setSexe(eleve.getSexe());
    }

    public void transfertDataToEleve(Eleve eleve){
        eleve.setId(getId());
        eleve.setMatricule(getMatricule());
        eleve.setAdresse(getAdresse());
        try {
            eleve.setAnnee(simpleDateFormat.parse(getAnnee()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        eleve.setContact(getContact());
        eleve.setContactParent(getContactParent());
        eleve.setLieuNaissance(getLieuNaissance());
        try {
            eleve.setDateNaissance(simpleDateFormat.parse(getDateNaissance()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        eleve.setDateCreation(new Date());
        
        if (!getEmail().isEmpty()) {
            eleve.setEmail(getEmail());
        }
        eleve.setIdclasse(getIdclasse());
        eleve.setNom(getNom());
        eleve.setParent(getParent());
        eleve.setPeriode(getPeriode());
        if(getStorageFileName() != null && !getStorageFileName().isEmpty()){
            eleve.setPhoto(getStorageFileName());
        }
        eleve.setPrenom(getPrenom());
        eleve.setSexe(getSexe());
    }
    
}
