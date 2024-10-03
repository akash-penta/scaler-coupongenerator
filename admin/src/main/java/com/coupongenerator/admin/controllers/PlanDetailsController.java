package com.coupongenerator.admin.controllers;

import com.coupongenerator.admin.dtos.CreatePlanRequestDto;
import com.coupongenerator.admin.dtos.ExceptionDto;
import com.coupongenerator.admin.dtos.PlanResponseDto;
import com.coupongenerator.admin.dtos.UpdatePlanRequestDto;
import com.coupongenerator.admin.exceptions.PlanAlreadyExistsException;
import com.coupongenerator.admin.exceptions.PlanNotFoundException;
import com.coupongenerator.admin.services.PlanDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/plan")
public class PlanDetailsController {

    @Autowired
    private PlanDetailsService planDetailsService;

    @PostMapping
    public ResponseEntity<?> createPlan(
            @Valid @RequestBody CreatePlanRequestDto requestDto,
            BindingResult bindingResult
    ) throws PlanAlreadyExistsException {
        if(bindingResult.hasErrors()) {
            Set<String> errorSet = new HashSet<>();
            Map<String, String> errorMap = new HashMap<>();

            bindingResult.getAllErrors().forEach(objectError -> {
                FieldError fieldError = (FieldError) objectError;
                errorSet.add(fieldError.getDefaultMessage());
            });

            if(errorSet.contains("Plan name is mandatory")) {
                errorMap.put("planName", "Plan name is mandatory");
            }
            else if(errorSet.contains("Plan name should not be blank")) {
                errorMap.put("planName", "Plan name should not be blank");
            }

            if(errorSet.contains("Amount is mandatory")) {
                errorMap.put("amount", "Amount is mandatory");
            }
            else if(errorSet.contains("Amount minimum should be 0")) {
                errorMap.put("amount", "Amount minimum should be 0");
            }

            if(errorSet.contains("Month is mandatory")) {
                errorMap.put("months", "Month is mandatory");
            }
            else if(errorSet.contains("Months minimum should be 1")) {
                errorMap.put("months", "Months minimum should be 1");
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ExceptionDto(HttpStatus.BAD_REQUEST.value(), errorMap)
            );
        }

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
        if(planName.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ExceptionDto(HttpStatus.BAD_REQUEST.value(), "Plan Name should not be blank")
            );
        }

        PlanResponseDto responseDto = planDetailsService.getPlan(planName);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updatePlan(
            @PathVariable String id,
            @Valid @RequestBody UpdatePlanRequestDto requestDto,
            BindingResult bindingResult
    ) throws PlanNotFoundException, PlanAlreadyExistsException {
        if(id.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ExceptionDto(HttpStatus.BAD_REQUEST.value(), "Id should not be blank")
            );
        }
        UUID uuid = null;
        try {
            uuid = UUID.fromString(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ExceptionDto(HttpStatus.BAD_REQUEST.value(), "Invalid id")
            );
        }

        if(bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            bindingResult.getAllErrors().forEach(objectError -> {
                FieldError fieldError = (FieldError) objectError;
                errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            });

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ExceptionDto(HttpStatus.BAD_REQUEST.value(), errorMap)
            );
        }

        planDetailsService.updatePlan(uuid, requestDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlan(
            @PathVariable String id
    ) throws PlanNotFoundException {
        if(id.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ExceptionDto(HttpStatus.BAD_REQUEST.value(), "Id should not be blank")
            );
        }
        UUID uuid = null;
        try {
            uuid = UUID.fromString(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ExceptionDto(HttpStatus.BAD_REQUEST.value(), "Invalid id")
            );
        }

        planDetailsService.deletePlan(uuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }
}
