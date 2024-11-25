package com.sistema.examenes.sistema_examenes_backend.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /uploads/{folder}/{filename} - Imagen encontrada")
    void testGetImageSuccess() throws Exception {
        // Datos simulados
        String folder = "images";
        String filename = "example.jpg";
        Path filePath = Paths.get("src/main/resources/static/uploads/" + folder + "/" + filename);

        // Crear un archivo simulado en el sistema de archivos
        Files.createDirectories(filePath.getParent()); // Asegura que el directorio exista
        Files.createFile(filePath); // Crea el archivo simulado

        // Realizar la petición usando MockMvc
        mockMvc.perform(get("/uploads/{folder}/{filename}", folder, filename))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG))
                .andDo(print());

        // Limpieza
        Files.deleteIfExists(filePath); // Elimina el archivo después de la prueba
    }


    @Test
    @DisplayName("GET /uploads/{folder}/{filename} - Imagen no encontrada")
    void testGetImageNotFound() throws Exception {
        // Datos simulados
        String folder = "images";
        String filename = "nonexistent.jpg";

        // Asegura que el archivo no exista
        Path filePath = Paths.get("src/main/resources/static/uploads/" + folder + "/" + filename);
        Files.deleteIfExists(filePath);

        // Petición al controlador
        mockMvc.perform(get("/uploads/{folder}/{filename}", folder, filename))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

}
