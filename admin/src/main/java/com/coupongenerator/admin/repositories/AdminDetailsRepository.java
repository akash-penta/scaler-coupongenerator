package com.coupongenerator.admin.repositories;

import com.coupongenerator.admin.entities.AdminDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AdminDetailsRepository extends JpaRepository<AdminDetails, UUID> {
    Optional<AdminDetails> findByUserName(String userName);
}
