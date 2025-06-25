package com.ecommerce.priceservice.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PriceDomain {
    private Long brandId;
    private Long productId;
    private Integer priceList;
    private Integer priority;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal price;
    private String currency;
}