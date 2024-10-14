package com.coupongenerator.user.repositories;

import com.coupongenerator.user.entities.CouponTemplate;
import com.coupongenerator.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CouponTemplateRepository extends JpaRepository<CouponTemplate, UUID> {
    List<CouponTemplate> findAllByCreatedBy(User createdBy);

    Optional<CouponTemplate> findByNameAndCreatedBy(String name, User createdBy);
}
