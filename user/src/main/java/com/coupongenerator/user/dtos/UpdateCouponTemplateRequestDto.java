package com.coupongenerator.user.dtos;

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

    private Date endDate;

    private Integer amount;
}
