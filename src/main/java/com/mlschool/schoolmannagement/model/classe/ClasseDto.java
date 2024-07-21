package com.mlschool.schoolmannagement.model.classe;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Null;
import lombok.Data;

@Data
public class ClasseDto {

    private Integer id;

    @NotEmpty(message = "la classe est obligatoire !")
    private String classe; 

    @Null(message = "la pension est obligatoire")
    private Double pension; 

    private Date dateCreation;
    
}
