package com.mlschool.schoolmannagement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mlschool.schoolmannagement.model.Eleve;
import com.mlschool.schoolmannagement.model.evaluation.Evaluation;
import com.mlschool.schoolmannagement.model.modelResult.ResultMoyenneByClassePeriode;
import com.mlschool.schoolmannagement.repository.EvaluationRepository;
import com.mlschool.schoolmannagement.service.EvaluationService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/test")
@AllArgsConstructor
public class Test {

    private final EvaluationService evaluationService;

    private final EvaluationRepository evaluationRepository;

 
    // @PostMapping("")
    // public Map<String,String>  showHome(@RequestParam Map<String,String> notes){
    //     return notes;
    // }

    @PostMapping("")
    public  ResultMoyenneByClassePeriode postConsultationmoyenne(Model model,@RequestParam String periode,@RequestParam Integer idclasse) {
        // List<Object[]> moyennes =  evaluationRepository.getMoyenneAndRankByClasse(idclasse, periode);

        // return moyennes;
        List<Object[]> list = evaluationRepository.getMoyenneAndRankByClasse(idclasse, periode);
        List<ResultMoyenneByClassePeriode> tableau = new ArrayList<>();
        for (Object[] objects : list) {
            ResultMoyenneByClassePeriode result = new ResultMoyenneByClassePeriode(
                    (Evaluation) objects[0],
                    (Double) objects[1],
                    (Double) objects[2],
                    (Long) objects[3],
                    // "1er" 
                    (tableau.size() + 1) == 1 ? "1er" : (tableau.size() + 1) + "eme"
            );
            tableau.add(result);
        }
        Evaluation eval = (Evaluation) list.get(0)[0];
        Double mo = (Double) list.get(0)[1];
        Double no = (Double) list.get(0)[2];
        Long bo = (Long) list.get(0)[3];
        Object v0 = (tableau.size() + 1) == 1 ? "1er" : (tableau.size() + 1) + "eme";
        return new ResultMoyenneByClassePeriode(
            eval,
            mo,
            no,
            bo,
            v0    );
    }
    // @PostMapping("")
    // public List<ResultMoyenneByClassePeriode> postConsultationmoyenne(Model model,@RequestParam String periode,@RequestParam Integer idclasse) {
    //     // List<Object[]> moyennes =  evaluationRepository.getMoyenneAndRankByClasse(idclasse, periode);

    //     // return moyennes;
    //     List<Object[]> list = evaluationRepository.getMoyenneAndRankByClasse(idclasse, periode);
    //     List<ResultMoyenneByClassePeriode> tableau = new ArrayList<>();
    //     for (Object[] objects : list) {
    //         ResultMoyenneByClassePeriode result = new ResultMoyenneByClassePeriode(
    //                 (Evaluation) objects[0],
    //                 (Double) objects[1],
    //                 (Double) objects[2],
    //                 (Long) objects[3],
    //                 // "1er" 
    //                 (tableau.size() + 1) == 1 ? "1er" : (tableau.size() + 1) + "eme"
    //         );
    //         tableau.add(result);
    //     }
    //     return tableau;
    // }
}
