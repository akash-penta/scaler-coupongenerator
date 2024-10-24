package com.coupongenerator.user.dtos;

import com.coupongenerator.user.entities.Coupon;
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
public class CouponResponseDto {
    private UUID id;
    private Date createdAt;
    private Date modifiedAt;
    private UserDto createdBy;

    private String couponTemplateName;
    private String couponTemplateDescription;
    private Date couponTemplateStartDate;
    private Date couponTemplateEndDate;
    private int couponTemplateAmount;

    private String couponStatus;

    private String customerName;
    private String customerPhoneNo;
    private String customerEmail;

    public static CouponResponseDto from(Coupon coupon) {
        return builder()
                .id(coupon.getId())
                .createdAt(coupon.getCreatedAt())
                .modifiedAt(coupon.getModifiedAt())
                .createdBy(UserDto.from(coupon.getCreatedBy()))
                .couponTemplateName(coupon.getCouponTemplate().getName())
                .couponTemplateDescription(coupon.getCouponTemplate().getDescription())
                .couponTemplateStartDate(coupon.getCouponTemplate().getStartDate())
                .couponTemplateEndDate(coupon.getCouponTemplate().getEndDate())
                .couponTemplateAmount(coupon.getCouponTemplate().getAmount())
                .couponStatus(coupon.getCouponStatus().name())
                .customerName(coupon.getCustomer().getName())
                .customerPhoneNo(coupon.getCustomer().getPhoneNo())
                .customerEmail(coupon.getCustomer().getEmail())
                .build();
    }
}
