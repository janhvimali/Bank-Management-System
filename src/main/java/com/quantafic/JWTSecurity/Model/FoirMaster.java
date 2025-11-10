package com.quantafic.JWTSecurity.Model;


import com.quantafic.JWTSecurity.Enum.RiskLevel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "foir_master")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoirMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "lower limit is required")
    @Column(name = "lower_limit" , nullable = false)
    private BigDecimal lowerLimit;

    @NotNull(message = "upper limit is required")
    @Column(name = "upper_limit" , nullable = false)
    private BigDecimal upperLimit;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_level" , nullable = false)
    private RiskLevel riskLevel;

    @NotNull(message = "System points is required")
    @Column(name = "system_point" , nullable = false)
    private BigDecimal sysPoints;

    private Instant createdAt;

    private Instant updatedAt;

    // Pre-persist initialization of createdAt and updatedAt (if needed)
    @PrePersist
    public void prePersist() {
        Instant now = Instant.now();
        if (createdAt == null) {
            createdAt = now;
        }
        if (updatedAt == null) {
            updatedAt = now;
        }
    }

}
