package com.coupongenerator.user.enums;

public enum CouponStatusActionRequest {
    ACTIVE,
    BLOCK,
    USE;

    public static boolean contains(String action) {
        for(CouponStatusActionRequest couponStatusActionRequest: CouponStatusActionRequest.values()) {
            if(couponStatusActionRequest.name().equals(action.toUpperCase())) {
                return true;
            }
        }
        return false;
    }
}
