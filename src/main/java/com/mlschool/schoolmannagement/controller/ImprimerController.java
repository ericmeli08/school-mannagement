package com.mlschool.schoolmannagement.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.DocumentException;
import com.mlschool.schoolmannagement.service.BulletinService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ImprimerController {

    
    private final BulletinService bulletinService;

    @PostMapping("/generate-pdf")
    public ResponseEntity<Resource> generatePDF(@RequestParam String periode,@RequestParam Integer id) throws IOException {
        String file = "";
        try {
            file = bulletinService.Imprimer(periode, id);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        
        Path path = Paths.get(file); 

        Resource resource = new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/pdf"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
