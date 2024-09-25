package com.coupongenerator.admin.controllers;

import com.coupongenerator.admin.dtos.CreatePlanRequestDto;
import com.coupongenerator.admin.dtos.PlanResponseDto;
import com.coupongenerator.admin.exceptions.PlanAlreadyExistsException;
import com.coupongenerator.admin.services.PlanDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plan")
public class PlanDetailsController {

    @Autowired
    private PlanDetailsService planDetailsService;

    @PostMapping
    public ResponseEntity<?> createPlan(
            @RequestBody CreatePlanRequestDto requestDto
    ) throws PlanAlreadyExistsException {

        PlanResponseDto responseDto = planDetailsService.createPlan(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<?> getAllPlans() {
        List<PlanResponseDto> responseDtoList = planDetailsService.getAllPlans();
        return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
    }
}
