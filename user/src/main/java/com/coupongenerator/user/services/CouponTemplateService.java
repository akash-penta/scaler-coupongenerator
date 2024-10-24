package com.coupongenerator.user.services;

import com.coupongenerator.user.dtos.CouponTemplateResponseDto;
import com.coupongenerator.user.dtos.CreateCouponTemplateRequestDto;
import com.coupongenerator.user.dtos.UpdateCouponTemplateRequestDto;
import com.coupongenerator.user.entities.CouponTemplate;
import com.coupongenerator.user.entities.User;
import com.coupongenerator.user.exceptions.CouponTemplateAlreadyExistsException;
import com.coupongenerator.user.exceptions.CouponTemplateNotFoundException;
import com.coupongenerator.user.exceptions.UnauthorizedOperation;
import com.coupongenerator.user.repositories.CouponTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CouponTemplateService {

    @Autowired
    private CouponTemplateRepository couponTemplateRepository;

    public CouponTemplateResponseDto createCouponTemplate(
            User currentUser,
            CreateCouponTemplateRequestDto requestDto
    ) throws CouponTemplateAlreadyExistsException {
        isCouponTemplateExistsWithSameName(currentUser, requestDto.getName());

        CouponTemplate couponTemplate = new CouponTemplate();

        couponTemplate.setName(requestDto.getName());
        couponTemplate.setDescription(requestDto.getDescription());
        couponTemplate.setStartDate(requestDto.getStartDate());
        couponTemplate.setEndDate(requestDto.getEndDate());
        couponTemplate.setAmount(requestDto.getAmount());

        couponTemplate.setCreatedBy(currentUser);

        Date currentDate = new Date();
        couponTemplate.setCreatedAt(currentDate);
        couponTemplate.setModifiedAt(currentDate);

        couponTemplateRepository.save(couponTemplate);

        return CouponTemplateResponseDto.from(couponTemplate);
    }

    public CouponTemplateResponseDto getCouponTemplateByName(User currentUser, String couponTemplateName) throws CouponTemplateNotFoundException {
        Optional<CouponTemplate> optionalCouponTemplate = couponTemplateRepository.findByNameAndCreatedBy(
                couponTemplateName, currentUser
        );

        if(optionalCouponTemplate.isEmpty()) {
            throw new CouponTemplateNotFoundException("Coupon template not found with name: " + couponTemplateName);
        }

        return CouponTemplateResponseDto.from(optionalCouponTemplate.get());
    }

    public CouponTemplate getCouponTemplateByNameObject(User currentUser, String couponTemplateName) throws CouponTemplateNotFoundException {
        Optional<CouponTemplate> optionalCouponTemplate = couponTemplateRepository.findByNameAndCreatedBy(
                couponTemplateName, currentUser
        );

        if(optionalCouponTemplate.isEmpty()) {
            throw new CouponTemplateNotFoundException("Coupon template not found with name: " + couponTemplateName);
        }

        return optionalCouponTemplate.get();
    }

    public List<CouponTemplateResponseDto> getAllCouponsTemplates(User currentUser) {
        List<CouponTemplate> couponTemplates = couponTemplateRepository.findAllByCreatedBy(currentUser);

        List<CouponTemplateResponseDto> responseDtoList = new ArrayList<>();

        couponTemplates.forEach(couponTemplate -> responseDtoList.add(CouponTemplateResponseDto.from(couponTemplate)));

        return responseDtoList;
    }

    public void updateCouponTemplate(
            User currentUser, UUID id, UpdateCouponTemplateRequestDto requestDto
    ) throws CouponTemplateNotFoundException, CouponTemplateAlreadyExistsException, UnauthorizedOperation {
        Optional<CouponTemplate> optionalCouponTemplate = couponTemplateRepository.findById(id);

        if(optionalCouponTemplate.isEmpty()) {
            throw new CouponTemplateNotFoundException("Coupon template not found with id: " + id);
        }

        CouponTemplate couponTemplate = optionalCouponTemplate.get();

        if(!couponTemplate.getCreatedBy().getUsername().equalsIgnoreCase(currentUser.getUsername())) {
            throw new UnauthorizedOperation("Your not authorized to this operation");
        }

        if(requestDto.getName() != null && !requestDto.getName().equalsIgnoreCase(couponTemplate.getName())) {
            isCouponTemplateExistsWithSameName(currentUser, requestDto.getName());

            couponTemplate.setName(requestDto.getName());
        }

        if(requestDto.getDescription() != null) {
            couponTemplate.setDescription(requestDto.getDescription());
        }

        if(requestDto.getStartDate() != null) {
            couponTemplate.setStartDate(requestDto.getStartDate());
        }

        if(requestDto.getEndDate() != null) {
            couponTemplate.setEndDate(requestDto.getEndDate());
        }

        if(requestDto.getAmount() != null) {
            couponTemplate.setAmount(requestDto.getAmount());
        }

        Date currentDate = new Date();
        couponTemplate.setModifiedAt(currentDate);

        couponTemplateRepository.save(couponTemplate);
    }

    public void deleteCouponTemplate(User currentUser, UUID id) throws CouponTemplateNotFoundException, UnauthorizedOperation {
        Optional<CouponTemplate> optionalCouponTemplate = couponTemplateRepository.findById(id);

        if(optionalCouponTemplate.isEmpty()) {
            throw new CouponTemplateNotFoundException("Coupon template not found with id: " + id);
        }

        CouponTemplate couponTemplate = optionalCouponTemplate.get();

        if(!couponTemplate.getCreatedBy().getUsername().equalsIgnoreCase(currentUser.getUsername())) {
            throw new UnauthorizedOperation("Your not authorized to this operation");
        }

        couponTemplateRepository.deleteById(id);
    }

    private void isCouponTemplateExistsWithSameName(User currentUser, String name) throws CouponTemplateAlreadyExistsException {
        List<CouponTemplate> couponTemplates = couponTemplateRepository.findAllByCreatedBy(currentUser);

        for(CouponTemplate couponTemplate: couponTemplates) {
            if(couponTemplate.getName().equalsIgnoreCase(name)) {
                throw new CouponTemplateAlreadyExistsException("Coupon template already exists with same name");
            }
        }
    }


}
