package com.coupongenerator.admin.controllers;

import com.coupongenerator.admin.dtos.CreatePlanRequestDto;
import com.coupongenerator.admin.dtos.CreatePlanResponseDto;
import com.coupongenerator.admin.services.PlanDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/plan")
public class PlanDetailsController {

    @Autowired
    private PlanDetailsService planDetailsService;

    @PostMapping
    public ResponseEntity<?> createPlan(
            @RequestBody CreatePlanRequestDto requestDto
    ) {

        CreatePlanResponseDto responseDto = planDetailsService.createPlan(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
