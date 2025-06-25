package com.ecommerce.priceservice.infrastructure.exceptions;

import com.ecommerce.priceservice.domain.exceptions.PriceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.core.MethodParameter;
import jakarta.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

class GlobalExceptionHandlerTest {

    @Test
    @DisplayName("Retorna 404 NOT_FOUND con mensaje adecuado al lanzar PriceNotFoundException")
    void returnsNotFoundAndMessageForPriceNotFoundException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        PriceNotFoundException ex = new PriceNotFoundException("No price was found for productId 1");
        var response = handler.handlePriceNotFoundException(ex);

        assertEquals(404, response.getStatusCode().value());
        assertEquals("Price Not Found", response.getBody().get("error"));
        assertTrue(((List<?>) response.getBody().get("messages")).get(0).toString().contains("No price was found"));
    }

    @Test
    @DisplayName("Retorna 400 BAD_REQUEST con lista de errores al validar argumentos inválidos")
    void returnsBadRequestAndErrorListForValidationException() {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("object", "field", "must not be null");
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(
                new MethodParameter(this.getClass().getDeclaredMethods()[0], -1),
                bindingResult
        );
        HttpServletRequest request = mock(HttpServletRequest.class);
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        var response = handler.handleValidationException(ex, request);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("Validation Error", response.getBody().getError());
        assertTrue(response.getBody().getMessages().get(0).contains("field: must not be null"));
    }

    @Test
    @DisplayName("Retorna 500 INTERNAL_SERVER_ERROR con mensaje para excepciones genéricas inesperadas")
    void returnsInternalServerErrorAndMessageForGenericException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        Exception ex = new Exception("Unexpected error");
        HttpServletRequest request = mock(HttpServletRequest.class);

        var response = handler.handleGenericException(ex, request);

        assertEquals(500, response.getStatusCode().value());
        assertEquals("Internal Server Error", response.getBody().getError());
        assertTrue(response.getBody().getMessages().get(0).contains("Unexpected error"));
    }
}