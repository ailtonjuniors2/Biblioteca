package com.biblioteca.biblioteca.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Livro {

    @Id
    @Pattern(
            regexp = "^(97(8|9))?\\d{9}(\\d|X)$|^\\d{9}(\\d|X)$",
            message = "ISBN deve ser válido (ISBN-10 ou ISBN-13)"
    )
    @Column(nullable = false, unique = true)
    private String isbn;

    @NotBlank(message = "Título é obrigatório")
    private String titulo;

    @NotBlank(message = "Autor é obrigatório")
    private String autor;

    private String anoPublicacao;

    private Integer numeroPaginas;

    private boolean disponivel = true;
}

