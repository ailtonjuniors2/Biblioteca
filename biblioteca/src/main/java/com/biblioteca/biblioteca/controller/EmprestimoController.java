package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.dto.EmprestimoDTO;
import com.biblioteca.biblioteca.dto.EmprestimoRequestDTO;
import com.biblioteca.biblioteca.service.EmprestimoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emprestimos")
@RequiredArgsConstructor
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    @PostMapping
    public EmprestimoDTO criar(@RequestBody EmprestimoRequestDTO req) {
        return emprestimoService.criarEmprestimo(req);
    }

    @PutMapping("/{id}/devolver")
    public EmprestimoDTO devolver(@PathVariable Long id) {
        return emprestimoService.devolver(id);
    }
}
