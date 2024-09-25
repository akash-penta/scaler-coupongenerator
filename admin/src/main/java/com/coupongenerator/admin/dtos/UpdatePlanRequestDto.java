package com.coupongenerator.admin.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePlanRequestDto {
    private String planName;
    private Integer amount;
    private Integer months;
}
