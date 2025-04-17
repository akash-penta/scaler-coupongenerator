package com.coupongenerator.payment.repositories;

import com.coupongenerator.payment.entities.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, UUID> {
}
