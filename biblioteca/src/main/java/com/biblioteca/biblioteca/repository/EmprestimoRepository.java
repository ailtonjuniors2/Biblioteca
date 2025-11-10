package com.biblioteca.biblioteca.repository;

import com.biblioteca.biblioteca.model.Emprestimo;
import com.biblioteca.biblioteca.model.Livro;
import com.biblioteca.biblioteca.model.StatusEmprestimo;
import com.biblioteca.biblioteca.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    boolean existsByLivroAndStatus(Livro livro, StatusEmprestimo status);
    List<Emprestimo> findByUsuario(Usuario usuario);
}
