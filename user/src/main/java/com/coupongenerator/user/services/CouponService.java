package com.coupongenerator.user.services;

import com.coupongenerator.user.dtos.CouponResponseDto;
import com.coupongenerator.user.dtos.CreateCouponRequestDto;
import com.coupongenerator.user.entities.Coupon;
import com.coupongenerator.user.entities.CouponTemplate;
import com.coupongenerator.user.entities.Customer;
import com.coupongenerator.user.entities.User;
import com.coupongenerator.user.enums.CouponStatus;
import com.coupongenerator.user.enums.CouponStatusActionRequest;
import com.coupongenerator.user.exceptions.*;
import com.coupongenerator.user.repositories.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponTemplateService couponTemplateService;

    @Autowired
    private CustomerService customerService;

    public CouponResponseDto createCoupon(
            User currentUser, CreateCouponRequestDto requestDto
    ) throws CouponTemplateNotFoundException, CouldNotCreateCouponException {
        Date currentDate = new Date();
        CouponTemplate couponTemplate = couponTemplateService.getCouponTemplateByNameObject(
                currentUser, requestDto.getCouponTemplateName()
        );

        if(currentDate.after(couponTemplate.getEndDate())) {
            throw new CouldNotCreateCouponException("Current date already crossed end date of coupon template");
        }

        Customer customer = customerService.getCustomer(
                requestDto.getCustomerName(), requestDto.getCustomerPhoneNo(), requestDto.getCustomerEmail()
        );

        Coupon coupon = new Coupon();

        coupon.setCouponTemplate(couponTemplate);
        coupon.setCouponStatus(CouponStatus.ACTIVE);
        coupon.setCustomer(customer);
        coupon.setCreatedBy(currentUser);
        coupon.setCreatedAt(currentDate);
        coupon.setModifiedAt(currentDate);

        couponRepository.save(coupon);

        return CouponResponseDto.from(coupon);
    }

    public List<CouponResponseDto> getAllCoupons(User currentUser) {
        List<Coupon> couponList = couponRepository.findAllByCreatedBy(currentUser);

        List<CouponResponseDto> responseDtoList = new ArrayList<>();

        Date currentDate = new Date();

        couponList.forEach(coupon -> {
            if(currentDate.after(coupon.getCouponTemplate().getEndDate())) {
                coupon.setCouponStatus(CouponStatus.EXPIRED);
                coupon.setModifiedAt(currentDate);
                couponRepository.save(coupon);
            }
            responseDtoList.add(CouponResponseDto.from(coupon));
        });

        return responseDtoList;
    }

    public List<CouponResponseDto> getAllCouponsByPhoneNo(
            String phoneNo, User currentUser
    ) throws CustomerNotFoundException {
        Customer customer = customerService.getCustomer(phoneNo);

        List<Coupon> couponList = couponRepository.findAllByCreatedByAndCustomer(currentUser, customer);

        List<CouponResponseDto> responseDtoList = new ArrayList<>();

        Date currentDate = new Date();

        couponList.forEach(coupon -> {
            if(currentDate.after(coupon.getCouponTemplate().getEndDate())) {
                coupon.setCouponStatus(CouponStatus.EXPIRED);
                coupon.setModifiedAt(currentDate);
                couponRepository.save(coupon);
            }
            responseDtoList.add(CouponResponseDto.from(coupon));
        });

        return responseDtoList;
    }

    public CouponResponseDto getCouponById(
            UUID uuid, User currentUser
    ) throws CouponNotFoundException, UnauthorizedOperation {
        Optional<Coupon> optionalCoupon = couponRepository.findById(uuid);

        if(optionalCoupon.isEmpty()) {
            throw new CouponNotFoundException("Coupon not found with id: " + uuid.toString());
        }

        Coupon coupon = optionalCoupon.get();

        if(!coupon.getCreatedBy().getUsername().equals(currentUser.getUsername())) {
            throw new UnauthorizedOperation("Your not authorized to this operation");
        }

        Date currentDate = new Date();

        if(currentDate.after(coupon.getCouponTemplate().getEndDate())) {
            coupon.setCouponStatus(CouponStatus.EXPIRED);
            coupon.setModifiedAt(currentDate);
            couponRepository.save(coupon);
        }

        return CouponResponseDto.from(coupon);
    }

    public void deleteCouponById(UUID uuid, User currentUser) throws CouponNotFoundException, UnauthorizedOperation {
        Optional<Coupon> optionalCoupon = couponRepository.findById(uuid);

        if(optionalCoupon.isEmpty()) {
            throw new CouponNotFoundException("Coupon not found with id: " + uuid.toString());
        }

        Coupon coupon = optionalCoupon.get();

        if(!coupon.getCreatedBy().getUsername().equals(currentUser.getUsername())) {
            throw new UnauthorizedOperation("Your not authorized to this operation");
        }

        couponRepository.deleteById(uuid);
    }

    public void updateCouponById(
            UUID uuid, CouponStatusActionRequest actionRequest, User currentUser
    ) throws CouponNotFoundException, UnauthorizedOperation, CouldNotUpdateCouponException {
        Optional<Coupon> optionalCoupon = couponRepository.findById(uuid);

        if(optionalCoupon.isEmpty()) {
            throw new CouponNotFoundException("Coupon not found with id: " + uuid.toString());
        }

        Coupon coupon = optionalCoupon.get();

        if(!coupon.getCreatedBy().getUsername().equals(currentUser.getUsername())) {
            throw new UnauthorizedOperation("Your not authorized to this operation");
        }

        Date currentDate = new Date();

        // Checking coupon is expired or not
        if(currentDate.after(coupon.getCouponTemplate().getEndDate())) {
            if(coupon.getCouponStatus().equals(CouponStatus.EXPIRED)) {
                throw new CouldNotUpdateCouponException("Coupon already expired");
            }
            coupon.setCouponStatus(CouponStatus.EXPIRED);
            coupon.setModifiedAt(currentDate);
            couponRepository.save(coupon);

            throw new CouldNotUpdateCouponException("Coupon already expired");
        }

        // Checking whether coupon is used or not
        if(coupon.getCouponStatus().equals(CouponStatus.USED)) {
            throw new CouldNotUpdateCouponException("Coupon already used");
        }

        // Active action request from user
        if(actionRequest.equals(CouponStatusActionRequest.ACTIVE)) {
            if(coupon.getCouponStatus().equals(CouponStatus.ACTIVE)) {
                throw new CouldNotUpdateCouponException("Coupon already in Active");
            }
            coupon.setCouponStatus(CouponStatus.ACTIVE);
        }

        // Block action request from user
        if(actionRequest.equals(CouponStatusActionRequest.BLOCK)) {
            if(coupon.getCouponStatus().equals(CouponStatus.BLOCKED)) {
                throw new CouldNotUpdateCouponException("Coupon already Blocked");
            }
            coupon.setCouponStatus(CouponStatus.BLOCKED);
        }

        // Use action request from user
        if(actionRequest.equals(CouponStatusActionRequest.USE)) {
            if(currentDate.before(coupon.getCouponTemplate().getStartDate())) {
                throw new CouldNotUpdateCouponException("Coupon starts from " + coupon.getCouponTemplate().getStartDate());
            }
            coupon.setCouponStatus(CouponStatus.USED);
        }

        coupon.setModifiedAt(currentDate);
        couponRepository.save(coupon);
    }
}
