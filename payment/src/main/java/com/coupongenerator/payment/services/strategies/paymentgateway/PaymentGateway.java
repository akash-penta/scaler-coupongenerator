package com.coupongenerator.payment.services.strategies.paymentgateway;

import java.util.UUID;

public interface PaymentGateway {
    String getPaymentLink(UUID uuid);
}
