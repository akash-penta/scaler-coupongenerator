package com.coupongenerator.admin.dtos;

import com.coupongenerator.admin.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequestDto {

    private String userName;

    private String businessName;

    private String status;

    private String planName;
}
