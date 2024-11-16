package com.coupongenerator.user.entities;

import com.coupongenerator.user.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetails extends BaseModel {

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "plan")
    private PlanDetails planDetails;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
}
