package com.coupongenerator.user.dtos;


import com.coupongenerator.user.entities.Coupon;
import com.coupongenerator.user.entities.CouponTemplate;
import com.coupongenerator.user.entities.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CouponTemplateResponseDto {
    private UUID id;
    private Date createdAt;
    private Date modifiedAt;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private int amount;
    private UserDto createdBy;
    private List<Coupon> coupons;

    public CouponTemplateResponseDto(UUID id, Date createdAt, Date modifiedAt, String name, String description, Date startDate, Date endDate, int amount, User createdBy) {
        this.id = id;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.createdBy = UserDto.from(createdBy);
    }

    public static CouponTemplateResponseDto from(CouponTemplate couponTemplate) {
        return new CouponTemplateResponseDto(
                couponTemplate.getId(),
                couponTemplate.getCreatedAt(),
                couponTemplate.getModifiedAt(),
                couponTemplate.getName(),
                couponTemplate.getDescription(),
                couponTemplate.getStartDate(),
                couponTemplate.getEndDate(),
                couponTemplate.getAmount(),
                couponTemplate.getCreatedBy()
        );
    }
}
