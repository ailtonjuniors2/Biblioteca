package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.model.Livro;
import com.biblioteca.biblioteca.service.LivroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    // Criar livro
    @PostMapping
    public ResponseEntity<Livro> criarLivro(@RequestBody Livro livro) {
        Livro novo = livroService.criarLivro(livro);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    // Atualizar livro
    @PutMapping("/{issn}")
    public ResponseEntity<Livro> atualizarLivro(@PathVariable String issn, @RequestBody Livro atualizado) {
        Livro atualizadoFinal = livroService.atualizarLivro(issn, atualizado);
        return ResponseEntity.ok(atualizadoFinal);
    }

    // Deletar livro
    @DeleteMapping("/{issn}")
    public ResponseEntity<Void> deletarLivro(@PathVariable String issn) {
        livroService.deletarLivro(issn);
        return ResponseEntity.noContent().build();
    }

    // Buscar livro por ISSN
    @GetMapping("/{issn}")
    public ResponseEntity<Livro> buscarPorIssn(@PathVariable String issn) {
        Livro livro = livroService.buscarPorIssn(issn);
        return ResponseEntity.ok(livro);
    }

    // Buscar por título e/ou autor
    @GetMapping
    public ResponseEntity<List<Livro>> buscarPorTituloOuAutor(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String autor) {
        List<Livro> lista = livroService.buscarPorTituloOuAutor(
                Optional.ofNullable(titulo), Optional.ofNullable(autor));
        return ResponseEntity.ok(lista);
    }

    // Listar todos
    @GetMapping("/todos")
    public ResponseEntity<List<Livro>> listarTodos() {
        return ResponseEntity.ok(livroService.listarTodos());
    }
}
