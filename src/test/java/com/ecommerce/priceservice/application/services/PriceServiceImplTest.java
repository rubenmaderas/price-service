package com.ecommerce.priceservice.application.services;

import com.ecommerce.priceservice.domain.exceptions.PriceNotFoundException;
import com.ecommerce.priceservice.domain.models.PriceDomain;
import com.ecommerce.priceservice.domain.repository.PriceRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PriceServiceImplTest {

    @Mock
    private PriceRepositoryPort priceRepositoryPort;

    @InjectMocks
    private PriceServiceImpl priceService;

    private final Long productId = 35455L;
    private final Long brandId = 1L;
    private final LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);

    private PriceDomain priceDomain;

    @BeforeEach
    void setUp() {

        priceDomain = new PriceDomain(
                brandId,
                productId,
                1,
                0,
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59),
                BigDecimal.valueOf(35.50),
                "EUR"
        );
    }

    @Test
    @DisplayName("Debería devolver un precio cuando existe")
    void shouldReturnPriceWhenExists() {
        when(priceRepositoryPort.findByProductBrandAndDate(productId, brandId, applicationDate))
                .thenReturn(Optional.of(priceDomain));

        PriceDomain result = priceService.getPrice(productId, brandId, applicationDate);

        assertNotNull(result);
        assertEquals(priceDomain.getProductId(), result.getProductId());
        assertEquals(priceDomain.getBrandId(), result.getBrandId());
        assertEquals(priceDomain.getPriceList(), result.getPriceList());
        assertEquals(0, priceDomain.getPrice().compareTo(result.getPrice()));

        verify(priceRepositoryPort, times(1)).findByProductBrandAndDate(productId, brandId, applicationDate);
    }

    @Test
    @DisplayName("Debería lanzar PriceNotFoundException cuando no existe precio")
    void shouldThrowExceptionWhenPriceNotFound() {
        when(priceRepositoryPort.findByProductBrandAndDate(productId, brandId, applicationDate))
                .thenReturn(Optional.empty());

        PriceNotFoundException exception = assertThrows(PriceNotFoundException.class, () -> {
            priceService.getPrice(productId, brandId, applicationDate);
        });

        assertTrue(exception.getMessage().contains("No price was found"));
        verify(priceRepositoryPort, times(1)).findByProductBrandAndDate(productId, brandId, applicationDate);
    }
}