package com.biblioteca.biblioteca.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class EmprestimoDTO {

    private Long id;
    private String isbn;
    private String titulo;
    private String usuarioNome;
    private String bibliotecarioNome;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucaoPrevista;
    private LocalDate dataDevolucaoReal;
    private String status;
}
