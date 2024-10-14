package com.coupongenerator.user.exceptions;

import com.coupongenerator.user.dtos.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvices {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleUserNotFoundException(UserNotFoundException userNotFoundException) {
        return new ResponseEntity<>(
                new ExceptionDto(HttpStatus.NOT_FOUND.value(), userNotFoundException.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(CouponTemplateAlreadyExistsException.class)
    public ResponseEntity<ExceptionDto> handleCouponTemplateAlreadyExistsException(CouponTemplateAlreadyExistsException couponTemplateAlreadyExistsException) {
        return new ResponseEntity<>(
                new ExceptionDto(HttpStatus.CONFLICT.value(), couponTemplateAlreadyExistsException.getMessage()),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(CouponTemplateNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleCouponTemplateNotFoundException(CouponTemplateNotFoundException couponTemplateNotFoundException) {
        return new ResponseEntity<>(
                new ExceptionDto(HttpStatus.NOT_FOUND.value(), couponTemplateNotFoundException.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(UnauthorizedOperation.class)
    public ResponseEntity<ExceptionDto> handleUnauthorizedOperation(UnauthorizedOperation unauthorizedOperation) {
        return new ResponseEntity<>(
                new ExceptionDto(HttpStatus.UNAUTHORIZED.value(), unauthorizedOperation.getMessage()),
                HttpStatus.UNAUTHORIZED
        );
    }
}
