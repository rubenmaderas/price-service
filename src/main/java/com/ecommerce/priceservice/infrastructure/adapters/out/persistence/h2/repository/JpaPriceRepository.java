package com.ecommerce.priceservice.infrastructure.adapters.out.persistence.h2.repository;

import com.ecommerce.priceservice.infrastructure.adapters.out.persistence.h2.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Repository interface for accessing price data in the H2 database.
 * Extends JpaRepository to provide CRUD operations for PriceEntity.
 */
public interface JpaPriceRepository extends JpaRepository<PriceEntity, Long> {

    /**
     * Finds the highest priority price for a given product and brand,
     * where the application date is within the validity period.
     *
     * @param productId                the product identifier
     * @param brandId                  the brand identifier
     * @param applicationDateStartDate the application date (must be after or equal to the start date)
     * @param applicationDateEndDate   the application date (must be before or equal to the end date)
     * @return an Optional containing the matching PriceEntity, if found
     */
    Optional<PriceEntity> findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
            Long productId, Long brandId, LocalDateTime applicationDateStartDate, LocalDateTime applicationDateEndDate);

}