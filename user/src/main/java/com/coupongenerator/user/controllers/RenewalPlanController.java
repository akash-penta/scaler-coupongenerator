package com.coupongenerator.user.controllers;

import com.coupongenerator.user.dtos.ExceptionDto;
import com.coupongenerator.user.dtos.PaymentIdResponseDto;
import com.coupongenerator.user.dtos.PlanResponseDto;
import com.coupongenerator.user.entities.User;
import com.coupongenerator.user.exceptions.CantCreateNewPaymentLinkException;
import com.coupongenerator.user.exceptions.PlanNotFoundException;
import com.coupongenerator.user.exceptions.UnauthorizedOperation;
import com.coupongenerator.user.exceptions.UserNotFoundException;
import com.coupongenerator.user.services.AuthenticationService;
import com.coupongenerator.user.services.PlanDetailsService;
import com.coupongenerator.user.services.RenewalPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/renewal")
public class RenewalPlanController {

    @Autowired
    private PlanDetailsService planDetailsService;

    @Autowired
    private RenewalPlanService renewalPlanService;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/allPlans")
    public ResponseEntity<?> getAllPlans() throws UserNotFoundException, UnauthorizedOperation {
        User currentUser = authenticationService.getCurrentUser();

        List<PlanResponseDto> responseDtoList = planDetailsService.getAllPlans();
        return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
    }

    @PostMapping("/getPaymentLink/{planName}")
    public ResponseEntity<?> getPaymentLint(
            @PathVariable String planName
    ) throws UserNotFoundException, UnauthorizedOperation, CantCreateNewPaymentLinkException, PlanNotFoundException {
        if(planName.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ExceptionDto(HttpStatus.BAD_REQUEST.value(), "Plan Name should not be blank")
            );
        }

        User currentUser = authenticationService.getCurrentUser();

        if(currentUser.getNextPlan() != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ExceptionDto(409, "Already next plan exists, try after " + currentUser.getExpireDate().toString()));
        }

        PaymentIdResponseDto responseDto = renewalPlanService.getPaymentId(currentUser, planName);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/getPaymentLink")
    public ResponseEntity<?> getExistingPaymentLink() throws UserNotFoundException, UnauthorizedOperation {
        User currentUser = authenticationService.getCurrentUser();

        PaymentIdResponseDto responseDto = renewalPlanService.getExistingPaymentId(currentUser);

        if(responseDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDto(HttpStatus.NOT_FOUND.value(), "No active initiated payment link"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
