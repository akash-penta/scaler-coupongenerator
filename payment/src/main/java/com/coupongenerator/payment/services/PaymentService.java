package com.coupongenerator.payment.services;

import com.coupongenerator.payment.entities.PaymentDetails;
import com.coupongenerator.payment.entities.PlanDetails;
import com.coupongenerator.payment.entities.User;
import com.coupongenerator.payment.enums.PaymentStatus;
import com.coupongenerator.payment.enums.UserStatus;
import com.coupongenerator.payment.exceptions.PaymentDetailsNotFoundException;
import com.coupongenerator.payment.repositories.PaymentDetailsRepository;
import com.coupongenerator.payment.repositories.UserRepository;
import com.coupongenerator.payment.services.strategies.PaymentGatewayStrategy;
import com.coupongenerator.payment.services.strategies.paymentgateway.PaymentGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentGatewayStrategy paymentGatewayStrategy;

    @Autowired
    private PaymentDetailsRepository paymentDetailsRepository;

    @Autowired
    private UserRepository userRepository;

    public String getPaymentLink(UUID paymentId) throws PaymentDetailsNotFoundException {

        Optional<PaymentDetails> optionalPaymentDetails = paymentDetailsRepository.findById(paymentId);
        if(optionalPaymentDetails.isEmpty()) {
            throw new PaymentDetailsNotFoundException("Payment details not found");
        }

        PaymentGateway paymentGateway = paymentGatewayStrategy.getPaymentGateway();

        String paymentLink = paymentGateway.getPaymentLink(paymentId);

        PaymentDetails paymentDetails = optionalPaymentDetails.get();
        paymentDetails.setPaymentStatus(PaymentStatus.GENERATEDLINK);
        Date currentDate = new Date();
        paymentDetails.setModifiedAt(currentDate);
        paymentDetailsRepository.save(paymentDetails);

        return paymentLink;
    }

    public void doPayment(UUID paymentId, String paymentStatus) throws PaymentDetailsNotFoundException {
        Optional<PaymentDetails> optionalPaymentDetails = paymentDetailsRepository.findById(paymentId);
        if(optionalPaymentDetails.isEmpty()) {
            throw new PaymentDetailsNotFoundException("Payment details not found");
        }

        PaymentDetails paymentDetails = optionalPaymentDetails.get();
        paymentDetails.setPaymentStatus(PaymentStatus.valueOf(paymentStatus));
        Date currentDate = new Date();
        paymentDetails.setModifiedAt(currentDate);
        paymentDetailsRepository.save(paymentDetails);

        User user = paymentDetails.getUser();
        PlanDetails planDetails = paymentDetails.getPlanDetails();

        if(user.getStatus().equals(UserStatus.BLOCKED)) {
            return;
        }

        if(user.getStatus().equals(UserStatus.ACTIVE)) {
            user.setNextPlan(planDetails);
        }
        else if(user.getStatus().equals(UserStatus.EXPIRED)) {
            user.setStatus(UserStatus.ACTIVE);
            user.setCurrentPlan(planDetails);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.add(Calendar.MONTH, planDetails.getMonths());
            Date expireDate = calendar.getTime();
            user.setExpireDate(expireDate);
        }

        user.setCreatedAt(currentDate);
        user.setModifiedAt(currentDate);

        userRepository.save(user);
    }
}
