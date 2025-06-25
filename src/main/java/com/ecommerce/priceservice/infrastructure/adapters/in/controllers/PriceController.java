package com.ecommerce.priceservice.infrastructure.adapters.in.controllers;

import com.ecommerce.priceservice.application.usecases.IPriceService;
import com.ecommerce.priceservice.domain.models.PriceDomain;
import com.ecommerce.priceservice.infrastructure.adapters.in.dto.PriceResponseDto;
import com.ecommerce.priceservice.infrastructure.adapters.in.mappers.PriceDtoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * REST controller for handling price queries.
 * Exposes an endpoint to retrieve the applicable price for a given product,
 * brand, and application date.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/brands/{brandId}/products/{productId}")
public class PriceController {

    private final IPriceService priceService;
    private final PriceDtoMapper priceDtoMapper;

    /**
     * Retrieves the price for a given product, brand, and application date.
     *
     * @param productId        the ID of the product
     * @param brandId          the ID of the brand
     * @param applicationDate  the date and time of application
     * @return a ResponseEntity containing the PriceResponseDto with the applicable price
     */
    @GetMapping("/prices")
    public ResponseEntity<PriceResponseDto> getPrice(
            @PathVariable Long productId,
            @PathVariable Long brandId,
            @RequestParam("applicationDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @NotNull LocalDateTime applicationDate
    ) {
        PriceDomain price = priceService.getPrice(productId, brandId, applicationDate);
        return ResponseEntity.ok(priceDtoMapper.toResponseDto(price));
    }
}