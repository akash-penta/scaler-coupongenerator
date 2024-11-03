package com.coupongenerator.user.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCouponTemplateRequestDto {

    private String name;

    private String description;

    private Date startDate;

    @Future(message = "End date should be future")
    private Date endDate;

    @Positive(message = "Amount should be greater then 0")
    private Integer amount;
}
