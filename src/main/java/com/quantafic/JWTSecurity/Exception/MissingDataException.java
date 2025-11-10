package com.quantafic.JWTSecurity.Exception;

public class MissingDataException extends RuntimeException{
    public MissingDataException(String message) {
        super(message);
    }
}
