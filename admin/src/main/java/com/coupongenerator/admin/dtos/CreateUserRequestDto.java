package com.coupongenerator.admin.dtos;

import com.coupongenerator.admin.entities.PlanDetails;
import com.coupongenerator.admin.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequestDto {

    private String userName;

    private String password;

    private String businessName;

    private String planName;

}
