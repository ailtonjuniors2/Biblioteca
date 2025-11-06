package com.biblioteca.biblioteca.exception;

public class LivroNotFoundException extends RuntimeException {
    public LivroNotFoundException(String issn) {
        super("Livro não encontrado com ISSN: " + issn);
    }
}