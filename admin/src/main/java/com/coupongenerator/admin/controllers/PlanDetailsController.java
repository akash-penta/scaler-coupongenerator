package com.coupongenerator.admin.controllers;

import com.coupongenerator.admin.dtos.CreatePlanRequestDto;
import com.coupongenerator.admin.dtos.PlanResponseDto;
import com.coupongenerator.admin.dtos.UpdatePlanRequestDto;
import com.coupongenerator.admin.exceptions.PlanAlreadyExistsException;
import com.coupongenerator.admin.exceptions.PlanNotFoundException;
import com.coupongenerator.admin.services.PlanDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping("/{planName}")
    public ResponseEntity<?> getPlan(
            @PathVariable String planName
    ) throws PlanNotFoundException {
        PlanResponseDto responseDto = planDetailsService.getPlan(planName);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updatePlan(
            @PathVariable UUID id,
            @RequestBody UpdatePlanRequestDto requestDto
    ) throws PlanNotFoundException {
        planDetailsService.updatePlan(id, requestDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlan(
            @PathVariable UUID id
    ) throws PlanNotFoundException {
        planDetailsService.deletePlan(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }
}
