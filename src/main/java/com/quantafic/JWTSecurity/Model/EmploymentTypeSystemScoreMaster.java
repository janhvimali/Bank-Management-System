package com.quantafic.JWTSecurity.Model;

import com.quantafic.JWTSecurity.Enum.RiskLevel;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "employeement_type_system_score_master")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmploymentTypeSystemScoreMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Enumerated(EnumType.STRING)
    @Column(name = "employment_type", nullable = false)
    private String employmentType;

    @Column(name = "system_point" , nullable = false)
    private BigDecimal sysPoints;

}
