package com.coupongenerator.payment.services.strategies.paymentgateway;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RazorpayPaymentGateway implements PaymentGateway {
    @Override
    public String getPaymentLink(UUID uuid) {
        return "https://coupongenerator.razorpay.com/payment/" + uuid;
    }
}
