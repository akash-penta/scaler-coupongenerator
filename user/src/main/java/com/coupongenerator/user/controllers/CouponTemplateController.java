package com.coupongenerator.user.controllers;

import com.coupongenerator.user.dtos.CouponTemplateResponseDto;
import com.coupongenerator.user.dtos.CreateCouponTemplateRequestDto;
import com.coupongenerator.user.dtos.UpdateCouponTemplateRequestDto;
import com.coupongenerator.user.entities.User;
import com.coupongenerator.user.exceptions.CouponTemplateAlreadyExistsException;
import com.coupongenerator.user.exceptions.CouponTemplateNotFoundException;
import com.coupongenerator.user.exceptions.UnauthorizedOperation;
import com.coupongenerator.user.exceptions.UserNotFoundException;
import com.coupongenerator.user.services.AuthenticationService;
import com.coupongenerator.user.services.CouponTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/coupon/template")
public class CouponTemplateController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private CouponTemplateService couponTemplateService;

    @PostMapping
    public ResponseEntity<?> createCouponTemplate(
            @RequestBody CreateCouponTemplateRequestDto requestDto
        ) throws UserNotFoundException, CouponTemplateAlreadyExistsException {
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
    ) throws UserNotFoundException, CouponTemplateNotFoundException {
        User currentUser = authenticationService.getCurrentUser();

        CouponTemplateResponseDto responseDto = couponTemplateService.getCouponTemplateByName(
                currentUser,
                couponTemplateName
        );

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<?> getAllCouponsTemplates() throws UserNotFoundException {
        User currentUser = authenticationService.getCurrentUser();

        List<CouponTemplateResponseDto> responseDtoList = couponTemplateService.getAllCouponsTemplates(currentUser);

        return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCouponTemplate(
            @PathVariable String id,
            @RequestBody UpdateCouponTemplateRequestDto requestDto
    ) throws UserNotFoundException, CouponTemplateNotFoundException, CouponTemplateAlreadyExistsException, UnauthorizedOperation {
        User currentUser = authenticationService.getCurrentUser();

        UUID uuid = UUID.fromString(id);

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

        UUID uuid = UUID.fromString(id);

        couponTemplateService.deleteCouponTemplate(
                currentUser, uuid
        );

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }

}
