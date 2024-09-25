package com.coupongenerator.admin.dtos;

import com.coupongenerator.admin.entities.PlanDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePlanResponseDto {
    private UUID id;
    private Date createdAt;
    private Date modifiedAt;
    private String planName;
    private int amount;
    private int months;

    public static CreatePlanResponseDto fromPlanDetailsEntity(PlanDetails planDetails) {
        return new CreatePlanResponseDto(
                planDetails.getId(),
                planDetails.getCreatedAt(),
                planDetails.getModifiedAt(),
                planDetails.getPlanName(),
                planDetails.getAmount(),
                planDetails.getMonths()
        );
    }
}
