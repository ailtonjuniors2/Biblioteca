package com.biblioteca.biblioteca.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LivroDTO {
    private Long id;
    private String isbn;
    private String titulo;
    private String autor;
    private String anoPublicacao;
    private int numeroPaginas;
}

