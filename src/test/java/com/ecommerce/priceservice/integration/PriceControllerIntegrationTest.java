package com.ecommerce.priceservice.integration;

import com.ecommerce.priceservice.infrastructure.adapters.in.dto.PriceResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PriceControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final Long PRODUCT_ID = 35455L;
    private static final Long BRAND_ID = 1L;

    private String getUrl(Long productId, Long brandId, String dateTime) {
        return String.format("http://localhost:%d/brands/%d/products/%d/prices?applicationDate=%s",
                port, brandId, productId, dateTime);
    }

    private void assertPrice(ResponseEntity<PriceResponseDto> response,
                             int expectedPriceList,
                             BigDecimal expectedPrice) {
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(PRODUCT_ID, response.getBody().getProductId());
        assertEquals(BRAND_ID, response.getBody().getBrandId());
        assertEquals(expectedPriceList, response.getBody().getPriceList());
        assertEquals(0, expectedPrice.compareTo(response.getBody().getPrice()));
    }

    @Nested
    @DisplayName("Tests de precios disponibles")
    class AvailablePrices {

        @Test
        @DisplayName("Petición a las 10:00 del 14-06-2020 para producto 35455 y brand 1 (ZARA)")
        void shouldReturnPrice_Tariff1() {
            String dateTime = "2020-06-14T10:00:00";
            ResponseEntity<PriceResponseDto> response = restTemplate.getForEntity(
                    getUrl(PRODUCT_ID, BRAND_ID, dateTime),
                    PriceResponseDto.class
            );
            assertPrice(response, 1, BigDecimal.valueOf(35.50));
        }

        @Test
        @DisplayName("Petición a las 16:00 del 14-06-2020 para producto 35455 y brand 1 (ZARA)")
        void shouldReturnPrice_Tariff2_HighPriority() {
            String dateTime = "2020-06-14T16:00:00";
            ResponseEntity<PriceResponseDto> response = restTemplate.getForEntity(
                    getUrl(PRODUCT_ID, BRAND_ID, dateTime),
                    PriceResponseDto.class
            );
            assertPrice(response, 2, BigDecimal.valueOf(25.45));
        }

        @Test
        @DisplayName("Petición a las 10:00 del 15-06-2020 para producto 35455 y brand 1 (ZARA)")
        void shouldReturnPrice_Tariff3() {
            String dateTime = "2020-06-15T10:00:00";
            ResponseEntity<PriceResponseDto> response = restTemplate.getForEntity(
                    getUrl(PRODUCT_ID, BRAND_ID, dateTime),
                    PriceResponseDto.class
            );
            assertPrice(response, 3, BigDecimal.valueOf(30.50));
        }

        @Test
        @DisplayName("Petición a las 21:00 del 16-06-2020 para producto 35455 y brand 1 (ZARA)")
        void shouldReturnPrice_Tariff4() {
            String dateTime = "2020-06-16T21:00:00";
            ResponseEntity<PriceResponseDto> response = restTemplate.getForEntity(
                    getUrl(PRODUCT_ID, BRAND_ID, dateTime),
                    PriceResponseDto.class
            );
            assertPrice(response, 4, BigDecimal.valueOf(38.95));
        }
    }

    @Nested
    @DisplayName("Tests de errores")
    class ErrorCases {

        @Test
        @DisplayName("Petición a las 10:00 del 14-06-2020 para producto inexistente y brand 1 (ZARA)")
        void shouldReturnNotFound_WhenNoPriceForInvalidProduct() {
            Long invalidProductId = 99999L;
            String dateTime = "2020-06-14T10:00:00";
            ResponseEntity<String> response = restTemplate.getForEntity(
                    getUrl(invalidProductId, BRAND_ID, dateTime),
                    String.class
            );
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNotNull(response.getBody());
            assertTrue(response.getBody().contains("No price was found"));
        }

        @Test
        @DisplayName("Petición fuera de rango de fechas para producto 35455 y brand 1 (ZARA)")
        void shouldReturnNotFound_WhenNoPriceForDate() {
            String dateTime = "2019-01-01T00:00:00";
            ResponseEntity<String> response = restTemplate.getForEntity(
                    getUrl(PRODUCT_ID, BRAND_ID, dateTime),
                    String.class
            );
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }
    }
}