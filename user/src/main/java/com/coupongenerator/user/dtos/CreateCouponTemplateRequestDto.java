package com.coupongenerator.user.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCouponTemplateRequestDto {

    @NotNull(message = "Name is mandatory")
    @NotBlank(message = "Name should not be blank")
    private String name;

    private String description;

    @NotNull(message = "Start Date is mandatory")
    private Date startDate;

    @NotNull(message = "End Date is mandatory")
    @Future(message = "End date should be future")
    private Date endDate;

    @NotNull(message = "Amount is mandatory")
    @Positive(message = "Amount should be greater then 0")
    private Integer amount;
}
