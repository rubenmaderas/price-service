package com.ecommerce.priceservice.domain.repository;

import com.ecommerce.priceservice.domain.models.PriceDomain;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Repository port interface for accessing price data.
 * Defines the contract for retrieving prices by product, brand, and application date.
 */
public interface PriceRepositoryPort {

    /**
     * Finds the price for a product and brand at a specific date.
     *
     * @param productId       the ID of the product
     * @param brandId         the ID of the brand
     * @param applicationDate the date for which the price is requested
     * @return an Optional containing the PriceDomain object matching the criteria, if present
     */
    Optional<PriceDomain> findByProductBrandAndDate(Long productId, Long brandId, LocalDateTime applicationDate);
}