package com.coupongenerator.user.dtos;

import com.coupongenerator.user.entities.PlanDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanDetailsDto {

    private UUID id;

    private Date createdAt;

    private Date modifiedAt;

    private String planName;

    private int amount;

    private int months;

    public static PlanDetailsDto from(PlanDetails planDetails) {
        return builder()
                .id(planDetails.getId())
                .createdAt(planDetails.getCreatedAt())
                .modifiedAt(planDetails.getModifiedAt())
                .planName(planDetails.getPlanName())
                .amount(planDetails.getAmount())
                .months(planDetails.getMonths())
                .build();
    }
}
