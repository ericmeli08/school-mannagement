package com.mlschool.schoolmannagement.model.modelResult;

import com.mlschool.schoolmannagement.model.evaluation.Evaluation;

import lombok.Data;

@Data
public class ResultListNoteByType {
     private Evaluation evaluation;
    private String rang;
    private String appreciation;

   
    public ResultListNoteByType(Evaluation evaluation, String rang, String appreciation) {
        this.evaluation = evaluation;
        this.rang = rang;
        this.appreciation = appreciation;
    }
}
