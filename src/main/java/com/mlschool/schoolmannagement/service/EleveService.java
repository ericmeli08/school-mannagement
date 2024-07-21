package com.mlschool.schoolmannagement.service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mlschool.schoolmannagement.model.Eleve;
import com.mlschool.schoolmannagement.repository.EleveRepository;

@Service
public class EleveService {

    @Autowired
    private EleveRepository eleveRepository;

    private static final String UPLOAD_DIR  = "public/images/";

    public Eleve getLastInsert(){
        return eleveRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).getFirst();
    }

    public void updateMatricule(){
        Eleve eleve = getLastInsert();
        String year = new SimpleDateFormat("yyyy").format(new Date());
        String lastTwoChars = year.substring(year.length() - 2);
        String matricule = lastTwoChars + "S" + formatNumber(eleve.getId());
        eleve.setMatricule(matricule);
        eleveRepository.save(eleve);
    }

    public static String formatNumber(int number) {
        return "%05d".formatted(number);
    }

    public void save(Eleve eleve){
        eleveRepository.save(eleve);
    }

    public void delete(Eleve eleve){
        eleveRepository.delete(eleve);
    }

    public void deleteImage(String fileName){
        Path oldImagePath = Paths.get(UPLOAD_DIR + fileName);
        try {
            Files.delete(oldImagePath);
        } catch (Exception e) {
            System.out.println("\n\n\n\n\n\n\n\n\n\n\nException : " + e.getMessage()+"\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        }
    }

    public String newSaveImage(MultipartFile image,String fileName){
        deleteImage(fileName);

        return saveImage(image);
    }

    public String saveImage(MultipartFile image){
        Date date = new Date();
        String storageFileName = date.getTime() + "_" + image.getOriginalFilename();

        try {

            Path uploadPath = Paths.get(UPLOAD_DIR);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            InputStream inputStream = image.getInputStream();
            Files.copy(inputStream, Paths.get(UPLOAD_DIR + storageFileName),StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
           System.out.println("\n\n\n\n\n\n\n\n\n\nException : " + e.getMessage()+" \n\n\n\n\n\n\n\n\n\n\n\n\n");
        }
        return storageFileName;
    }

    public Eleve findById(int i){
        Optional<Eleve> optional = eleveRepository.findById(i);
        if (optional.isEmpty()) {
            return null;
        }
        return optional.get();
    }

    public Eleve findByMatricule(String matricule){
        return eleveRepository.findByMatricule(matricule);
    }

    public Integer getIdFromMatricule(String matricule){
        try {
            if(matricule.length() < 7){
                return null;
            }
            return Integer.parseInt(matricule.substring(matricule.length() - 5));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public List<Eleve> findAll(Sort by ) {
        return eleveRepository.findAll(by);
    }

    public List<Eleve> findElevesByClasse(Integer idclasse){
        return eleveRepository.findElevesByClasse(idclasse);
    }

    public List<Eleve> findElevesByClasseAndMatiereNoNote(Integer idclasse,Integer idmatiere){
        return eleveRepository.findElevesByClasseAndMatiereNoNote(idclasse,idmatiere);
    }


    public ArrayList<Object> getClassesAndStudents(){
        ArrayList<Object> classes = new ArrayList<Object>();
        List<Object[]> students = eleveRepository.getClassesAndStudents();

        for (Object[] objects : students) {
            classes.add(new Object(){
                public String classe = objects[0].toString();
                public Long eleves = ((Long) objects[1]);
            });
        }
        return classes;
    }

    
}

