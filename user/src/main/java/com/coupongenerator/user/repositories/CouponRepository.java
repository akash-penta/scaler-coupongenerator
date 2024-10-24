package com.coupongenerator.user.repositories;

import com.coupongenerator.user.entities.Coupon;
import com.coupongenerator.user.entities.Customer;
import com.coupongenerator.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, UUID> {
    List<Coupon> findAllByCreatedBy(User createdBy);
    List<Coupon> findAllByCreatedByAndCustomer(User createdBy, Customer customer);
}
