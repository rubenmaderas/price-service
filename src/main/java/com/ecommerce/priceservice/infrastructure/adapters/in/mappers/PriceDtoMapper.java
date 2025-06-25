package com.ecommerce.priceservice.infrastructure.adapters.in.mappers;

import com.ecommerce.priceservice.domain.models.PriceDomain;
import com.ecommerce.priceservice.infrastructure.adapters.in.dto.PriceResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceDtoMapper {

    /**
     * Converts a PriceEntity to a PriceDomain.
     *
     * @param domain
     * @return
     */
    PriceResponseDto toResponseDto(PriceDomain domain);
}