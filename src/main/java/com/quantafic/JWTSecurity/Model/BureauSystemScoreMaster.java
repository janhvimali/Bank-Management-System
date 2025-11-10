package com.quantafic.JWTSecurity.Model;


import com.quantafic.JWTSecurity.Enum.RiskLevel;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "bureau_system_score_master")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BureauSystemScoreMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lower_limit" , nullable = false)
    private Integer lowerLimit;

    @Column(name = "upper_limit" , nullable = false)
    private Integer upperLimit;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_level" , nullable = false)
    private RiskLevel riskLevel;

    @Column(name = "system_point" , nullable = false)
    private BigDecimal sysPoints;

}

