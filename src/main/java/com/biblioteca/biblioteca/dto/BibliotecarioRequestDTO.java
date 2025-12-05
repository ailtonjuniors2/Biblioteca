package com.biblioteca.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BibliotecarioRequestDTO extends UsuarioRequestDTO{
    @NotBlank(message = "A matrícula é obrigatória")
    private String matricula;
}