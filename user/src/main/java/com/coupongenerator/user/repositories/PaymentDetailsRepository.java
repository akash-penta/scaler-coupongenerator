package com.coupongenerator.user.repositories;

import com.coupongenerator.user.entities.PaymentDetails;
import com.coupongenerator.user.entities.User;
import com.coupongenerator.user.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, UUID> {
    Optional<PaymentDetails> findByUserAndPaymentStatusIn(User user, List<PaymentStatus> paymentStatusList);
}
