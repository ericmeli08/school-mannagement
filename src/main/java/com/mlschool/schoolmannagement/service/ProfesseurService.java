package com.mlschool.schoolmannagement.service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mlschool.schoolmannagement.model.professeur.Professeur;
import com.mlschool.schoolmannagement.repository.ProfesseurRepository;
import com.mlschool.schoolmannagement.repository.ProfesseurRepository;

@Service
public class ProfesseurService {

    @Autowired
    private ProfesseurRepository professeurRepository;

    private static final String UPLOAD_DIR  = "public/images/";

    public void updateMatricule(){
        Professeur professeur = getLastInsert();
        String year = new SimpleDateFormat("yyyy").format(new Date());
        String lastTwoChars = year.substring(year.length() - 2);
        String matricule = "P"+lastTwoChars + "S" + formatNumber(professeur.getId());
        professeur.setMatricule(matricule);
        professeurRepository.save(professeur);
    }

    public static String formatNumber(int number) {
        return "%05d".formatted(number);
    }

    public void save(Professeur Professeur){
        professeurRepository.save(Professeur);
    }

    public void delete(Professeur Professeur){
        professeurRepository.delete(Professeur);
    }

    public void deleteImage(String fileName){
        Path oldImagePath = Paths.get(UPLOAD_DIR + fileName);
        try {
            Files.delete(oldImagePath);
        } catch (Exception e) {
            System.out.println("\n\n\n\n\n\n\n\n\n\n\nException : " + e.getMessage()+"\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        }
    }

    public Professeur getLastInsert(){
        return professeurRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).getFirst();
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

    public Professeur findById(int i){
        Optional<Professeur> optional = professeurRepository.findById(i);
        if (optional.isEmpty()) {
            return null;
        }
        return optional.get();
    }

    public Professeur findByMatricule(String matricule){
        return professeurRepository.findByMatricule(matricule).getFirst();
    }

    public List<Professeur> findAll(Sort by) {
        return professeurRepository.findAll(by);
    }

}
