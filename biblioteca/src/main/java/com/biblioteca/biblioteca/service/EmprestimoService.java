package com.biblioteca.biblioteca.service;

import com.biblioteca.biblioteca.exception.RegraNegocioException;
import com.biblioteca.biblioteca.exception.ResourceNotFoundException;
import com.biblioteca.biblioteca.model.Emprestimo;
import com.biblioteca.biblioteca.model.Livro;
import com.biblioteca.biblioteca.model.StatusEmprestimo;
import com.biblioteca.biblioteca.model.Usuario;
import com.biblioteca.biblioteca.repository.EmprestimoRepository;
import com.biblioteca.biblioteca.repository.LivroRepository;
import com.biblioteca.biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EmprestimoService {
    @Autowired
    private EmprestimoRepository emprestimoRepository;
    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Emprestimo criarEmprestimo(Long livroId, Long usuarioId) {
        Livro livro = livroRepository.findById(livroId)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado"));
        if (emprestimoRepository.existsByLivroAndStatus(livro, StatusEmprestimo.EMPRESTADO)) {
            throw new RegraNegocioException("Livro já emprestado");
        }
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        Emprestimo emp = new Emprestimo();
        emp.setLivro(livro);
        emp.setUsuario(usuario);
        emp.setDataEmprestimo(LocalDate.now());
        emp.setDataDevolucao(LocalDate.now().plusDays(7));
        emp.setStatus(StatusEmprestimo.EMPRESTADO);
        return emprestimoRepository.save(emp);
    }
    // Devolução
    public Emprestimo devolver(Long id) {
        Emprestimo emp = emprestimoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empréstimo não encontrado"));
        if (LocalDate.now().isAfter(emp.getDataDevolucao())) {
            emp.setStatus(StatusEmprestimo.ATRASADO);
        } else {
            emp.setStatus(StatusEmprestimo.DEVOLVIDO);
        }
        return emprestimoRepository.save(emp);
    }
}