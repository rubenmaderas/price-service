package com.ecommerce.priceservice.application.services;

import com.ecommerce.priceservice.application.usecases.IPriceService;
import com.ecommerce.priceservice.domain.exceptions.PriceNotFoundException;
import com.ecommerce.priceservice.domain.models.PriceDomain;
import com.ecommerce.priceservice.domain.repository.PriceRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Service implementation for handling price-related operations.
 * Implements the IPriceService interface to provide business logic for retrieving prices.
 */
@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements IPriceService {

    /** Port for accessing price repository operations. */
    private final PriceRepositoryPort priceRepositoryPort;

    /**
     * Retrieves the price for a given product, brand, and application date.
     * Throws PriceNotFoundException if no price is found matching the criteria.
     *
     * @param productId       the ID of the product
     * @param brandId         the ID of the brand
     * @param applicationDate the date for which the price is requested
     * @return the PriceDomain object matching the criteria
     * @throws PriceNotFoundException if no price is found for the given parameters
     */
    @Override
    public PriceDomain getPrice(Long productId, Long brandId, LocalDateTime applicationDate) {
        return priceRepositoryPort.findByProductBrandAndDate(productId, brandId, applicationDate)
                .orElseThrow(() -> new PriceNotFoundException(
                        String.format("No price was found for productId %d, brandId %d with applicationDate %s",
                                productId, brandId, applicationDate)
                ));
    }
}