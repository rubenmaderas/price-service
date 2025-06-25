package com.ecommerce.priceservice.infrastructure.adapters.in.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PriceResponseDto {
    private Long productId;
    private Long brandId;
    private Integer priceList;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal price;
}