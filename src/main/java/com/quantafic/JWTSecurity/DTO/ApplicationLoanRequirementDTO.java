package com.quantafic.JWTSecurity.DTO;

import com.quantafic.JWTSecurity.Enum.LoanProduct;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ApplicationLoanRequirementDTO {

    private LoanProduct loanProduct;

    @NotNull(message = "loan amount required")
    private BigDecimal requestedAmount;

    @NotNull(message = "loan tenure required")
    private Integer requestedTenure;

    @NotBlank(message = "loan purpose required")
    private String loanPurpose;

    public ApplicationLoanRequirementDTO(LoanProduct loanProduct, BigDecimal requestedAmount, int requestedTenure, String loanPurpose) {
        this.loanProduct = loanProduct;
        this.requestedAmount = requestedAmount;
        this.requestedTenure = requestedTenure;
        this.loanPurpose = loanPurpose;
    }

    public LoanProduct getLoanProduct() {
        return loanProduct;
    }

    public BigDecimal getRequestedAmount() {
        return requestedAmount;
    }

    public Integer getRequestedTenure() {
        return requestedTenure;
    }

    public String getLoanPurpose() {
        return loanPurpose;
    }

    @Override
    public String toString() {
        return "ApplicationLoanRequirementDTO{" +
                ", loanProduct=" + loanProduct +
                ", requestedAmount=" + requestedAmount +
                ", requestedTenure=" + requestedTenure +
                ", loanPurpose='" + loanPurpose + '\'' +
                '}';
    }
}
