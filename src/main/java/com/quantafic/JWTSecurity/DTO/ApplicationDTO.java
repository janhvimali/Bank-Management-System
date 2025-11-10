package com.quantafic.JWTSecurity.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.quantafic.JWTSecurity.Enum.ApplicationStatus;
import com.quantafic.JWTSecurity.Enum.LoanProduct;
import com.quantafic.JWTSecurity.Model.Customers;
import com.quantafic.JWTSecurity.Model.LoanOffers;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationDTO {
    private String applicationId;
    private LoanProduct loanProduct;
    private BigDecimal requestedAmount;
    private int requestedTenure;
    private String loanPurpose;
    private Integer preferredEmiDate;
    private BigDecimal grossMonthlyIncome;
    private BigDecimal netMonthlyIncome;
    private BigDecimal existingEmiObligation;
    private ApplicationStatus status;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private LoanOffers loanOffers;
    private String branchCode;
    private String customerId;
    private String bureauId;
}
