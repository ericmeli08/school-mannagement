package com.mlschool.schoolmannagement.model.scolarite;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;

import com.mlschool.schoolmannagement.model.Eleve;

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
@Data
@Table(name = "scolarite")
public class Scolarite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ideleve", nullable = false)
    private Integer ideleve; 

    @ManyToOne
    @JoinColumn(name = "ideleve",insertable = false, updatable = false)
    private Eleve eleve;

    @Column(name = "versement", nullable = false)
    private double versement;

    @CreatedDate  
    private Date dateversement;


}

