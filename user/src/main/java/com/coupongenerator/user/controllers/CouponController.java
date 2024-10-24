package com.coupongenerator.user.controllers;

import com.coupongenerator.user.dtos.CouponResponseDto;
import com.coupongenerator.user.dtos.CreateCouponRequestDto;
import com.coupongenerator.user.entities.User;
import com.coupongenerator.user.enums.CouponStatus;
import com.coupongenerator.user.enums.CouponStatusActionRequest;
import com.coupongenerator.user.exceptions.*;
import com.coupongenerator.user.services.AuthenticationService;
import com.coupongenerator.user.services.CouponService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private CouponService couponService;

    @PostMapping
    public ResponseEntity<?> createCoupon(
            @RequestBody CreateCouponRequestDto requestDto
    ) throws UserNotFoundException, CouponTemplateNotFoundException, CouldNotCreateCouponException, UnauthorizedOperation {
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
        User currentUser = authenticationService.getCurrentUser();

        List<CouponResponseDto> responseDtoList = couponService.getAllCouponsByPhoneNo(phoneNo, currentUser);

        return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCouponById(
            @PathVariable String id
    ) throws UserNotFoundException, UnauthorizedOperation, CouponNotFoundException {
        User currentUser = authenticationService.getCurrentUser();

        UUID uuid = UUID.fromString(id);

        CouponResponseDto responseDto = couponService.getCouponById(uuid, currentUser);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCouponById(
            @PathVariable String id
    ) throws UserNotFoundException, UnauthorizedOperation, CouponNotFoundException {
        User currentUser = authenticationService.getCurrentUser();

        UUID uuid = UUID.fromString(id);

        couponService.deleteCouponById(uuid, currentUser);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }

    @PatchMapping("/{id}/{action}")
    public ResponseEntity<?> updateCoupon(
            @PathVariable String id, @PathVariable String action
    ) throws UserNotFoundException, UnauthorizedOperation, CouldNotUpdateCouponException, CouponNotFoundException {
        User currentUser = authenticationService.getCurrentUser();
        System.out.println(id + " " + action);
        UUID uuid = UUID.fromString(id);

        CouponStatusActionRequest actionRequest = CouponStatusActionRequest.valueOf(action.toUpperCase());
        System.out.println(uuid + " " + actionRequest);
        couponService.updateCouponById(uuid, actionRequest, currentUser);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }
}
