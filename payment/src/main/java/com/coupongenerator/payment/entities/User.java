package com.coupongenerator.payment.entities;

import com.coupongenerator.payment.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseModel {

    @Column(nullable = false, unique = true)
    private String userName;

    private String password;

    @Column(nullable = false)
    private String businessName;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private Date expireDate;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "currentPlan")
    private PlanDetails currentPlan;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "nextPlan")
    private PlanDetails nextPlan;
}
