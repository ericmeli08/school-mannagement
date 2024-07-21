package com.mlschool.schoolmannagement.service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mlschool.schoolmannagement.model.Eleve;
import com.mlschool.schoolmannagement.model.ecole.Ecole;
import com.mlschool.schoolmannagement.repository.EcoleRepository;

@Service
public class EcoleService {

    @Autowired
    private EcoleRepository ecoleRepository;

    private static final String UPLOAD_DIR  = "public/images/";


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

    public void save(Ecole ecole){
        ecoleRepository.save(ecole);
    }

    public List<Ecole> findAll(Sort by ) {
        return ecoleRepository.findAll(by);
    }
    
}
