package com.biblioteca.biblioteca.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Usuário é obrigatório")
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @NotNull(message = "Bibliotecário é obrigatório")
    @ManyToOne
    @JoinColumn(name = "bibliotecario_id", nullable = false)
    private Bibliotecario bibliotecario;

    @NotNull(message = "Livro é obrigatório")
    @ManyToOne
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro;

    @NotNull(message = "Data de empréstimo é obrigatória")
    private LocalDate dataEmprestimo;

    private LocalDate dataDevolucao;
}
