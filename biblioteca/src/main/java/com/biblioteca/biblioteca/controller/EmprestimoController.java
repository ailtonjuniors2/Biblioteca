package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.dto.EmprestimoDTO;
import com.biblioteca.biblioteca.model.Emprestimo;
import com.biblioteca.biblioteca.service.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emprestimos")

public class EmprestimoController {
    @Autowired
    private EmprestimoService emprestimoService;

    @PostMapping
    public ResponseEntity<Emprestimo> criar(@RequestBody EmprestimoDTO dto) {
        Emprestimo e = emprestimoService.criarEmprestimo(dto.livroId(), dto.usuarioId());
        return ResponseEntity.ok(e);
    }
    @PostMapping("/{id}/devolver")
    public ResponseEntity<Emprestimo> devolver(@PathVariable Long id) {
        Emprestimo e = emprestimoService.devolver(id);
        return ResponseEntity.ok(e);
    }
}
