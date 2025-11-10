package com.quantafic.JWTSecurity.DTO;

public class AESWrapper<T> {
    private String encryptReq;
    private String div;
    private String sourceSys;

    public String getEncryptReq() {
        return encryptReq;
    }

    public void setEncryptReq(String encryptReq) {
        this.encryptReq = encryptReq;
    }

    public String getDiv() {
        return div;
    }

    public void setDiv(String div) {
        this.div = div;
    }

    public String getSourceSys() {
        return sourceSys;
    }

    public void setSourceSys(String sourceSys) {
        this.sourceSys = sourceSys;
    }
}

