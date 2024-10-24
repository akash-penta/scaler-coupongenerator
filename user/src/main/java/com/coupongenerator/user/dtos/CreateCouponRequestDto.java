package com.coupongenerator.user.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCouponRequestDto {

    private String couponTemplateName;

    private String customerName;

    private String customerPhoneNo;

    private String customerEmail;

}
