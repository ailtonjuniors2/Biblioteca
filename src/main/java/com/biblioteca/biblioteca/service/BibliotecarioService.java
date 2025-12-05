package com.biblioteca.biblioteca.service;

import com.biblioteca.biblioteca.dto.BibliotecarioDTO;
import com.biblioteca.biblioteca.dto.BibliotecarioRequestDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import com.biblioteca.biblioteca.model.Bibliotecario;
import org.springframework.stereotype.Service;
import com.biblioteca.biblioteca.repository.BibliotecarioRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BibliotecarioService {

    private final BibliotecarioRepository bibliotecarioRepository;

    public BibliotecarioDTO criarBibliotecario(BibliotecarioRequestDTO request) {
        if (bibliotecarioRepository.existsByMatricula(request.getMatricula())){
            throw new IllegalArgumentException("Matrícula já existente");
        }

        Bibliotecario bibliotecario = Bibliotecario.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .senha(request.getSenha())
                .matricula(request.getMatricula())
                .build();

        bibliotecarioRepository.save(bibliotecario);

        return toDTO(bibliotecario);
    }

    public List<BibliotecarioDTO> listarBibliotecarios() {
        return bibliotecarioRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public BibliotecarioDTO buscarPorId(Long id) {
        Bibliotecario bibliotecario = bibliotecarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bibliotecário não encontrado"));
        return toDTO(bibliotecario);
    }

    public BibliotecarioDTO atualizar(Long id, BibliotecarioRequestDTO request) {
        Bibliotecario bibliotecario = bibliotecarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bibliotecário não encontrado"));

        bibliotecario.setMatricula(request.getMatricula());
        bibliotecario.setNome(request.getNome());
        bibliotecario.setEmail(request.getEmail());
        bibliotecario.setSenha(request.getSenha());

        bibliotecarioRepository.save(bibliotecario);
        return toDTO(bibliotecario);
    }

    public void deletar(Long id) {
        if (!bibliotecarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Bibliotecário não encontrado");
        }
        bibliotecarioRepository.deleteById(id);
    }

    private BibliotecarioDTO toDTO(Bibliotecario bibliotecario) {
        return BibliotecarioDTO.builder()
                .id(bibliotecario.getId())
                .nome(bibliotecario.getNome())
                .email(bibliotecario.getEmail())
                .matricula(bibliotecario.getMatricula())
                .build();
    }
}
