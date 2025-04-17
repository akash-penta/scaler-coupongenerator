package com.coupongenerator.payment.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoPaymentRequestDto {
    private String paymentId;
    private String paymentStatus;
}
