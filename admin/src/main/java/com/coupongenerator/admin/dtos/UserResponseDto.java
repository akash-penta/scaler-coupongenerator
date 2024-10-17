package com.coupongenerator.admin.dtos;

import com.coupongenerator.admin.entities.PlanDetails;
import com.coupongenerator.admin.entities.User;
import com.coupongenerator.admin.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private UUID id;

    private Date createdAt;

    private Date modifiedAt;

    private String userName;

    private String businessName;

    private String status;

    private Date expireDate;

    private PlanDetails currentPlan;

    public static UserResponseDto fromUserEntity(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getCreatedAt(),
                user.getModifiedAt(),
                user.getUserName(),
                user.getBusinessName(),
                user.getStatus().name(),
                user.getExpireDate(),
                user.getCurrentPlan()
        );
    }
}
