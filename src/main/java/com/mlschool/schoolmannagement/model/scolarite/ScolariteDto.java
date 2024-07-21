package com.mlschool.schoolmannagement.model.scolarite;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ScolariteDto {


    private int id;

    private Integer ideleve;

    @NotEmpty(message = "le versement est obligatoire !")
    private double versement;

    private Date dateversement;

}

