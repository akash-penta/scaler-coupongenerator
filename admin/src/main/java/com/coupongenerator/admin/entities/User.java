package com.coupongenerator.admin.entities;

import com.coupongenerator.admin.enums.UserStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseModel {

    private String userName;

    private String password;

    private String businessName;

    private UserStatus status;

    private Date expireDate;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "plan")
    private PlanDetails currentPlan;
}
