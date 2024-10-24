package com.coupongenerator.admin.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequestDto {

    @NotNull(message = "User name is mandatory")
    @NotBlank(message = "User name should not be blank")
    private String userName;

    @NotNull(message = "Password is mandatory")
    @NotBlank(message = "Password should not be blank")
    private String password;

    @NotNull(message = "Business name is mandatory")
    @NotBlank(message = "Business name should not be blank")
    private String businessName;

    @NotNull(message = "Plan name is mandatory")
    @NotBlank(message = "Plan name should not be blank")
    private String planName;

}
