package com.impacto.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.file.*;
import java.util.*;
@Service
public class StorageService {
    private final Path root;
    public StorageService(@Value("${storage.path}") String p)throws IOException {
        root=Paths.get(p).toAbsolutePath().normalize();
        Files.createDirectories(root);
    }
    public String store(MultipartFile file)throws IOException {
        if(file.isEmpty()||file.getSize()>10*1024*1024)throw new IOException("Archivo inválido");
        String ext="";
        String name=file.getOriginalFilename();
        if(name!=null&&name.contains("."))ext=name.substring(name.lastIndexOf('.'));
        String safe=UUID.randomUUID()+ext.replaceAll("[^a-zA-Z0-9.]","");
        Path target=root.resolve(safe).normalize();
        if(!target.getParent().equals(root))throw new IOException("Ruta inválida");
        Files.copy(file.getInputStream(),target,StandardCopyOption.REPLACE_EXISTING);
        return safe;
    }
}
