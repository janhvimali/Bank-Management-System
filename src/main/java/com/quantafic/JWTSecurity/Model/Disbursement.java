package com.quantafic.JWTSecurity.Model;

import com.quantafic.JWTSecurity.Enum.Mode;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "disbursement")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Disbursement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "disbursement_id", nullable = false)
    private Long disbursementId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    @Column(name = "disbursement_amount" , nullable = false)
    private BigDecimal disbursementAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "disbursement_mode", nullable = false)
    private Mode disbursementMode;

    @Column(name = "beneficiary_name", nullable = false)
    private String beneficiaryName;

    @Column(name = "bank_account_number", nullable = false)
    private String bankAccountNumber;

    @Column(name = "ifsc", nullable = false)
    private String ifsc;

    @Builder.Default
    @Column(name = "disbursal_date", nullable = false, updatable = false)
    private Instant disbursalDate = Instant.now();
}

