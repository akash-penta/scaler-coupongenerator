package com.coupongenerator.payment.controllers;

import com.coupongenerator.payment.dtos.DoPaymentRequestDto;
import com.coupongenerator.payment.dtos.ExceptionDto;
import com.coupongenerator.payment.dtos.GetPaymentLinkResponseDto;
import com.coupongenerator.payment.enums.PaymentStatus;
import com.coupongenerator.payment.exceptions.PaymentDetailsNotFoundException;
import com.coupongenerator.payment.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/getPaymentLink/{paymentId}")
    public ResponseEntity<?> getPaymentLink(
            @PathVariable String paymentId
    ) throws PaymentDetailsNotFoundException {
        UUID paymentUuid = null;
        try {
            paymentUuid = UUID.fromString(paymentId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ExceptionDto(HttpStatus.BAD_REQUEST.value(), "Invalid payment id")
            );
        }

        String paymentLink = paymentService.getPaymentLink(paymentUuid);

        return ResponseEntity.status(HttpStatus.CREATED).body(new GetPaymentLinkResponseDto(paymentLink));
    }

    //callback method from payment site
    @PostMapping("/doPayment")
    public ResponseEntity<?> doPayment(
            DoPaymentRequestDto requestDto
    ) {
        UUID paymentUuid = null;
        try {
            paymentUuid = UUID.fromString(requestDto.getPaymentId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ExceptionDto(HttpStatus.BAD_REQUEST.value(), "Invalid payment id")
            );
        }

        if(PaymentStatus.SUCCESS.toString().equals(requestDto.getPaymentStatus()) ||
                PaymentStatus.FAILED.toString().equals(requestDto.getPaymentStatus())) {
           paymentService.doPayment(paymentUuid, requestDto.getPaymentStatus());
           return ResponseEntity.ok("Updated details...");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ExceptionDto(HttpStatus.BAD_REQUEST.value(), "Invalid payment status")
            );
        }
    }
}
