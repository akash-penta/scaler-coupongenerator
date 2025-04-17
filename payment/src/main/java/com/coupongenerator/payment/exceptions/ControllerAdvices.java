package com.coupongenerator.payment.exceptions;

import com.coupongenerator.payment.dtos.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvices {
    @ExceptionHandler(PaymentDetailsNotFoundException.class)
    public ResponseEntity<ExceptionDto> handlePaymentDetailsNotFoundException(PaymentDetailsNotFoundException paymentDetailsNotFoundException) {
        return new ResponseEntity<>(
                new ExceptionDto(HttpStatus.NOT_FOUND.value(), paymentDetailsNotFoundException.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }
}
