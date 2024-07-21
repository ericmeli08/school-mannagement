package com.mlschool.schoolmannagement.model.modelResult;

import lombok.Data;

@Data
public class BilanGeneral {
    
    private String rang;
    private Double totalNote;
    private Long totalCoef;
    private String moyenne;

    public BilanGeneral(String rang, Double totalNote, Long totalCoef, String moyenne) {
        this.rang = rang;
        this.totalNote = totalNote;
        this.totalCoef = totalCoef;
        this.moyenne = moyenne;
    }
}
