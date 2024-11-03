package com.coupongenerator.user.controllers;

import com.coupongenerator.user.dtos.CouponTemplateResponseDto;
import com.coupongenerator.user.dtos.CreateCouponTemplateRequestDto;
import com.coupongenerator.user.dtos.ExceptionDto;
import com.coupongenerator.user.dtos.UpdateCouponTemplateRequestDto;
import com.coupongenerator.user.entities.User;
import com.coupongenerator.user.exceptions.CouponTemplateAlreadyExistsException;
import com.coupongenerator.user.exceptions.CouponTemplateNotFoundException;
import com.coupongenerator.user.exceptions.UnauthorizedOperation;
import com.coupongenerator.user.exceptions.UserNotFoundException;
import com.coupongenerator.user.services.AuthenticationService;
import com.coupongenerator.user.services.CouponTemplateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/couponTemplate")
public class CouponTemplateController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private CouponTemplateService couponTemplateService;

    @PostMapping
    public ResponseEntity<?> createCouponTemplate(
            @Valid @RequestBody CreateCouponTemplateRequestDto requestDto,
            BindingResult bindingResult
        ) throws UserNotFoundException, CouponTemplateAlreadyExistsException, UnauthorizedOperation {
        if(bindingResult.hasErrors()) {
            Set<String> errorSet = new HashSet<>();
            Map<String, String> errorMap = new HashMap<>();

            bindingResult.getAllErrors().forEach(objectError -> {
                FieldError fieldError = (FieldError) objectError;
                errorSet.add(fieldError.getDefaultMessage());
            });

            if(errorSet.contains("Name is mandatory")) {
                errorMap.put("name", "Name is mandatory");
            }
            else if(errorSet.contains("Name should not be blank")) {
                errorMap.put("name", "Name should not be blank");
            }

            if(errorSet.contains("Start Date is mandatory")) {
                errorMap.put("startDate", "Start Date is mandatory");
            }

            if(errorSet.contains("End Date is mandatory")) {
                errorMap.put("endDate", "End Date is mandatory");
            }
            else if(errorSet.contains("End date should be future")) {
                errorMap.put("endDate", "End date should be future");
            }

            if(errorSet.contains("Amount is mandatory")) {
                errorMap.put("amount", "Amount is mandatory");
            }
            else if(errorSet.contains("Amount should be greater then 0")) {
                errorMap.put("amount", "Amount should be greater then 0");
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ExceptionDto(HttpStatus.BAD_REQUEST.value(), errorMap)
            );
        }

        User currentUser = authenticationService.getCurrentUser();

        CouponTemplateResponseDto responseDto = couponTemplateService.createCouponTemplate(
                currentUser,
                requestDto
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{couponTemplateName}")
    public ResponseEntity<?> getCouponTemplateByName(
            @PathVariable String couponTemplateName
    ) throws UserNotFoundException, CouponTemplateNotFoundException, UnauthorizedOperation {
        if(couponTemplateName.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ExceptionDto(HttpStatus.BAD_REQUEST.value(), "Coupon Template Name should not be blank")
            );
        }

        User currentUser = authenticationService.getCurrentUser();

        CouponTemplateResponseDto responseDto = couponTemplateService.getCouponTemplateByName(
                currentUser,
                couponTemplateName
        );

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<?> getAllCouponsTemplates() throws UserNotFoundException, UnauthorizedOperation {
        User currentUser = authenticationService.getCurrentUser();

        List<CouponTemplateResponseDto> responseDtoList = couponTemplateService.getAllCouponsTemplates(currentUser);

        return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCouponTemplate(
            @PathVariable String id,
            @Valid @RequestBody UpdateCouponTemplateRequestDto requestDto,
            BindingResult bindingResult
    ) throws UserNotFoundException, CouponTemplateNotFoundException, CouponTemplateAlreadyExistsException, UnauthorizedOperation {
        if(bindingResult.hasErrors()) {
            Set<String> errorSet = new HashSet<>();
            Map<String, String> errorMap = new HashMap<>();

            bindingResult.getAllErrors().forEach(objectError -> {
                FieldError fieldError = (FieldError) objectError;
                errorSet.add(fieldError.getDefaultMessage());
            });

            if(errorSet.contains("End date should be future")) {
                errorMap.put("endDate", "End date should be future");
            }

            if(errorSet.contains("Amount should be greater then 0")) {
                errorMap.put("amount", "Amount should be greater then 0");
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ExceptionDto(HttpStatus.BAD_REQUEST.value(), errorMap)
            );
        }
        User currentUser = authenticationService.getCurrentUser();

        UUID uuid = null;
        try {
            uuid = UUID.fromString(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ExceptionDto(HttpStatus.BAD_REQUEST.value(), "Invalid id")
            );
        }

        couponTemplateService.updateCouponTemplate(
                currentUser,
                uuid,
                requestDto
        );

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCouponTemplate(
            @PathVariable String id
    ) throws UserNotFoundException, CouponTemplateNotFoundException, UnauthorizedOperation {
        User currentUser = authenticationService.getCurrentUser();

        UUID uuid = null;
        try {
            uuid = UUID.fromString(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ExceptionDto(HttpStatus.BAD_REQUEST.value(), "Invalid id")
            );
        }

        couponTemplateService.deleteCouponTemplate(
                currentUser, uuid
        );

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }

}
