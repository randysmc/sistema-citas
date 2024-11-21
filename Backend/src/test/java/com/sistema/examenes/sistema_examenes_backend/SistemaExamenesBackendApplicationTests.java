package com.sistema.examenes.sistema_examenes_backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class SistemaExamenesBackendApplicationTests {

	@Test
	void contextLoads() {
		// Este método asegura que la aplicación se carga correctamente
	}

	@Test
	void testMainMethod() {
		// Llamamos al método main de la aplicación para cubrirlo en Jacoco
		SistemaExamenesBackendApplication.main(new String[] {});
	}
}
