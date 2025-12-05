package com.biblioteca.biblioteca.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("BIBLIOTECARIO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Bibliotecario extends Usuario {

    @NotBlank(message = "Matrícula é obrigatória")
    @Column(nullable = false, unique = true)
    @Pattern(regexp = "\\d+", message = "Matrícula deve conter apenas números")
    @Size(min = 6, max = 6, message = "A matrícula deve conter 6 números")
    private String matricula;
}
