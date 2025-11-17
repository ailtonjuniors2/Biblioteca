package com.biblioteca.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LivroRequestDTO {

    @NotBlank(message = "ISBN é obrigatório")
    @Pattern(
            regexp = "^(97(8|9))?\\d{9}(\\d|X)$|^\\d{9}(\\d|X)$",
            message = "ISBN deve ser válido"
    )
    private String isbn;

    @NotBlank(message = "Autor é obrigatório")
    private String autor;
}
