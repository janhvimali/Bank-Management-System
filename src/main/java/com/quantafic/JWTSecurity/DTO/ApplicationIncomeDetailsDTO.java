package com.quantafic.JWTSecurity.DTO;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ApplicationIncomeDetailsDTO {
    private BigDecimal grossMonthlyIncome;
    private BigDecimal netMonthlyIncome;
    private BigDecimal existingEmiObligation;

    @NotNull(message = "loan amount required. if not put zero")
    public BigDecimal getExistingEmiObligation() {
        return existingEmiObligation;
    }

    @NotNull(message = "Gross Monthly Income is required")
    public BigDecimal getGrossMonthlyIncome() {
        return grossMonthlyIncome;
    }

    @NotNull(message = "Net Monthly Income is required")
    public BigDecimal getNetMonthlyIncome() {
        return netMonthlyIncome;
    }


    public void setGrossMonthlyIncome(BigDecimal grossMonthlyIncome) {
        this.grossMonthlyIncome = grossMonthlyIncome;
    }

    public void setNetMonthlyIncome(BigDecimal netMonthlyIncome) {
        this.netMonthlyIncome = netMonthlyIncome;
    }

    public void setExistingEmiObligation(BigDecimal existingEmiObligation) {
        this.existingEmiObligation = existingEmiObligation;
    }
}
