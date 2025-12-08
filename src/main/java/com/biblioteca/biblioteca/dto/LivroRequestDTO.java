package com.biblioteca.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LivroRequestDTO {

    @NotBlank
    private String autor;

    @NotBlank
    @Pattern(regexp = "\\d{13}", message = "ISBN deve conter 13 dígitos")
    private String isbn;
}


