package com.quantafic.JWTSecurity.DTO;

import com.quantafic.JWTSecurity.Enum.Mode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class DisbursementDTO {
    private String bankAccountNo;
    private String ifsc;
    private String bankName;
    private String branchName;
    private Mode mode;
    private String beneficiaryName;

    @NotBlank(message = "Bank Account number is required")
    public String getBankAccountNo() {
        return bankAccountNo;
    }

    @NotBlank(message = "IFSC code is required")
    public String getIfsc() {
        return ifsc;
    }

    @NotBlank(message = "Bank name is required")
    public String getBankName() {
        return bankName;
    }

    @NotBlank(message = "Branch name is required")
    public String getBranchName() {
        return branchName;
    }


    @NotNull(message = "Mode of payment is required")
    public Mode getMode() {
        return mode;
    }

    @NotBlank(message = "Beneficiary name is required")
    public String getBeneficiaryName() {
        return beneficiaryName;
    }


}
