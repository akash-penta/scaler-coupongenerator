package com.coupongenerator.user.controllers;

import com.coupongenerator.user.dtos.CouponResponseDto;
import com.coupongenerator.user.dtos.CreateCouponRequestDto;
import com.coupongenerator.user.dtos.ExceptionDto;
import com.coupongenerator.user.entities.User;
import com.coupongenerator.user.enums.CouponStatusActionRequest;
import com.coupongenerator.user.exceptions.*;
import com.coupongenerator.user.services.AuthenticationService;
import com.coupongenerator.user.services.CouponService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private CouponService couponService;

    @PostMapping
    public ResponseEntity<?> createCoupon(
            @Valid @RequestBody CreateCouponRequestDto requestDto,
            BindingResult bindingResult
    ) throws UserNotFoundException, CouponTemplateNotFoundException, CouldNotCreateCouponException, UnauthorizedOperation {
        if(bindingResult.hasErrors()) {
            Set<String> errorSet = new HashSet<>();
            Map<String, String> errorMap = new HashMap<>();

            bindingResult.getAllErrors().forEach(objectError -> {
                FieldError fieldError = (FieldError) objectError;
                errorSet.add(fieldError.getDefaultMessage());
            });

            if(errorSet.contains("Coupon template name is mandatory")) {
                errorMap.put("couponTemplateName", "Coupon template name is mandatory");
            }
            else if(errorSet.contains("Coupon template name should not be blank")) {
                errorMap.put("couponTemplateName", "Coupon template name should not be blank");
            }

            if(errorSet.contains("Customer phone number is mandatory")) {
                errorMap.put("customerPhoneNo", "Customer phone number is mandatory");
            }
            else if(errorSet.contains("Customer phone number is Invalid")) {
                errorMap.put("customerPhoneNo", "Customer phone number is Invalid");
            }

            if(errorSet.contains("Customer email is Invalid")) {
                errorMap.put("customerEmail", "Customer email is Invalid");
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ExceptionDto(HttpStatus.BAD_REQUEST.value(), errorMap)
            );
        }

        User currentUser = authenticationService.getCurrentUser();

        CouponResponseDto responseDto = couponService.createCoupon(currentUser, requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<?> getAllCoupons() throws UserNotFoundException, UnauthorizedOperation {
        User currentUser = authenticationService.getCurrentUser();

        List<CouponResponseDto> responseDtoList = couponService.getAllCoupons(currentUser);

        return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
    }

    @GetMapping("/phoneNo/{phoneNo}")
    public ResponseEntity<?> getAllCouponsByPhoneNo(
        @PathVariable String phoneNo
    ) throws UserNotFoundException, UnauthorizedOperation, CustomerNotFoundException {
        String regex = "^(\\+\\d{1,3}[- ]?)?\\d{10}$";
        if(phoneNo == null || phoneNo.isBlank() || !phoneNo.matches(regex)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ExceptionDto(HttpStatus.BAD_REQUEST.value(), "Phone number is Invalid")
            );
        }
        User currentUser = authenticationService.getCurrentUser();

        List<CouponResponseDto> responseDtoList = couponService.getAllCouponsByPhoneNo(phoneNo, currentUser);

        return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCouponById(
            @PathVariable String id
    ) throws UserNotFoundException, UnauthorizedOperation, CouponNotFoundException {
        User currentUser = authenticationService.getCurrentUser();

        UUID uuid = null;
        try {
            uuid = UUID.fromString(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ExceptionDto(HttpStatus.BAD_REQUEST.value(), "Invalid id")
            );
        }

        CouponResponseDto responseDto = couponService.getCouponById(uuid, currentUser);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCouponById(
            @PathVariable String id
    ) throws UserNotFoundException, UnauthorizedOperation, CouponNotFoundException {
        User currentUser = authenticationService.getCurrentUser();

        UUID uuid = null;
        try {
            uuid = UUID.fromString(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ExceptionDto(HttpStatus.BAD_REQUEST.value(), "Invalid id")
            );
        }

        couponService.deleteCouponById(uuid, currentUser);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }

    @PatchMapping("/{id}/{action}")
    public ResponseEntity<?> updateCoupon(
            @PathVariable String id, @PathVariable String action
    ) throws UserNotFoundException, UnauthorizedOperation, CouldNotUpdateCouponException, CouponNotFoundException {
        User currentUser = authenticationService.getCurrentUser();

        UUID uuid = null;
        try {
            uuid = UUID.fromString(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ExceptionDto(HttpStatus.BAD_REQUEST.value(), "Invalid id")
            );
        }

        if(!CouponStatusActionRequest.contains(action)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ExceptionDto(HttpStatus.BAD_REQUEST.value(), "Invalid actions, only accepts(ACTIVE, BLOCK, USE)")
            );
        }

        CouponStatusActionRequest actionRequest = CouponStatusActionRequest.valueOf(action.toUpperCase());

        couponService.updateCouponById(uuid, actionRequest, currentUser);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }
}
