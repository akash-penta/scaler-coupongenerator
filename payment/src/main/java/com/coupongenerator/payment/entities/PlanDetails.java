package com.coupongenerator.payment.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanDetails extends BaseModel{

    @Column(nullable = false, unique = true)
    private String planName;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private int months;
}
