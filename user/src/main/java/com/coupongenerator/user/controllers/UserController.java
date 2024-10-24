package com.coupongenerator.user.controllers;

import com.coupongenerator.user.dtos.PlanDetailsDto;
import com.coupongenerator.user.dtos.UserDto;
import com.coupongenerator.user.entities.PlanDetails;
import com.coupongenerator.user.entities.User;
import com.coupongenerator.user.exceptions.PlanNotFoundException;
import com.coupongenerator.user.exceptions.UnauthorizedOperation;
import com.coupongenerator.user.exceptions.UserNotFoundException;
import com.coupongenerator.user.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/my")
public class UserController {

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/details")
    public ResponseEntity<?> getMyDetails() throws UserNotFoundException, UnauthorizedOperation {
        User currentUser = authenticationService.getCurrentUser();

        return ResponseEntity.status(HttpStatus.OK).body(UserDto.from(currentUser));
    }

    @GetMapping("/planDetails")
    public ResponseEntity<?> getMyPlanDetails() throws UserNotFoundException, UnauthorizedOperation {
        User currentUser = authenticationService.getCurrentUser();

        return ResponseEntity.status(HttpStatus.OK).body(PlanDetailsDto.from(currentUser.getCurrentPlan()));
    }

    @GetMapping("/upcomingPlanDetails")
    public ResponseEntity<?> getUpcomingPlanDetails()
            throws UserNotFoundException, UnauthorizedOperation, PlanNotFoundException {
        User currentUser = authenticationService.getCurrentUser();

        PlanDetails nextPlan = currentUser.getNextPlan();
        if(nextPlan == null) {
            throw new PlanNotFoundException("There is no new upcoming plan");
        }

        return ResponseEntity.status(HttpStatus.OK).body(PlanDetailsDto.from(nextPlan));
    }
}
