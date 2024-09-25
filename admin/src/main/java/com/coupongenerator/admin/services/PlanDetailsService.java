package com.coupongenerator.admin.services;

import com.coupongenerator.admin.dtos.CreatePlanRequestDto;
import com.coupongenerator.admin.dtos.CreatePlanResponseDto;
import com.coupongenerator.admin.entities.PlanDetails;
import com.coupongenerator.admin.exceptions.PlanAlreadyExistsException;
import com.coupongenerator.admin.repositories.PlanDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PlanDetailsService {

    @Autowired
    private PlanDetailsRepository planDetailsRepository;

    public CreatePlanResponseDto createPlan(CreatePlanRequestDto requestDto) throws PlanAlreadyExistsException {
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

        return CreatePlanResponseDto.fromPlanDetailsEntity(planDetails);

    }
}
