package com.ecommerce.priceservice.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a price is not found in the system.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PriceNotFoundException extends RuntimeException {

    /**
     * Creates a new instance of PriceNotFoundException with a detailed message.
     *
     * @param message the message describing the cause of the exception
     */
    public PriceNotFoundException(String message) {
        super(message);
    }
}