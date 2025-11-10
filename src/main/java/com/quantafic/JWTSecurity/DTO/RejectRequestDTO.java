package com.quantafic.JWTSecurity.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class RejectRequestDTO {
    @NotBlank(message = "Reason is required")
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }
}
