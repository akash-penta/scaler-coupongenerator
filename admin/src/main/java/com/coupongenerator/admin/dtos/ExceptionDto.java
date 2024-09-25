package com.coupongenerator.admin.dtos;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDto {
    private int statusCode;
    private String message;
}
