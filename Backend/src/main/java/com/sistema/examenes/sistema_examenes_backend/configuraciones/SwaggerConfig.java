package com.sistema.examenes.sistema_examenes_backend.configuraciones;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi apiDocket() {
        return GroupedOpenApi.builder()
                .group("citas-api") // Puedes cambiar el nombre del grupo
                .packagesToScan("com.sistema.examenes.sistema_examenes_backend.controladores")
                .pathsToMatch("/**") // Incluye todas las rutas
                .build();
    }
}
