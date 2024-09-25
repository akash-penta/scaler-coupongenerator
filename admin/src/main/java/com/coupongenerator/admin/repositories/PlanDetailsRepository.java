package com.coupongenerator.admin.repositories;

import com.coupongenerator.admin.entities.PlanDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlanDetailsRepository extends JpaRepository<PlanDetails, UUID> {
    Optional<PlanDetails> findByPlanName(String planName);
}
