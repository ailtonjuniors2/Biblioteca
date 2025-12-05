package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.model.Livro;
import com.biblioteca.biblioteca.service.LivroService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService livroService;

    @PostMapping("/{isbn}")
    public Livro criarLivro(@PathVariable String isbn) {
        return livroService.criarLivro(isbn);
    }

    @GetMapping
    public List<Livro> listar() {
        return livroService.listarTodos();
    }

    @GetMapping("/{isbn}")
    public Livro buscar(@PathVariable String isbn) {
        return livroService.buscarPorISBN(isbn);
    }

    @DeleteMapping("/{isbn}")
    public String deletar(@PathVariable String isbn) {
        livroService.deletar(isbn);
        return "Livro removido.";
    }

    @PutMapping("/{isbn}/status")
    public String atualizarStatus(
            @PathVariable String isbn,
            @RequestParam boolean disponivel
    ) {
        livroService.atualizarDisponibilidade(isbn, disponivel);
        return "Status atualizado com sucesso.";
    }
}
