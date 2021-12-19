package com.aimtupsu.normsecretsanta.exception;

public class SecretSantaRuntimeException extends RuntimeException {

    public SecretSantaRuntimeException(String message) {
        super(message);
    }

    public SecretSantaRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

}