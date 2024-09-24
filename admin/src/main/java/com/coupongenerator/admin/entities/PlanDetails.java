package com.coupongenerator.admin.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanDetails extends BaseModel{

    private String planName;

    private int amount;

    private int months;
}
