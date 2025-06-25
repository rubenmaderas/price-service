package com.ecommerce.priceservice.infrastructure.adapters.out.persistence.h2.mappers;

import com.ecommerce.priceservice.domain.models.PriceDomain;
import com.ecommerce.priceservice.infrastructure.adapters.out.persistence.h2.entity.PriceEntity;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting between PriceEntity and PriceDomain.
 * Uses MapStruct for automatic implementation generation.
 */
@Mapper(componentModel = "spring")
public interface PriceEntityMapper {

    /**
     * Converts a PriceEntity to a PriceDomain model.
     *
     * @param entity the PriceEntity to convert
     * @return the resulting PriceDomain object
     */
    PriceDomain toDomain(PriceEntity entity);

}