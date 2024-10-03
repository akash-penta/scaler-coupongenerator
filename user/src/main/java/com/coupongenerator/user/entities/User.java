package com.coupongenerator.user.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
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

    private com.coupongenerator.user.enums.UserStatus status;

    private Date expireDate;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "plan")
    private PlanDetails currentPlan;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.userName;
    }
}
