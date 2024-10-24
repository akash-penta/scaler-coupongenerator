package com.coupongenerator.user.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

    @NotNull(message = "User name is mandatory")
    @NotBlank(message = "User name should not be blank")
    private String userName;

    @NotNull(message = "Password is mandatory")
    @NotBlank(message = "Password should not be blank")
    private String password;
}
