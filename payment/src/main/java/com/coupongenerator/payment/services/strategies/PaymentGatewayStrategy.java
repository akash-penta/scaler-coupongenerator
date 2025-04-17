package com.coupongenerator.payment.services.strategies;

import com.coupongenerator.payment.services.strategies.paymentgateway.PaymentGateway;
import com.coupongenerator.payment.services.strategies.paymentgateway.RazorpayPaymentGateway;
import com.coupongenerator.payment.services.strategies.paymentgateway.StripePaymentGateway;
import org.springframework.stereotype.Service;

@Service
public class PaymentGatewayStrategy {

    final private RazorpayPaymentGateway razorpayPaymentGateway;
    final private StripePaymentGateway stripePaymentGateway;

    public PaymentGatewayStrategy(
            RazorpayPaymentGateway razorpayPaymentGateway,
            StripePaymentGateway stripePaymentGateway) {
        this.razorpayPaymentGateway = razorpayPaymentGateway;
        this.stripePaymentGateway = stripePaymentGateway;
    }

    public PaymentGateway getPaymentGateway() {
        return razorpayPaymentGateway;
    }
}
