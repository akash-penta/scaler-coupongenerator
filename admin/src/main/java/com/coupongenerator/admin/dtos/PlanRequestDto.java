package com.coupongenerator.admin.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlanRequestDto {
    @NotNull(message = "Plan name is mandatory")
    @NotBlank(message = "Plan name should not be blank")
    private String planName;

    @NotNull(message = "Amount is mandatory")
    @Min(value = 0, message = "Amount minimum should be 0")
    private Integer amount;

    @NotNull(message = "Month is mandatory")
    @Min(value = 1, message = "Months minimum should be 1")
    private Integer months;
}
