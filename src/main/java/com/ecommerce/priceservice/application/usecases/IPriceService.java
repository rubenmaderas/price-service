package com.ecommerce.priceservice.application.usecases;

import com.ecommerce.priceservice.domain.models.PriceDomain;

import java.time.LocalDateTime;

/**
 * Interface for price service use cases.
 * Defines the contract for retrieving the applicable price for a product, brand, and application date.
 */
public interface IPriceService {
    /**
     * Retrieves the applicable price for a given product, brand, and application date.
     *
     * @param productId       the identifier of the product
     * @param brandId         the identifier of the brand
     * @param applicationDate the date and time when the price is to be applied
     * @return the PriceDomain object containing the price details
     */
    PriceDomain getPrice(Long productId, Long brandId, LocalDateTime applicationDate);
}