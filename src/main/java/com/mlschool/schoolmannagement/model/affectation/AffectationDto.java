package com.mlschool.schoolmannagement.model.affectation;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;

import com.mlschool.schoolmannagement.model.classe.Classe;
import com.mlschool.schoolmannagement.model.matiere.Matiere;
import com.mlschool.schoolmannagement.model.professeur.Professeur;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class AffectationDto {

    private Integer id;

    private Integer idmatiere;

    private Integer coefficient;

    private Integer idclasse;

    private Integer idprofesseur;

    private Date createdDate;
}
