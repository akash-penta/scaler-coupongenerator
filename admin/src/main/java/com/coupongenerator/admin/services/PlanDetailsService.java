package com.coupongenerator.admin.services;

import com.coupongenerator.admin.dtos.CreatePlanRequestDto;
import com.coupongenerator.admin.dtos.PlanResponseDto;
import com.coupongenerator.admin.dtos.UpdatePlanRequestDto;
import com.coupongenerator.admin.entities.PlanDetails;
import com.coupongenerator.admin.exceptions.PlanAlreadyExistsException;
import com.coupongenerator.admin.exceptions.PlanNotFoundException;
import com.coupongenerator.admin.repositories.PlanDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlanDetailsService {

    @Autowired
    private PlanDetailsRepository planDetailsRepository;

    public PlanResponseDto createPlan(CreatePlanRequestDto requestDto) throws PlanAlreadyExistsException {
        Optional<PlanDetails> optionalPlanDetails = planDetailsRepository.findByPlanName(requestDto.getPlanName());

        if(optionalPlanDetails.isPresent()) {
            throw new PlanAlreadyExistsException("Plan already exist with same plan name");
        }

        PlanDetails planDetails = new PlanDetails();

        planDetails.setPlanName(requestDto.getPlanName());
        planDetails.setAmount(requestDto.getAmount());
        planDetails.setMonths(requestDto.getMonths());

        Date currentDate = new Date();
        planDetails.setCreatedAt(currentDate);
        planDetails.setModifiedAt(currentDate);

        planDetailsRepository.save(planDetails);

        return PlanResponseDto.fromPlanDetailsEntity(planDetails);

    }

    public List<PlanResponseDto> getAllPlans() {
        List<PlanDetails> planDetailsList = planDetailsRepository.findAll();

        List<PlanResponseDto> responseDtoList = new ArrayList<>();

        planDetailsList.forEach(planDetails -> responseDtoList.add(PlanResponseDto.fromPlanDetailsEntity(planDetails)));

        return responseDtoList;
    }

    public PlanResponseDto getPlan(String planName) throws PlanNotFoundException {
        Optional<PlanDetails> optionalPlanDetails = planDetailsRepository.findByPlanName(planName);

        if(optionalPlanDetails.isEmpty()) {
            throw new PlanNotFoundException("Plan not found with plan name:" + planName);
        }

        PlanDetails planDetails = optionalPlanDetails.get();

        return PlanResponseDto.fromPlanDetailsEntity(planDetails);
    }

    public PlanDetails getPlanDetailsObject(String planName) throws PlanNotFoundException {
        Optional<PlanDetails> optionalPlanDetails = planDetailsRepository.findByPlanName(planName);

        if(optionalPlanDetails.isEmpty()) {
            throw new PlanNotFoundException("Plan not found with plan name:" + planName);
        }

        return optionalPlanDetails.get();
    }

    public void updatePlan(UUID id, UpdatePlanRequestDto requestDto) throws PlanNotFoundException {
        Optional<PlanDetails> optionalPlanDetails = planDetailsRepository.findById(id);

        if(optionalPlanDetails.isEmpty()) {
            throw new PlanNotFoundException("Plan not found with plan id:" + id);
        }

        PlanDetails planDetails = optionalPlanDetails.get();

        if(requestDto.getPlanName() != null && !requestDto.getPlanName().isEmpty()) {
            planDetails.setPlanName(requestDto.getPlanName());
        }

        if(requestDto.getAmount() != null) {
            planDetails.setAmount(requestDto.getAmount());
        }

        if(requestDto.getMonths() != null) {
            planDetails.setMonths(requestDto.getMonths());
        }

        Date currentDate = new Date();
        planDetails.setModifiedAt(currentDate);

        planDetailsRepository.save(planDetails);
    }

    public void deletePlan(UUID id) throws PlanNotFoundException {
        Optional<PlanDetails> optionalPlanDetails = planDetailsRepository.findById(id);

        if(optionalPlanDetails.isEmpty()) {
            throw new PlanNotFoundException("Plan not found with plan id:" + id);
        }

        planDetailsRepository.deleteById(id);
    }
}
