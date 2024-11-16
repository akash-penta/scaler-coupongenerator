package com.coupongenerator.user.services;

import com.coupongenerator.user.dtos.PlanResponseDto;
import com.coupongenerator.user.entities.PlanDetails;
import com.coupongenerator.user.repositories.PlanDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlanDetailsService {

    @Autowired
    private PlanDetailsRepository planDetailsRepository;

    public List<PlanResponseDto> getAllPlans() {
        List<PlanDetails> planDetailsList = planDetailsRepository.findAll();

        List<PlanResponseDto> responseDtoList = new ArrayList<>();

        planDetailsList.forEach(planDetails -> responseDtoList.add(PlanResponseDto.fromPlanDetailsEntity(planDetails)));

        return responseDtoList;
    }
}
