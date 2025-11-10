package com.quantafic.JWTSecurity.Model;

import com.quantafic.JWTSecurity.Enum.LoanProduct;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;


@Entity
@Table(name = "loan_product_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LoanProductMaster {

    @Id
    @Column(name = "loan_product_is")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanProductId;

    @NotNull(message = "Loan Product is Required")
    @Enumerated(EnumType.STRING)
    @Column(name = "loan_product")
    private LoanProduct loanProduct;

    @NotBlank(message = "Product name is Required")
    @Column(name = "product_name")
    private String productName;

    @NotNull(message = "Interest Rate is Required")
    @Column(name = "interest_rate")
    private BigDecimal interestRate;

    @NotNull(message = "Minimum loan Amount is required")
    @Column(name = "min_loan_amount")
    private BigDecimal minLoanAmount;

    @NotNull(message = "Maximum loan Amount is required")
    @Column(name = "max_loan_amount")
    private BigDecimal maxLoanAmount;

    @NotNull(message = "Minimum loan tenure is required")
    @Column(name = "min_tenure")
    private Integer minTenure;


    @Column(name = "insurance")
    private BigDecimal insurance;

    @NotNull(message = "Loan processing fees is required")
    @Column(name = "processing_fees")
    private BigDecimal processingFees;

    @NotNull(message = "Maximum loan tenure is required")
    @Column(name = "max_tenure")
    private Integer maxTenure;


    @Column(name = "description")
    private String description; // Optional product description

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
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
