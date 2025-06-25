package com.ecommerce.priceservice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class PriceServiceApplicationTest {

	@Test
	@DisplayName("Contexto de Spring se carga correctamente sin errores")
	void contextLoads() {
		assertTrue(true);
	}

	@Test
	@DisplayName("El metodo main se ejecuta sin lanzar excepciones")
	void mainMethod_ShouldRunWithoutExceptions() {
		assertDoesNotThrow(() -> PriceServiceApplication.main(new String[]{}));
	}
}
