package com.coupongenerator.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCouponRequestDto {

    @NotNull(message = "Coupon template name is mandatory")
    @NotBlank(message = "Coupon template name should not be blank")
    private String couponTemplateName;

    private String customerName;

    @NotNull(message = "Customer phone number is mandatory")
    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "Customer phone number is Invalid")
    private String customerPhoneNo;

    @Email(message = "Customer email is Invalid")
    private String customerEmail;

}
