package com.mlschool.schoolmannagement.model.modelResult;

import com.mlschool.schoolmannagement.model.evaluation.Evaluation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ResultMoyenneByClassePeriode {

    private Evaluation evaluation;
    private Double total;
    private Double moyenne;
    private Long coefficients;
    private Object rang;

    public ResultMoyenneByClassePeriode(Evaluation evaluation,Double total,Double moyenne,Long coefficients,Object rang){
        this.evaluation = evaluation;
        this.total = total;
        this.moyenne = moyenne;
        this.coefficients = coefficients;
        this.rang = rang;
    }
}
