package com.cura.base;

public class FrameworkException extends RuntimeException {
    public FrameworkException(String message, Throwable cause) {
        super(message, cause);
    }

    public FrameworkException(String message) {
        super(message);
    }
}
