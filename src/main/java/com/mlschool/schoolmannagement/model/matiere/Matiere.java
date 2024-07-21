package com.mlschool.schoolmannagement.model.matiere;

import com.mlschool.schoolmannagement.model.typematiere.TypeMatiere;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Matiere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nom;
    
    @Column(name="idtypematiere")
    private Integer idtypematiere;

    @ManyToOne
    @JoinColumn(name="idtypematiere", insertable = false,updatable = false)
    private TypeMatiere typeMatiere;

}
