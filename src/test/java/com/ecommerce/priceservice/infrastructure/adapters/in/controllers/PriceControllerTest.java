package com.ecommerce.priceservice.infrastructure.adapters.in.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String BASE_URL = "/brands/{brandId}/products/{productId}/prices";

    @Nested
    @DisplayName("Tests de precios disponibles")
    class AvailablePrices {

        @Test
        @DisplayName("Petición a las 10:00 del 14-06-2020 para producto 35455 y brand 1 (ZARA)")
        void test1() throws Exception {
            mockMvc.perform(get(BASE_URL, 1, 35455)
                            .param("applicationDate", "2020-06-14T10:00:00"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.priceList", is(1)))
                    .andExpect(jsonPath("$.price", is(35.50)));
        }

        @Test
        @DisplayName("Petición a las 16:00 del 14-06-2020 para producto 35455 y brand 1 (ZARA)")
        void test2() throws Exception {
            mockMvc.perform(get(BASE_URL, 1, 35455)
                            .param("applicationDate", "2020-06-14T16:00:00"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.priceList", is(2)))
                    .andExpect(jsonPath("$.price", is(25.45)));
        }

        @Test
        @DisplayName("Petición a las 21:00 del 14-06-2020 para producto 35455 y brand 1 (ZARA)")
        void test3() throws Exception {
            mockMvc.perform(get(BASE_URL, 1, 35455)
                            .param("applicationDate", "2020-06-14T21:00:00"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.priceList", is(1)))
                    .andExpect(jsonPath("$.price", is(35.50)));
        }

        @Test
        @DisplayName("Petición a las 10:00 del 15-06-2020 para producto 35455 y brand 1 (ZARA)")
        void test4() throws Exception {
            mockMvc.perform(get(BASE_URL, 1, 35455)
                            .param("applicationDate", "2020-06-15T10:00:00"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.priceList", is(3)))
                    .andExpect(jsonPath("$.price", is(30.50)));
        }

        @Test
        @DisplayName("Petición a las 21:00 del 16-06-2020 para producto 35455 y brand 1 (ZARA)")
        void test5() throws Exception {
            mockMvc.perform(get(BASE_URL, 1, 35455)
                            .param("applicationDate", "2020-06-16T21:00:00"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.priceList", is(4)))
                    .andExpect(jsonPath("$.price", is(38.95)));
        }
    }

    @Nested
    @DisplayName("Tests de manejo de errores y validaciones")
    class ErrorCases {

        @Test
        @DisplayName("Debe devolver 404 Not Found cuando no existe precio para productId inexistente")
        void testNoPriceFound() throws Exception {
            mockMvc.perform(get(BASE_URL, 1, 99999)
                            .param("applicationDate", "2020-06-14T10:00:00"))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.messages[0]", containsString("No price was found")));
        }

        @Test
        @DisplayName("Debe devolver 400 Bad Request cuando productId no es numérico")
        void shouldReturnBadRequest_WhenProductIdIsInvalid() throws Exception {
            mockMvc.perform(get(BASE_URL, 1, "abc")
                            .param("applicationDate", "2020-06-14T10:00:00"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error", is("Bad Request")));
        }

        @Test
        @DisplayName("Debe devolver 400 Bad Request cuando applicationDate tiene formato inválido")
        void shouldReturnBadRequest_WhenApplicationDateIsInvalidFormat() throws Exception {
            mockMvc.perform(get(BASE_URL, 1, 35455)
                            .param("applicationDate", "invalid-date-format"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error", is("Bad Request")));
        }

        @Test
        @DisplayName("Debe devolver 400 Bad Request cuando falta el parámetro applicationDate")
        void shouldReturnBadRequest_WhenApplicationDateIsMissing() throws Exception {
            mockMvc.perform(get(BASE_URL, 1, 35455))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error", is("Validation Error")))
                    .andExpect(jsonPath("$.messages[0]", containsString("Missing required parameter")));
        }
    }
}