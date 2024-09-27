package com.coupongenerator.admin.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePlanRequestDto {

//    @NotBlank(message = "Plan name should not be blank")
    private String planName;

    @Min(value = 0, message = "Amount minimum should be 0")
    private Integer amount;

    @Min(value = 1, message = "Months minimum should be 1")
    private Integer months;
}
