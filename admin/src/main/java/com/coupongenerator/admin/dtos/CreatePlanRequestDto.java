package com.coupongenerator.admin.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePlanRequestDto {
    private String planName;
    private int amount;
    private int months;
}
