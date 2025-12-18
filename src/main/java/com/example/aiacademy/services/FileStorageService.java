package com.example.aiacademy.services;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path uploadRoot;

    public FileStorageService() throws IOException {
        this.uploadRoot = Paths.get("uploads").toAbsolutePath().normalize();
        Files.createDirectories(uploadRoot);
    }

    public String storeFile(MultipartFile file, String subFolder) {
        String original = StringUtils.cleanPath(file.getOriginalFilename());
        String ext = "";
        int idx = original.lastIndexOf('.');
        if (idx>0) ext = original.substring(idx);
        String fileName = UUID.randomUUID().toString()+ext;

        Path dir = uploadRoot;
        if (subFolder != null && !subFolder.isBlank()){
            dir  = uploadRoot.resolve(subFolder);
        }
        try {
            Files.createDirectories(dir);
            Path target = dir.resolve(fileName).normalize();
            try(var in = file.getInputStream()) {
                Files.copy(in,target, StandardCopyOption.REPLACE_EXISTING);
            }

            String publicPath = "/files/" + (subFolder == null ? "" : subFolder + "/") + fileName;
            return publicPath;
        }catch (IOException e){
            throw new RuntimeException("Failed to store file", e);
        }
    }


}
