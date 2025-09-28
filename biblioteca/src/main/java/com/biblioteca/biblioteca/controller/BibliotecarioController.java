package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.dto.BibliotecarioDTO;
import com.biblioteca.biblioteca.dto.BibliotecarioRequestDTO;
import com.biblioteca.biblioteca.service.BibliotecarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bibliotecarios")
@RequiredArgsConstructor
public class BibliotecarioController {

    private final BibliotecarioService bibliotecarioService;

    @PostMapping
    public ResponseEntity<BibliotecarioDTO> criar(@Valid @RequestBody BibliotecarioRequestDTO request) {
        return ResponseEntity.ok(bibliotecarioService.criarBibliotecario(request));
    }

    @GetMapping
    public ResponseEntity<List<BibliotecarioDTO>> listar() {
        return ResponseEntity.ok(bibliotecarioService.listarBibliotecarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BibliotecarioDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(bibliotecarioService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BibliotecarioDTO> atualizar(@PathVariable Long id,
                                                      @Valid @RequestBody BibliotecarioRequestDTO request) {
        return ResponseEntity.ok(bibliotecarioService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        bibliotecarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}