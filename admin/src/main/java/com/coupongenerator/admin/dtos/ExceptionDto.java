package com.coupongenerator.admin.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionDto {
    private int statusCode;
    private String message;
    private Map<String, String> validationErrorMessages;

    public ExceptionDto(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public ExceptionDto(int statusCode, Map<String, String> validationErrorMessages) {
        this.statusCode = statusCode;
        this.validationErrorMessages = validationErrorMessages;
    }
}
