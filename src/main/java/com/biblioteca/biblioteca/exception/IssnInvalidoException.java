package com.biblioteca.biblioteca.exception;

public class IssnInvalidoException extends RuntimeException {
    public IssnInvalidoException(String issn) {
        super("ISSN inválido ou não registrado na API externa: " + issn);
    }
}