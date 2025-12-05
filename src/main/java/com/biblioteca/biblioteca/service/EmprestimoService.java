package com.biblioteca.biblioteca.service;

import com.biblioteca.biblioteca.dto.EmprestimoDTO;
import com.biblioteca.biblioteca.dto.EmprestimoRequestDTO;
import com.biblioteca.biblioteca.model.*;
import com.biblioteca.biblioteca.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final LivroRepository livroRepository;
    private final UsuarioRepository usuarioRepository;
    private final BibliotecarioRepository bibliotecarioRepository;

    public EmprestimoDTO criarEmprestimo(EmprestimoRequestDTO req) {

        Livro livro = livroRepository.findById(req.getIsbn())
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        if (!livro.isDisponivel()) {
            throw new RuntimeException("Livro está indisponível");
        }

        Usuario usuario = usuarioRepository.findById(req.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Bibliotecario bibliotecario = bibliotecarioRepository.findById(req.getBibliotecarioId())
                .orElseThrow(() -> new RuntimeException("Bibliotecário não encontrado"));

        Emprestimo emp = Emprestimo.builder()
                .livro(livro)
                .usuario(usuario)
                .bibliotecario(bibliotecario)
                .dataEmprestimo(LocalDate.now())
                .dataDevolucaoPrevista(LocalDate.now().plusDays(7))
                .status(StatusEmprestimo.ATIVO)
                .build();

        // Livro fica indisponível
        livro.setDisponivel(false);
        livroRepository.save(livro);

        Emprestimo salvo = emprestimoRepository.save(emp);

        return toDTO(salvo);
    }

    public EmprestimoDTO devolver(Long id) {
        Emprestimo emp = emprestimoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));

        emp.setDataDevolucaoReal(LocalDate.now());
        emp.setStatus(StatusEmprestimo.DEVOLVIDO);

        // livro volta a ficar disponível
        emp.getLivro().setDisponivel(true);
        livroRepository.save(emp.getLivro());

        emprestimoRepository.save(emp);

        return toDTO(emp);
    }

    private EmprestimoDTO toDTO(Emprestimo e) {
        return EmprestimoDTO.builder()
                .id(e.getId())
                .isbn(e.getLivro().getIsbn())
                .titulo(e.getLivro().getTitulo())
                .usuarioNome(e.getUsuario().getNome())
                .bibliotecarioNome(e.getBibliotecario().getNome())
                .dataEmprestimo(e.getDataEmprestimo())
                .dataDevolucaoPrevista(e.getDataDevolucaoPrevista())
                .dataDevolucaoReal(e.getDataDevolucaoReal())
                .status(e.getStatus().name())
                .build();
    }
}
