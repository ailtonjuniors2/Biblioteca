package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.dto.UsuarioDTO;
import com.biblioteca.biblioteca.dto.UsuarioRequestDTO;
import com.biblioteca.biblioteca.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDTO> criar(@Valid @RequestBody UsuarioRequestDTO request) {
        return ResponseEntity.ok(usuarioService.criarUsuario(request));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listar() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable Long id,
                                                @Valid @RequestBody UsuarioRequestDTO request) {
        return ResponseEntity.ok(usuarioService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
