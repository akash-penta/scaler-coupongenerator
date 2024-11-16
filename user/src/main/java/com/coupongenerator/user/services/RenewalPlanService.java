package com.coupongenerator.user.services;

import com.coupongenerator.user.entities.PaymentDetails;
import com.coupongenerator.user.entities.PlanDetails;
import com.coupongenerator.user.entities.User;
import com.coupongenerator.user.enums.PaymentStatus;
import com.coupongenerator.user.exceptions.CantCreateNewPaymentLinkException;
import com.coupongenerator.user.exceptions.PlanNotFoundException;
import com.coupongenerator.user.repositories.PaymentDetailsRepository;
import com.coupongenerator.user.repositories.PlanDetailsRepository;
import com.coupongenerator.user.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class RenewalPlanService {

    @Autowired
    private PlanDetailsRepository planDetailsRepository;

    @Autowired
    private PaymentDetailsRepository paymentDetailsRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${payment.server.link}")
    private String paymentUrl;

    @Transactional
    public String getPaymentLink(User currentUser, String planName) throws PlanNotFoundException, CantCreateNewPaymentLinkException {
        Optional<PaymentDetails> optionalPaymentDetails = paymentDetailsRepository.findByUserAndPaymentStatus(currentUser, PaymentStatus.INITIATED);
        if(optionalPaymentDetails.isPresent()) {
            throw new CantCreateNewPaymentLinkException("Payment link already exists");
        }

        Optional<PlanDetails> optionalPlanDetails = planDetailsRepository.findByPlanName(planName);

        if(optionalPlanDetails.isEmpty()) {
            throw new PlanNotFoundException("Plan not found with plan name:" + planName);
        }
        PlanDetails planDetails = optionalPlanDetails.get();

        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setPlanDetails(planDetails);
        paymentDetails.setUser(currentUser);
        paymentDetails.setPaymentStatus(PaymentStatus.INITIATED);
        Date currentDate = new Date();
        paymentDetails.setCreatedAt(currentDate);
        paymentDetails.setModifiedAt(currentDate);
        paymentDetailsRepository.save(paymentDetails);

        return paymentUrl + paymentDetails.getId();
    }

    public String getExistingPaymentLink(User currentUser) {
        Optional<PaymentDetails> optionalPaymentDetails = paymentDetailsRepository.findByUserAndPaymentStatus(currentUser, PaymentStatus.INITIATED);
        if(optionalPaymentDetails.isPresent()) {
            PaymentDetails paymentDetails = optionalPaymentDetails.get();
            return paymentUrl + paymentDetails.getId();
        }

        return null;
    }
}
