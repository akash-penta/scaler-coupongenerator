package com.coupongenerator.user.entities;

import com.coupongenerator.user.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseModel implements UserDetails {

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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "createdBy", fetch = FetchType.LAZY)
    private List<CouponTemplate> couponTemplates;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

}
