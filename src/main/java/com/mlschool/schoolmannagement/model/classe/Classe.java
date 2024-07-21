package com.mlschool.schoolmannagement.model.classe;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Classe {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "classe")
    private String classe; 

    @Column(name = "pension")
    private Double pension; 

    @Column(name = "datecreation")
    @CreatedDate
    private Date dateCreation;
    
}
