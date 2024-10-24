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

    @ExceptionHandler(PlanNotFoundException.class)
    public ResponseEntity<ExceptionDto> handlePlanNotFoundException(PlanNotFoundException planNotFoundException) {
        return new ResponseEntity<>(
                new ExceptionDto(HttpStatus.NOT_FOUND.value(), planNotFoundException.getMessage()),
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

    @ExceptionHandler(CouldNotCreateCouponException.class)
    public ResponseEntity<ExceptionDto> handleCouldNotCreateCouponException(CouldNotCreateCouponException couldNotCreateCouponException) {
        return new ResponseEntity<>(
                new ExceptionDto(HttpStatus.BAD_REQUEST.value(), couldNotCreateCouponException.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(CouldNotUpdateCouponException.class)
    public ResponseEntity<ExceptionDto> handleCouldNotUpdateCouponException(CouldNotUpdateCouponException couldNotUpdateCouponException) {
        return new ResponseEntity<>(
                new ExceptionDto(HttpStatus.BAD_REQUEST.value(), couldNotUpdateCouponException.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(CouponNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleCouponNotFoundException(CouponNotFoundException couponNotFoundException) {
        return new ResponseEntity<>(
                new ExceptionDto(HttpStatus.NOT_FOUND.value(), couponNotFoundException.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleCustomerNotFoundException(CustomerNotFoundException customerNotFoundException) {
        return new ResponseEntity<>(
                new ExceptionDto(HttpStatus.NOT_FOUND.value(), customerNotFoundException.getMessage()),
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
