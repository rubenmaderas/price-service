package com.ecommerce.priceservice.infrastructure.adapters.out.persistence.h2;

import com.ecommerce.priceservice.domain.models.PriceDomain;
import com.ecommerce.priceservice.domain.repository.PriceRepositoryPort;
import com.ecommerce.priceservice.infrastructure.adapters.out.persistence.h2.mappers.PriceEntityMapper;
import com.ecommerce.priceservice.infrastructure.adapters.out.persistence.h2.repository.JpaPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Adapter class responsible for handling persistence operations related to prices
 * using an H2 database. Implements the PriceRepositoryPort interface to provide
 * access to price data from the infrastructure layer.
 */
@Repository
@RequiredArgsConstructor
public class PricePersistenceAdapter implements PriceRepositoryPort {

    private final JpaPriceRepository jpaPriceRepository;
    private final PriceEntityMapper priceEntityMapper;

    /**
     * Finds the price for a given product, brand, and application date.
     * Retrieves the highest priority price within the validity period and maps it to the domain model.
     *
     * @param productId       the identifier of the product
     * @param brandId         the identifier of the brand
     * @param applicationDate the date for which the price is requested
     * @return an Optional containing the PriceDomain object matching the criteria, if present
     */
    @Override
    public Optional<PriceDomain> findByProductBrandAndDate(Long productId, Long brandId, LocalDateTime applicationDate) {
        return jpaPriceRepository
                .findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        productId, brandId, applicationDate, applicationDate)
                .map(priceEntityMapper::toDomain);
    }

}