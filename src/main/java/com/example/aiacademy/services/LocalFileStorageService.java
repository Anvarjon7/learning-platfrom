package com.example.aiacademy.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class LocalFileStorageService implements FileStorageService {

    @Value("${file.storage.location}")
    private String storageLocation;


    public String storeFile(MultipartFile file, String folderName) {
        try {
            // Create folder path
            Path folder = Paths.get(storageLocation).resolve(folderName);
            Files.createDirectories(folder);

            // Normalize filename
            String filename = StringUtils.cleanPath(file.getOriginalFilename());

            if (filename.contains("..")) {
                throw new RuntimeException("Invalid filename: " + filename);
            }

            Path targetPath = folder.resolve(filename);

            // Save file
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            // Return URL path for frontend or storage reference
            return "/uploads/" + folderName + "/" + filename;

        } catch (IOException e) {
            throw new RuntimeException("File storage failed: " + e.getMessage());
        }
    }
}
