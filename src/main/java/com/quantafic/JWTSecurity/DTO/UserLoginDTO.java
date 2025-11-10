package com.quantafic.JWTSecurity.DTO;


import jakarta.validation.constraints.NotBlank;

public class UserLoginDTO {
    private String staffId;
    private String password;

    @NotBlank(message = "Staff Id Required")
    public String getStaffId() {
        return staffId;
    }

    @NotBlank(message = "Password Required")
    public String getPassword() {
        return password;
    }
}
