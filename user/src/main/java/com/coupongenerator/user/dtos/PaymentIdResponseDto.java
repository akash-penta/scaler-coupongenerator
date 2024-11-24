package com.coupongenerator.user.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentIdResponseDto {
    private String paymentId;
    private UserDto user;
    private PlanDetailsDto planDetails;
}
