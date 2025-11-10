package com.quantafic.JWTSecurity.DTO;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class EditLoanOfferDTO {
    private BigDecimal loanPrincipal;
    private int loanTenure;

    @NotNull(message = "loan amount required")
    public BigDecimal getLoanPrincipal() {
        return loanPrincipal;
    }

    @NotNull(message = "loan tenure required")
    public int getLoanTenure() {
        return loanTenure;
    }
}
