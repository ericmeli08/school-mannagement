package com.mlschool.schoolmannagement.model.modelResult;

import lombok.Data;

@Data
public class Bilan {
    
    private Double totalNote;
    private Integer totalCoefficient;
    private String moyenne;

    public Bilan(Double totalNote, Integer totalCoefficient, String moyenne) {
        this.totalNote = totalNote;
        this.totalCoefficient = totalCoefficient;
        this.moyenne = moyenne;
    }
}
