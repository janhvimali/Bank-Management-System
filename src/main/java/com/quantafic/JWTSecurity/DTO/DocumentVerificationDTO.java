package com.quantafic.JWTSecurity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DocumentVerificationDTO {
    private boolean success;
    private String message;
    private String error;
    private DataDTO data;


    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class DataDTO{
        private boolean verified;
    }
}
