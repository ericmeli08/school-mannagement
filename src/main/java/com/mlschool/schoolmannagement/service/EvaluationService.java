package com.mlschool.schoolmannagement.service;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.mlschool.schoolmannagement.model.Eleve;
import com.mlschool.schoolmannagement.model.evaluation.Evaluation;
import com.mlschool.schoolmannagement.model.modelResult.Bilan;
import com.mlschool.schoolmannagement.model.modelResult.BilanGeneral;
import com.mlschool.schoolmannagement.model.modelResult.ResultListNoteByType;
import com.mlschool.schoolmannagement.model.modelResult.ResultMoyenneByClassePeriode;
import com.mlschool.schoolmannagement.repository.EleveRepository;
import com.mlschool.schoolmannagement.repository.EvaluationRepository;

@Service
public class EvaluationService {

    private DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance(Locale.US);
    
    private DecimalFormat decimalFormat = new DecimalFormat("#.##",decimalFormatSymbols); 

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private EleveService eleveService;

    public void save(Evaluation evaluation){
        evaluationRepository.save(evaluation);
    }

    public List<Evaluation> getEvaluationsbyAffectationAndPeriode(Integer idaffectation,String periode){
        return evaluationRepository.getEvaluationsbyAffectationAndPeriode(idaffectation, periode);
    }

    public String getFirstMoyenneByClasse(Integer idclasse,String periode){
        List<Object[]> list = evaluationRepository.getMoyenneAndRankByClasse(idclasse,periode);
        Double moy = (Double)(list.get(0)[2]); 
        return decimalFormat.format(moy); 
    }

    public String getLastMoyenneByClasse(Integer idclasse,String periode){
        List<Object[]> list = evaluationRepository.getMoyenneAndRankByClasse(idclasse,periode);
        System.out.println("\n\n\n\n\n\n\n\n\n ------  " + list.size() + "\n\n\n\n\n\n\n\n\n\n");  
        Double moy = (Double)(list.get(list.size()-1)[2]); 
        return decimalFormat.format(moy);
    }
   
    public String getMoyenneByClasse(Integer idclasse,String periode){
        List<Object[]> list = evaluationRepository.getMoyenneAndRankByClasse(idclasse,periode);

        Double sum = 0.0;
        for(Object[] objects : list){   
            sum += (Double) objects[2];
        } 
        sum /= list.size();

        return decimalFormat.format(sum);
    }

    public BilanGeneral getBilanGeneral(Integer ideleve, String periode) {

    Integer pos = 1;
    Double moy;
    Double total = 0.0;
    Long coefficients = Long.parseLong("0");
    Evaluation evaluation;

    Eleve eleve = eleveService.findById(ideleve);
    List<Object[]> list = evaluationRepository.getMoyenneAndRankByClasse(eleve.getIdclasse(), periode);
    Double moyenne = evaluationRepository.getMoyenneEleveById(ideleve, periode);

    for (Object[] objects : list) {
        evaluation = (Evaluation) objects[0];
        moy = (Double) objects[2];

        if (evaluation.getIdEleve() == ideleve) {
            total = (Double) objects[1];
            coefficients = (Long) objects[3];
        }
        if (moy > moyenne) {
            pos++;
        }
    }

    String rang = pos == 1 ? "1er" : pos + "eme";
    String moyenneStr = decimalFormat.format(total / coefficients);

    return new BilanGeneral(rang, total, coefficients, moyenneStr);
}


    public List<ResultListNoteByType> getListNoteEleveByTypeMatiereAndPeriode(Integer ideleve, String periode, Integer idType) {
        ArrayList<Object[]> list = evaluationRepository.getListNoteEleveByTypeMatiereAndPeriode(ideleve, periode, idType);
        List<ResultListNoteByType> tableau = new ArrayList<>();
        for (Object[] objects : list) {
            Evaluation evaluation = (Evaluation) objects[0];
            Long rang = (Long) objects[1];
            String appreciation = getAppreciation(evaluation.getNote());

            ResultListNoteByType result = new ResultListNoteByType(
                    evaluation,
                    rang == 0 ? "1er" : (rang+1) + "eme", 
                    appreciation
            );
            tableau.add(result);
        }
        return tableau;
    }

    public List<ResultMoyenneByClassePeriode> getMoyenneAndRankByClasse(Integer idclasse, String periode) {
        List<Object[]> list = evaluationRepository.getMoyenneAndRankByClasse(idclasse, periode);
        List<ResultMoyenneByClassePeriode> tableau = new ArrayList<>();
        for (Object[] objects : list) {
            ResultMoyenneByClassePeriode result = new ResultMoyenneByClassePeriode(
                    (Evaluation) objects[0],
                    (Double) objects[1],
                    Double.valueOf(decimalFormat.format((Double) objects[2])),
                    (Long) objects[3],
                    (tableau.size() + 1) == 1 ? "1er" : (tableau.size() + 1) + "eme"
            );
            tableau.add(result);
        }
        return tableau;
    }

    public String getAppreciation(Double note){
        String appreciation;
        if(note < 10){
            appreciation = "Mediocre";
        }else if(note < 12){
            appreciation = "Passable";
        }else if(note < 14){
            appreciation = "Assez bien";
        }else if(note < 16){
            appreciation = "Bien";
        }else if(note < 18){
            appreciation = "Tres bien";
        }else{
            appreciation = "Exellent";
        }

        return appreciation;
    }

    public Long getNombreEleveByClasse (Integer classe){
        return evaluationRepository.getNombreEleveByClasse(classe);
    }  
    
    public Bilan traitementBilan(Integer ideleve, String periode, Integer idType) {
    ArrayList<Object[]> list = evaluationRepository.getListNoteEleveByTypeMatiereAndPeriode(ideleve, periode, idType);

    Double totalNotes = 0.0;
    Integer totalCoefficients = 0;
    for (Object[] objects : list) {
        totalNotes += ((Evaluation) objects[0]).getNote() * ((Evaluation) objects[0]).getAffectation().getCoefficient();
        totalCoefficients += ((Evaluation) objects[0]).getAffectation().getCoefficient();
    }

    String moyenne = decimalFormat.format(totalNotes / totalCoefficients); // Calculer la moyenne

    return new Bilan(totalNotes, totalCoefficients, moyenne);
}

    public Long getRangEleveByIdClassePeriode (Integer ideleve,Integer idclasse, String periode){
        return evaluationRepository.getRangEleveById(ideleve,idclasse,periode);
    }  

}
