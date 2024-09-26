package com.coupongenerator.admin.exceptions;

import com.coupongenerator.admin.dtos.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvices {
    @ExceptionHandler(PlanAlreadyExistsException.class)
    public ResponseEntity<ExceptionDto> handlePlanAlreadyExistsException(PlanAlreadyExistsException planAlreadyExistsException) {
        return new ResponseEntity<>(
                new ExceptionDto(HttpStatus.CONFLICT.value(), planAlreadyExistsException.getMessage()),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(PlanNotFoundException.class)
    public ResponseEntity<ExceptionDto> handlePlanNotFoundException(PlanNotFoundException planNotFoundException) {
        return new ResponseEntity<>(
                new ExceptionDto(HttpStatus.NOT_FOUND.value(), planNotFoundException.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ExceptionDto> handleUserAlreadyExistsException(UserAlreadyExistsException userAlreadyExistsException) {
        return new ResponseEntity<>(
                new ExceptionDto(HttpStatus.CONFLICT.value(), userAlreadyExistsException.getMessage()),
                HttpStatus.CONFLICT
        );
    }
}
