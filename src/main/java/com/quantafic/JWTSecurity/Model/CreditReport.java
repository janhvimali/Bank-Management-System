package com.quantafic.JWTSecurity.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.quantafic.JWTSecurity.Enum.SystemDecision;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.Instant;


@Entity
@Table(name = "credit_reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CreditReport {

    @Id
    @GeneratedValue(generator = "creditReport-id-gen")
    @GenericGenerator(
            name="creditReport-id-gen",
            strategy = "com.quantafic.JWTSecurity.Model.Generator.SystemScoreIdGenerator"
    )
    @Column(name = "creditReport_id", nullable = false)
    private String creditReportId;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "application_id", nullable = false)
//    @JsonIgnore
//    private Application application;


    @Column(name = "application_id", nullable = false)
    private String applicationId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bureau_id", nullable = false) // FK column in credit_reports table
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Bureau bureau;


    @Column(name = "bureau_score", nullable = false)
    private Integer BureauScore;

    @Column(name = "foir", nullable = false, precision = 5, scale = 2)
    private BigDecimal foir;

    @Column(name = "dti", precision = 5, scale = 2)
    private BigDecimal dti;

    @Column(name = "system_score", precision = 5, scale = 2)
    private BigDecimal systemScore;

    @Enumerated(EnumType.STRING)
    @Column(name = "system_decision")
    private SystemDecision systemDecision;

    @Column(name = "calculated_at")
    private Instant calculatedAt;
}
