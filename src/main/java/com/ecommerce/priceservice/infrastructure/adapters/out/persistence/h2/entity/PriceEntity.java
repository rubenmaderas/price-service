package com.ecommerce.priceservice.infrastructure.adapters.out.persistence.h2.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "prices")
public class PriceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long brandId;
    private Long productId;
    private Integer priceList;
    private Integer priority;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal price;
    private String currency;
}

