package com.mlschool.schoolmannagement.model.ecole;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


@Data
public class EcoleDto {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd") ;
    
    private int id;

    @NotEmpty(message = "Le nom est obligatoire !")
    private String nom;

    @NotEmpty(message = "L'adresse est obligatoire !")
    private String adresse;

    @NotEmpty(message = "Le email est obligatoire !")
    private String email;

    private MultipartFile file;

    @NotEmpty(message = "Le contact est obligatoire !")
    private String contact;

    @NotEmpty(message = "La ville est obligatoire !")
    private String ville;

    private String logo;

    @NotEmpty(message = "Le nom du directeur est obligatoire !")
    private String directeur;

    @NotEmpty(message = "L'annee academique est obligatoire !")
    private String annee;


    public void transfertDataToEcole(Ecole ecole){
        ecole.setId(getId());
        ecole.setNom(getNom());
        ecole.setAdresse(getAdresse());
        ecole.setEmail(getEmail());
        ecole.setVille(getVille());
        ecole.setContact(getContact());
        ecole.setDirecteur(getDirecteur());
        ecole.setLogo(getLogo());
        System.out.println("\n\n\n\n\n\n\n\n\n   "+ getAnnee()+ "\n\n\n\n\n\n\n\n\n\n");
        try {
            ecole.setAnnee(simpleDateFormat.parse(getAnnee()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
