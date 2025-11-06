package com.biblioteca.biblioteca.exception;

public class ApiExternalException extends RuntimeException {
    public ApiExternalException(String message, Throwable cause) {
        super(message, cause);
    }
    public ApiExternalException(String message) { super(message); }
}