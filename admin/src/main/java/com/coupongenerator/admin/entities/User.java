package com.coupongenerator.admin.entities;

import com.coupongenerator.admin.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    @JoinColumn(name = "plan")
    private PlanDetails currentPlan;
}
