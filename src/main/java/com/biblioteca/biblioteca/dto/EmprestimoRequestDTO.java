package com.biblioteca.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmprestimoRequestDTO {

    @NotBlank(message = "ISBN é obrigatório")
    private String isbn;

    @NotNull(message = "ID do usuário é obrigatório")
    private Long usuarioId;

    @NotNull(message = "ID do bibliotecário é obrigatório")
    private Long bibliotecarioId;
}
